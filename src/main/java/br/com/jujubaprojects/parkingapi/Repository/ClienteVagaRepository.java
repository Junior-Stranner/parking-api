package br.com.jujubaprojects.parkingapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jujubaprojects.parkingapi.Entity.ClienteVaga;

import java.util.Optional;

public interface ClienteVagaRepository extends JpaRepository<ClienteVaga , Long>{

   Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);
}
