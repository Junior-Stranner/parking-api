package br.com.jujubaprojects.parkingapi.Repository;

import br.com.jujubaprojects.parkingapi.Entity.ClienteVaga;
import br.com.jujubaprojects.parkingapi.Repository.Projection.ClienteVagaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClienteVagaRepository extends JpaRepository<ClienteVaga,Long> {
    
    long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

    Page<ClienteVagaProjection> findAllByClienteCpf(String cpf, Pageable pageable);

    Page<ClienteVagaProjection> findAllByClienteUsuarioId(Long id, Pageable pageable);

    Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);
}

