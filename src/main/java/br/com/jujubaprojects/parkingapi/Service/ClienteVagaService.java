package br.com.jujubaprojects.parkingapi.Service;

import br.com.jujubaprojects.parkingapi.Entity.ClienteVaga;
import br.com.jujubaprojects.parkingapi.Repository.ClienteVagaRepository;
import br.com.jujubaprojects.parkingapi.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Service
public class ClienteVagaService {


    private final ClienteVagaRepository repository;

    @Transactional
    public ClienteVaga salvar(ClienteVaga clienteVaga) {
        return repository.save(clienteVaga);
    }

    @Transactional(readOnly = true)
    public ClienteVaga buscarPorRecibo(String recibo) {
        return repository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
                () -> new EntityNotFoundException((
                        String.format("Recibo '%s' não encontrado no sistema ou check-out já realizado", recibo))
                )
        );
    }

}
