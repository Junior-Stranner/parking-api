package br.com.jujubaprojects.parkingapi.Web.Controller;

import br.com.jujubaprojects.parkingapi.Entity.Usuario;
import br.com.jujubaprojects.parkingapi.Service.UsuarioService;
import br.com.jujubaprojects.parkingapi.Web.dto.UsuarioCreateDto;
import br.com.jujubaprojects.parkingapi.Web.dto.UsuarioResponseDto;
import br.com.jujubaprojects.parkingapi.Web.dto.UsuarioSenhaDto;
import br.com.jujubaprojects.parkingapi.Web.dto.mapper.UsuarioMapper;
import br.com.jujubaprojects.parkingapi.Web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios",description = "Contem todas as operações relativas para cadastro ,edição e leitura de um Usuário")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuariosControlller {

    @Autowired
    private  UsuarioService usuarioService;

    @Operation(summary = "Criar um novo usuuário", description = "Recurso para criar um novo usuário",
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create( @Valid @RequestBody UsuarioCreateDto usuarioCreateDto){
      Usuario usuario = this.usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(usuario));
    }


    
    @Operation(summary = "Recuperar um usuário pelo id", description = "Recuperar um usuário pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable("id") Long id) {
        Usuario usuario = this.usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(usuario));
    }


    @Operation(summary = "Atualizar senha", description = "Atualizar senha",
    responses = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Senha não confere",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> UpdatePassword(@PathVariable("id") long id, @Valid  @RequestBody UsuarioSenhaDto usuarioSenhaDto){
      Usuario usuario =  this.usuarioService.editarSenha(id , usuarioSenhaDto.getSenhaAtual(), usuarioSenhaDto.getNovaSenha(), usuarioSenhaDto.getConfirmaSenha());
        return ResponseEntity.ok(UsuarioMapper.toDto(usuario));
    }

      @Operation(summary = "Listar todos os usuários", description = "Listar todos os usuários cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                            content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class))))
            })
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
    List<Usuario> usuarios  = usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }
}
