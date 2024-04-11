package br.com.jujubaprojects.parkingapi.Web.dto.mapper;

import org.modelmapper.ModelMapper;

import br.com.jujubaprojects.parkingapi.Entity.Vaga;
import br.com.jujubaprojects.parkingapi.Web.dto.VagaCreateDto;
import br.com.jujubaprojects.parkingapi.Web.dto.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto dto){
        return new ModelMapper().map(dto, Vaga.class);
        
    }

    public static VagaResponseDto toDto(Vaga entity){
        return new ModelMapper().map(entity, VagaResponseDto.class);
    }
    
}
