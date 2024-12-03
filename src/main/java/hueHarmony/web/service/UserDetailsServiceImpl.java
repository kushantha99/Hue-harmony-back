package hueHarmony.web.service;

import hueHarmony.web.repository.UserRepository;
import hueHarmony.web.dto.UserAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthDto user = userRepository.findUserByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        user.setUsername(username);

//        return new User(
//                username,
//                user.getPassword(),
//                user.getRoles().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.toUpperCase())).collect(Collectors.toList())
//        );

        return user;
    }
}
