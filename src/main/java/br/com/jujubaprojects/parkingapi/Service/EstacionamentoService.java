package br.com.jujubaprojects.parkingapi.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.com.jujubaprojects.parkingapi.Entity.Cliente;
import br.com.jujubaprojects.parkingapi.Entity.ClienteVaga;
import br.com.jujubaprojects.parkingapi.Entity.Vaga;
import br.com.jujubaprojects.parkingapi.Utils.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EstacionamentoService {
    

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;


   // Método para registrar o check-in de um cliente em uma vaga de estacionamento
@Transactional
public ClienteVaga checkIn(ClienteVaga clienteVaga) {
    // Busca o cliente pelo CPF fornecido na instância de ClienteVaga
    Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
    // Define o cliente encontrado como o cliente associado à instância de ClienteVaga
    clienteVaga.setCliente(cliente);

    // Busca uma vaga livre no estacionamento
    Vaga vaga = vagaService.buscarPorVagaLivre();
    // Define o status da vaga como OCUPADA
    vaga.setStatus(Vaga.StatusVaga.OCUPADA);
    // Associa a vaga encontrada à instância de ClienteVaga
    clienteVaga.setVaga(vaga);

    // Define a data e hora de entrada como o momento atual
    clienteVaga.setDataEntrada(LocalDateTime.now());
    // Gera um recibo para o cliente
    clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

    // Salva a instância de ClienteVaga no banco de dados e retorna
    return clienteVagaService.salvar(clienteVaga);
}

// Método para registrar o check-out de um cliente com base no recibo fornecido
@Transactional
public ClienteVaga checkOut(String recibo) {
    // Busca a instância de ClienteVaga associada ao recibo fornecido
    ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);

    // Define a data e hora de saída como o momento atual
    LocalDateTime dataSaida = LocalDateTime.now();

    // Calcula o custo total do estacionamento com base na data e hora de entrada e saída
    BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida);
    // Define o valor calculado como o valor associado à instância de ClienteVaga
    clienteVaga.setValor(valor);

    // Obtém o total de vezes que o cliente utilizou o estacionamento completo
    long totalDeVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());

    // Calcula o desconto com base no valor total e no total de vezes utilizado
    BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalDeVezes);
    // Define o desconto calculado como o desconto associado à instância de ClienteVaga
    clienteVaga.setDesconto(desconto);

    // Define a data e hora de saída como o momento atual
    clienteVaga.setDataSaida(dataSaida);
    // Define o status da vaga associada à instância de ClienteVaga como LIVRE
    clienteVaga.getVaga().setStatus(Vaga.StatusVaga.LIVRE);

    // Salva a instância de ClienteVaga no banco de dados e retorna
    return clienteVagaService.salvar(clienteVaga);
}



}
