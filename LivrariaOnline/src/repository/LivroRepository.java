package repository;

import model.Livro;
import util.PersistenceUtil;
import java.util.*;

public class LivroRepository {
    private Map<String, Livro> byIsbn = new HashMap<>(); // Map usado para indexação por ISBN
    private List<Livro> lista = new ArrayList<>(); // List para catálogo ordenado
    private final String FILE = "livros.db";

    @SuppressWarnings("unchecked")
    public LivroRepository() {
        Object o = PersistenceUtil.load(FILE);
        if (o != null) {
            try {
                List<Livro> l = (List<Livro>) o;
                lista = l;
                for (Livro lv : l) byIsbn.put(lv.getIsbn(), lv);
            } catch (ClassCastException e) {
                lista = new ArrayList<>();
            }
        }
    }

    public synchronized void salvar(Livro l) {
        if (!byIsbn.containsKey(l.getIsbn())) {
            lista.add(l);
        } else {
            // substituir
            lista.removeIf(x -> x.getIsbn().equals(l.getIsbn()));
            lista.add(l);
        }
        byIsbn.put(l.getIsbn(), l);
        PersistenceUtil.save(lista, FILE);
    }

    public List<Livro> listar() {
        return new ArrayList<>(lista);
    }

    public Optional<Livro> buscarPorIsbn(String isbn) {
        return Optional.ofNullable(byIsbn.get(isbn));
    }

    public List<Livro> buscarPorTitulo(String termo) {
        String t = termo.toLowerCase();
        List<Livro> out = new ArrayList<>();
        for (Livro l : lista) {
            if (l.getTitulo().toLowerCase().contains(t)) out.add(l);
        }
        return out;
    }

    public boolean remover(String isbn) {
        if (!byIsbn.containsKey(isbn)) return false;
        Livro l = byIsbn.remove(isbn);
        lista.remove(l);
        PersistenceUtil.save(lista, FILE);
        return true;
    }
}
