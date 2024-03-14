package br.com.jujubaprojects.parkingapi.Web.dto.mapper;

import br.com.jujubaprojects.parkingapi.Entity.Cliente;
import br.com.jujubaprojects.parkingapi.Web.dto.ClienteCreateDto;
import br.com.jujubaprojects.parkingapi.Web.dto.ClienteResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toCliente(ClienteCreateDto dto){
      return new ModelMapper().map(dto,Cliente.class);
    }
    public static ClienteResponseDto ToDto(Cliente entity){
        return new ModelMapper().map(entity,ClienteResponseDto.class);
    }

}
