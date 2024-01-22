package br.com.jujubaprojects.parkingapi.dto.mapper;

import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.jujubaprojects.parkingapi.Entity.Usuario;
import br.com.jujubaprojects.parkingapi.dto.UsuarioCreateDto;
import br.com.jujubaprojects.parkingapi.dto.UsuarioResponseDto;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto usuarioCreateDto){
        return new ModelMapper().map(usuarioCreateDto, Usuario.class);

    }

        public static UsuarioResponseDto toDto(Usuario usuario){
            String role = usuario.getRole().name().substring("Role" .length());
            PropertyMap<Usuario,UsuarioResponseDto> props = new PropertyMap<Usuario,UsuarioResponseDto>() {
                @Override
                protected void configure(){
                    map().setRole(role);
                }
            };
            
            ModelMapper mapper = new ModelMapper();
            mapper.addMappings(props);
            return mapper.map(usuario, UsuarioResponseDto.class);
        
    }
}
