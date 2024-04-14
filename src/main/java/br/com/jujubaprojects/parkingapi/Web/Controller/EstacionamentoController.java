package br.com.jujubaprojects.parkingapi.Web.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jujubaprojects.parkingapi.Entity.ClienteVaga;
import br.com.jujubaprojects.parkingapi.Service.EstacionamentoService;
import br.com.jujubaprojects.parkingapi.Web.dto.EstacionamentoCreateDto;
import br.com.jujubaprojects.parkingapi.Web.dto.EstacionamentoResponseDto;
import br.com.jujubaprojects.parkingapi.Web.dto.mapper.ClienteMapper;
import br.com.jujubaprojects.parkingapi.Web.dto.mapper.ClienteVagaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamento")
public class EstacionamentoController {

    public final EstacionamentoService estacionamentoService;
    
    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkIn(@RequestBody @Valid EstacionamentoCreateDto dto) {
        ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(dto);
        estacionamentoService.checkIn(clienteVaga);
        EstacionamentoResponseDto responseDto = ClienteVagaMapper.toDto(clienteVaga);
        
        URI location = ServletUriComponentsBuilder
            // Obtém a URI atual da requisição e adiciona o código da vaga como parte da URI
           .fromCurrentRequestUri().path("/{recibo}")
           // Expande o código da vaga na URI
           .buildAndExpand(clienteVaga.getRebico())
            // Converte a URI em um objeto URI
           .toUri();
    // Retorna uma resposta com o código de status 201 Created e a URI do recurso criado
    return ResponseEntity.created(location).build();
    }
    
}
