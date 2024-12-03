package hueHarmony.web.config;

import hueHarmony.web.service.UserDetailsServiceImpl;
import hueHarmony.web.util.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, JwtFilter jwtFilter) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/login/validate", "/customize/banners", "/users/signup", "/product/create","/product/delete","product/view/**","/product/**", "/orders/**", "/webhook", "cart/**", "supplier/**").permitAll()
                    .requestMatchers("/login/validate", "/customize/banners", "/user/signup", "product/create","purchase-order/**","grn/**").permitAll()
                    .requestMatchers("/login/validate", "/customize/banners", "/user/signup", "/product/create","/product/delete","product/view/**","/product/**","/products","/product/update").permitAll()
                    .requestMatchers("/login/validate", "/customize/banners", "/user/signup", "/product/create","/product/delete","product/view/**","/product/**","/pos/**","/loyalty/**").permitAll()
                    .requestMatchers("/login/validate", "/customize/banners", "/user/signup", "/product/create","/product/delete","product/view/**","/product/**","/products","/product/update", "/product/read/popular").permitAll()
//                    .requestMatchers("/login/validate", "/signup/user", "/product/attribute/create", "/product/variation/create", "/category/create", "/product/attribute/view", "/product/attribute/get/**", "category/view").permitAll()
//                    .requestMatchers("/login/view").hasRole("USER")
//                    .requestMatchers("/product/create", "/product/attribute/create", "/product/variation/create").hasAnyRole("ADMIN", "MANAGER")
//                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                    .requestMatchers("/api/manager/**").hasRole("MANAGER")
                    .requestMatchers("/login/validate", "/customize/banners", "/users/signup", "/product/create","/product/delete","product/view/**","/product/**", "/orders/**", "/webhook","/customer/**","/wholeSale/**","/wholeSale/update-order-status/**","/notes/**").permitAll()
                    .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow all origins, methods, and headers
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
