package br.com.jujubaprojects.parkingapi.Web.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor @AllArgsConstructor
@Getter@Setter
public class ClienteCreateDto {

    @NotNull()
    @Size(min = 5, max =100)
    private String nome;
    @Size(min = 11,max =11)
    @CPF
    private String cpf;
}
