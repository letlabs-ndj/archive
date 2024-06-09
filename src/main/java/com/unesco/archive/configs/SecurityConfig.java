//package com.unesco.archive.configs;
//
//import com.unesco.archive.model.enums.UserRole;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig {
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public SecurityConfig(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(registry->{
//                    registry.requestMatchers("/","/js/**","/css/**","/images/**",
//                            "arcive/**").permitAll();
//                    registry.anyRequest().authenticated();
//                })
//                .formLogin(formLogin->{
//                    formLogin.loginPage("/login").permitAll();
//                    formLogin.defaultSuccessUrl("/upload",true);
//                })
//                .logout(logout->{
//                    logout.invalidateHttpSession(true);
//                    logout.clearAuthentication(true);
//                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
//                    logout.logoutSuccessUrl("/login?logout");
//                    logout.permitAll();
//                })
//                .build();
//    }
//
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
//
//}
