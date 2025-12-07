package service;

import model.Livro;
import repository.LivroRepository;
import repository.EstoqueRepository;

import java.util.List;
import java.util.Optional;

public class CatalogService {
    private LivroRepository livroRepo = new LivroRepository();
    private EstoqueRepository estoqueRepo = new EstoqueRepository();

    public void cadastrarLivro(Livro l, int quantidade) {
        livroRepo.salvar(l);
        estoqueRepo.setQuantidade(l.getIsbn(), quantidade);
    }

    public List<Livro> listarCatalogo() {
        return livroRepo.listar();
    }

    public Optional<Livro> buscarPorIsbn(String isbn) {
        return livroRepo.buscarPorIsbn(isbn);
    }

    public List<Livro> buscarPorTitulo(String termo) {
        return livroRepo.buscarPorTitulo(termo);
    }

    public boolean removerLivro(String isbn) {
        return livroRepo.remover(isbn);
    }

    public int estoque(String isbn) {
        return estoqueRepo.getQuantidade(isbn);
    }
}
