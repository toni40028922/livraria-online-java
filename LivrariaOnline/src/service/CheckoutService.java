// service/CheckoutService.java - COM PAGAMENTO
package service;  // ATUALIZADO EM 15/12/2025

import model.*;
import repository.EstoqueRepository;
import repository.PedidoRepository;
import java.util.Map;

public class CheckoutService {
    private EstoqueRepository estoqueRepo = new EstoqueRepository();
    private PedidoRepository pedidoRepo = new PedidoRepository();
    
    public NotaFiscal checkout(Carrinho carrinho, String tipo, Cliente cliente) {
        // 1. Verificar estoque
        if (!verificarEstoque(carrinho)) {
            System.out.println("❌ Falha na verificação de estoque!");
            return null;
        }
        
        // 2. Reduzir estoque
        boolean estoqueReduzido = reduzirEstoque(carrinho);
        if (!estoqueReduzido) {
            System.out.println("❌ Falha ao reduzir estoque!");
            return null;
        }
        
        // 3. Criar pedido
        Pedido pedido = new Pedido(carrinho.getClienteCpf(), carrinho.getItens(), tipo);
        
        // 4. Salvar pedido
        pedidoRepo.salvar(pedido);
        System.out.println("✅ Pedido salvo: #" + pedido.getId());
        
        // 5. Gerar Nota Fiscal
        NotaFiscal notaFiscal = new NotaFiscal(pedido, cliente, tipo);
        
        return notaFiscal;
    }
    
    private boolean verificarEstoque(Carrinho carrinho) {
        for (Map.Entry<Livro, Integer> entry : carrinho.getItens().entrySet()) {
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            int estoque = estoqueRepo.getQuantidade(livro.getIsbn());
            
            System.out.println("🔍 [DEBUG] ISBN: " + livro.getIsbn() + 
                    " | Estoque no repositório: " + estoque + 
                    " | Quantidade no catálogo: " + 
                    new service.CatalogService().estoque(livro.getIsbn()));
            
            System.out.println("🔍 Verificando estoque: " + livro.getTitulo() + 
                             " - Estoque: " + estoque + ", Necessário: " + quantidade);
            
            if (estoque < quantidade) {
                System.out.println("❌ Estoque insuficiente: " + livro.getTitulo());
                return false;
            }
        }
        return true;
    }
    
    private boolean reduzirEstoque(Carrinho carrinho) {
        boolean sucesso = true;
        for (Map.Entry<Livro, Integer> entry : carrinho.getItens().entrySet()) {
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            
            boolean reduzido = estoqueRepo.reduzir(livro.getIsbn(), quantidade);
            if (reduzido) {
                System.out.println("📉 Estoque reduzido: " + livro.getTitulo() + 
                                 " -" + quantidade + " unidades");
            } else {
                System.out.println("❌ Falha ao reduzir estoque de: " + livro.getTitulo());
                sucesso = false;
            }
        }
        return sucesso;
    }
}
