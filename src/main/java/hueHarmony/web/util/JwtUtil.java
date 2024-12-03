package hueHarmony.web.util;
import hueHarmony.web.dto.UserAuthDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtil {

    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final int REMEMBER_ME_DEFAULT_TIME = 3600*5;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<SimpleGrantedAuthority> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return Arrays.stream(claims.get("roles").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public int extractUserId(String token) {
        return Integer.parseInt(extractAllClaims(token).get("userId", String.class));
    }

    public int extractUserIdWithToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserAuthDto){
            return ((UserAuthDto) principal).getUserId();
        }
        return 0;
//        return Integer.parseInt(extractAllClaims(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString()).get("userId", String.class));
    }

    public List<String> extractRoleWithToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserAuthDto){
            return ((UserAuthDto) principal).getRoles();
        }
        return Collections.emptyList();
//        return (List<String>) extractAllClaims(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString()).get("roles", List.class);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            // Log the error or handle it according to your application's requirements
            throw new RuntimeException("Error extracting claims from JWT token: " + e.getMessage());
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(Date.from(Instant.now()));
    }

    public static String generateToken(String username, boolean rememberMeOn, List<String> roles, int userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("userId", userId);
        return createToken(claims, username, rememberMeOn);
    }

    private static String createToken(Map<String, Object> claims, String subject, boolean rememberMeOn) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(rememberMeOn ? REMEMBER_ME_DEFAULT_TIME : 3600))) // Expiry set to 1 hour from now
                .signWith(secretKey)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && isSignatureValid(token));
    }

    private boolean isSignatureValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
