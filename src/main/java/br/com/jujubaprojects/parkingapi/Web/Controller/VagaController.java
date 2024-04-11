package br.com.jujubaprojects.parkingapi.Web.Controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jujubaprojects.parkingapi.Entity.Vaga;
import br.com.jujubaprojects.parkingapi.Service.VagaService;
import br.com.jujubaprojects.parkingapi.Web.dto.VagaCreateDto;
import br.com.jujubaprojects.parkingapi.Web.dto.VagaResponseDto;
import br.com.jujubaprojects.parkingapi.Web.dto.mapper.VagaMapper;
import br.com.jujubaprojects.parkingapi.Web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.headers.Header;


@Tag(name = "Vagas", description = "Contém todas as opereções relativas ao recurso de uma vaga")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

    private final VagaService vagaService;

    @Operation(summary = "Criar uma nova vaga", description = "Recurso para criar uma nova vaga." +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado")),
                    @ApiResponse(responseCode = "409", description = "Vaga já cadastrada",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    // Anotação para mapear uma requisição HTTP POST para este método
@PostMapping
// Restringe o acesso a este método apenas para usuários com a função 'ADMIN'
@PreAuthorize("hasRole('ADMIN')")
// Método responsável por criar uma nova vaga
public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDto dto) {
    // Converte o objeto VagaCreateDto em um objeto Vaga usando o VagaMapper
    Vaga vaga = VagaMapper.toVaga(dto);
    // Salva a nova vaga no banco de dados usando o serviço vagaService
    vagaService.salvar(vaga);
    // Constrói a URI do recurso criado
    URI location = ServletUriComponentsBuilder
        // Obtém a URI atual da requisição e adiciona o código da vaga como parte da URI
        .fromCurrentRequestUri().path("/{codigo}")
        // Expande o código da vaga na URI
        .buildAndExpand(vaga.getCodigo())
        // Converte a URI em um objeto URI
        .toUri();
    // Retorna uma resposta com o código de status 201 Created e a URI do recurso criado
    return ResponseEntity.created(location).build();
}


    @Operation(summary = "Localizar uma vaga", description = "Recurso para retornar uma vaga pelo seu código" +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = VagaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Vaga não localizada",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> getByCodigo(@PathVariable String codigo) {
        Vaga vaga = vagaService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}
