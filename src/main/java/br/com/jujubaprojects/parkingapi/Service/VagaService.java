package br.com.jujubaprojects.parkingapi.Service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.jujubaprojects.parkingapi.Entity.Vaga;
import br.com.jujubaprojects.parkingapi.Entity.Vaga.StatusVaga;
import br.com.jujubaprojects.parkingapi.Repository.VagaRepository;
import br.com.jujubaprojects.parkingapi.exception.CodigoUniqueViolationException;
import br.com.jujubaprojects.parkingapi.exception.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VagaService {
    
    private static final StatusVaga LIVRE = null;
    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar(Vaga vaga) {
        try {
            return vagaRepository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException(
                    String.format("Vaga com código '%s' já cadastrada", vaga.getCodigo())
            );
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo) {
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com código '%s' não foi encontrada", codigo))
        );
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(// Aqui está o erro , não funciona o LIVRE 
                () -> new EntityNotFoundException("Nenhuma vaga livre foi encontrada")
        );
    }


}
