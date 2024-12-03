package hueHarmony.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//@Data
//@NoArgsConstructor
public class UserAuthDto implements UserDetails {

    @Getter
    private final int userId;

    @Setter
    private String username;

    private final String password;

    private final List<? extends GrantedAuthority> authorities;

    public UserAuthDto(int userid, String password, Object roles) {
        this.userId = userid;
        this.password = password;
//        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_"+roles));
        this.authorities = Arrays.stream(roles.toString().split(",")).map(role -> new SimpleGrantedAuthority("ROLE_"+role.toUpperCase())).toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public List<String> getRoles(){
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserAuthDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

//     @Override
// public Collection<? extends GrantedAuthority> getAuthorities() {
//     // Return user roles/authorities
//     return null; // Replace with actual implementation
// }

// @Override
// public String getPassword() {
//     // Return user password
//     return null; // Replace with actual implementation
// }

// @Override
// public String getUsername() {
//     // Return user username
//     return null; // Replace with actual implementation
// }

@Override
public boolean isAccountNonExpired() {
    // Return true if account is not expired
    return true; // Modify as per your logic
}

@Override
public boolean isAccountNonLocked() {
    // Return true if account is not locked
    return true; // Modify as per your logic
}

@Override
public boolean isCredentialsNonExpired() {
    // Return true if credentials are not expired
    return true; // Modify as per your logic
}

@Override
public boolean isEnabled() {
    // Return true if user is enabled
    return true; // Modify as per your logic
}

}
