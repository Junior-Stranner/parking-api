package br.com.jujubaprojects.parkingapi.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jujubaprojects.parkingapi.Entity.Vaga;

public interface VagaRepository extends JpaRepository< Vaga, Long>{
   
    Optional<Vaga> findByCodigo(String codigo);
}
