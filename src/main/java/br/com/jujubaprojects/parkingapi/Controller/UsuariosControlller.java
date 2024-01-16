package br.com.jujubaprojects.parkingapi.Controller;

import br.com.jujubaprojects.parkingapi.Entity.Usuario;
import br.com.jujubaprojects.parkingapi.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.BinaryOperator;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuariosControlller {

    @Autowired
    private  UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        this.usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable("id") Long id) {
        Usuario usuario = this.usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> UpdatePassword(@PathVariable("id") long id,  @RequestBody Usuario usuario){
        this.usuarioService.editarSenha(id , usuario.getPassword());
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
    List<Usuario> usuarios  = usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }
}
