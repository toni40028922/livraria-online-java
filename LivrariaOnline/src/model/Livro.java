package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;
    private String isbn;
    private String titulo;
    private Set<Autor> autores = new HashSet<>();
    private double preco;
    private String genero;

    public Livro() {}

    public Livro(String isbn, String titulo, double preco, String genero) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.preco = preco;
        this.genero = genero;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Set<Autor> getAutores() { return autores; }
    public void addAutor(Autor a) { autores.add(a); }
    public void removeAutor(Autor a) { autores.remove(a); }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    @Override
    public String toString() {
        return String.format("ISBN:%s | %s | R$ %.2f | Autores: %s", isbn, titulo, preco, autores);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro)) return false;
        Livro livro = (Livro) o;
        return Objects.equals(isbn, livro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
