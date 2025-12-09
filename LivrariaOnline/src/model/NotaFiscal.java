package model; // ATUALIZADO EM 09/12/2025

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

public class NotaFiscal implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contador = 1;
    
    private int numero;
    private Pedido pedido;
    private Cliente cliente;
    private LocalDateTime dataEmissao;
    private String tipo; // "COMPRA" ou "ALUGUEL"
    
    public NotaFiscal(Pedido pedido, Cliente cliente, String tipo) {
        this.numero = contador++;
        this.pedido = pedido;
        this.cliente = cliente;
        this.dataEmissao = LocalDateTime.now();
        this.tipo = tipo;
    }
    
    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(50)).append("\n");
        sb.append("            NOTA FISCAL\n");
        sb.append("=".repeat(50)).append("\n");
        sb.append("Número: ").append(numero).append("\n");
        sb.append("Data: ").append(dataEmissao).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append("\n");
        sb.append("CPF: ").append(cliente.getCpf()).append("\n");
        sb.append("Tipo: ").append(tipo).append("\n");
        sb.append("-".repeat(50)).append("\n");
        
        // Adiciona os itens do pedido
        Map<Livro, Integer> itens = pedido.getItens();
        
        for (Map.Entry<Livro, Integer> entry : itens.entrySet()) {
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            double subtotal = livro.getPreco() * quantidade;
            sb.append(String.format("%s x%d = R$ %.2f\n", 
                livro.getTitulo(), quantidade, subtotal));
        }
        
        sb.append("-".repeat(50)).append("\n");
        sb.append(String.format("TOTAL: R$ %.2f\n", pedido.getTotal()));
        sb.append("=".repeat(50)).append("\n");
        sb.append("Obrigado pela preferência!\n");
        sb.append("=".repeat(50)).append("\n");
        
        return sb.toString();
    }
    
    // Getters
    public int getNumero() { return numero; }
    public Pedido getPedido() { return pedido; }
    public Cliente getCliente() { return cliente; }
    public LocalDateTime getDataEmissao() { return dataEmissao; }
    public String getTipo() { return tipo; }
}
