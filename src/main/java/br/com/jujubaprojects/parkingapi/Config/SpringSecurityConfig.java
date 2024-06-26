package br.com.jujubaprojects.parkingapi.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.jujubaprojects.parkingapi.jwt.JwtAuthenticationEntryPoint;
import br.com.jujubaprojects.parkingapi.jwt.JwtAuthorizationFilter;

import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  {
        
        private static final String[] DOCUMENTATION_OPENAPI = {
                "/docs/index.html",
                "/docs-park.html","/docs-park/**",
                "/v3/api-docs/**",
                "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
                "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
        };
    
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())
                    .formLogin(form -> form.disable())
                    .httpBasic(basic -> basic.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/v1/usuarios").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/{id}").permitAll()
                            .requestMatchers(HttpMethod.PATCH, "/api/v1/usuarios/{id}").permitAll()

                            .requestMatchers(HttpMethod.POST, "/api/v1/clientes").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/clientes").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/clientes/{id}").hasRole("ADMIn")
                            .requestMatchers(HttpMethod.GET, "/api/v1/clientes/detalhes").permitAll()

                            .requestMatchers(HttpMethod.POST, "/api/v1/estacionamento").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/v1/estacionamento").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/estacionamento").hasRole("ADMIN")
                            
                            .requestMatchers(HttpMethod.POST, "/api/v1/vagas").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/v1/vagas").hasRole("ADMIN")


                    //        .requestMatchers(HttpMethod.POST,"api/v1/clientes").hasRole("CLIENTE")
                            .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                            .anyRequest().authenticated()
                    ).sessionManagement(
                            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    ).addFilterBefore(
                            jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                    ).exceptionHandling(ex -> ex
                            .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                    ).build();
        }

        @Bean
        public JwtAuthorizationFilter jwtAuthorizationFilter() {
            return new JwtAuthorizationFilter();
        }
    
    
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }
    }