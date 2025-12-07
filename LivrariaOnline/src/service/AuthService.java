// service/AuthService.java (nova ou atualizada)
package service;

import model.Cliente;
import repository.ClienteRepository;
import java.util.Optional;

public class AuthService {
    private ClienteRepository clienteRepo = new ClienteRepository();
    private Cliente clienteLogado = null;
    
    // Cadastro completo
    public boolean cadastrar(String cpf, String nome, String email, String senha) {
        // Validar CPF único
        if (clienteRepo.buscarPorCpf(cpf).isPresent()) {
            return false;
        }
        
        Cliente cliente = new Cliente(cpf, nome, email, senha);
        clienteRepo.salvar(cliente);
        return true;
    }
    
    // Login com senha
    public boolean login(String cpf, String senha) {
        Optional<Cliente> clienteOpt = clienteRepo.buscarPorCpf(cpf);
        
        if (clienteOpt.isPresent() && clienteOpt.get().validarSenha(senha)) {
            this.clienteLogado = clienteOpt.get();
            return true;
        }
        return false;
    }
    
    // Recuperar senha (simulação)
    public boolean recuperarSenha(String cpf, String email) {
        Optional<Cliente> clienteOpt = clienteRepo.buscarPorCpf(cpf);
        
        if (clienteOpt.isPresent() && clienteOpt.get().getEmail().equals(email)) {
            // Em sistema real, enviaria email. Aqui só simulamos
            System.out.println("Email de recuperação enviado para: " + email);
            System.out.println("Sua senha é: " + clienteOpt.get().getSenha()); // Na prática, nunca faça isso!
            return true;
        }
        return false;
    }
    
    // Atualizar dados do cliente
    public boolean atualizarPerfil(String novoEmail, String novoEndereco) {
        if (clienteLogado == null) return false;
        
        clienteLogado.setEmail(novoEmail);
        clienteLogado.setEndereco(novoEndereco);
        clienteRepo.salvar(clienteLogado);
        return true;
    }
    
    // Logout
    public void logout() {
        this.clienteLogado = null;
    }
    
    // Getters
    public Cliente getClienteLogado() { return clienteLogado; }
    public boolean isLogado() { return clienteLogado != null; }
}