package repository;

import model.Pedido;
import util.PersistenceUtil;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {
    private List<Pedido> pedidos = new ArrayList<>();
    private final String FILE = "pedidos.db";

    @SuppressWarnings("unchecked")
    public PedidoRepository() {
        Object o = PersistenceUtil.load(FILE);
        if (o != null) {
            try {
                pedidos = (List<Pedido>) o;
            } catch (ClassCastException e) {
                pedidos = new ArrayList<>();
            }
        }
    }

    public synchronized void salvar(Pedido p) {
        pedidos.add(p);
        PersistenceUtil.save(pedidos, FILE);
    }

    public List<Pedido> listar() {
        return new ArrayList<>(pedidos);
    }
}
