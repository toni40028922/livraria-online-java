package app;

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
        
        System.out.println(VERDE + "\nObrigado por usar a Livraria Online! Até a próxima!" + RESET);
        sc.close();
    }
    
    // ========== MENU VISITANTE ==========
    private static boolean menuVisitante() {
        System.out.println(AMARELO + "\n" + "=".repeat(50) + RESET);
        System.out.println(CYAN + "           MENU VISITANTE           " + RESET);
        System.out.println(AMARELO + "=".repeat(50) + RESET);
        System.out.println("1. 📚 Ver catálogo de livros");
        System.out.println("2. 🔍 Buscar livro");
        System.out.println("3. 📝 Cadastrar nova conta");
        System.out.println("4. 🔑 Fazer login");
        System.out.println("5. 🔓 Recuperar senha");
        System.out.println("0. ❌ Sair do sistema");
        System.out.print(VERDE + "\nEscolha uma opção: " + RESET);
        
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
                System.out.println(VERMELHO + "Opção inválida! Tente novamente." + RESET);
        }
        return true; // Continua no sistema
    }
    
    // ========== MENU CLIENTE LOGADO ==========
    private static boolean menuCliente() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(AMARELO + "\n" + "=".repeat(50) + RESET);
        System.out.println(CYAN + "           MENU CLIENTE           " + RESET);
        System.out.println(AMARELO + "=".repeat(50) + RESET);
        System.out.println("👤 Usuário: " + VERDE + cliente.getNome() + RESET);
        System.out.println("📧 Email: " + cliente.getEmail());
        System.out.println("\n1. 📚 Ver catálogo");
        System.out.println("2. 🔍 Buscar livro");
        System.out.println("3. 🛒 Gerenciar carrinho");
        System.out.println("4. 💳 Finalizar compra");
        System.out.println("5. 📄 Ver Nota Fiscal da última compra");
        System.out.println("6. 📋 Meus pedidos");
        System.out.println("7. ⚙️  Meu perfil");
        System.out.println("8. 🔒 Logout");
        System.out.println("0. ❌ Sair do sistema");
        System.out.print(VERDE + "\nEscolha uma opção: " + RESET);
        
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
                System.out.println(VERMELHO + "Opção inválida! Tente novamente." + RESET);
        }
        return true; // Continua no sistema
    }
    
    // ========== FUNÇÕES DO VISITANTE ==========
    
    private static void cadastrarCliente() {
        System.out.println(CYAN + "\n=== CADASTRO DE NOVO CLIENTE ===" + RESET);
        
        System.out.print("CPF (apenas números): ");
        String cpf = sc.nextLine().trim();
        
        // Validar se CPF já existe
        if (cpf.length() != 11 || !cpf.matches("\\d+")) {
            System.out.println(VERMELHO + "CPF inválido! Use 11 números." + RESET);
            return;
        }
        
        System.out.print("Nome completo: ");
        String nome = sc.nextLine().trim();
        
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        
        if (!email.contains("@")) {
            System.out.println(VERMELHO + "Email inválido!" + RESET);
            return;
        }
        
        System.out.print("Senha (mínimo 4 caracteres): ");
        String senha = sc.nextLine().trim();
        
        if (senha.length() < 4) {
            System.out.println(VERMELHO + "Senha muito curta!" + RESET);
            return;
        }
        
        if (auth.cadastrar(cpf, nome, email, senha)) {
            System.out.println(VERDE + "\n✅ Cadastro realizado com sucesso!" + RESET);
            System.out.println("Faça login para começar a comprar.");
        } else {
            System.out.println(VERMELHO + "\n❌ CPF já cadastrado no sistema!" + RESET);
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
            System.out.println(VERDE + "\n✅ Login realizado com sucesso!" + RESET);
            System.out.println("Bem-vindo(a), " + cliente.getNome() + "!");
        } else {
            System.out.println(VERMELHO + "\n❌ CPF ou senha incorretos!" + RESET);
            System.out.println("Verifique seus dados ou cadastre-se.");
        }
    }
    
    private static void recuperarSenha() {
        System.out.println(CYAN + "\n=== RECUPERAÇÃO DE SENHA ===" + RESET);
        
        System.out.print("Digite seu CPF: ");
        String cpf = sc.nextLine().trim();
        
        System.out.print("Digite seu email cadastrado: ");
        String email = sc.nextLine().trim();
        
        if (auth.recuperarSenha(cpf, email)) {
            System.out.println(VERDE + "\n✅ Instruções enviadas para seu email!" + RESET);
        } else {
            System.out.println(VERMELHO + "\n❌ Dados não encontrados!" + RESET);
            System.out.println("Verifique se o CPF e email estão corretos.");
        }
    }
    
    // ========== FUNÇÕES DO CATÁLOGO ==========
    
    private static void listarCatalogo() {
        System.out.println(CYAN + "\n=== CATÁLOGO DE LIVROS ===" + RESET);
        
        List<Livro> catalogo = catalog.listarCatalogo();
        
        if (catalogo.isEmpty()) {
            System.out.println("Nenhum livro cadastrado no sistema.");
        } else {
            System.out.println("Total de livros: " + catalogo.size());
            System.out.println("-".repeat(60));
            
            for (int i = 0; i < catalogo.size(); i++) {
                Livro livro = catalogo.get(i);
                int estoque = catalog.estoque(livro.getIsbn());
                String statusEstoque = estoque > 0 ? VERDE + "Disponível" + RESET : VERMELHO + "Esgotado" + RESET;
                
                System.out.printf("%d. %s\n", i + 1, livro.getTitulo());
                System.out.printf("   Autor: %s\n", livro.getAutores());
                System.out.printf("   ISBN: %s | Gênero: %s\n", livro.getIsbn(), livro.getGenero());
                System.out.printf("   Preço: R$ %.2f | Estoque: %d (%s)\n", 
                    livro.getPreco(), estoque, statusEstoque);
                System.out.println("-".repeat(60));
            }
        }
    }
    
    private static void buscarLivro() {
        System.out.println(CYAN + "\n=== BUSCAR LIVRO ===" + RESET);
        System.out.println("1. Buscar por ISBN");
        System.out.println("2. Buscar por título");
        System.out.println("3. Buscar por autor");
        System.out.print(VERDE + "Escolha: " + RESET);
        
        String opcao = sc.nextLine();
        
        switch (opcao) {
            case "1":
                System.out.print("Digite o ISBN: ");
                String isbn = sc.nextLine().trim();
                Optional<Livro> livro = catalog.buscarPorIsbn(isbn);
                
                if (livro.isPresent()) {
                    exibirDetalhesLivro(livro.get());
                } else {
                    System.out.println(VERMELHO + "Livro não encontrado!" + RESET);
                }
                break;
                
            case "2":
                System.out.print("Digite parte do título: ");
                String titulo = sc.nextLine().trim();
                List<Livro> resultados = catalog.buscarPorTitulo(titulo);
                exibirResultadosBusca(resultados);
                break;
                
            case "3":
                System.out.print("Digite o nome do autor: ");
                String autor = sc.nextLine().trim();
                resultados = catalog.buscarPorAutor(autor);
                exibirResultadosBusca(resultados);
                break;
                
            default:
                System.out.println(VERMELHO + "Opção inválida!" + RESET);
        }
    }
    
    private static void exibirDetalhesLivro(Livro livro) {
        System.out.println(CYAN + "\n=== DETALHES DO LIVRO ===" + RESET);
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("ISBN: " + livro.getIsbn());
        System.out.println("Autor(es): " + livro.getAutores());
        System.out.println("Gênero: " + livro.getGenero());
        System.out.printf("Preço: R$ %.2f\n", livro.getPreco());
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
            
            System.out.print("\nDeseja ver detalhes de algum livro? (número ou 0 para voltar): ");
            String escolha = sc.nextLine();
            
            if (!escolha.equals("0")) {
                try {
                    int index = Integer.parseInt(escolha) - 1;
                    if (index >= 0 && index < resultados.size()) {
                        exibirDetalhesLivro(resultados.get(index));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(VERMELHO + "Opção inválida!" + RESET);
                }
            }
        }
    }
    
    // ========== FUNÇÕES DO CARRINHO ==========
    
    private static void gerenciarCarrinho() {
        Cliente cliente = auth.getClienteLogado();
        Carrinho carrinho = cartService.criarCarrinho(cliente.getCpf());
        
        boolean noCarrinho = true;
        
        while (noCarrinho) {
            System.out.println(CYAN + "\n=== MEU CARRINHO ===" + RESET);
            System.out.println("1. ➕ Adicionar livro");
            System.out.println("2. ➖ Remover livro");
            System.out.println("3. 👁️  Ver carrinho");
            System.out.println("4. 🗑️  Limpar carrinho");
            System.out.println("0. ↩️  Voltar");
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
                    System.out.println(VERDE + "✅ Carrinho limpo!" + RESET);
                    break;
                    
                case "0":
                    noCarrinho = false;
                    break;
                    
                default:
                    System.out.println(VERMELHO + "Opção inválida!" + RESET);
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
                System.out.println(VERDE + "✅ Livro adicionado ao carrinho!" + RESET);
            } else {
                System.out.println(VERMELHO + "❌ Livro não encontrado!" + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(VERMELHO + "Quantidade inválida!" + RESET);
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
                System.out.println(VERDE + "✅ Livro removido do carrinho!" + RESET);
            } else {
                System.out.println(VERMELHO + "❌ Livro não encontrado no carrinho!" + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(VERMELHO + "Quantidade inválida!" + RESET);
        }
    }
    
    private static void exibirCarrinho(Carrinho carrinho) {
        Map<Livro, Integer> itens = carrinho.getItens();
        
        if (itens.isEmpty()) {
            System.out.println("🛒 Seu carrinho está vazio.");
        } else {
            System.out.println(CYAN + "\n=== ITENS NO CARRINHO ===" + RESET);
            
            double total = 0;
            for (Map.Entry<Livro, Integer> entry : itens.entrySet()) {
                Livro livro = entry.getKey();
                int quantidade = entry.getValue();
                double subtotal = livro.getPreco() * quantidade;
                total += subtotal;
                
                System.out.printf("📖 %s\n", livro.getTitulo());
                System.out.printf("   Autor: %s\n", livro.getAutores());
                System.out.printf("   Quantidade: %d | Preço unitário: R$ %.2f\n", quantidade, livro.getPreco());
                System.out.printf("   Subtotal: R$ %.2f\n", subtotal);
                System.out.println("-".repeat(40));
            }
            
            System.out.printf(VERDE + "💰 TOTAL DO CARRINHO: R$ %.2f\n" + RESET, total);
        }
    }
    
    // ========== FUNÇÕES DE COMPRA ==========
    
    private static void finalizarCompra() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(CYAN + "\n=== FINALIZAR COMPRA ===" + RESET);
        
        // Criar carrinho temporário
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
                    System.out.println(VERDE + "✅ Adicionado!" + RESET);
                } else {
                    System.out.println(VERMELHO + "❌ Livro não encontrado!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(VERMELHO + "Quantidade inválida!" + RESET);
            }
        }
        
        if (cartService.carrinhoVazio(carrinho)) {
            System.out.println(VERMELHO + "❌ Carrinho vazio! Compra cancelada." + RESET);
            return;
        }
        
        System.out.println(CYAN + "\n=== RESUMO DA COMPRA ===" + RESET);
        exibirCarrinho(carrinho);
        
        System.out.print("\n" + VERDE + "Continuar com o pagamento? (S/N): " + RESET);
        String continuar = sc.nextLine().trim();
        
        if (!continuar.equalsIgnoreCase("S")) {
            System.out.println(AMARELO + "Compra cancelada pelo usuário." + RESET);
            return;
        }
        
        // ESCOLHER MÉTODO DE PAGAMENTO
        System.out.println(CYAN + "\n=== MÉTODO DE PAGAMENTO ===" + RESET);
        System.out.println("1. 💳 Cartão de Crédito");
        System.out.println("2. 📱 PIX");
        System.out.println("3. 📄 Boleto Bancário");
        System.out.print(VERDE + "Escolha: " + RESET);
        
        String opcaoPagamento = sc.nextLine();
        Pagamento.MetodoPagamento metodo = Pagamento.MetodoPagamento.CARTAO_CREDITO;
        String dadosPagamento = "";
        
        switch (opcaoPagamento) {
            case "1":
                metodo = Pagamento.MetodoPagamento.CARTAO_CREDITO;
                System.out.println("\n💳 PAGAMENTO COM CARTÃO DE CRÉDITO");
                System.out.print("Número do cartão (16 dígitos): ");
                String numeroCartao = sc.nextLine().trim();
                
                System.out.print("Nome no cartão: ");
                String nomeCartao = sc.nextLine().trim();
                
                System.out.print("Validade (MM/AA): ");
                String validade = sc.nextLine().trim();
                
                System.out.print("CVV: ");
                String cvv = sc.nextLine().trim();
                
                // Validação simples
                if (numeroCartao.length() != 16 || !numeroCartao.matches("\\d+")) {
                    System.out.println(VERMELHO + "❌ Número do cartão inválido!" + RESET);
                    return;
                }
                
                dadosPagamento = numeroCartao;
                break;
                
            case "2":
                metodo = Pagamento.MetodoPagamento.PIX;
                System.out.println("\n📱 PAGAMENTO VIA PIX");
                System.out.println("Será gerado um código PIX para pagamento.");
                System.out.println("O pagamento é processado instantaneamente!");
                dadosPagamento = "PIX";
                break;
                
            case "3":
                metodo = Pagamento.MetodoPagamento.BOLETO;
                System.out.println("\n📄 PAGAMENTO VIA BOLETO");
                System.out.println("O boleto será gerado com vencimento em 3 dias.");
                System.out.println("Enviaremos por email: " + cliente.getEmail());
                dadosPagamento = "BOLETO";
                break;
                
            default:
                System.out.println(VERMELHO + "❌ Opção inválida!" + RESET);
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
        System.out.println("\n" + AMARELO + "⏳ Processando pagamento..." + RESET);
        
        try {
            // Simula delay de processamento
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simulação: 90% de chance de aprovação
        boolean pagamentoAprovado = Math.random() > 0.1;
        
        if (pagamentoAprovado) {
            // Processar checkout
            NotaFiscal notaFiscal = checkout.checkout(carrinho, "COMPRA", cliente);
            
            if (notaFiscal != null) {
                System.out.println(VERDE + "\n✅ PAGAMENTO APROVADO!" + RESET);
                System.out.println(VERDE + "✅ COMPRA REALIZADA COM SUCESSO!" + RESET);
                
                // Gerar comprovante de pagamento
                System.out.println("\n" + "=".repeat(50));
                System.out.println("         COMPROVANTE DE PAGAMENTO         ");
                System.out.println("=".repeat(50));
                System.out.println("Método: " + metodo.toString().replace("_", " "));
                System.out.println("Valor: R$ " + String.format("%.2f", carrinho.calcularTotal()));
                System.out.println("Status: APROVADO");
                System.out.println("Data: " + java.time.LocalDateTime.now());
                
                if (metodo == Pagamento.MetodoPagamento.PIX) {
                    System.out.println("\n--- PIX ---");
                    System.out.println("Código: PIX-" + System.currentTimeMillis());
                    System.out.println("Copie e cole no app do seu banco!");
                }
                
                if (metodo == Pagamento.MetodoPagamento.CARTAO_CREDITO) {
                    System.out.println("\n--- Cartão ---");
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
            System.out.println(VERMELHO + "\n❌ PAGAMENTO RECUSADO!" + RESET);
            System.out.println("Tente novamente com outro método.");
        }
    }
    
    private static void meusPedidos() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(CYAN + "\n=== MEUS PEDIDOS ===" + RESET);
        
        List<Pedido> todosPedidos = reports.relatorioVendas();
        List<Pedido> meusPedidos = todosPedidos.stream()
            .filter(pedido -> pedido.getClienteCpf().equals(cliente.getCpf()))
            .toList();
        
        if (meusPedidos.isEmpty()) {
            System.out.println("📭 Você ainda não realizou nenhum pedido.");
            System.out.println("Comece a comprar na opção 'Finalizar compra'!");
        } else {
            System.out.println("📋 Total de pedidos: " + meusPedidos.size());
            System.out.println("-".repeat(60));
            
            for (int i = 0; i < meusPedidos.size(); i++) {
                Pedido pedido = meusPedidos.get(i);
                System.out.printf("%d. Pedido #%s\n", i + 1, pedido.getId().substring(0, 8));
                System.out.printf("   Data: %s\n", pedido.getDataHora().toLocalDate());
                System.out.printf("   Total: R$ %.2f\n", pedido.getTotal());
                System.out.printf("   Itens: %d livro(s)\n", pedido.getItens().size());
                System.out.println("-".repeat(60));
            }
        }
    }
    
    // ========== FUNÇÕES DO PERFIL ==========
    
    private static void meuPerfil() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(CYAN + "\n=== MEU PERFIL ===" + RESET);
        System.out.println("👤 Nome: " + cliente.getNome());
        System.out.println("📧 Email: " + cliente.getEmail());
        System.out.println("🔐 CPF: " + cliente.getCpf());
        System.out.println("🏠 Endereço: " + cliente.getEndereco());
        
        System.out.println("\n1. ✏️  Editar perfil");
        System.out.println("2. 🔐 Alterar senha");
        System.out.println("0. ↩️  Voltar");
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
                System.out.println(VERMELHO + "Opção inválida!" + RESET);
        }
    }
    
    private static void editarPerfil() {
        Cliente cliente = auth.getClienteLogado();
        
        System.out.println(CYAN + "\n=== EDITAR PERFIL ===" + RESET);
        
        System.out.print("Novo email (atual: " + cliente.getEmail() + "): ");
        String novoEmail = sc.nextLine().trim();
        if (novoEmail.isEmpty()) novoEmail = cliente.getEmail();
        
        System.out.print("Novo endereço (atual: " + cliente.getEndereco() + "): ");
        String novoEndereco = sc.nextLine().trim();
        if (novoEndereco.isEmpty()) novoEndereco = cliente.getEndereco();
        
        if (auth.atualizarPerfil(novoEmail, novoEndereco)) {
            System.out.println(VERDE + "✅ Perfil atualizado com sucesso!" + RESET);
        } else {
            System.out.println(VERMELHO + "❌ Erro ao atualizar perfil!" + RESET);
        }
    }
    
    private static void alterarSenha() {
        System.out.println(CYAN + "\n=== ALTERAR SENHA ===" + RESET);
        
        System.out.print("Senha atual: ");
        String senhaAtual = sc.nextLine().trim();
        
        Cliente cliente = auth.getClienteLogado();
        
        if (!cliente.validarSenha(senhaAtual)) {
            System.out.println(VERMELHO + "❌ Senha atual incorreta!" + RESET);
            return;
        }
        
        System.out.print("Nova senha: ");
        String novaSenha = sc.nextLine().trim();
        
        System.out.print("Confirmar nova senha: ");
        String confirmarSenha = sc.nextLine().trim();
        
        if (!novaSenha.equals(confirmarSenha)) {
            System.out.println(VERMELHO + "❌ As senhas não coincidem!" + RESET);
            return;
        }
        
        if (novaSenha.length() < 4) {
            System.out.println(VERMELHO + "❌ A senha deve ter pelo menos 4 caracteres!" + RESET);
            return;
        }
        
        cliente.setSenha(novaSenha);
        System.out.println(VERDE + "✅ Senha alterada com sucesso!" + RESET);
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
        auth.cadastrar("12345678901", "João Silva", "joao@email.com", "senha123");
        auth.cadastrar("98765432100", "Maria Santos", "maria@email.com", "senha456");
        
        System.out.println(VERDE + "✅ Dados de exemplo carregados com sucesso!" + RESET);
        System.out.println("• 4 livros cadastrados no catálogo");
        System.out.println("• 2 clientes de exemplo disponíveis para login");
    }
}