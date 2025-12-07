package service;

import repository.EstoqueRepository;
import repository.PedidoRepository;
import model.Pedido;
import java.util.List;
import java.util.Map;

public class ReportService {
    private EstoqueRepository estoqueRepo = new EstoqueRepository();
    private PedidoRepository pedidoRepo = new PedidoRepository();

    public Map<String,Integer> relatorioEstoque() {
        return estoqueRepo.all();
    }

    public List<Pedido> relatorioVendas() {
        return pedidoRepo.listar();
    }

    public double totalVendas() {
        return pedidoRepo.listar().stream().mapToDouble(Pedido::getTotal).sum();
    }
}
