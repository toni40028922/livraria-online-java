package model;

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
    
    public void imprimir() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            NOTA FISCAL");
        System.out.println("=".repeat(50));
        System.out.println("Número: " + numero);
        System.out.println("Data: " + dataEmissao);
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Tipo: " + tipo);
        System.out.println("-".repeat(50));
        
        if (pedido.getItens() instanceof Map) {
            // Se for Map (com quantidades)
            Map<Livro, Integer> itens = (Map<Livro, Integer>) pedido.getItens();
            for (Map.Entry<Livro, Integer> entry : itens.entrySet()) {
                Livro livro = entry.getKey();
                int quantidade = entry.getValue();
                double subtotal = livro.getPreco() * quantidade;
                System.out.printf("%s x%d = R$ %.2f\n", 
                    livro.getTitulo(), quantidade, subtotal);
            }
        } else {
            // Se for List (1 unidade cada)
            for (Livro livro : pedido.getItens()) {
                System.out.printf("%s = R$ %.2f\n", livro.getTitulo(), livro.getPreco());
            }
        }
        
        System.out.println("-".repeat(50));
        System.out.printf("TOTAL: R$ %.2f\n", pedido.getTotal());
        System.out.println("=".repeat(50));
        System.out.println("Obrigado pela preferência!");
        System.out.println("=".repeat(50) + "\n");
    }
    
    // Getters
    public int getNumero() { return numero; }
    public Pedido getPedido() { return pedido; }
    public Cliente getCliente() { return cliente; }
    public LocalDateTime getDataEmissao() { return dataEmissao; }
    public String getTipo() { return tipo; }
}