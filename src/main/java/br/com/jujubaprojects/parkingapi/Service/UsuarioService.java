package br.com.jujubaprojects.parkingapi.Service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jujubaprojects.parkingapi.Entity.Usuario;
import br.com.jujubaprojects.parkingapi.Repository.UsuarioRepository;
import br.com.jujubaprojects.parkingapi.exception.UnauthorizedAccessException;
import br.com.jujubaprojects.parkingapi.exception.EntityNotFoundException;
import br.com.jujubaprojects.parkingapi.exception.PasswordInvalidException;
import br.com.jujubaprojects.parkingapi.exception.UsernameUniqueViolationException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor // Anotação do Lombok para gerar automaticamente um construtor com as dependências necessárias
@Service // Indica que esta classe é um componente de serviço gerenciado pelo Spring
public class UsuarioService {

    private final UsuarioRepository usuarioRepository; // Declaração do atributo usuarioRepository que é uma instância de UsuarioRepository
    private final PasswordEncoder passwordEncoder; // Declaração do atributo passwordEncoder que é uma instância de PasswordEncoder

   

    @Transactional // Esta anotação indica que o método é transacional, ou seja, será executado em uma transação de banco de dados
    public Usuario salvar(Usuario usuario) { // Método para salvar um usuário
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Codifica a senha do usuário antes de salvar no banco de dados
            return usuarioRepository.save(usuario); // Salva o usuário no banco de dados usando o repository
        } catch (org.springframework.dao.DataIntegrityViolationException ex) { // Captura uma exceção de violação de integridade de dados
            throw new UsernameUniqueViolationException(String.format("Username '%s' já cadastrado", usuario.getUsername())); // Lança uma exceção personalizada se o nome de usuário já estiver cadastrado
        }
    }

    @Transactional(readOnly = true) // Esta anotação indica que o método é apenas de leitura no banco de dados
    public Usuario buscarPorId(Long id) { // Método para buscar um usuário pelo ID
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Obtém a autenticação atual
        String username = authentication.getName(); // Obtém o nome de usuário autenticado
        Usuario user = usuarioRepository.findById(id).orElse(null); // Busca o usuário pelo ID
        
        // Verifica se o usuário existe e se o ID do usuário autenticado corresponde ao ID do usuário solicitado
        if (user != null && user.getUsername().equals(username)) {
            return user;
        } else {
            throw new UnauthorizedAccessException("Acesso não autorizado"); // Lança uma exceção se o acesso não for autorizado
        }
    }

    @Transactional // Esta anotação indica que o método é transacional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) { // Método para editar a senha de um usuário
        if (!novaSenha.equals(confirmaSenha)) { // Verifica se a nova senha coincide com a confirmação de senha
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }

        Usuario user = buscarPorId(id); // Busca o usuário pelo ID
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) { // Verifica se a senha atual fornecida coincide com a senha armazenada
            throw new PasswordInvalidException("Sua senha não confere.");
        }

        user.setPassword(passwordEncoder.encode(novaSenha)); // Atualiza a senha do usuário
        return user; // Retorna o usuário atualizado
    }

    @Transactional(readOnly = true) // Esta anotação indica que o método é apenas de leitura no banco de dados
    public List<Usuario> buscarTodos() { // Método para buscar todos os usuários
        return usuarioRepository.findAll(); // Retorna uma lista de todos os usuários do banco de dados
    }

    @Transactional(readOnly = true) // Esta anotação indica que o método é apenas de leitura no banco de dados
    public Usuario buscarPorUsername(String username) { // Método para buscar um usuário pelo nome de usuário
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username)) // Lança uma exceção se o usuário não for encontrado
        );
    }

    @Transactional(readOnly = true) // Esta anotação indica que o método é apenas de leitura no banco de dados
    public Usuario.Role buscarRolePorUsername(String username) { // Método para buscar a role (papel) de um usuário pelo nome de usuário
        return usuarioRepository.findRoleByUsername(username); // Retorna a role do usuário pelo nome de usuário
    }
}

