package service;

import model.Livro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CatalogServiceTest {

    @Test
    public void testCadastrarEBuscar() {
        CatalogService cs = new CatalogService();

        // Criar livro de teste
        Livro l = new Livro("9999", "Livro Teste JUnit", 45.0, "Aventura");

        // Cadastrar com estoque
        cs.cadastrarLivro(l, 10);

        // Verificar se busca por ISBN funciona
        var encontrado = cs.buscarPorIsbn("9999");
        Assertions.assertTrue(encontrado.isPresent());

        // Verificar se estoque está correto
        int estoque = cs.estoque("9999");
        Assertions.assertEquals(10, estoque);
    }
}
