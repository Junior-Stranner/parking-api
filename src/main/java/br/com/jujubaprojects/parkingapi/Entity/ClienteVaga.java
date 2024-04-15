package br.com.jujubaprojects.parkingapi.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "clientes_tem_vagas")
@EntityListeners(AuditingEntityListener.class)// Associa o ouvinte de eventos de auditoria "AuditingEntityListener" à entidade,
// permitindo a automação de tarefas de auditoria relacionadas ao ciclo de vida da entidade.
// Útil para manter o controle de alterações em registros e para fins de rastreabilidade e conformidade.
public class ClienteVaga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "numero_recibo", nullable = false, unique = true, length = 15)
    private String rebico;

    @Column( name = "placa", nullable = false, unique = true , length = 8)
    private String placa;

    @Column( name = "marca", nullable = false, unique = true , length = 45)
    private String marca;

    @Column( name = "modelo", nullable = false, unique = true , length = 45)
    private String modelo;

    @Column( name = "cor", nullable = false, unique = true , length = 45)
    private String cor;

    @Column( name = "data_entrada", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataEntrada;

    @Column( name = "data_saida")
    @DateTimeFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataSaida;

    @Column(name = "valor",columnDefinition = "decimal(7,2)")
    private BigDecimal valor;
    @Column(name = "deconto",columnDefinition = "decimal(7,2)")
    private BigDecimal desconto;

    @CreatedDate
    @Column(name = "data_criacao")
    @DateTimeFormat(pattern = "dd/MM/yyyy - HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name = "data_modificacao")
    @DateTimeFormat(pattern = "dd/MM/yyyy - HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataModificacao;

    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;

    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;

    
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga vaga;

    

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
        ClienteVaga other = (ClienteVaga) obj;
        if (id != other.id)
            return false;
        return true;
    }


}
