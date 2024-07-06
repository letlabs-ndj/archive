package com.unesco.archive.configs;

import com.unesco.archive.model.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userDetailService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, CustomUserDetailService userDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry->{
                    registry.requestMatchers("/","/js/**","/css/**","/images/**").permitAll();
                    registry.requestMatchers("/admin").hasRole("ADMIN");
                    registry.requestMatchers("/archive/**").hasRole("ADMIN");
                    registry.anyRequest().permitAll();
                })
                .formLogin(formLogin->{
                    formLogin.loginPage("/login").permitAll();
                    formLogin.defaultSuccessUrl("/admin",true);
                })
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1 = User.builder()
//                .username("letlabs")
//                .password(passwordEncoder.encode("123"))
//                .roles(UserRole.ADMIN.name())
//                .build();
//        UserDetails user2 = User.builder()
//                .username("mazinger")
//                .password(passwordEncoder.encode("456"))
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }

    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


}
