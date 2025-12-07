// service/CheckoutService.java - COM PAGAMENTO
package service;

import model.*;
import repository.EstoqueRepository;
import repository.PedidoRepository;
import java.util.Map;

public class CheckoutService {
    private EstoqueRepository estoqueRepo = new EstoqueRepository();
    private PedidoRepository pedidoRepo = new PedidoRepository();
    
    // Novo: processar checkout com pagamento
    public Pagamento processarCheckoutComPagamento(Carrinho carrinho, String tipo, Cliente cliente, 
                                                  Pagamento.MetodoPagamento metodoPagamento, 
                                                  String dadosPagamento) {
        // 1. Verificar estoque
        if (!verificarEstoque(carrinho)) {
            return null;
        }
        
        // 2. Criar pedido
        Pedido pedido = criarPedido(carrinho, tipo);
        
        // 3. Criar pagamento
        Pagamento pagamento = new Pagamento(pedido.getId(), carrinho.calcularTotal(), metodoPagamento);
        
        // 4. Processar pagamento (simulado)
        boolean pagamentoAprovado = pagamento.processarPagamento(dadosPagamento);
        
        if (!pagamentoAprovado) {
            System.out.println("❌ Pagamento recusado! Tente outro método.");
            return pagamento;
        }
        
        // 5. Se pagamento aprovado, finalizar compra
        reduzirEstoque(carrinho);
        pedidoRepo.salvar(pedido);
        carrinho.limpar();
        
        // 6. Gerar Nota Fiscal
        NotaFiscal notaFiscal = new NotaFiscal(pedido, cliente, tipo);
        
        System.out.println("\n" + notaFiscal.imprimir());
        System.out.println(pagamento.gerarComprovante());
        
        return pagamento;
    }
    
    // Método antigo (mantido para compatibilidade)
    public NotaFiscal checkout(Carrinho carrinho, String tipo, Cliente cliente) {
        if (!verificarEstoque(carrinho)) {
            return null;
        }
        
        reduzirEstoque(carrinho);
        Pedido pedido = criarPedido(carrinho, tipo);
        pedidoRepo.salvar(pedido);
        carrinho.limpar();
        
        return new NotaFiscal(pedido, cliente, tipo);
    }
    
    private boolean verificarEstoque(Carrinho carrinho) {
        for (Map.Entry<Livro, Integer> entry : carrinho.getItens().entrySet()) {
            Livro livro = entry.getKey();
            int quantidadeDesejada = entry.getValue();
            int estoqueDisponivel = estoqueRepo.getQuantidade(livro.getIsbn());
            
            if (estoqueDisponivel < quantidadeDesejada) {
                System.out.println("Estoque insuficiente para: " + livro.getTitulo());
                System.out.println("Desejado: " + quantidadeDesejada + " | Disponível: " + estoqueDisponivel);
                return false;
            }
        }
        return true;
    }
    
    private void reduzirEstoque(Carrinho carrinho) {
        for (Map.Entry<Livro, Integer> entry : carrinho.getItens().entrySet()) {
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            estoqueRepo.reduzir(livro.getIsbn(), quantidade);
        }
    }
    
    private Pedido criarPedido(Carrinho carrinho, String tipo) {
        return new Pedido(carrinho.getClienteCpf(), carrinho.getItens(), tipo);
    }
}