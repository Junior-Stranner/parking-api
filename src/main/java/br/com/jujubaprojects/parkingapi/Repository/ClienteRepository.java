package br.com.jujubaprojects.parkingapi.Repository;

import br.com.jujubaprojects.parkingapi.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
