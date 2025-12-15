package model; // ATUALIZADO EM 09/12/2025

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Carrinho implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clienteCpf;
    private Map<Livro, Integer> itens = new HashMap<>();
    
    public Carrinho() {}
    
    public Carrinho(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }
    
    public String getClienteCpf() { return clienteCpf; }
    public Map<Livro, Integer> getItens() { return itens; }
    
    public void adicionar(Livro livro, int quantidade) {
        itens.put(livro, itens.getOrDefault(livro, 0) + quantidade);
    }
    
    public boolean remover(Livro livro, int quantidade) {
        if (!itens.containsKey(livro)) return false;
        
        int qtdAtual = itens.get(livro);
        if (quantidade >= qtdAtual) {
            itens.remove(livro);
        } else {
            itens.put(livro, qtdAtual - quantidade);
        }
        return true;
    }
    
    // ADICIONE ESTES:
    public double calcularTotal() {
        double total = 0;
        for (Map.Entry<Livro, Integer> entry : itens.entrySet()) {
            total += entry.getKey().getPreco() * entry.getValue();
        }
        return total;
    }
    
    public void limpar() {
        itens.clear();
    }
    
    public boolean estaVazio() {
        return itens.isEmpty();
    }
    
    @Override
    public String toString() {
        return String.format("Carrinho[%s] total=R$ %.2f itens=%d", 
                           clienteCpf, calcularTotal(), itens.size());
    }
}

