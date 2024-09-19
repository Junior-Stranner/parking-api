package br.com.jujubaprojects.parkingapi.Repository;

import br.com.jujubaprojects.parkingapi.Entity.Cliente;
import br.com.jujubaprojects.parkingapi.Repository.Projection.ClienteProjection;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

   // Verifica se existe algum cliente com o CPF informado
   @Query("select (count(c) > 0) from Cliente c where c.cpf = :cpf")
   boolean existsByCpf(String cpf);

   // Verifica se existe algum cliente associado ao username (e-mail) informado
   @Query("select (count(c) > 0) from Cliente c where c.usuario.username = :username")
   boolean existsByEmail(String username);

   // Busca todos os clientes de forma paginada, retornando apenas os dados definidos na projeção ClienteProjection
   @Query("SELECT c FROM Cliente c")
   Page<ClienteProjection> findAllPageable(Pageable pageable);

   // Busca um cliente pelo ID do usuário associado
   Cliente findByUsuarioId(Long id);

   // Retorna um cliente como Optional, buscando pelo CPF informado
   Optional<Cliente> findByCpf(String cpf);

}
