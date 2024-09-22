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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.jujubaprojects.parkingapi.jwt.JwtAuthenticationEntryPoint;
import br.com.jujubaprojects.parkingapi.jwt.JwtAuthorizationFilter;

import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableWebMvc
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
                    .csrf(csrf -> csrf.disable()) // Desabilita a proteção CSRF (Cross-Site Request Forgery) pois a API é stateless
                    .formLogin(form -> form.disable())  // Desabilita o formulário de login padrão do Spring Security
                    .httpBasic(basic -> basic.disable())  // Desabilita a autenticação básica HTTP
                    .authorizeHttpRequests(auth -> auth    // Configura as autorizações de requisição HTTP
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
                    )// Define o gerenciamento de sessão como stateless, já que a autenticação será feita via JWT
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        // Adiciona um filtro JWT antes do filtro padrão de autenticação por nome de usuário/senha
        .addFilterBefore(
                jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
        )

        // Configura o ponto de entrada da autenticação JWT para lidar com exceções de autenticação
        .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        )

        // Constrói a configuração de segurança
        .build();
        }

     // Define o filtro de autorização JWT como um bean
     @Bean
     public JwtAuthorizationFilter jwtAuthorizationFilter() {
       return new JwtAuthorizationFilter();
   }

    // Define o encoder de senhas, utilizando o algoritmo BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

    // Define o gerenciador de autenticação, permitindo a integração com o processo de autenticação configurado
   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
      }
}