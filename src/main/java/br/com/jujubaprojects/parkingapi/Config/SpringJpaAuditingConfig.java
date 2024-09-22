package br.com.jujubaprojects.parkingapi.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// Habilita o suporte para auditoria no JPA (como a criação e modificação automática de registros)
@EnableJpaAuditing
// Define esta classe como uma configuração do Spring
@Configuration
public class SpringJpaAuditingConfig implements AuditorAware<String> {
    
    // Sobrescreve o método que retorna o auditor atual (usuário autenticado)
    @Override
    public Optional<String> getCurrentAuditor() {
        
        // Obtém o objeto de autenticação atual do contexto de segurança do Spring
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se a autenticação existe e se o usuário está autenticado
        if (authentication != null && authentication.isAuthenticated()) {

            // Retorna o nome do usuário autenticado como o auditor atual
            return Optional.of(authentication.getName());
        }

        // Retorna null se não houver um usuário autenticado
        return null;
    }
}

