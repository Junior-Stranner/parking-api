package br.com.jujubaprojects.parkingapi;

import br.com.jujubaprojects.parkingapi.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}