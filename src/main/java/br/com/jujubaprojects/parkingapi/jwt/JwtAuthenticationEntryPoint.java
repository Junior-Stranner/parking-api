import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

// Importación de SLF4J para el registro de eventos
@Slf4j
// Anotación para generar un logger llamado 'log'
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Método para manejar solicitudes de autenticación no autorizadas
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.info("http Status 401 {}", authException.getMessage()); //Registrando uma mensagem informativa indicando o status
        // HTTP 401 e uma mensagem de exceção
        response.setHeader("www-authenticate", "Bear realm= '/api/v1/auth'"); //Definindo o cabeçalho de autenticação
        // para informar ao cliente que ele precisa se autenticar
        response.sendError(401);// Enviando um código de status HTTP 401 (não autorizado) como resposta ao cliente
    }
}

