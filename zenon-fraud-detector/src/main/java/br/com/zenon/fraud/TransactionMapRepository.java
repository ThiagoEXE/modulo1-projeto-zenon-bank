package br.com.zenon.fraud;

import java.util.Comparator;
import java.util.Optional;

public class TransactionMapRepository implements TransactionRepository {
    
    public Optional<Transaction> buscaTransacao(Optional<Transaction> listaDeTransactions, String nome) {
        Optional<Transaction> buscaTransacao = listaDeTransactions
                .stream()
                .sorted(Comparator.comparing(Transaction::nameOrig))
                .filter(t -> t.nameOrig().equals(nome))
                .findFirst();
        return buscaTransacao;
        
    }
}
