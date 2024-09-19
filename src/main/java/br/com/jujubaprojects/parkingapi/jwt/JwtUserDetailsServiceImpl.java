package br.com.jujubaprojects.parkingapi.jwt;

import lombok.RequiredArgsConstructor; // Importa a anotação Lombok para geração automática de construtores
import org.springframework.security.core.userdetails.UserDetails; // Importa as classes relacionadas com os detalhes do usuário
import org.springframework.security.core.userdetails.UserDetailsService; // Importa as classes relacionadas com o serviço de detalhes do usuário
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Importa as classes relacionadas com exceções de nome de usuário não encontrado
import org.springframework.stereotype.Service; // Importa a anotação de serviço do Spring

import br.com.jujubaprojects.parkingapi.Entity.Usuario; // Importa a entidade de usuário
import br.com.jujubaprojects.parkingapi.Service.UsuarioService; // Importa o serviço de usuário

@RequiredArgsConstructor // Adiciona a anotação do Lombok para gerar automaticamente um construtor com parâmetros
@Service // Indica que esta classe é um serviço do Spring
public class JwtUserDetailsServiceImpl implements UserDetailsService { // Implementa a interface UserDetailsService do Spring Security

    private final UsuarioService usuarioService; // Injeta o serviço de usuário

    // Implementa o método da interface UserDetailsService para carregar os detalhes do usuário pelo username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsername(username); // Busca o usuário pelo username no serviço de usuário
        return new JwtUserDetails(usuario); // Retorna os detalhes do usuário encapsulados em JwtUserDetails
    }

    // Método para obter um token JWT autenticado para um usuário
    public JwtToken getTokenAuthenticated(String username) {
        Usuario.Role role = usuarioService.buscarRolePorUsername(username); // Busca o papel (role) do usuário pelo username no serviço de usuário
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length())); // Cria e retorna um token JWT autenticado com o username e a role do usuário
    }
}
