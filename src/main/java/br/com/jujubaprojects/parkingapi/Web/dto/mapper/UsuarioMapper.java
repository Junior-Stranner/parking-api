package br.com.jujubaprojects.parkingapi.Web.dto.mapper;


import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.jujubaprojects.parkingapi.Entity.Usuario;
import br.com.jujubaprojects.parkingapi.Web.dto.UsuarioResponseDto;
import br.com.jujubaprojects.parkingapi.Web.dto.UsuarioCreateDto;

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

  /*   public class PessoaMapper {
        private static final ModelMapper modelMapper = new ModelMapper();
    
        public static PessoaDto toDto(Pessoa pessoa) {
            // Insira a lógica para conversão do objeto Pessoa em um objeto PessoaDto
            return new ModelMapper().map(pessoa, PessoaDto);
        }
    }*/
}
