package br.com.jujubaprojects.parkingapi.jwt;

import jakarta.servlet.FilterChain; // Importa a classe FilterChain para encadear filtros de requisição
import jakarta.servlet.ServletException; // Importa a exceção ServletException
import jakarta.servlet.http.HttpServletRequest; // Importa a classe HttpServletRequest para representar uma solicitação HTTP
import jakarta.servlet.http.HttpServletResponse; // Importa a classe HttpServletResponse para representar uma resposta HTTP
import lombok.extern.slf4j.Slf4j; // Importa o logger SLF4J com Lombok
import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação @Autowired para injeção de dependência
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Importa a classe UsernamePasswordAuthenticationToken para autenticação do usuário
import org.springframework.security.core.context.SecurityContextHolder; // Importa a classe SecurityContextHolder para acessar o contexto de segurança
import org.springframework.security.core.userdetails.UserDetails; // Importa a classe UserDetails para detalhes do usuário
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter; // Importa a classe OncePerRequestFilter para criar um filtro executado uma vez por solicitação

import java.io.IOException; // Importa a exceção IOException

@Slf4j // Anotação para geração de logger SLF4J
public class JwtAuthorizationFilter extends OncePerRequestFilter { // Classe que implementa um filtro de autorização JWT executado uma vez por solicitação

    @Autowired // Anotação para injeção de dependência
    private JwtUserDetailsServiceImpl detailsService; // Serviço para detalhes do usuário JWT



    // Método que realiza o filtro interno da requisição
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION); // Obtém o token JWT do cabeçalho da requisição

        if (token == null || !token.startsWith(JwtUtils.JWT_BEARER)) { // Verifica se o token está ausente ou não começa com 'Bearer '
            log.info("JWT Token está nulo, vazio ou não iniciado com 'Bearer '."); // Registra um log informativo
            filterChain.doFilter(request, response); // Continua com a cadeia de filtros
            return; // Retorna
        }

        if (!JwtUtils.isTokenValid(token)) { // Verifica se o token JWT é inválido ou expirou
            log.warn("JWT Token está inválido ou expirado."); // Registra um log de aviso
            filterChain.doFilter(request, response); // Continua com a cadeia de filtros
            return; // Retorna
        }

        String username = JwtUtils.getUsernameFromToken(token); // Obtém o nome de usuário do token JWT

        toAuthentication(request, username); // Realiza a autenticação do usuário

        filterChain.doFilter(request, response); // Continua com a cadeia de filtros
    }

    // Método para autenticar o usuário
    private void toAuthentication(HttpServletRequest request, String username) {
        UserDetails userDetails = detailsService.loadUserByUsername(username); // Obtém os detalhes do usuário pelo nome de usuário

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities()); // Cria um token de autenticação com os detalhes do usuário

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Define os detalhes da autenticação

        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Define a autenticação no contexto de segurança
    }
}
