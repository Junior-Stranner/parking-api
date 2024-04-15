package br.com.jujubaprojects.parkingapi.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jujubaprojects.parkingapi.Entity.ClienteVaga;
import br.com.jujubaprojects.parkingapi.Repository.ClienteVagaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteVagasService {
    
    private final ClienteVagaRepository clienteVagaRepository;

   

    @Transactional
    public ClienteVaga salvar(ClienteVaga clienteVaga){
        return clienteVagaRepository.save(clienteVaga);

    }

}
