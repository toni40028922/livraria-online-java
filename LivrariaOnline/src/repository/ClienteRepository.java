// repository/ClienteRepository.java - ATUALIZADA
package repository;

import model.Cliente;
import util.PersistenceUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository {
    private List<Cliente> lista = new ArrayList<>();
    private final String FILE = "clientes.db";

    @SuppressWarnings("unchecked")
    public ClienteRepository() {
        Object o = PersistenceUtil.load(FILE);
        if (o != null) {
            try {
                lista = (List<Cliente>) o;
            } catch (ClassCastException e) {
                lista = new ArrayList<>();
            }
        }
    }

    public void salvar(Cliente c) {
        lista.removeIf(x -> x.getCpf().equals(c.getCpf()));
        lista.add(c);
        PersistenceUtil.save(lista, FILE);
    }

    public List<Cliente> listar() {
        return new ArrayList<>(lista);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return lista.stream()
            .filter(c -> c.getCpf().equals(cpf))
            .findFirst();
    }
    
    public Optional<Cliente> buscarPorEmail(String email) {
        return lista.stream()
            .filter(c -> c.getEmail().equals(email))
            .findFirst();
    }
}