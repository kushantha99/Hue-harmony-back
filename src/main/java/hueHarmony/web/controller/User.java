package hueHarmony.web.controller;

import hueHarmony.web.component.validation.ContentPermissionValidator;
import hueHarmony.web.dto.*;
import hueHarmony.web.model.Role;
import hueHarmony.web.service.UserService;
import hueHarmony.web.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class User {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    //Used for organization users to check other users profile.
    @GetMapping("/view/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> viewUser(@PathVariable("userId") int userId) {
        try{
            return ResponseEntity.status(200).body("");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }


    //Used for each user to check their profile.
    @GetMapping("/profile/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INVENTORYMANAGER', 'ROLE_BACKOFFICE', 'ROLE_SALESMANAGER', 'ROLE_USER', 'ROLE_CACHIER')")
    public ResponseEntity<Object> viewProfile(@PathVariable("userId") int userId) {
        try{
            if(userId!=jwtUtil.extractUserIdWithToken()) return ResponseEntity.status(403).body("Unauthorized to access.");

            return ResponseEntity.status(200).body("");
        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> filterUser(@Validated(FilterUserDto.whenOrganization.class) @ModelAttribute FilterUserDto request) {
        try{
            return ResponseEntity.status(200).body("");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createUser(
            @Validated(UserDto.onCreation.class) @RequestBody UserDto userDto,
            BindingResult bindingResult
    ) {
        try{
            if(userDto==null && bindingResult.hasErrors()) return ResponseEntity.status(400).body(bindingResult);

            assert userDto != null;
            userService.createNewUser(userDto);

            return ResponseEntity.status(201).body("New user created successfully.");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/assign-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> assignRole(
            @Validated(UserDto.onUpdateRole.class) @RequestBody UserDto userDto,
            BindingResult bindingResult
    ) {
        try{
            if(userDto==null && bindingResult.hasErrors()) return ResponseEntity.status(400).body(bindingResult);

            assert userDto != null;
            userService.updateUserRoles(userDto);

            return ResponseEntity.status(201).body("User roles are updated successfully.");
        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INVENTORYMANAGER', 'ROLE_BACKOFFICE', 'ROLE_SALESMANAGER', 'ROLE_USER', 'ROLE_CACHIER')")
    public ResponseEntity<Object> updateUser(
            @Validated(UserDto.onUpdate.class) @RequestBody UserDto userDto,
            BindingResult bindingResult
    ) {
        try{
            if(userDto==null && bindingResult.hasErrors()){
                if(bindingResult.getFieldErrors().stream().anyMatch(f -> Objects.equals(f.getDefaultMessage(), "Unauthorized to access."))){
                    return ResponseEntity.status(401).body("Unauthorized to access.");
                }
                return ResponseEntity.status(400).body(bindingResult);
            }

            assert userDto!= null;

            userService.updateUser(userDto);

            return ResponseEntity.status(200).body("User updated successfully.");
        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/signup")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> signup(
            @Validated(UserDto.onSignup.class) @RequestBody UserDto userDto,
            BindingResult bindingResult
    ) {
        try{
            if(userDto==null && bindingResult.hasErrors()) return ResponseEntity.status(400).body(bindingResult);

            assert userDto != null;
            hueHarmony.web.model.User newUser = userService.signupNewUser(userDto);

            UserProfileDto userProfileDto = new UserProfileDto(newUser.getUserId(), "", newUser.getUsername());

            Map<String, Object> payload = new HashMap<>();
            payload.put("user", userProfileDto);
            payload.put("token", JwtUtil.generateToken(newUser.getUsername(), false, newUser.getRoles().stream().map(Role::getRoleName).toList(), userProfileDto.getUserId()));
            payload.put("role", newUser.getRoles().get(0).getRoleName());
            payload.put("message", "User successfully created.");

            return ResponseEntity.status(201).body(payload);
        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INVENTORYMANAGER', 'ROLE_BACKOFFICE', 'ROLE_SALESMANAGER', 'ROLE_USER', 'ROLE_CACHIER')")
    public ResponseEntity<Object> changePassword(
            @Validated(ChangePasswordDto.onCheck.class) @RequestBody ChangePasswordDto changePasswordDto,
            BindingResult bindingResult
    ) {
        try{
            //Need to add 2-step verification by sending email or sms
            if(changePasswordDto==null && bindingResult.hasErrors()){
                if(bindingResult.getFieldErrors().stream().anyMatch(f -> Objects.equals(f.getDefaultMessage(), "Unauthorized to access."))){
                    return ResponseEntity.status(401).body("Unauthorized to access.");
                }
                return ResponseEntity.status(400).body(bindingResult);
            }

            assert changePasswordDto!= null;
            userService.changePassword(changePasswordDto);

            return ResponseEntity.status(200).body("Password changed successfully.");
        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteUser(
            @Validated(UserDto.class) @RequestBody UserDto userDto,
            BindingResult bindingResult
    ) {
        try{
            if(userDto==null && bindingResult.hasErrors()) return ResponseEntity.status(400).body(bindingResult);

            assert userDto!= null;
            userService.deleteUser(userDto);

            return ResponseEntity.status(200).body("User removed successfully.");
        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/enable")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> enableDisableUser(
            @Validated(UserDto.onUpdateStatus.class) @RequestBody UserDto userDto,
            BindingResult bindingResult
    ) {
        try{
            if(userDto==null && bindingResult.hasErrors()) return ResponseEntity.status(400).body(bindingResult);

            assert userDto!= null;
            userService.updateStatus(userDto);

            return ResponseEntity.status(200).body("User status changed successfully.");
        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
