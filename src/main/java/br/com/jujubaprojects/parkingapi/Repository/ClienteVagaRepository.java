package br.com.jujubaprojects.parkingapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jujubaprojects.parkingapi.Entity.ClienteVaga;

public interface ClienteVagaRepository extends JpaRepository<ClienteVaga , Long>{
    
}
