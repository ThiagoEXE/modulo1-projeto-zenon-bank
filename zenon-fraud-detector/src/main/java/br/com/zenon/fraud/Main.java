package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        
        TransactionIngestor transactionIngestor = new TransactionIngestor("data/PS_20174392719_1491204439457_log.csv");
        /*List<Transaction> listaDeTransactions = transactionIngestor.lerArquivoCSV();
        
        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer();
        // a - Apenas transações onde isFraud == true, imprima o tamanho da lista.
        long qtdFraud = fraudAnalyzer.totalFraud(listaDeTransactions);
        System.out.println("1. Total de Fraudes: " + qtdFraud);

        // b - Imprima as 3 fraudes de maior valor (amount).
        System.out.println("2. Top 3 Fraudes de Maior Valor:");
        List<Transaction> tresMaioresValores = fraudAnalyzer.top3Fraud(listaDeTransactions);
        tresMaioresValores.forEach(t -> System.out.println(t.amount().setScale(2, RoundingMode.HALF_UP).toPlainString()));

        // c - Obter apenas os nomes dos clientes de origem (nameOrig) dessas fraudes e depois gere uma lista sem repetições (Set ou distinct) com os 5 maiores clientes suspeitos.
        List<Transaction> top5Fraud = fraudAnalyzer.top5ClientesSuspeitos(listaDeTransactions);
        top5Fraud.forEach(t -> System.out.println(t.nameOrig()));

        // d - Calcule o prejuízo total causado pelas fraudes (soma dos amount).
        BigDecimal TotalPrejuizoFraud = fraudAnalyzer.prejuizoTotalFraud(listaDeTransactions);
        System.out.println("4. Prejuízo Total: " + TotalPrejuizoFraud);

        // e - Conte quantas fraudes ocorreram por tipo de transação (CASH_OUT, TRANSFER, etc...).
        Map<Transaction.Type, Long> fraudesPorTipo = fraudAnalyzer.contaFraudesPorTipo(listaDeTransactions);
        System.out.println("5. Fraudes por Tipo de Transação:");
        fraudesPorTipo.forEach((tipo, quantidade) -> System.out.println("- " +tipo + ": " + quantidade));*/
        Optional<Transaction> listaDeTransactions = transactionIngestor.lerArquivoCSV();
        long tempoInicial = System.nanoTime();
        TransactionRepository transactionListRepository = new TransactionListRepository();
        TransactionRepository transactionMapRepository = new TransactionMapRepository();
        String nome1 = "C12345";    
        Optional<Transaction> transacaoEncontrada = transactionMapRepository.buscaTransacao(listaDeTransactions, nome1);
        transacaoEncontrada.ifPresentOrElse(
                transaction -> System.out.println(transaction.toString()),
                () -> System.out.println("Transação não encontrada para o cliente: " + nome1)
        );
        String nome2 = "C1868032458";    
        Map<String, Transaction> transacaoEncontrada2 = transactionListRepository.buscaTransacao(listaDeTransactions, nome2);
        transacaoEncontrada2.ifPresentOrElse(
                transaction -> System.out.println(transaction.toString()),
                () -> System.out.println("Transação não encontrada para o cliente: " + nome2)
        );
        System.out.println("Tempo final de execução: " + (System.nanoTime() - tempoInicial));
    }

}
