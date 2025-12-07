package model;

import java.io.Serializable;
import java.util.Objects;

public class Autor implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String email;

    public Autor() {}

    public Autor(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor)) return false;
        Autor autor = (Autor) o;
        return Objects.equals(email, autor.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return String.format("%s <%s>", nome, email);
    }
}
