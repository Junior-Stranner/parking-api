package br.com.jujubaprojects.parkingapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; // Importa as classes relacionadas com a geração de chaves criptográficas
import lombok.extern.slf4j.Slf4j; // Importa a biblioteca de logging Lombok

import java.nio.charset.StandardCharsets; // Importa as classes relacionadas com a codificação de caracteres
import java.security.Key; // Importa as classes relacionadas com as chaves criptográficas
import java.time.LocalDateTime; // Importa as classes relacionadas com a manipulação de data e hora
import java.time.ZoneId; // Importa as classes relacionadas com a identificação de fusos horários
import java.util.Date; // Importa as classes relacionadas com a representação de datas

@Slf4j // Adiciona a anotação de logging do Lombok
public class JwtUtils {

    // Prefixo para o tipo de autenticação JWT
    public static final String JWT_BEARER = "Bearer ";

    // Header utilizado para enviar o token JWT
    public static final String JWT_AUTHORIZATION = "Authorization";

    // Chave secreta para assinar e verificar os tokens
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";

    // Tempo de expiração do token em dias, horas e minutos
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 2;

    // Construtor privado para impedir a instanciação da classe
    private JwtUtils(){
    }

    // Método para gerar a chave secreta para assinar os tokens
    private static Key generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); // Gera a chave secreta usando o algoritmo HMAC-SHA
    }

    // Método para calcular a data de expiração do token com base na data de emissão
    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(); // Converte a data de emissão para LocalDateTime
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES); // Calcula a data de expiração adicionando dias, horas e minutos
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant()); // Converte LocalDateTime de volta para Date
    }

    // Método para criar um token JWT com o username e a role
    public static JwtToken createToken(String username, String role) {
        Date issuedAt = new Date(); // Obtém a data de emissão atual
        Date limit = toExpireDate(issuedAt); // Calcula a data de expiração do token

        String token = Jwts.builder() // Cria um builder para o token JWT
                .setHeaderParam("typ", "JWT") // Define o tipo do token no cabeçalho
                .setSubject(username) // Define o username como o assunto do token
                .setIssuedAt(issuedAt) // Define a data de emissão do token
                .setExpiration(limit) // Define a data de expiração do token
                .signWith(generateKey(), SignatureAlgorithm.HS256) // Assina o token com a chave secreta usando o algoritmo HS256
                .claim("role", role) // Adiciona uma reivindicação (claim) ao token para a role do usuário
                .compact(); // Compacta o token em uma String

        return new JwtToken(token); // Retorna o token JWT encapsulado em um objeto JwtToken
    }

    // Método para extrair as reivindicações (claims) do token JWT
    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder() // Cria um parser para o token JWT
                    .setSigningKey(generateKey()).build() // Configura a chave secreta do parser
                    .parseClaimsJws(refactorToken(token)).getBody(); // Faz o parsing do token e retorna as reivindicações (claims)
        } catch (JwtException ex) {
            log.error(String.format("Token inválido: %s", ex.getMessage())); // Registra um erro se ocorrer uma exceção durante o parsing do token
        }
        return null; // Retorna null se o parsing do token falhar
    }

    // Método para obter o username do token JWT
    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject(); // Retorna o username extraído das reivindicações (claims) do token
    }

    // Método para verificar se um token JWT é válido
    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder() // Cria um parser para o token JWT
                    .setSigningKey(generateKey()).build() // Configura a chave secreta do parser
                    .parseClaimsJws(refactorToken(token)); // Faz o parsing do token JWT
            return true; // Retorna true se o parsing do token for bem-sucedido
        } catch (JwtException ex) {
            log.error(String.format("Token inválido: %s", ex.getMessage())); // Registra um erro se ocorrer uma exceção durante o parsing do token
        }
        return false; // Retorna false se o parsing do token falhar
    }

    // Método para remover o prefixo 'Bearer ' do token JWT, se presente
    private static String refactorToken(String token) {
        if (token.contains(JWT_BEARER)) { // Verifica se o token contém o prefixo 'Bearer '
            return token.substring(JWT_BEARER.length()); // Remove o prefixo 'Bearer ' do token
        }
        return token; // Retorna o token sem alterações se não contiver o prefixo 'Bearer '
    }
}
