package br.com.jujubaprojects.parkingapi.jwt;

import org.springframework.security.core.authority.AuthorityUtils; // Importa as classes relacionadas com a criação de autoridades do Spring Security
import org.springframework.security.core.userdetails.User; // Importa a classe de detalhes de usuário do Spring Security

import br.com.jujubaprojects.parkingapi.Entity.Usuario; // Importa a entidade de usuário

public class JwtUserDetails extends User { // Estende a classe User do Spring Security para personalizar os detalhes do usuário

     private Usuario usuario; // Declaração de um objeto do tipo Usuario para armazenar os detalhes do usuário

    // Construtor que recebe um objeto Usuario e inicializa os detalhes do usuário
    public JwtUserDetails(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name())); // Chama o construtor da classe User, passando o username, senha e lista de autoridades (roles) do usuário
        this.usuario = usuario; // Inicializa o objeto usuario com o usuário recebido como parâmetro
    }

    // Método para obter o ID do usuário
    public Long getId() {
        return this.usuario.getId(); // Retorna o ID do usuário
    }

    // Método para obter o papel (role) do usuário
    public String getRole() {
        return this.usuario.getRole().name(); // Retorna o nome do papel (role) do usuário
    }
}
