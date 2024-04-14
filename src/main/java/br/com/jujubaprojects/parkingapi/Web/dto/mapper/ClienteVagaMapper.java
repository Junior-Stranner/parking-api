package br.com.jujubaprojects.parkingapi.Web.dto.mapper;

import org.modelmapper.ModelMapper;

import br.com.jujubaprojects.parkingapi.Entity.ClienteVaga;
import br.com.jujubaprojects.parkingapi.Web.dto.EstacionamentoCreateDto;
import br.com.jujubaprojects.parkingapi.Web.dto.EstacionamentoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {
    
    public static ClienteVaga toClienteVaga(EstacionamentoCreateDto dto) {
        return new ModelMapper().map(dto, ClienteVaga.class);
    }

    public static EstacionamentoResponseDto toDto(ClienteVaga clienteVaga) {
        return new ModelMapper().map(clienteVaga, EstacionamentoResponseDto.class);
    }
}
