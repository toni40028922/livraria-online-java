// service/CheckoutService.java - COM PAGAMENTO
package service;  // ATUALIZADO EM 09/12/2025

import model.*;
import repository.EstoqueRepository;
import repository.PedidoRepository;
import java.util.Map;

public class CheckoutService {
    private EstoqueRepository estoqueRepo = new EstoqueRepository();
    private PedidoRepository pedidoRepo = new PedidoRepository();
    
    // Mtodo principal
    public NotaFiscal checkout(Carrinho carrinho, String tipo, Cliente cliente) {
        // 1. Verificar estoque
        if (!verificarEstoque(carrinho)) {
            return null;
        }
        
        // 2. Reduzir estoque
        reduzirEstoque(carrinho);
        
        // 3. Criar pedido
        Pedido pedido = new Pedido(carrinho.getClienteCpf(), carrinho.getItens(), tipo); // esta linha ainda continua com x
        
        // 4. Salvar pedido
        pedidoRepo.salvar(pedido);
        
        // 5. Gerar Nota Fiscal
        NotaFiscal notaFiscal = new NotaFiscal(pedido, cliente, tipo);
        
        return notaFiscal;
    }
    
    // Mtodo para checkout com pagamento
    public boolean checkoutComPagamento(Carrinho carrinho, String tipo, Cliente cliente, 
                                       Pagamento.MetodoPagamento metodo) {
        
        NotaFiscal notaFiscal = checkout(carrinho, tipo, cliente);
        
        if (notaFiscal == null) {
            return false;
        }
        
        // Cria pagamento
        Pagamento pagamento = new Pagamento(
            notaFiscal.getPedido().getId(),
            carrinho.calcularTotal(), // esta linha ainda continua com x
            metodo
        );
        
        // Processa pagamento
        boolean aprovado = pagamento.processarPagamento("");
        
        if (aprovado) {
            System.out.println(notaFiscal.imprimir());
            System.out.println(pagamento.gerarComprovante());
            carrinho.limpar(); // esta linha ainda continua com x
            return true;
        } else {
            System.out.println(" Pagamento recusado!");
            return false;
        }
    }
    
    private boolean verificarEstoque(Carrinho carrinho) {
        for (Map.Entry<Livro, Integer> entry : carrinho.getItens().entrySet()) { // esta linha ainda continua com x
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            int estoque = estoqueRepo.getQuantidade(livro.getIsbn());
            
            if (estoque < quantidade) {
                System.out.println("Estoque insuficiente: " + livro.getTitulo());
                return false;
            }
        }
        return true;
    }
    
    private void reduzirEstoque(Carrinho carrinho) {
        for (Map.Entry<Livro, Integer> entry : carrinho.getItens().entrySet()) { // esta linha ainda continua com x
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            estoqueRepo.reduzir(livro.getIsbn(), quantidade);
        }
    }
}

