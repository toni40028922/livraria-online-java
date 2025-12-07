package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrinho implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clienteCpf;
    private List<Livro> itens = new ArrayList<>();

    public Carrinho() {}

    public Carrinho(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    public String getClienteCpf() { return clienteCpf; }

    public List<Livro> getItens() { return itens; }

    public void adicionar(Livro l) { itens.add(l); }

    public boolean removerPorIsbn(String isbn) {
        return itens.removeIf(l -> l.getIsbn().equals(isbn));
    }

    public double total() {
        return itens.stream().mapToDouble(Livro::getPreco).sum();
    }

    @Override
    public String toString() {
        return String.format("Carrinho[%s] total=R$ %.2f itens=%d", clienteCpf, total(), itens.size());
    }
}
