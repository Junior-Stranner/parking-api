package br.com.jujubaprojects.parkingapi.Service;

import br.com.jujubaprojects.parkingapi.Entity.Usuario;
import br.com.jujubaprojects.parkingapi.Repository.UsuarioRepository;
import br.com.jujubaprojects.parkingapi.dto.UsuarioCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository ;
    @Transactional
    public Usuario salvar(Usuario usuario){
      UsuarioCreateDto usuarioCreateDto = new UsuarioCreateDto();
        usuarioCreateDto.setPassword(usuario.getPassword());
        usuarioCreateDto.setUsername(usuario.getUsername());
        Usuario usuarioCreateDto1= usuarioRepository.save(usuario);
        return usuario;
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontradp"));
    }

    @Transactional()
    public Usuario editarSenha(Long id , String senhaAtual, String novaSenha , String confirmaSenha) {

        if(!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com a configuração de senha");
        }
        Usuario usuario = buscarPorId(id);
       
        if(!usuario.getPassword().equals(senhaAtual)){
            throw new RuntimeException("sua senha não confere");

        }
        usuario.setPassword(novaSenha);
        return usuario;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
       return usuarioRepository.findAll();
    }
}
