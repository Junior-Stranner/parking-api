package br.com.jujubaprojects.parkingapi.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vagas")
@Getter@Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)// Associa o ouvinte de eventos de auditoria "AuditingEntityListener" à entidade,
// permitindo a automação de tarefas de auditoria relacionadas ao ciclo de vida da entidade.
// Útil para manter o controle de alterações em registros e para fins de rastreabilidade e conformidade.
public class Vaga implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo", nullable = false, unique = true, length = 4)
    private String codigo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVaga status;

    @CreatedDate
    @Column(name = "data_criacao")
    @DateTimeFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name = "data_modificacao")
    @DateTimeFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataModificacao;
    

    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;
    
    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;

    public enum StatusVaga {
        LIVRE, OCUPADA
    }

    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vaga vaga = (Vaga) o;
        return Objects.equals(id, vaga.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
