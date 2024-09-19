package br.com.jujubaprojects.parkingapi.Service;

import br.com.jujubaprojects.parkingapi.Entity.Cliente;
import br.com.jujubaprojects.parkingapi.Repository.ClienteRepository;
import br.com.jujubaprojects.parkingapi.Repository.Projection.ClienteProjection;
import br.com.jujubaprojects.parkingapi.exception.CpfUniqueViolationException;
import br.com.jujubaprojects.parkingapi.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

  
   
    @Transactional
    public Cliente salvar(Cliente cliente) throws CpfUniqueViolationException {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(
                    String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", cliente.getCpf())
            );
        }
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<ClienteProjection> buscarTodos(Pageable pageable) {
        return clienteRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorUsuarioId(Long id) {
        return clienteRepository.findByUsuarioId(id);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorCpf(String cpf) {
       return clienteRepository.findByCpf(cpf).orElseThrow(
        () -> new EntityNotFoundException("Cliente com CPF '%s' não encontrado " +cpf)
       );
     }

}
