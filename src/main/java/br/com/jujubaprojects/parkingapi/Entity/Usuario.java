package br.com.jujubaprojects.parkingapi.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.jujubaprojects.parkingapi.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name= "username",nullable = false , length = 100, unique = true)
    private String username;

    @Column(name= "password",nullable = false ,length = 200 , unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name= "role",nullable = false , length = 20)
    private Role role;

    @CreatedDate
    @Column(name= "data_Criacao")
    @DateTimeFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name= "data_Modificacao")
    @DateTimeFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataModificacao;

    @Column(name= "criado_Por")
    private String criadoPor;
    @Column(name= "modificado_Por")
    private String modificadoPor;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + "]";
    }

 

}
