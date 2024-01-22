package br.com.jujubaprojects.parkingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter @AllArgsConstructor  @ToString
public class UsuarioSenhaDto {
    

    private String senhaAtual;
    private String novaSenha;
    private String confirmaSenha;

}
