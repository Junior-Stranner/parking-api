package br.com.jujubaprojects.parkingapi.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {
    private static final double PRIMEIROS_15_MINUTES = 5.00;
    private static final double PRIMEIROS_60_MINUTES = 9.25;
    private static final double ADICIONAL_15_MINUTES = 1.75;
    private static final double DESCONTO_PERCENTUAL = 0.30;

    public static BigDecimal calcularCusto(LocalDateTime entrada, LocalDateTime saida) {
        // Calcula a duração em minutos entre entrada e saída
        long minutos = entrada.until(saida, ChronoUnit.MINUTES);
        double total = 0.0; // Inicializa o custo total como zero

        // Verifica se a duração do estaiconamento é menor ou igual a 15 minutos
        if (minutos <= 15) {
            total = PRIMEIROS_15_MINUTES; // Atribui o custo para os primeiros 15 minutos
        } else if (minutos <= 60) { // Verifica se a duração do estacionamento é menor ou igual a 60 minutos
            total = PRIMEIROS_60_MINUTES; // Atribui o custo para os primeiros 60 minutos
        } else { // Se a duração do estacionamento for maior que 60 minutos
            long minutosAdicionais = minutos - 60; // Calcula minutos adicionais além da primeira hora
            Double partesTotais = ((double) minutosAdicionais / 15); // Calcula partes de 15 minutos além da primeira hora

            if (partesTotais > partesTotais.intValue()) { // Verifica se há partes fracionadas de 15 minutos
                // Calcula o custo total para os primeiros 60 minutos mais o custo adicional para os minutos restantes
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * (partesTotais.intValue() + 1));
            } else { // Se não houver partes fracionadas de 15 minutos
                // Calcula o custo total para os primeiros 60 minutos mais o custo adicional para os minutos restantes
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * partesTotais.intValue());
            }
        }

        // Retorna o custo total como um BigDecimal com 2 casas decimais arredondadas usando o modo HALF_EVEN
        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calcularDesconto(BigDecimal custo, long numeroDeVezes) {
        // Calcula o desconto se o número de vezes estacionado for múltiplo de 10
        BigDecimal desconto = ((numeroDeVezes > 0) && (numeroDeVezes % 10 == 0))
                ? custo.multiply(new BigDecimal(DESCONTO_PERCENTUAL)) // Aplica a porcentagem de desconto ao custo
                : new BigDecimal(0); // Caso contrário, nenhum desconto
        // Retorna o desconto como um BigDecimal com 2 casas decimais arredondadas usando o modo HALF_EVEN
        return desconto.setScale(2, RoundingMode.HALF_EVEN);
    }

    //2023-03-16T15:23:48.616463500
    // 20230316-152121

    public static String gerarRecibo(){ // Método estático para gerar um recibo
        LocalDateTime date = LocalDateTime.now(); // Obtém a data e hora atual
        String recibo = date.toString().substring(0,19); // Converte a data e hora em uma string e extrai os primeiros 19 caracteres (ano, mês, dia, hora, minutos e segundos)
        return recibo.replace("-", "") // Remove o caractere "-" da string
                .replace(":", "") // Remove o caractere ":" da string
                .replace("T", "-"); // Substitui o caractere "T" por "-"
    }

}
