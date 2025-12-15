package service; // ATUALIZADO EM 15/12/2025

import model.Carrinho;
import model.Livro;
import repository.LivroRepository;
import repository.EstoqueRepository;
import java.util.Optional;

public class CartService {
    private LivroRepository livroRepo = new LivroRepository();
    private EstoqueRepository estoqueRepo = new EstoqueRepository();

    public Carrinho criarCarrinho(String cpf) {
        return new Carrinho(cpf);
    }

    public boolean adicionarAoCarrinho(Carrinho carrinho, String isbn, int quantidade) {
        Optional<Livro> livroOpt = livroRepo.buscarPorIsbn(isbn);
        if (livroOpt.isEmpty()) {
            System.out.println("❌ Livro não encontrado!");
            return false;
        }
        
        Livro livro = livroOpt.get();
        
        // Verifica estoque
        int estoqueDisponivel = estoqueRepo.getQuantidade(isbn);
        if (estoqueDisponivel < quantidade) {
            System.out.println("❌ Estoque insuficiente!");
            System.out.println("   Disponível: " + estoqueDisponivel + " unidades");
            return false;
        }
        
        // Verifica se já tem no carrinho
        int quantidadeNoCarrinho = carrinho.getItens().getOrDefault(livro, 0);
        if (quantidadeNoCarrinho + quantidade > estoqueDisponivel) {
            System.out.println("❌ Não há estoque suficiente!");
            System.out.println("   Já no carrinho: " + quantidadeNoCarrinho + " unidades");
            System.out.println("   Disponível total: " + estoqueDisponivel + " unidades");
            return false;
        }
        
        carrinho.adicionar(livro, quantidade);
        return true;
    }

    public boolean removerDoCarrinho(Carrinho carrinho, String isbn, int quantidade) {
        Optional<Livro> livroOpt = livroRepo.buscarPorIsbn(isbn);
        if (livroOpt.isEmpty()) return false;
        
        return carrinho.remover(livroOpt.get(), quantidade);
    }
    
    public boolean removerTodosDoCarrinho(Carrinho carrinho, String isbn) {
        Optional<Livro> livroOpt = livroRepo.buscarPorIsbn(isbn);
        if (livroOpt.isEmpty()) return false;
        
        carrinho.getItens().remove(livroOpt.get());
        return true;
    }

    public double calcularTotal(Carrinho carrinho) {
        return carrinho.calcularTotal();
    }
    
    public boolean carrinhoVazio(Carrinho carrinho) {
        return carrinho.estaVazio();
    }
}
