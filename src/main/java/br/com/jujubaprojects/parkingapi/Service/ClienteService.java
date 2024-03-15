package br.com.jujubaprojects.parkingapi.Service;

import br.com.jujubaprojects.parkingapi.Entity.Cliente;
import br.com.jujubaprojects.parkingapi.Repository.ClienteRepository;
import br.com.jujubaprojects.parkingapi.exception.CpfUniqueViolationException;
import br.com.jujubaprojects.parkingapi.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
   

    @SuppressWarnings("null")
    @Transactional
    public Cliente salvar(Cliente cliente) throws CpfUniqueViolationException {
        try {
            return clienteRepository.save(cliente);
        }catch (DataIntegrityViolationException ex){
            throw new CpfUniqueViolationException(String.format("CPF '%s' não pode ser cadastrado, já que existe no sistema " ,cliente.getCpf()));
        }
    }
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))
        );
    }

}
