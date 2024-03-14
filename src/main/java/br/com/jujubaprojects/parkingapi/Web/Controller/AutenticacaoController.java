package br.com.jujubaprojects.parkingapi.Web.Controller;

// Importações relacionadas ao uso de servlets HTTP do pacote Jakarta EE
import br.com.jujubaprojects.parkingapi.Web.dto.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

// Importação para validação de objetos com anotação @Valid do pacote Jakarta EE
import jakarta.validation.Valid;

// Importação do Lombok para geração automática de construtores obrigatórios
import lombok.RequiredArgsConstructor;

// Importação do Lombok para geração automática de logs SLF4J
import lombok.extern.slf4j.Slf4j;

// Importações relacionadas ao Spring Framework para lidar com HTTP
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Importações relacionadas ao Spring Security para gerenciamento de autenticação
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

// Importações do Spring MVC para anotações de controle REST
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Importações de classes do projeto relacionadas a DTOs, exceções e serviços JWT
import br.com.jujubaprojects.parkingapi.Web.dto.UsuarioLoginDto;
import br.com.jujubaprojects.parkingapi.Web.exception.ErrorMessage;
import br.com.jujubaprojects.parkingapi.jwt.JwtToken;
import br.com.jujubaprojects.parkingapi.jwt.JwtUserDetailsService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Authenticação", description = "Recurso para proceder com a authenticação na API")
public class AutenticacaoController {

    // Injeção do serviço responsável pela autenticação JWT do usuário
    private final JwtUserDetailsService jwtUserDetailsService;
    
    // Injeção do gerenciador de autenticação do Spring Security
    private final AuthenticationManager authenticationManager;

    public AutenticacaoController(JwtUserDetailsService jwtUserDetailsService, AuthenticationManager authenticationManager) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    // Endpoint para autenticar o usuário
    @Operation(summary = "Authenticar na API", description = "Recurso para authenticar na AOI",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authenticação realizada com sucesso e retorno de um bearer Token",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "campos inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request) {
        // Registro de informações sobre o processo de autenticação
        log.info("Processo de autenticação pelo login {}", dto.getUsername());
        try {
            // Criação de um token de autenticação com as credenciais fornecidas
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            // Autenticação do usuário utilizando o gerenciador de autenticação do Spring Security
            authenticationManager.authenticate(authenticationToken);

            // Geração do token JWT após a autenticação bem-sucedida
            JwtToken token = jwtUserDetailsService.getTokenAuthenticated(dto.getUsername());

            // Resposta com o token JWT
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            // Registro de aviso sobre credenciais inválidas
            log.warn("Credenciais inválidas do usuário '{}'", dto.getUsername());
        }
        // Resposta de erro para credenciais inválidas
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }

}
