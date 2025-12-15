package app; // ATUALIZAO 09/12/2025  ||  (ATUALIZAO RECENTE - 13/12/2025)

import model.*;
import service.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Map;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static CatalogService catalog = new CatalogService();
    private static CartService cartService = new CartService();
    private static CheckoutService checkout = new CheckoutService();
    private static ReportService reports = new ReportService();
    private static AuthService auth = new AuthService();
    
 // ADICIONE ESTA LINHA:
    private static Carrinho carrinhoAtual = null;
    
    // Cor para terminal (opcional - deixa mais bonito)
    private static final String RESET = "\u001B[0m";
    private static final String VERDE = "\u001B[32m";
    private static final String AZUL = "\u001B[34m";
    private static final String AMARELO = "\u001B[33m";
    private static final String VERMELHO = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        System.out.println(CYAN + "=".repeat(60) + RESET);
        System.out.println(AZUL + "           LIVRARIA ONLINE - SISTEMA COMPLETO           " + RESET);
        System.out.println(CYAN + "=".repeat(60) + RESET);
        System.out.println("Sistema de gerenciamento de livros com cadastro de clientes,");
        System.out.println("carrinho de compras, nota fiscal e controle de estoque.");
        
        // Carregar alguns dados de exemplo para testes
        carregarDadosExemplo();
        
        boolean sistemaAtivo = true;
        
        while (sistemaAtivo) {
            if (!auth.isLogado()) {
                sistemaAtivo = menuVisitante();
            } else {
                sistemaAtivo = menuCliente();
            }
        }
        
        System.out.println(VERDE + "\nObrigado por usar a Livraria Online! At a prxima!" + RESET);
        sc.close();
    }
    
    // ========== MENU VISITANTE ==========
    private static boolean menuVisitante() {
        System.out.println(AMARELO + "\n" + "=".repeat(50) + RESET);
        System.out.println(CYAN + "           MENU VISITANTE           " + RESET);
        System.out.println(AMARELO + "=".repeat(50) + RESET);
        System.out.println("1.  Ver catlogo de livros");
        System.out.println("2.  Buscar livro");
        System.out.println("3.  Cadastrar nova conta");
        System.out.println("4.  Fazer login");
        System.out.println("5.  Recuperar senha");
        System.out.println("0.  Sair do sistema");
        System.out.print(VERDE + "\nEscolha uma opo: " + RESET);
        
        String opcao = sc.nextLine();
        
        switch (opcao) {
            case "1":
                listarCatalogo();
                break;
            case "2":
                buscarLivro();
                break;
            case "3":
                cadastrarCliente();
                break;
            case "4":
                fazerLogin();
                break;
            case "5":
                recuperarSenha();
                break;
            case "0":
                return false; // Sai do sistema
            default:
                System.out.println(VERMELHO + "Opo invlida! Tente novamente." + RESET);
        }
        return true; // Continua no sistema
    }
    
    // ========== MENU CLIENTE LOGADO ==========
    private static boolean menuCliente() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(AMARELO + "\n" + "=".repeat(50) + RESET);
        System.out.println(CYAN + "           MENU CLIENTE           " + RESET);
        System.out.println(AMARELO + "=".repeat(50) + RESET);
        System.out.println(" Usurio: " + VERDE + cliente.getNome() + RESET);
        System.out.println(" Email: " + cliente.getEmail());
        System.out.println("\n1.  Ver catlogo");
        System.out.println("2.  Buscar livro");
        System.out.println("3.  Gerenciar carrinho");
        System.out.println("4.  Finalizar compra");
        System.out.println("5.  Ver Nota Fiscal da ltima compra");
        System.out.println("6.  Meus pedidos");
        System.out.println("7.   Meu perfil");
        System.out.println("8.  Logout");
        System.out.println("0.  Sair do sistema");
        System.out.print(VERDE + "\nEscolha uma opo: " + RESET);
        
        String opcao = sc.nextLine();
        
        switch (opcao) {
            case "1":
                listarCatalogo();
                break;
            case "2":
                buscarLivro();
                break;
            case "3":
                gerenciarCarrinho();
                break;
            case "4":
                finalizarCompra();
                break;
            case "5":
                verUltimaNotaFiscal();
                break;
            case "6":
                meusPedidos();
                break;
            case "7":
                meuPerfil();
                break;
            case "8":
                auth.logout();
                System.out.println(VERDE + "Logout realizado com sucesso!" + RESET);
                break;
            case "0":
                return false; // Sai do sistema
            default:
                System.out.println(VERMELHO + "Opo invlida! Tente novamente." + RESET);
        }
        return true; // Continua no sistema
    }
    
    // ========== FUNES DO VISITANTE ==========
    
    private static void cadastrarCliente() {
        System.out.println(CYAN + "\n=== CADASTRO DE NOVO CLIENTE ===" + RESET);
        
        System.out.print("CPF (apenas nmeros): ");
        String cpf = sc.nextLine().trim();
        
        // Validar se CPF j existe
        if (cpf.length() != 11 || !cpf.matches("\\d+")) {
            System.out.println(VERMELHO + "CPF invlido! Use 11 nmeros." + RESET);
            return;
        }
        
        System.out.print("Nome completo: ");
        String nome = sc.nextLine().trim();
        
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        
        if (!email.contains("@")) {
            System.out.println(VERMELHO + "Email invlido!" + RESET);
            return;
        }
        
        System.out.print("Senha (mnimo 4 caracteres): ");
        String senha = sc.nextLine().trim();
        
        if (senha.length() < 4) {
            System.out.println(VERMELHO + "Senha muito curta!" + RESET);
            return;
        }
        
        if (auth.cadastrar(cpf, nome, email, senha)) {
            System.out.println(VERDE + "\n Cadastro realizado com sucesso!" + RESET);
            System.out.println("Faa login para comear a comprar.");
        } else {
            System.out.println(VERMELHO + "\n CPF j cadastrado no sistema!" + RESET);
        }
    }
    
    private static void fazerLogin() {
        System.out.println(CYAN + "\n=== LOGIN NO SISTEMA ===" + RESET);
        
        System.out.print("CPF: ");
        String cpf = sc.nextLine().trim();
        
        System.out.print("Senha: ");
        String senha = sc.nextLine().trim();
        
        if (auth.login(cpf, senha)) {
            Cliente cliente = auth.getClienteLogado();
            System.out.println(VERDE + "\n Login realizado com sucesso!" + RESET);
            System.out.println("Bem-vindo(a), " + cliente.getNome() + "!");
        } else {
            System.out.println(VERMELHO + "\n CPF ou senha incorretos!" + RESET);
            System.out.println("Verifique seus dados ou cadastre-se.");
        }
    }
    
    private static void recuperarSenha() {
        System.out.println(CYAN + "\n=== RECUPERAO DE SENHA ===" + RESET);
        
        System.out.print("Digite seu CPF: ");
        String cpf = sc.nextLine().trim();
        
        System.out.print("Digite seu email cadastrado: ");
        String email = sc.nextLine().trim();
        
        if (auth.recuperarSenha(cpf, email)) {
            System.out.println(VERDE + "\n Instrues enviadas para seu email!" + RESET);
        } else {
            System.out.println(VERMELHO + "\n Dados no encontrados!" + RESET);
            System.out.println("Verifique se o CPF e email esto corretos.");
        }
    }
    
    // ========== FUNES DO CATLOGO ==========
    
    private static void listarCatalogo() {
        System.out.println(CYAN + "\n=== CATLOGO DE LIVROS ===" + RESET);
        
        List<Livro> catalogo = catalog.listarCatalogo();
        
        if (catalogo.isEmpty()) {
            System.out.println("Nenhum livro cadastrado no sistema.");
        } else {
            System.out.println("Total de livros: " + catalogo.size());
            System.out.println("-".repeat(60));
            
            for (int i = 0; i < catalogo.size(); i++) {
                Livro livro = catalogo.get(i);
                int estoque = catalog.estoque(livro.getIsbn());
                String statusEstoque = estoque > 0 ? VERDE + "Disponvel" + RESET : VERMELHO + "Esgotado" + RESET;
                
                System.out.printf("%d. %s\n", i + 1, livro.getTitulo());
                System.out.printf("   Autor: %s\n", livro.getAutores());
                System.out.printf("   ISBN: %s | Gnero: %s\n", livro.getIsbn(), livro.getGenero());
                System.out.printf("   Preo: R$ %.2f | Estoque: %d (%s)\n", 
                    livro.getPreco(), estoque, statusEstoque);
                System.out.println("-".repeat(60));
            }
        }
    }
    
    private static void buscarLivro() {
        boolean buscando = true;
        
        while (buscando) {
            System.out.println(CYAN + "\n=== BUSCAR LIVRO ===" + RESET);
            System.out.println("1. Buscar por ISBN");
            System.out.println("2. Buscar por ttulo");
            System.out.println("0.   Voltar ao menu");
            System.out.print(VERDE + "Escolha: " + RESET);
            
            String opcao = sc.nextLine();
            
            switch (opcao) {
                case "1":
                    System.out.print("Digite o ISBN (ou 'voltar'): ");
                    String isbn = sc.nextLine().trim();
                    
                    if (isbn.equalsIgnoreCase("voltar")) {
                        System.out.println("Voltando...");
                        break;
                    }
                    
                    Optional<Livro> livro = catalog.buscarPorIsbn(isbn);
                    
                    if (livro.isPresent()) {
                        exibirDetalhesLivro(livro.get());
                        
                        // Pergunta se quer fazer outra busca
                        System.out.print("\nDeseja fazer outra busca? (S/N): ");
                        String continuar = sc.nextLine().trim();
                        if (!continuar.equalsIgnoreCase("S")) {
                            buscando = false;
                        }
                    } else {
                        System.out.println(VERMELHO + "Livro no encontrado!" + RESET);
                        System.out.print("Tentar novamente? (S/N): ");
                        String tentar = sc.nextLine().trim();
                        if (!tentar.equalsIgnoreCase("S")) {
                            buscando = false;
                        }
                    }
                    break;
                    
                case "2":
                    System.out.print("Digite parte do ttulo (ou 'voltar'): ");
                    String titulo = sc.nextLine().trim();
                    
                    if (titulo.equalsIgnoreCase("voltar")) {
                        System.out.println("Voltando...");
                        break;
                    }
                    
                    List<Livro> resultados = catalog.buscarPorTitulo(titulo);
                    exibirResultadosBusca(resultados);
                    
                    // Se no encontrou, pergunta se quer continuar
                    if (resultados.isEmpty()) {
                        System.out.print("\nDeseja tentar outra busca? (S/N): ");
                        String outra = sc.nextLine().trim();
                        if (!outra.equalsIgnoreCase("S")) {
                            buscando = false;
                        }
                    }
                    break;
                    
                case "0":
                    buscando = false;
                    System.out.println("Voltando ao menu...");
                    break;
                    
                default:
                    System.out.println(VERMELHO + "Opo invlida! Digite 1, 2 ou 0." + RESET);
            }
        }
    }
    
    private static void exibirDetalhesLivro(Livro livro) {
        System.out.println(CYAN + "\n=== DETALHES DO LIVRO ===" + RESET);
        System.out.println("Ttulo: " + livro.getTitulo());
        System.out.println("ISBN: " + livro.getIsbn());
        System.out.println("Autor(es): " + livro.getAutores());
        System.out.println("Gnero: " + livro.getGenero());
        System.out.printf("Preo: R$ %.2f\n", livro.getPreco());
        System.out.println("Estoque: " + catalog.estoque(livro.getIsbn()) + " unidades");
    }
    
    private static void exibirResultadosBusca(List<Livro> resultados) {
        if (resultados.isEmpty()) {
            System.out.println(VERMELHO + "Nenhum livro encontrado!" + RESET);
        } else {
            System.out.println(CYAN + "\n=== RESULTADOS DA BUSCA ===" + RESET);
            System.out.println("Encontrados " + resultados.size() + " livro(s):");
            
            for (int i = 0; i < resultados.size(); i++) {
                Livro livro = resultados.get(i);
                System.out.printf("%d. %s (%s) - R$ %.2f\n",
                    i + 1, livro.getTitulo(), livro.getAutores(), livro.getPreco());
            }
            
            System.out.print("\nDeseja ver detalhes de algum livro? (nmero ou 0 para voltar): ");
            String escolha = sc.nextLine();
            
            if (!escolha.equals("0")) {
                try {
                    int index = Integer.parseInt(escolha) - 1;
                    if (index >= 0 && index < resultados.size()) {
                        exibirDetalhesLivro(resultados.get(index));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(VERMELHO + "Opo invlida!" + RESET);
                }
            }
        }
    }
    
    // ========== FUNES DO CARRINHO ==========
    
    private static void gerenciarCarrinho() {
        Cliente cliente = auth.getClienteLogado();
     // Se no tem carrinho ou carrinho de outro cliente, cria novo
        if (carrinhoAtual == null || !carrinhoAtual.getClienteCpf().equals(cliente.getCpf())) {
            carrinhoAtual = cartService.criarCarrinho(cliente.getCpf());
        }
        
        Carrinho carrinho = cartService.criarCarrinho(cliente.getCpf());
        
        boolean noCarrinho = true;
        
        while (noCarrinho) {
            System.out.println(CYAN + "\n=== MEU CARRINHO ===" + RESET);
            System.out.println("1.  Adicionar livro");
            System.out.println("2.  Remover livro");
            System.out.println("3.   Ver carrinho");
            System.out.println("4.   Limpar carrinho");
            System.out.println("0.   Voltar");
            System.out.print(VERDE + "Escolha: " + RESET);
            
            String opcao = sc.nextLine();
            
            switch (opcao) {
                case "1":
                    adicionarAoCarrinho(carrinho);
                    break;
                    
                case "2":
                    removerDoCarrinho(carrinho);
                    break;
                    
                case "3":
                    exibirCarrinho(carrinho);
                    break;
                    
                case "4":
                    carrinho.limpar();
                    System.out.println(VERDE + " Carrinho limpo!" + RESET);
                    break;
                    
                case "0":
                    noCarrinho = false;
                    break;
                    
                default:
                    System.out.println(VERMELHO + "Opo invlida!" + RESET);
            }
        }
    }
    
    private static void adicionarAoCarrinho(Carrinho carrinho) {
        System.out.print("ISBN do livro: ");
        String isbn = sc.nextLine().trim();
        
        System.out.print("Quantidade: ");
        try {
            int quantidade = Integer.parseInt(sc.nextLine());
            
            if (quantidade <= 0) {
                System.out.println(VERMELHO + "Quantidade deve ser maior que zero!" + RESET);
                return;
            }
            
            if (cartService.adicionarAoCarrinho(carrinho, isbn, quantidade)) {
                System.out.println(VERDE + " Livro adicionado ao carrinho!" + RESET);
            } else {
                System.out.println(VERMELHO + " Livro no encontrado!" + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(VERMELHO + "Quantidade invlida!" + RESET);
        }
    }
    
    private static void removerDoCarrinho(Carrinho carrinho) {
        System.out.print("ISBN do livro para remover: ");
        String isbn = sc.nextLine().trim();
        
        System.out.print("Quantidade a remover (ou 0 para remover todos): ");
        try {
            int quantidade = Integer.parseInt(sc.nextLine());
            
            boolean removido;
            if (quantidade == 0) {
                removido = cartService.removerTodosDoCarrinho(carrinho, isbn);
            } else {
                removido = cartService.removerDoCarrinho(carrinho, isbn, quantidade);
            }
            
            if (removido) {
                System.out.println(VERDE + " Livro removido do carrinho!" + RESET);
            } else {
                System.out.println(VERMELHO + " Livro no encontrado no carrinho!" + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(VERMELHO + "Quantidade invlida!" + RESET);
        }
    }
    
    private static void exibirCarrinho(Carrinho carrinho) {
        Map<Livro, Integer> itens = carrinho.getItens();
        
        if (itens.isEmpty()) {
            System.out.println(" Seu carrinho est vazio.");
        } else {
            System.out.println(CYAN + "\n=== ITENS NO CARRINHO ===" + RESET);
            
            double total = 0;
            for (Map.Entry<Livro, Integer> entry : itens.entrySet()) {
                Livro livro = entry.getKey();
                int quantidade = entry.getValue();
                double subtotal = livro.getPreco() * quantidade;
                total += subtotal;
                
                System.out.printf(" %s\n", livro.getTitulo());
                System.out.printf("   Autor: %s\n", livro.getAutores());
                System.out.printf("   Quantidade: %d | Preo unitrio: R$ %.2f\n", quantidade, livro.getPreco());
                System.out.printf("   Subtotal: R$ %.2f\n", subtotal);
                System.out.println("-".repeat(40));
            }
            
            System.out.printf(VERDE + " TOTAL DO CARRINHO: R$ %.2f\n" + RESET, total);
        }
    }
    
    // ========== FUNES DE COMPRA ==========
    
    @SuppressWarnings("unused")
    private static void finalizarCompra() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(CYAN + "\n=== FINALIZAR COMPRA ===" + RESET);
     // Usa carrinho existente ou cria novo
        if (carrinhoAtual == null || !carrinhoAtual.getClienteCpf().equals(cliente.getCpf())) {
            carrinhoAtual = cartService.criarCarrinho(cliente.getCpf());
        }
        
        // Se carrinho j tem itens, pergunta se quer usar ou comear novo
        if (!carrinhoAtual.estaVazio()) {
            System.out.println("Seu carrinho atual tem " + carrinhoAtual.getItens().size() + " item(ns).");
            System.out.print("Deseja: 1-Usar este carrinho  2-Comear novo carrinho  3-Voltar: ");
            String escolha = sc.nextLine().trim();
            
            if (escolha.equals("2")) {
                carrinhoAtual = cartService.criarCarrinho(cliente.getCpf());
                carrinhoAtual.limpar();
            } else if (escolha.equals("3")) {
                return;
            }
            // Se escolher 1, continua com carrinho atual
        }
        
        // Criar carrinho temporrio
        Carrinho carrinho = cartService.criarCarrinho(cliente.getCpf());
        
        System.out.println("Adicione os livros ao carrinho:");
        boolean adicionando = true;
        
        while (adicionando) {
            System.out.print("\nISBN do livro (ou ENTER para finalizar): ");
            String isbn = sc.nextLine().trim();
            
            if (isbn.isEmpty()) {
                adicionando = false;
                break;
            }
            
            System.out.print("Quantidade: ");
            try {
                int quantidade = Integer.parseInt(sc.nextLine());
                
                if (cartService.adicionarAoCarrinho(carrinho, isbn, quantidade)) {
                    System.out.println(VERDE + " Adicionado!" + RESET);
                } else {
                    System.out.println(VERMELHO + " Livro no encontrado!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(VERMELHO + "Quantidade invlida!" + RESET);
            }
        }
        
        if (cartService.carrinhoVazio(carrinho)) {
            System.out.println(VERMELHO + " Carrinho vazio! Compra cancelada." + RESET);
            return;
        }
        
        System.out.println(CYAN + "\n=== RESUMO DA COMPRA ===" + RESET);
        exibirCarrinho(carrinho);
        
        System.out.print("\n" + VERDE + "Continuar com o pagamento? (S/N): " + RESET);
        String continuar = sc.nextLine().trim();
        
        if (!continuar.equalsIgnoreCase("S")) {
            System.out.println(AMARELO + "Compra cancelada pelo usurio." + RESET);
            return;
        }
        
        // ESCOLHER MTODO DE PAGAMENTO
        System.out.println(CYAN + "\n=== MTODO DE PAGAMENTO ===" + RESET);
        System.out.println("1.  Carto de Crdito");
        System.out.println("2.  PIX");
        System.out.println("3.  Boleto Bancrio");
        System.out.print(VERDE + "Escolha: " + RESET);
        
        String opcaoPagamento = sc.nextLine();
        Pagamento.MetodoPagamento metodo = Pagamento.MetodoPagamento.CARTAO_CREDITO;
        String dadosPagamento = "";
        
        switch (opcaoPagamento) {
            case "1":
                metodo = Pagamento.MetodoPagamento.CARTAO_CREDITO;
                System.out.println("\n PAGAMENTO COM CARTO DE CRDITO");
                System.out.print("Nmero do carto (16 dgitos): ");
                String numeroCartao = sc.nextLine().trim();
                
                System.out.print("Nome no carto: ");
                String nomeCartao = sc.nextLine().trim();
                
                System.out.print("Validade (MM/AA): ");
                String validade = sc.nextLine().trim();
                
                System.out.print("CVV: ");
                String cvv = sc.nextLine().trim();
                
                // Validao simples
                if (numeroCartao.length() != 16 || !numeroCartao.matches("\\d+")) {
                    System.out.println(VERMELHO + " Nmero do carto invlido!" + RESET);
                    return;
                }
                
                dadosPagamento = numeroCartao;
                break;
                
            case "2":
                metodo = Pagamento.MetodoPagamento.PIX;
                System.out.println("\n PAGAMENTO VIA PIX");
                System.out.println("Ser gerado um cdigo PIX para pagamento.");
                System.out.println("O pagamento  processado instantaneamente!");
                dadosPagamento = "PIX";
                break;
                
            case "3":
                metodo = Pagamento.MetodoPagamento.BOLETO;
                System.out.println("\n PAGAMENTO VIA BOLETO");
                System.out.println("O boleto ser gerado com vencimento em 3 dias.");
                System.out.println("Enviaremos por email: " + cliente.getEmail());
                dadosPagamento = "BOLETO";
                break;
                
            default:
                System.out.println(VERMELHO + " Opo invlida!" + RESET);
                return;
        }
        
        // CONFIRMAR PAGAMENTO
        System.out.print("\n" + VERDE + "Confirmar pagamento de R$ " + 
                        String.format("%.2f", carrinho.calcularTotal()) + 
                        "? (S/N): " + RESET);
        String confirmacao = sc.nextLine().trim();
        
        if (!confirmacao.equalsIgnoreCase("S")) {
            System.out.println(AMARELO + "Pagamento cancelado." + RESET);
            return;
        }
        
        // PROCESSAR PAGAMENTO
        System.out.println("\n" + AMARELO + " Processando pagamento..." + RESET);
        
        try {
            // Simula delay de processamento
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simulao: 90% de chance de aprovao
        boolean pagamentoAprovado = Math.random() > 0.1;
        
        if (pagamentoAprovado) {
            // Processar checkout
            NotaFiscal notaFiscal = checkout.checkout(carrinho, "COMPRA", cliente);
            
            if (notaFiscal != null) {
                System.out.println(VERDE + "\n PAGAMENTO APROVADO!" + RESET);
                System.out.println(VERDE + " COMPRA REALIZADA COM SUCESSO!" + RESET);
                
                // Gerar comprovante de pagamento
                System.out.println("\n" + "=".repeat(50));
                System.out.println("         COMPROVANTE DE PAGAMENTO         ");
                System.out.println("=".repeat(50));
                System.out.println("Mtodo: " + metodo.toString().replace("_", " "));
                System.out.println("Valor: R$ " + String.format("%.2f", carrinho.calcularTotal()));
                System.out.println("Status: APROVADO");
                System.out.println("Data: " + java.time.LocalDateTime.now());
                
                if (metodo == Pagamento.MetodoPagamento.PIX) {
                    System.out.println("\n--- PIX ---");
                    System.out.println("Cdigo: PIX-" + System.currentTimeMillis());
                    System.out.println("Copie e cole no app do seu banco!");
                }
                
                if (metodo == Pagamento.MetodoPagamento.CARTAO_CREDITO) {
                    System.out.println("\n--- Carto ---");
                    System.out.println("Final: **** **** **** " + dadosPagamento.substring(12));
                }
                
                System.out.println("=".repeat(50));
                
                // Mostrar nota fiscal
                System.out.print("\nDeseja imprimir a nota fiscal? (S/N): ");
                String imprimir = sc.nextLine().trim();
                
                if (imprimir.equalsIgnoreCase("S")) {
                    notaFiscal.imprimir();
                }
            }
        } else {
            System.out.println(VERMELHO + "\n PAGAMENTO RECUSADO!" + RESET);
            System.out.println("Tente novamente com outro mtodo.");
        }
    }
   
    private static void verUltimaNotaFiscal() {
        System.out.println(CYAN + "\n=== NOTA FISCAL DE DEMONSTRAO ===" + RESET);
        
        if (!auth.isLogado()) {
            System.out.println(VERMELHO + " Faa login primeiro!" + RESET);
            return;
        }
        
        Cliente cliente = auth.getClienteLogado();
        
        // Mostra uma nota fiscal de exemplo
        System.out.println("Esta  uma demonstrao de como ficaria uma nota fiscal:");
        System.out.println("=".repeat(50));
        System.out.println("            NOTA FISCAL DE EXEMPLO");
        System.out.println("=".repeat(50));
        System.out.println("Nmero: NF-0001");
        System.out.println("Data: " + java.time.LocalDateTime.now());
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Tipo: COMPRA");
        System.out.println("-".repeat(50));
        System.out.println("Dom Casmurro x1 = R$ 29,90");
        System.out.println("1984 x2 = R$ 79,80");
        System.out.println("-".repeat(50));
        System.out.println("TOTAL: R$ 109,70");
        System.out.println("=".repeat(50));
        System.out.println("Obrigado pela preferncia!");
        System.out.println("=".repeat(50));
        
        System.out.println("\n" + VERDE + " Em uma compra real, a nota fiscal seria gerada automaticamente aps o pagamento." + RESET);
    }
    
    private static void meusPedidos() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(CYAN + "\n=== MEUS PEDIDOS ===" + RESET);
        
     // DEBUG: Mostra o que est sendo buscado
        System.out.println("[DEBUG] Buscando pedidos para CPF: " + cliente.getCpf());
        
        List<Pedido> todosPedidos = reports.relatorioVendas();
        System.out.println("[DEBUG] Total de pedidos no sistema: " + todosPedidos.size());
        
        List<Pedido> meusPedidos = todosPedidos.stream()
            .filter(pedido -> pedido.getClienteCpf().equals(cliente.getCpf()))
            .toList();
        
        System.out.println("[DEBUG] Meus pedidos encontrados: " + meusPedidos.size());
        
        if (meusPedidos.isEmpty()) {
            System.out.println(" Voc ainda no realizou nenhum pedido.");
            System.out.println("Comece a comprar na opo 'Finalizar compra'!");
        } else {
            System.out.println(" Total de pedidos: " + meusPedidos.size());
            System.out.println("-".repeat(60));
            
            for (int i = 0; i < meusPedidos.size(); i++) {
                Pedido pedido = meusPedidos.get(i);
                System.out.printf("%d. Pedido #%s\n", i + 1, pedido.getId());
                System.out.printf("   Data: %s\n", pedido.getDataHora().toLocalDate());
                System.out.printf("   Total: R$ %.2f\n", pedido.getTotal());
                System.out.printf("   Itens: %d livro(s)\n", pedido.getItens().size());
                System.out.println("-".repeat(60));
            }
        }
    }
    
    // ========== FUNES DO PERFIL ==========
    
    private static void meuPerfil() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(CYAN + "\n=== MEU PERFIL ===" + RESET);
        System.out.println(" Nome: " + cliente.getNome());
        System.out.println(" Email: " + cliente.getEmail());
        System.out.println(" CPF: " + cliente.getCpf());
        System.out.println(" Endereo: " + cliente.getEndereco());
        
        System.out.println("\n1.   Editar perfil");
        System.out.println("2.  Alterar senha");
        System.out.println("0.   Voltar");
        System.out.print(VERDE + "Escolha: " + RESET);
        
        String opcao = sc.nextLine();
        
        switch (opcao) {
            case "1":
                editarPerfil();
                break;
                
            case "2":
                alterarSenha();
                break;
                
            case "0":
                break;
                
            default:
                System.out.println(VERMELHO + "Opo invlida!" + RESET);
        }
    }
    
    private static void editarPerfil() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(CYAN + "\n=== EDITAR PERFIL ===" + RESET);
        
        System.out.print("Novo email (atual: " + cliente.getEmail() + "): ");
        String novoEmail = sc.nextLine().trim();
        if (novoEmail.isEmpty()) novoEmail = cliente.getEmail();
        
        System.out.print("Novo endereo (atual: " + cliente.getEndereco() + "): ");
        String novoEndereco = sc.nextLine().trim();
        if (novoEndereco.isEmpty()) novoEndereco = cliente.getEndereco();
        
        if (auth.atualizarPerfil(novoEmail, novoEndereco)) {
            System.out.println(VERDE + " Perfil atualizado com sucesso!" + RESET);
        } else {
            System.out.println(VERMELHO + " Erro ao atualizar perfil!" + RESET);
        }
    }
    
    private static void alterarSenha() {
        System.out.println(CYAN + "\n=== ALTERAR SENHA ===" + RESET);
        
        System.out.print("Senha atual: ");
        String senhaAtual = sc.nextLine().trim();
        
        Cliente cliente = auth.getClienteLogado();
        
        if (!cliente.validarSenha(senhaAtual)) {
            System.out.println(VERMELHO + " Senha atual incorreta!" + RESET);
            return;
        }
        
        System.out.print("Nova senha: ");
        String novaSenha = sc.nextLine().trim();
        
        System.out.print("Confirmar nova senha: ");
        String confirmarSenha = sc.nextLine().trim();
        
        if (!novaSenha.equals(confirmarSenha)) {
            System.out.println(VERMELHO + " As senhas no coincidem!" + RESET);
            return;
        }
        
        if (novaSenha.length() < 4) {
            System.out.println(VERMELHO + " A senha deve ter pelo menos 4 caracteres!" + RESET);
            return;
        }
        
        cliente.setSenha(novaSenha);
        System.out.println(VERDE + " Senha alterada com sucesso!" + RESET);
    }
    
    // ========== DADOS DE EXEMPLO ==========
    
    private static void carregarDadosExemplo() {
        System.out.println(AMARELO + "\nCarregando dados de exemplo..." + RESET);
        
        // Criar alguns livros de exemplo
        Autor autor1 = new Autor("Machado de Assis", "machado@email.com");
        Autor autor2 = new Autor("Clarice Lispector", "clarice@email.com");
        Autor autor3 = new Autor("George Orwell", "orwell@email.com");
        Autor autor4 = new Autor("J.K. Rowling", "rowling@email.com");
        
        Livro livro1 = new Livro("978-85-359-0275-5", "Dom Casmurro", 29.90, "Romance");
        livro1.addAutor(autor1);
        catalog.cadastrarLivro(livro1, 10);
        
        Livro livro2 = new Livro("978-85-325-0909-6", "A Hora da Estrela", 34.90, "Romance");
        livro2.addAutor(autor2);
        catalog.cadastrarLivro(livro2, 8);
        
        Livro livro3 = new Livro("978-85-7232-228-8", "1984", 39.90, "Distopia");
        livro3.addAutor(autor3);
        catalog.cadastrarLivro(livro3, 15);
        
        Livro livro4 = new Livro("978-85-325-1108-2", "Harry Potter e a Pedra Filosofal", 49.90, "Fantasia");
        livro4.addAutor(autor4);
        catalog.cadastrarLivro(livro4, 12);
        
        // Criar alguns clientes de exemplo
        auth.cadastrar("12345678901", "Joo Silva", "joao@email.com", "senha123");
        auth.cadastrar("98765432100", "Maria Santos", "maria@email.com", "senha456");
        
        System.out.println(VERDE + " Dados de exemplo carregados com sucesso!" + RESET);
        System.out.println(" 4 livros cadastrados no catlogo");
        System.out.println(" 2 clientes de exemplo disponveis para login");
    }
}

