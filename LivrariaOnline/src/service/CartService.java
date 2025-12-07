package service;

import model.Carrinho;
import model.Livro;
import repository.LivroRepository;
import java.util.Optional;

public class CartService {
    private LivroRepository livroRepo = new LivroRepository();

    public Carrinho criarCarrinho(String cpf) {
        return new Carrinho(cpf);
    }

    public boolean adicionarAoCarrinho(Carrinho c, String isbn) {
        Optional<Livro> op = livroRepo.buscarPorIsbn(isbn);
        if (op.isEmpty()) return false;
        c.adicionar(op.get());
        return true;
    }

    public boolean removerDoCarrinho(Carrinho c, String isbn) {
        return c.removerPorIsbn(isbn);
    }

    public double total(Carrinho c) {
        return c.total();
    }
}