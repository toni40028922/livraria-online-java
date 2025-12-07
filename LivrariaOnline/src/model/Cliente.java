// model/Cliente.java - ATUALIZADA
package model;

import java.io.Serializable;
import java.util.Objects;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cpf;
    private String nome;
    private String email;
    private String senha;        // NOVO
    private String endereco;     // NOVO
    
    public Cliente() {}
    
    public Cliente(String cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = "Não informado";
    }
    
    // Getters e Setters
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    // Método para validar senha
    public boolean validarSenha(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) <%s>", nome, cpf, email);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente c = (Cliente) o;
        return Objects.equals(cpf, c.cpf);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
