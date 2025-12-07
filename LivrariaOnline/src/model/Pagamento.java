// model/Pagamento.java (nova classe)
package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum MetodoPagamento {
        CARTAO_CREDITO,
        PIX,
        BOLETO
    }
    
    public enum StatusPagamento {
        PENDENTE,
        APROVADO,
        RECUSADO,
        CANCELADO
    }
    
    private String id;
    private String pedidoId;
    private double valor;
    private MetodoPagamento metodo;
    private StatusPagamento status;
    private LocalDateTime dataPagamento;
    private String codigoPix; // Para pagamentos via PIX
    private String ultimosDigitosCartao; // Para pagamentos com cartão
    
    public Pagamento(String pedidoId, double valor, MetodoPagamento metodo) {
        this.id = "PAY" + System.currentTimeMillis();
        this.pedidoId = pedidoId;
        this.valor = valor;
        this.metodo = metodo;
        this.status = StatusPagamento.PENDENTE;
        this.dataPagamento = LocalDateTime.now();
        
        if (metodo == MetodoPagamento.PIX) {
            // Gerar código PIX fake (em sistema real seria um QR code)
            this.codigoPix = "00020126580014BR.GOV.BCB.PIX0136" + 
                            System.currentTimeMillis() + "5204000053039865404" + 
                            String.format("%.2f", valor) + "5802BR5925LIVRARIA ONLINE 6009SAO PAULO62070503***6304";
        }
    }
    
    // Método para simular processamento do pagamento
    public boolean processarPagamento(String dadosPagamento) {
        try {
            Thread.sleep(1000); // Simula delay de processamento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simulação: 90% de chance de aprovação
        boolean aprovado = Math.random() > 0.1;
        
        if (aprovado) {
            this.status = StatusPagamento.APROVADO;
            
            if (this.metodo == MetodoPagamento.CARTAO_CREDITO) {
                // Guarda últimos 4 dígitos
                if (dadosPagamento.length() >= 4) {
                    this.ultimosDigitosCartao = dadosPagamento.substring(dadosPagamento.length() - 4);
                }
            }
            
            return true;
        } else {
            this.status = StatusPagamento.RECUSADO;
            return false;
        }
    }
    
    // Método para gerar comprovante
    public String gerarComprovante() {
        StringBuilder comprovante = new StringBuilder();
        comprovante.append("=".repeat(50)).append("\n");
        comprovante.append("         COMPROVANTE DE PAGAMENTO         \n");
        comprovante.append("=".repeat(50)).append("\n");
        comprovante.append("ID do Pagamento: ").append(id).append("\n");
        comprovante.append("Pedido: ").append(pedidoId).append("\n");
        comprovante.append("Valor: R$ ").append(String.format("%.2f", valor)).append("\n");
        comprovante.append("Método: ").append(metodo.toString().replace("_", " ")).append("\n");
        comprovante.append("Status: ").append(status.toString()).append("\n");
        comprovante.append("Data: ").append(dataPagamento).append("\n");
        
        if (metodo == MetodoPagamento.PIX && codigoPix != null) {
            comprovante.append("\n--- PIX ---\n");
            comprovante.append("Código PIX (simulado):\n");
            comprovante.append(codigoPix.substring(0, 50)).append("...\n");
            comprovante.append("Copie e cole no app do seu banco!\n");
        }
        
        if (metodo == MetodoPagamento.CARTAO_CREDITO && ultimosDigitosCartao != null) {
            comprovante.append("\n--- Cartão de Crédito ---\n");
            comprovante.append("Final: **** **** **** ").append(ultimosDigitosCartao).append("\n");
        }
        
        comprovante.append("=".repeat(50)).append("\n");
        return comprovante.toString();
    }
    
    // Getters
    public String getId() { return id; }
    public String getPedidoId() { return pedidoId; }
    public double getValor() { return valor; }
    public MetodoPagamento getMetodo() { return metodo; }
    public StatusPagamento getStatus() { return status; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public String getCodigoPix() { return codigoPix; }
    public String getUltimosDigitosCartao() { return ultimosDigitosCartao; }
    
    @Override
    public String toString() {
        return String.format("Pagamento %s | %s | R$ %.2f | %s", 
                           id, metodo, valor, status);
    }
}