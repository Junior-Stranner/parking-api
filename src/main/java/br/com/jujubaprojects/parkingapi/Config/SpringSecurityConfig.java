import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Configuração do filtro de segurança
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Desabilita CSRF
                .csrf(csrf -> csrf.disable())
                // Desabilita login via formulário
                .formLogin(form -> form.disable())
                // Desabilita autenticação básica via HTTP
                .httpBasic(basic -> basic.disable())
                // Autoriza as requisições HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permite requisições POST para criar usuários
                        .requestMatchers(HttpMethod.POST, "api/v1/usuarios").permitAll()
                        // Permite requisições POST para autenticação
                        .requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll()
                        // Exige autenticação para outras requisições
                        .anyRequest().authenticated()
                )
                // Configura gerenciamento de sessão
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Adiciona filtro de autorização JWT antes do filtro padrão
                .addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

      // Bean para criar uma instância do filtro de autorização JWT
     @Bean
     public JwtAuthorizationFilter jwtAuthorizationFilter() {
       return new JwtAuthorizationFilter();
   }

    // Bean para criar uma instância do codificador de senhas BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }

    // Bean para criar uma instância do gerenciador de autenticação com base na configuração de autenticação fornecida
     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
     }

}
