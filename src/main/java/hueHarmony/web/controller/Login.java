package hueHarmony.web.controller;

import hueHarmony.web.dto.UserProfileDto;
import hueHarmony.web.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import hueHarmony.web.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class Login {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
//    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/validate")
    public ResponseEntity<Object> generateToken(@RequestBody JsonNode data) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(data.get("username").asText(), data.get("password").asText())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserProfileDto userProfileDto = userService.getUserProfile(userDetails.getUsername());
            userProfileDto.setUsername(userDetails.getUsername());
            List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            Map<String, Object> payload = new HashMap<>();
            payload.put("user", userProfileDto);
            payload.put("token", JwtUtil.generateToken(data.get("username").asText(), (data.has("rememberMe") && data.get("rememberMe").asBoolean()), userRoles, userProfileDto.getUserId()));
            payload.put("role", userRoles.get(0).substring(5));
            return ResponseEntity.ok(payload);
        } catch (AuthenticationException exception) {
            return ResponseEntity.badRequest().body("Invalid username or password entered.");
        }
    }

    @GetMapping("/view")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String view(){
        return "Hello";
    }

    @GetMapping("/profile/view")
    public ResponseEntity<Object> getProfile(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                String token = (String) authentication.getCredentials();
                String username = authentication.getName();
                String role = authentication.getAuthorities().stream().map(authority->authority.getAuthority().substring(5)).collect(Collectors.joining(","));

                UserProfileDto userProfileDto = userService.getUserProfile(username);
                if (userProfileDto.getUserId() != -1) {
                    Map<String, Object> result = new HashMap<>();
                    userProfileDto.setUsername(username);
                    result.put("user", userProfileDto);
                    result.put("role", role);
                    result.put("token", token);
                    return ResponseEntity.status(HttpStatus.OK).body(result);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error!!! Please try again later...");
        }

    }
}
