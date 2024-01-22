package br.com.jujubaprojects.parkingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioResponseDto {
    
    
    private Long id;
    private String username;
    private String role;
}
