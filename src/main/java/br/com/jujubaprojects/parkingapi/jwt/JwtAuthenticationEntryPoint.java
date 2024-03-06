package br.com.jujubaprojects.parkingapi.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j; // Importación de la anotación SLF4J para el registro de eventos

@Slf4j // Anotación para generar un logger llamado 'log'
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    // Método para manejar solicitudes de autenticación no autorizadas
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("http Status 401 {}", authException.getMessage()); // Registro de un mensaje informativo indicando el estado HTTP 401 y el mensaje de excepción
        response.setHeader("www-authenticate", "Bear realm= '/api/v1/auth'"); // Establecimiento del encabezado de autenticación para indicar al cliente que necesita autenticarse
        response.sendError(401); // Envío de un código de estado HTTP 401 (No Autorizado) como respuesta al cliente
    }
}
