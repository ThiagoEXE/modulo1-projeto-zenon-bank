package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FraudAnalyzer {

    /*Carregue 50.000 linhas em TransactionIngestore crie uma nova classe FraudAnalyzer que usa a Stream API para:
    * Apenas transações onde isFraud == true, imprima o tamanho da lista.
    * Imprima as 3 fraudes de maior valor (amount).
    * Obter apenas os nomes dos clientes de origem (nameOrig) dessas fraudes e depois gere uma lista sem repetições (Set ou distinct) com os 5 maiores clientes suspeitos.
    * Calcule o prejuízo total causado pelas fraudes (soma dos amount).
    * Conte quantas fraudes ocorreram por tipo de transação (CASH_OUT, TRANSFER, etc...).*/
    public long totalFraud(List<Transaction> listaDeTransactions) {

        long qtdFraud = listaDeTransactions.stream()
                .filter(Transaction::isFraud)
                .count();
        return qtdFraud;
    }

    public List<Transaction> top3Fraud(List<Transaction> listaDeTransactions) {
        List<Transaction> tresMaioresValores = listaDeTransactions.stream()
                .filter(t -> t != null && t.amount() != null)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(3)
                .toList();
        return tresMaioresValores;
    }

    public List<Transaction> top5ClientesSuspeitos(List<Transaction> listaDeTransactions) {
        List<Transaction> cincoMaioresSuspeitos = listaDeTransactions.stream()
                .filter(t -> t != null && t.amount() != null)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(5)
                .sorted(Comparator.comparing(
                        t -> t.nameOrig() != null ? Long.parseLong(t.nameOrig().replaceAll("\\D+", "")) : 0L
                ))
                .distinct()
                .toList();
        System.out.println("3. Clientes Suspeitos:");
        return cincoMaioresSuspeitos;
    }

    public BigDecimal prejuizoTotalFraud(List<Transaction> listaDeTransactions) {
        BigDecimal prejuizoTotal = listaDeTransactions
                .stream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return prejuizoTotal;

    }

    public Map<Transaction.Type, Long> contaFraudesPorTipo(List<Transaction> listaDeTransactions) {
        Map<Transaction.Type, Long> fraudesPorTipo = listaDeTransactions
                .stream()
                .filter(t -> t != null && t.type() != null)
                .collect( Collectors.groupingBy(
                        Transaction::type,
                        Collectors.counting()
                ));

        return fraudesPorTipo;
    }
}
