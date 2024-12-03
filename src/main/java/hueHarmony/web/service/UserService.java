package hueHarmony.web.service;

import hueHarmony.web.dto.ChangePasswordDto;
import hueHarmony.web.dto.UserDto;
import hueHarmony.web.model.enums.UserStatus;
import hueHarmony.web.repository.RoleRepository;
import hueHarmony.web.repository.UserRepository;
import hueHarmony.web.dto.UserProfileDto;
import hueHarmony.web.model.Role;
import hueHarmony.web.model.User;
import hueHarmony.web.repository.RoleRepository;
import hueHarmony.web.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final FirebaseStorageService firebaseStorageService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(FirebaseStorageService firebaseStorageService, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional
    public void createNewUser(UserDto user){
        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setFullName(user.getFullName());
        newUser.setUserStatus(UserStatus.ACTIVE);
        newUser.setRoles(user.getRoles().stream().map(role -> entityManager.getReference(Role.class, role)).toList());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(newUser);
    }

    @Transactional
    public void updateUserRoles(UserDto userDto){
        Optional<User> user = userRepository.findById((long) userDto.getUserId());

        if(user.isEmpty()) throw new IllegalArgumentException("User not found.");

        User currentUser = user.get();
        currentUser.setRoles(userDto.getRoles().stream().map(role -> entityManager.getReference(Role.class, role)).toList());

        userRepository.save(currentUser);
    }

    @Transactional
    public User signupNewUser(UserDto user){
        Optional<Role> optionalRole = roleRepository.findByRoleName("USER");
        if(optionalRole.isEmpty()) throw new RuntimeException("Role user is not found.");

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setUserStatus(UserStatus.ACTIVE);
        newUser.setUsername(user.getUsername());
        Role role = optionalRole.get();
        newUser.getRoles().add(role);

        return userRepository.save(newUser);
    }

    @Transactional
    public void changePassword(ChangePasswordDto passwordDto){
        userRepository.updatePasswordByUserIdAndNewPassword(passwordDto.getUserId(), passwordDto.getNewPassword());
    }

    @Transactional
    public void updateStatus(UserDto userDto){
        userRepository.updateUserStatusByUserIdAndUserStatus(userDto.getUserId(), userDto.getUserStatus());
    }

    @Transactional
    public void deleteUser(UserDto userDto){
        userRepository.deleteById((long) userDto.getUserId());
    }

    @Transactional(readOnly=true)
    public boolean isUsernameExists(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean isUserHavePermission(int accessedUser, String userId){
        return accessedUser==Integer.parseInt(userId);
    }

    @Transactional(readOnly=true)
    public boolean isRoleExist(int roleId){
        return roleRepository.existsById((long) roleId);
    }

    @Transactional(readOnly=true)
    public boolean isUserExist(int userId){
        return userRepository.existsById((long) userId);
    }

    @Transactional(readOnly=true)
    public boolean isEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly=true)
    public boolean isPasswordSame(String password, int userId){
        return userRepository.checkPasswordMatchByOldPasswordAndUserId(password, userId);
    }

    public UserProfileDto getUserProfile(String username){
        UserProfileDto userProfileDto = userRepository.getUserProfileByUsername(username);
        if(userProfileDto.getAvatar()!=null && !userProfileDto.getAvatar().isEmpty()){
            userProfileDto.setAvatar(
                    firebaseStorageService.getFileDownloadUrl(userProfileDto.getAvatar(), 1, TimeUnit.DAYS)
            );
        }
        return userProfileDto;
    }

    @Transactional
    public void updateUser(UserDto userDto){
        Optional<User> user = userRepository.findById((long) userDto.getUserId());

        if(user.isEmpty()) throw new IllegalArgumentException("User not found.");

        User currentUser = user.get();

        currentUser.setEmail(userDto.getEmail());
        currentUser.setUsername(userDto.getUsername());
        currentUser.setFullName(userDto.getFullName());

        String newImage = "";
        try {
            if (userDto.getProfileImage() != null){
                newImage = firebaseStorageService.uploadFile("", userDto.getProfileImage().getBytes(), "png");
                currentUser.setProfileImage(newImage);
            }

            userRepository.save(currentUser);
        } catch (Exception e) {
            if(!newImage.isEmpty()) firebaseStorageService.deleteFile(currentUser.getProfileImage());
            throw new RuntimeException("An unexpected error occurred while updating user profile.");
        }
    }

}
