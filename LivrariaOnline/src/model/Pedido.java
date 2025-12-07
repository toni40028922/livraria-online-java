package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String clienteCpf;
    private List<Livro> itens;
    private double total;
    private LocalDateTime dataHora;

    public Pedido() {}

    public Pedido(String clienteCpf, List<Livro> itens, double total) {
        this.id = UUID.randomUUID().toString();
        this.clienteCpf = clienteCpf;
        this.itens = itens;
        this.total = total;
        this.dataHora = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getClienteCpf() { return clienteCpf; }
    public List<Livro> getItens() { return itens; }
    public double getTotal() { return total; }
    public LocalDateTime getDataHora() { return dataHora; }

    @Override
    public String toString() {
        return String.format("Pedido %s | cliente=%s | total=R$ %.2f | itens=%d | %s",
            id, clienteCpf, total, itens.size(), dataHora.toString());
    }
}
