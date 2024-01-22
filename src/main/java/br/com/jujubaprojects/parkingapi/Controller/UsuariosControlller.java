package br.com.jujubaprojects.parkingapi.Controller;

import br.com.jujubaprojects.parkingapi.Entity.Usuario;
import br.com.jujubaprojects.parkingapi.Service.UsuarioService;
import br.com.jujubaprojects.parkingapi.dto.UsuarioCreateDto;
import br.com.jujubaprojects.parkingapi.dto.UsuarioResponseDto;
import br.com.jujubaprojects.parkingapi.dto.UsuarioSenhaDto;
import br.com.jujubaprojects.parkingapi.dto.mapper.UsuarioMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuariosControlller {

    @Autowired
    private  UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody UsuarioCreateDto usuarioCreateDto){
      Usuario usuario = this.usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(usuario));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable("id") Long id) {
        Usuario usuario = this.usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(usuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> UpdatePassword(@PathVariable("id") long id,  @RequestBody UsuarioSenhaDto usuarioSenhaDto){
      Usuario usuario =  this.usuarioService.editarSenha(id , usuarioSenhaDto.getSenhaAtual(), usuarioSenhaDto.getNovaSenha(), usuarioSenhaDto.getConfirmaSenha());
        return ResponseEntity.ok(UsuarioMapper.toDto(usuario));
    }
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
    List<Usuario> usuarios  = usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }
}
