package repository; // ATUALIZADO EM 09/12/2025

import util.PersistenceUtil;
import java.util.*;

public class EstoqueRepository {
    private Map<String, Integer> estoque = new HashMap<>();
    private final String FILE = "estoque.db";

    @SuppressWarnings("unchecked")
    public EstoqueRepository() {
        Object o = PersistenceUtil.load(FILE);
        if (o != null) {
            try {
                estoque = (Map<String, Integer>) o;
            } catch (ClassCastException e) {
                estoque = new HashMap<>();
            }
        }
    }

    public synchronized void setQuantidade(String isbn, int q) {
        estoque.put(isbn, q);
        PersistenceUtil.save(estoque, FILE);
    }

    public synchronized boolean reduzir(String isbn, int q) {
        Integer atual = estoque.getOrDefault(isbn, 0);
        if (atual < q) return false;
        estoque.put(isbn, atual - q);
        PersistenceUtil.save(estoque, FILE);
        return true;
    }

    public int getQuantidade(String isbn) {
        return estoque.getOrDefault(isbn, 0);
    }

    public Map<String, Integer> all() {
        return new HashMap<>(estoque);
    }
}
