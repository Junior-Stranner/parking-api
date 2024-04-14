package br.com.jujubaprojects.parkingapi.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.jujubaprojects.parkingapi.Entity.Cliente;
import br.com.jujubaprojects.parkingapi.Entity.ClienteVaga;
import br.com.jujubaprojects.parkingapi.Entity.Vaga;
import br.com.jujubaprojects.parkingapi.Utils.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class Estacionamento {
    

    private final ClienteVagasService clienteVagasService;
    private final ClienteService clienteService;
    private final VagaService vagaService;

    public ClienteVaga checkIn(ClienteVaga clienteVaga){
        Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
        clienteVaga.setCliente(cliente);

        Vaga vaga = vagaService.buscarPorVagaLivre();
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);
        clienteVaga.setVaga(vaga);

        clienteVaga.setDataEntrada(LocalDateTime.now());
        clienteVaga.setRebico(EstacionamentoUtils.gerarRecibo());

        return clienteVagasService.salvar(clienteVaga);
    }
}
