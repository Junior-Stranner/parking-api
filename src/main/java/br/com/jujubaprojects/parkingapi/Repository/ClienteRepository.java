package br.com.jujubaprojects.parkingapi.Repository;

import br.com.jujubaprojects.parkingapi.Entity.Cliente;
import br.com.jujubaprojects.parkingapi.Repository.Projection.ClienteProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c from Cliente c")
    Page<ClienteProjection> findAllPageable(Pageable pageable);

    Cliente findByUsuarioId(Long id);
}
