package br.com.jujubaprojects.parkingapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {
    // Constantes para configuração do JWT
    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789"; // Chave secreta para assinatura do JWT
    public static final long EXPIRE_DAYS = 0; // Dias de expiração do token
    public static final long EXPIRE_HOURS = 0; // Horas de expiração do token
    public static final long EXPIRE_MINUTES = 30; // Minutos de expiração do token

    private JwtUtils() {
        // Construtor privado para evitar instância da classe
    }

    // Método para gerar a chave de assinatura
    private static Key generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Método para calcular a data de expiração com base na data de emissão do token
    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    // Método para criar um token JWT
    public static JwtToken createToken(String username, String role) {
        Date issuedAt = new Date(); // Data de emissão do token
        Date limit = toExpireDate(issuedAt); // Data de expiração do token

        // Construção do token JWT
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(limit)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .claim("role", role)
                .compact();

        return new JwtToken(token); // Retorna o token JWT encapsulado em um objeto JwtToken
    }

    // Método para extrair as reivindicações (claims) do token JWT
    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token)).getBody();
        } catch (JwtException ex) {
            log.error(String.format("Token inválido %s", ex.getMessage()));
        }
        return null;
    }

    // Método para obter o nome de usuário a partir do token JWT
    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // Método para verificar se um token JWT é válido
    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token));
            return true;
        } catch (JwtException ex) {
            log.error(String.format("Token invalido %s", ex.getMessage())); // Log error message if token validation fails
        }
        return false;
    }

    // Removes "Bearer " prefix from the token string before parsing
    private static String refactorToken(String token) {
        if (token.contains(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }
}
