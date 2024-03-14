package br.com.jujubaprojects.parkingapi.Web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ClienteResponseDto {
    private long id;
    private String nome;
    private String cpf;
}
