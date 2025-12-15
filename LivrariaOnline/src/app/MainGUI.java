package app;

import model.*;
import service.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MainGUI {
    private static AuthService auth = new AuthService();
    private static CatalogService catalog = new CatalogService();
    private static CartService cartService = new CartService();
    private static CheckoutService checkout = new CheckoutService();
    private static ReportService reports = new ReportService();
    private static Carrinho carrinhoAtual;
    private static JFrame frameAtual;
    
    public static void main(String[] args) {
        // Carregar dados de exemplo
        carregarDadosExemplo();
        
        // Criar interface
        SwingUtilities.invokeLater(() -> {
            criarJanelaLogin();
        });
    }
    
    private static void carregarDadosExemplo() {
        System.out.println("Carregando dados de exemplo...");
        
        // Cadastrar livros de exemplo
        Autor autor1 = new Autor("Machado de Assis", "machado@email.com");
        Livro livro1 = new Livro("9788535902755", "Dom Casmurro", 29.90, "Romance");
        livro1.addAutor(autor1);
        catalog.cadastrarLivro(livro1, 10);
        
        Autor autor2 = new Autor("Clarice Lispector", "clarice@email.com");
        Livro livro2 = new Livro("9788532509096", "A Hora da Estrela", 34.90, "Romance");
        livro2.addAutor(autor2);
        catalog.cadastrarLivro(livro2, 8);
        
        Autor autor3 = new Autor("George Orwell", "orwell@email.com");
        Livro livro3 = new Livro("9788572322288", "1984", 39.90, "Distopia");
        livro3.addAutor(autor3);
        catalog.cadastrarLivro(livro3, 15);
        
        Autor autor4 = new Autor("J.K. Rowling", "rowling@email.com");
        Livro livro4 = new Livro("9788532511082", "Harry Potter e a Pedra Filosofal", 49.90, "Fantasia");
        livro4.addAutor(autor4);
        catalog.cadastrarLivro(livro4, 12);
        
        // Cadastrar clientes de exemplo
        auth.cadastrar("12345678901", "Joao Silva", "joao@email.com", "senha123");
        auth.cadastrar("98765432100", "Maria Santos", "maria@email.com", "senha456");
        
        System.out.println("Dados carregados: 4 livros, 2 clientes");
    }
    
    private static void criarJanelaLogin() {
        if (frameAtual != null) {
            frameAtual.dispose();
        }
        
        frameAtual = new JFrame("Livraria Online - Login");
        frameAtual.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAtual.setSize(450, 350);
        frameAtual.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Titulo
        JLabel lblTitulo = new JLabel("LIVRARIA ONLINE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 100, 200));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        
        JLabel lblSubtitulo = new JLabel("Sistema Completo de Gerenciamento", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubtitulo.setForeground(Color.GRAY);
        
        gbc.gridy = 1;
        panel.add(lblSubtitulo, gbc);
        
        // Espaco
        gbc.gridy = 2;
        panel.add(Box.createVerticalStrut(30), gbc);
        
        // Campos
        gbc.gridwidth = 1;
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblCpf, gbc);
        
        gbc.gridx = 1;
        JTextField txtCpf = new JTextField(15);
        txtCpf.setText("12345678901");
        panel.add(txtCpf, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblSenha, gbc);
        
        gbc.gridx = 1;
        JPasswordField txtSenha = new JPasswordField(15);
        txtSenha.setText("senha123");
        panel.add(txtSenha, gbc);
        
        // Botoes
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(20), gbc);
        
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JButton btnCadastrar = new JButton("CRIAR CONTA");
        btnCadastrar.setBackground(new Color(0, 120, 215));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(btnCadastrar, gbc);
        
        gbc.gridx = 1;
        JButton btnLogin = new JButton("ENTRAR");
        btnLogin.setBackground(new Color(0, 150, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(btnLogin, gbc);
        
        // Informacoes de teste
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JLabel lblTeste = new JLabel("<html><center>Para teste:<br>CPF: 12345678901 | Senha: senha123<br>CPF: 98765432100 | Senha: senha456</center></html>", SwingConstants.CENTER);
        lblTeste.setFont(new Font("Arial", Font.PLAIN, 10));
        lblTeste.setForeground(Color.DARK_GRAY);
        panel.add(lblTeste, gbc);
        
        // Acao do botao Entrar
        btnLogin.addActionListener(e -> {
            String cpf = txtCpf.getText().trim();
            String senha = new String(txtSenha.getPassword());
            
            if (auth.login(cpf, senha)) {
                carrinhoAtual = new Carrinho(cpf);
                Cliente cliente = auth.getClienteLogado();
                JOptionPane.showMessageDialog(frameAtual, 
                    "Login realizado com sucesso!\nBem-vindo(a), " + cliente.getNome() + "!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                frameAtual.dispose();
                criarMenuPrincipal();
            } else {
                JOptionPane.showMessageDialog(frameAtual, 
                    "CPF ou senha incorretos!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Acao do botao Cadastrar
        btnCadastrar.addActionListener(e -> {
            frameAtual.dispose();
            criarJanelaCadastro();
        });
        
        // Pressionar Enter no campo de senha
        txtSenha.addActionListener(e -> btnLogin.doClick());
        
        frameAtual.add(panel);
        frameAtual.setVisible(true);
    }
    
    private static void criarJanelaCadastro() {
        frameAtual = new JFrame("Livraria Online - Cadastro");
        frameAtual.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAtual.setSize(500, 450);
        frameAtual.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Titulo
        JLabel lblTitulo = new JLabel("CRIAR NOVA CONTA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 100, 200));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        
        gbc.gridy = 1;
        panel.add(Box.createVerticalStrut(20), gbc);
        
        // Campos de cadastro
        String[] labels = {"CPF (11 numeros):", "Nome completo:", "Email:", "Senha (min. 4 caracteres):", "Confirmar Senha:"};
        JTextField[] campos = new JTextField[labels.length];
        
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = i + 2;
            
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(label, gbc);
            
            gbc.gridx = 1;
            if (i == 3 || i == 4) {
                campos[i] = new JPasswordField(20);
            } else {
                campos[i] = new JTextField(20);
            }
            panel.add(campos[i], gbc);
        }
        
        // Botoes
        gbc.gridy = labels.length + 3;
        gbc.gridwidth = 1;
        
        JButton btnVoltar = new JButton("VOLTAR");
        btnVoltar.setBackground(Color.GRAY);
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.addActionListener(e -> {
            frameAtual.dispose();
            criarJanelaLogin();
        });
        panel.add(btnVoltar, gbc);
        
        gbc.gridx = 1;
        JButton btnCadastrar = new JButton("CADASTRAR");
        btnCadastrar.setBackground(new Color(0, 150, 0));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(btnCadastrar, gbc);
        
        // Acao do botao Cadastrar
        btnCadastrar.addActionListener(e -> {
            String cpf = campos[0].getText().trim();
            String nome = campos[1].getText().trim();
            String email = campos[2].getText().trim();
            String senha = campos[3].getText();
            String confirmarSenha = campos[4].getText();
            
            // Validacoes
            if (cpf.length() != 11 || !cpf.matches("\\d+")) {
                JOptionPane.showMessageDialog(frameAtual, "CPF deve ter 11 numeros!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(frameAtual, "Email invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!senha.equals(confirmarSenha)) {
                JOptionPane.showMessageDialog(frameAtual, "Senhas nao coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (senha.length() < 4) {
                JOptionPane.showMessageDialog(frameAtual, "Senha deve ter pelo menos 4 caracteres!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (auth.cadastrar(cpf, nome, email, senha)) {
                JOptionPane.showMessageDialog(frameAtual, 
                    "Cadastro realizado com sucesso!\nFaca login para continuar.", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                frameAtual.dispose();
                criarJanelaLogin();
            } else {
                JOptionPane.showMessageDialog(frameAtual, 
                    "CPF ja cadastrado no sistema!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        frameAtual.add(panel);
        frameAtual.setVisible(true);
    }
    
    private static void criarMenuPrincipal() {
        frameAtual = new JFrame("Livraria Online - Menu Principal");
        frameAtual.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAtual.setSize(700, 500);
        frameAtual.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        // Cabecalho
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        Cliente cliente = auth.getClienteLogado();
        JLabel lblUsuario = new JLabel("Usuario: " + cliente.getNome());
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton btnLogout = new JButton("SAIR");
        btnLogout.setBackground(new Color(200, 50, 50));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> {
            auth.logout();
            carrinhoAtual = null;
            frameAtual.dispose();
            criarJanelaLogin();
        });
        
        headerPanel.add(lblUsuario, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);
        
        // Titulo
        JLabel lblTitulo = new JLabel("MENU PRINCIPAL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 100, 200));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Painel de botoes
        JPanel btnPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        String[] botoes = {
            "VER CATALOGO", "BUSCAR LIVRO", 
            "MEU CARRINHO", "FINALIZAR COMPRA",
            "MEUS PEDIDOS", "MEU PERFIL",
            "RELATORIOS", "AJUDA"
        };
        
        Color[] cores = {
            new Color(0, 120, 215), new Color(0, 150, 200),
            new Color(255, 140, 0), new Color(0, 180, 0),
            new Color(150, 100, 200), new Color(200, 100, 50),
            new Color(100, 150, 100), new Color(100, 100, 100)
        };
        
        for (int i = 0; i < botoes.length; i++) {
            JButton btn = criarBotaoMenu(botoes[i], cores[i]);
            
            switch (i) {
                case 0: // Ver Catalogo
                    btn.addActionListener(e -> mostrarCatalogoInterativo());
                    break;
                case 1: // Buscar Livro
                    btn.addActionListener(e -> buscarLivro());
                    break;
                case 2: // Meu Carrinho
                    btn.addActionListener(e -> mostrarCarrinho());
                    break;
                case 3: // Finalizar Compra
                    btn.addActionListener(e -> finalizarCompraComPagamento());
                    break;
                case 4: // Meus Pedidos
                    btn.addActionListener(e -> mostrarHistoricoPedidos());
                    break;
                case 5: // Meu Perfil
                    btn.addActionListener(e -> mostrarPerfil());
                    break;
                case 6: // Relatorios
                    btn.addActionListener(e -> mostrarRelatorios());
                    break;
                case 7: // Ajuda
                    btn.addActionListener(e -> mostrarAjuda());
                    break;
            }
            
            btnPanel.add(btn);
        }
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(lblTitulo, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        frameAtual.add(panel);
        frameAtual.setVisible(true);
    }
    
    private static JButton criarBotaoMenu(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        btn.setFocusPainted(false);
        
        // Efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor);
            }
        });
        
        return btn;
    }
    
    // ========== MÉTODO DE PAGAMENTO COMPLETO ==========
    
    private static void finalizarCompraComPagamento() {
        if (carrinhoAtual == null || carrinhoAtual.getItens().isEmpty()) {
            int option = JOptionPane.showConfirmDialog(frameAtual, 
                "Seu carrinho esta vazio. Deseja adicionar livros agora?", 
                "Carrinho Vazio", JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                mostrarCatalogoInterativo();
            }
            return;
        }
        
        Cliente cliente = auth.getClienteLogado();
        double total = carrinhoAtual.calcularTotal();
        
        // Janela de seleção de método de pagamento
        JDialog pagamentoDialog = new JDialog(frameAtual, "Selecionar Método de Pagamento", true);
        pagamentoDialog.setSize(400, 300);
        pagamentoDialog.setLocationRelativeTo(frameAtual);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Resumo da compra
        JPanel resumoPanel = new JPanel(new BorderLayout());
        resumoPanel.setBorder(BorderFactory.createTitledBorder("Resumo da Compra"));
        
        StringBuilder resumo = new StringBuilder();
        resumo.append("<html><b>Cliente:</b> ").append(cliente.getNome()).append("<br>");
        resumo.append("<b>Total:</b> R$ ").append(String.format("%.2f", total)).append("<br><br>");
        resumo.append("<b>Itens:</b><br>");
        
        for (Map.Entry<Livro, Integer> entry : carrinhoAtual.getItens().entrySet()) {
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            resumo.append("• ").append(livro.getTitulo())
                  .append(" x").append(quantidade)
                  .append(" = R$ ").append(String.format("%.2f", livro.getPreco() * quantidade))
                  .append("<br>");
        }
        resumo.append("</html>");
        
        JLabel lblResumo = new JLabel(resumo.toString());
        resumoPanel.add(lblResumo, BorderLayout.CENTER);
        
        // Métodos de pagamento
        JPanel metodoPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        metodoPanel.setBorder(BorderFactory.createTitledBorder("Método de Pagamento"));
        
        ButtonGroup bg = new ButtonGroup();
        JRadioButton rbCartao = new JRadioButton("Cartão de Crédito", true);
        JRadioButton rbPix = new JRadioButton("PIX");
        JRadioButton rbBoleto = new JRadioButton("Boleto Bancário");
        
        bg.add(rbCartao);
        bg.add(rbPix);
        bg.add(rbBoleto);
        
        metodoPanel.add(rbCartao);
        metodoPanel.add(rbPix);
        metodoPanel.add(rbBoleto);
        
        // Botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnPagar = new JButton("Realizar Pagamento");
        btnPagar.setBackground(new Color(0, 150, 0));
        btnPagar.setForeground(Color.WHITE);
        
        botoesPanel.add(btnCancelar);
        botoesPanel.add(btnPagar);
        
        // Layout
        panel.add(resumoPanel, BorderLayout.NORTH);
        panel.add(metodoPanel, BorderLayout.CENTER);
        panel.add(botoesPanel, BorderLayout.SOUTH);
        
        // Ações
        btnCancelar.addActionListener(e -> pagamentoDialog.dispose());
        
        btnPagar.addActionListener(e -> {
            Pagamento.MetodoPagamento metodo;
            
            if (rbPix.isSelected()) {
                metodo = Pagamento.MetodoPagamento.PIX;
            } else if (rbBoleto.isSelected()) {
                metodo = Pagamento.MetodoPagamento.BOLETO;
            } else {
                metodo = Pagamento.MetodoPagamento.CARTAO_CREDITO;
            }
            
            pagamentoDialog.dispose();
            processarPagamento(metodo, cliente);
        });
        
        pagamentoDialog.add(panel);
        pagamentoDialog.setVisible(true);
    }
    
    private static void processarPagamento(Pagamento.MetodoPagamento metodo, Cliente cliente) {
        // Simular processamento
        JDialog processandoDialog = new JDialog(frameAtual, "Processando Pagamento", true);
        processandoDialog.setSize(300, 150);
        processandoDialog.setLocationRelativeTo(frameAtual);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblProcessando = new JLabel("Processando pagamento...", SwingConstants.CENTER);
        lblProcessando.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Simular delay
        Timer timer = new Timer(2000, evt -> {
            processandoDialog.dispose();
            
            // Criar pagamento
            Pagamento pagamento = new Pagamento(
                "PED" + System.currentTimeMillis(),
                carrinhoAtual.calcularTotal(),
                metodo
            );
            
            // Processar pagamento (90% de chance de sucesso)
            boolean aprovado = pagamento.processarPagamento("");
            
            if (aprovado) {
                // Finalizar compra
                NotaFiscal nota = checkout.checkout(carrinhoAtual, "COMPRA", cliente);
                
                if (nota != null) {
                    // Mostrar comprovante
                    JDialog comprovanteDialog = new JDialog(frameAtual, "Comprovante de Pagamento", true);
                    comprovanteDialog.setSize(500, 400);
                    comprovanteDialog.setLocationRelativeTo(frameAtual);
                    
                    JPanel comprovantePanel = new JPanel(new BorderLayout());
                    JTextArea txtComprovante = new JTextArea();
                    txtComprovante.setEditable(false);
                    txtComprovante.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    txtComprovante.setText(pagamento.gerarComprovante() + "\n\n" + nota.imprimir());
                    
                    JScrollPane scroll = new JScrollPane(txtComprovante);
                    comprovantePanel.add(scroll, BorderLayout.CENTER);
                    
                    JButton btnOK = new JButton("OK");
                    btnOK.addActionListener(e2 -> {
                        comprovanteDialog.dispose();
                        carrinhoAtual.limpar();
                        JOptionPane.showMessageDialog(frameAtual, 
                            "Compra realizada com sucesso!\nNota fiscal gerada.", 
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    });
                    
                    JPanel btnPanel = new JPanel();
                    btnPanel.add(btnOK);
                    comprovantePanel.add(btnPanel, BorderLayout.SOUTH);
                    
                    comprovanteDialog.add(comprovantePanel);
                    comprovanteDialog.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(frameAtual, 
                    "Pagamento recusado!\nTente novamente com outro método.", 
                    "Erro no Pagamento", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        timer.setRepeats(false);
        timer.start();
        
        panel.add(lblProcessando, BorderLayout.CENTER);
        processandoDialog.add(panel);
        processandoDialog.setVisible(true);
    }
    
    // ========== HISTÓRICO DE PEDIDOS ==========
    
    private static void mostrarHistoricoPedidos() {
        Cliente cliente = auth.getClienteLogado();
        List<Pedido> todosPedidos = reports.relatorioVendas();
        
        // Filtrar pedidos do cliente logado
        List<Pedido> meusPedidos = todosPedidos.stream()
            .filter(p -> p.getClienteCpf().equals(cliente.getCpf()))
            .toList();
        
        if (meusPedidos.isEmpty()) {
            JOptionPane.showMessageDialog(frameAtual, 
                "Você ainda não realizou nenhum pedido.\nComece comprando na opção 'Ver Catálogo'!", 
                "Histórico de Pedidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JDialog historicoDialog = new JDialog(frameAtual, "Meus Pedidos - Histórico", true);
        historicoDialog.setSize(800, 500);
        historicoDialog.setLocationRelativeTo(frameAtual);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabela de pedidos
        String[] colunas = {"ID", "Data", "Total", "Itens", "Tipo", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabelaPedidos = new JTable(tableModel);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
        
        // Popular tabela
        for (Pedido pedido : meusPedidos) {
            tableModel.addRow(new Object[]{
                pedido.getId(),
                pedido.getDataHora().toLocalDate().toString(),
                String.format("R$ %.2f", pedido.getTotal()),
                pedido.getItens().size(),
                pedido.getTipo(),
                "Concluído"  // Poderia vir do pagamento
            });
        }
        
        // Painel inferior
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        JButton btnDetalhes = new JButton("Ver Detalhes do Pedido");
        btnDetalhes.setBackground(new Color(0, 120, 215));
        btnDetalhes.setForeground(Color.WHITE);
        
        JButton btnFechar = new JButton("Fechar");
        
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesPanel.add(btnDetalhes);
        botoesPanel.add(btnFechar);
        
        // Estatísticas
        double totalGasto = meusPedidos.stream().mapToDouble(Pedido::getTotal).sum();
        int totalItens = meusPedidos.stream().mapToInt(p -> p.getItens().size()).sum();
        
        JLabel lblEstatisticas = new JLabel(String.format(
            "<html><b>Total de pedidos:</b> %d | <b>Total gasto:</b> R$ %.2f | <b>Total de itens:</b> %d</html>",
            meusPedidos.size(), totalGasto, totalItens
        ));
        lblEstatisticas.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        bottomPanel.add(lblEstatisticas, BorderLayout.NORTH);
        bottomPanel.add(botoesPanel, BorderLayout.SOUTH);
        
        // Ações
        btnDetalhes.addActionListener(e -> {
            int selectedRow = tabelaPedidos.getSelectedRow();
            if (selectedRow >= 0) {
                Pedido pedidoSelecionado = meusPedidos.get(selectedRow);
                mostrarDetalhesPedido(pedidoSelecionado);
            } else {
                JOptionPane.showMessageDialog(historicoDialog, 
                    "Selecione um pedido primeiro!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnFechar.addActionListener(e -> historicoDialog.dispose());
        
        // Double-click para ver detalhes
        tabelaPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    btnDetalhes.doClick();
                }
            }
        });
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        historicoDialog.add(panel);
        historicoDialog.setVisible(true);
    }
    
    private static void mostrarDetalhesPedido(Pedido pedido) {
        JDialog detalhesDialog = new JDialog(frameAtual, "Detalhes do Pedido #" + pedido.getId(), true);
        detalhesDialog.setSize(600, 400);
        detalhesDialog.setLocationRelativeTo(frameAtual);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Informações do pedido
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("<html>");
        detalhes.append("<h3>Pedido #").append(pedido.getId()).append("</h3>");
        detalhes.append("<b>Data:</b> ").append(pedido.getDataHora()).append("<br>");
        detalhes.append("<b>Tipo:</b> ").append(pedido.getTipo()).append("<br>");
        detalhes.append("<b>Total:</b> R$ ").append(String.format("%.2f", pedido.getTotal())).append("<br><br>");
        
        detalhes.append("<b>Itens do Pedido:</b><br>");
        detalhes.append("<table border='1' cellpadding='5' style='border-collapse: collapse;'>");
        detalhes.append("<tr><th>Livro</th><th>Quantidade</th><th>Preço Unit.</th><th>Subtotal</th></tr>");
        
        for (Map.Entry<Livro, Integer> entry : pedido.getItens().entrySet()) {
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            double subtotal = livro.getPreco() * quantidade;
            
            detalhes.append("<tr>");
            detalhes.append("<td>").append(livro.getTitulo()).append("</td>");
            detalhes.append("<td align='center'>").append(quantidade).append("</td>");
            detalhes.append("<td align='right'>R$ ").append(String.format("%.2f", livro.getPreco())).append("</td>");
            detalhes.append("<td align='right'>R$ ").append(String.format("%.2f", subtotal)).append("</td>");
            detalhes.append("</tr>");
        }
        
        detalhes.append("</table><br>");
        detalhes.append("<b>Total do Pedido:</b> R$ ").append(String.format("%.2f", pedido.getTotal()));
        detalhes.append("</html>");
        
        JEditorPane editorPane = new JEditorPane("text/html", detalhes.toString());
        editorPane.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(editorPane);
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> detalhesDialog.dispose());
        
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnFechar);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        detalhesDialog.add(panel);
        detalhesDialog.setVisible(true);
    }
    
    // ========== MÉTODOS EXISTENTES (mantenha os outros métodos aqui) ==========
    
    private static void mostrarCatalogoInterativo() {
        // ... (mantenha o código existente do catálogo interativo)
        JDialog dialog = new JDialog(frameAtual, "Catalogo de Livros", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(frameAtual);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabela de livros
        String[] colunas = {"Titulo", "Autor", "Genero", "Preco", "Estoque", "ISBN"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabelaLivros = new JTable(tableModel);
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaLivros);
        
        // Popular tabela com livros
        List<Livro> livros = catalog.listarCatalogo();
        for (Livro livro : livros) {
            tableModel.addRow(new Object[]{
                livro.getTitulo(),
                livro.getAutores().toString().replaceAll("[\\[\\]]", ""),
                livro.getGenero(),
                String.format("R$ %.2f", livro.getPreco()),
                catalog.estoque(livro.getIsbn()),
                livro.getIsbn()
            });
        }
        
        // Painel inferior com botoes
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnAdicionar = new JButton("Adicionar ao Carrinho");
        btnAdicionar.setBackground(new Color(0, 150, 0));
        btnAdicionar.setForeground(Color.WHITE);
        
        JButton btnFechar = new JButton("Fechar");
        
        bottomPanel.add(btnAdicionar);
        bottomPanel.add(btnFechar);
        
        // Acoes
        btnAdicionar.addActionListener(e -> {
            int selectedRow = tabelaLivros.getSelectedRow();
            if (selectedRow >= 0) {
                String isbn = (String) tableModel.getValueAt(selectedRow, 5);
                String titulo = (String) tableModel.getValueAt(selectedRow, 0);
                
                // Janela para quantidade
                JPanel qtdPanel = new JPanel(new FlowLayout());
                qtdPanel.add(new JLabel("Quantidade de '" + titulo + "':"));
                
                JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
                spinner.setPreferredSize(new Dimension(80, 25));
                qtdPanel.add(spinner);
                
                int result = JOptionPane.showConfirmDialog(dialog, qtdPanel, 
                    "Adicionar ao Carrinho", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (result == JOptionPane.OK_OPTION) {
                    int quantidade = (Integer) spinner.getValue();
                    
                    if (cartService.adicionarAoCarrinho(carrinhoAtual, isbn, quantidade)) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Livro adicionado ao carrinho com sucesso!", 
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Atualizar estoque na tabela
                        int novoEstoque = catalog.estoque(isbn);
                        tableModel.setValueAt(novoEstoque, selectedRow, 4);
                    } else {
                        JOptionPane.showMessageDialog(dialog, 
                            "Erro ao adicionar livro. Verifique o estoque.", 
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(dialog, 
                    "Selecione um livro primeiro!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnFechar.addActionListener(e -> dialog.dispose());
        
        // Double-click na linha para adicionar rapidamente
        tabelaLivros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    btnAdicionar.doClick();
                }
            }
        });
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private static void buscarLivro() {
        String termo = JOptionPane.showInputDialog(frameAtual, "Digite parte do titulo do livro:", "Buscar Livro", JOptionPane.QUESTION_MESSAGE);
        
        if (termo != null && !termo.trim().isEmpty()) {
            List<Livro> resultados = catalog.buscarPorTitulo(termo.trim());
            
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(frameAtual, 
                    "Nenhum livro encontrado com o termo: " + termo, 
                    "Resultados", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mostrar resultados em uma janela similar
                JDialog dialog = new JDialog(frameAtual, "Resultados da Busca", true);
                dialog.setSize(700, 400);
                dialog.setLocationRelativeTo(frameAtual);
                
                JPanel panel = new JPanel(new BorderLayout());
                
                // Tabela de resultados
                String[] colunas = {"Titulo", "Autor", "Genero", "Preco", "ISBN"};
                DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);
                
                JTable tabelaResultados = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(tabelaResultados);
                
                for (Livro livro : resultados) {
                    tableModel.addRow(new Object[]{
                        livro.getTitulo(),
                        livro.getAutores().toString().replaceAll("[\\[\\]]", ""),
                        livro.getGenero(),
                        String.format("R$ %.2f", livro.getPreco()),
                        livro.getIsbn()
                    });
                }
                
                panel.add(scrollPane, BorderLayout.CENTER);
                
                JButton btnFechar = new JButton("Fechar");
                btnFechar.addActionListener(e -> dialog.dispose());
                
                JPanel bottomPanel = new JPanel();
                bottomPanel.add(btnFechar);
                panel.add(bottomPanel, BorderLayout.SOUTH);
                
                dialog.add(panel);
                dialog.setVisible(true);
            }
        }
    }
    
    private static void mostrarCarrinho() {
        if (carrinhoAtual == null || carrinhoAtual.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(frameAtual, "Seu carrinho esta vazio.", "Meu Carrinho", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("MEU CARRINHO\n");
        sb.append("=".repeat(50)).append("\n\n");
        
        double total = 0;
        for (Map.Entry<Livro, Integer> entry : carrinhoAtual.getItens().entrySet()) {
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            double subtotal = livro.getPreco() * quantidade;
            total += subtotal;
            
            sb.append(String.format("• %s\n", livro.getTitulo()));
            sb.append(String.format("  Quantidade: %d | Preco unitario: R$ %.2f\n", quantidade, livro.getPreco()));
            sb.append(String.format("  Subtotal: R$ %.2f\n\n", subtotal));
        }
        
        sb.append("=".repeat(50)).append("\n");
        sb.append(String.format("TOTAL: R$ %.2f\n", total));
        
        JOptionPane.showMessageDialog(frameAtual, sb.toString(), "Meu Carrinho", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void finalizarCompra() {
        if (carrinhoAtual == null || carrinhoAtual.getItens().isEmpty()) {
            int option = JOptionPane.showConfirmDialog(frameAtual, 
                "Seu carrinho esta vazio. Deseja adicionar livros agora?", 
                "Carrinho Vazio", JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                adicionarLivroAoCarrinho();
            }
            return;
        }
        
        Cliente cliente = auth.getClienteLogado();
        
        // Resumo da compra
        StringBuilder sb = new StringBuilder();
        sb.append("RESUMO DA COMPRA\n");
        sb.append("=".repeat(50)).append("\n\n");
        
        double total = 0;
        for (Map.Entry<Livro, Integer> entry : carrinhoAtual.getItens().entrySet()) {
            Livro livro = entry.getKey();
            int quantidade = entry.getValue();
            double subtotal = livro.getPreco() * quantidade;
            total += subtotal;
            
            sb.append(String.format("• %s x%d = R$ %.2f\n", livro.getTitulo(), quantidade, subtotal));
        }
        
        sb.append("\n").append("=".repeat(50)).append("\n");
        sb.append(String.format("TOTAL: R$ %.2f\n\n", total));
        sb.append("Cliente: ").append(cliente.getNome()).append("\n");
        sb.append("CPF: ").append(cliente.getCpf()).append("\n\n");
        
        sb.append("Deseja finalizar a compra?");
        
        int confirmacao = JOptionPane.showConfirmDialog(frameAtual, sb.toString(), 
            "Finalizar Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            NotaFiscal nota = checkout.checkout(carrinhoAtual, "COMPRA", cliente);
            
            if (nota != null) {
                JOptionPane.showMessageDialog(frameAtual, 
                    nota.imprimir(), 
                    "Compra Realizada com Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                carrinhoAtual.limpar();
            } else {
                JOptionPane.showMessageDialog(frameAtual, 
                    "Erro ao processar compra. Verifique o estoque.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private static void adicionarLivroAoCarrinho() {
        String isbn = JOptionPane.showInputDialog(frameAtual, 
            "Digite o ISBN do livro:", "Adicionar ao Carrinho", JOptionPane.QUESTION_MESSAGE);
        
        if (isbn != null && !isbn.trim().isEmpty()) {
            String quantidadeStr = JOptionPane.showInputDialog(frameAtual, 
                "Digite a quantidade:", "Quantidade", JOptionPane.QUESTION_MESSAGE);
            
            try {
                int quantidade = Integer.parseInt(quantidadeStr);
                
                if (cartService.adicionarAoCarrinho(carrinhoAtual, isbn.trim(), quantidade)) {
                    JOptionPane.showMessageDialog(frameAtual, 
                        "Livro adicionado ao carrinho com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frameAtual, 
                        "Livro nao encontrado ou ISBN invalido.", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frameAtual, 
                    "Quantidade invalida!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private static void meusPedidos() {
        JOptionPane.showMessageDialog(frameAtual, 
            "Funcionalidade 'Meus Pedidos' em desenvolvimento.\nEm breve voce podera ver seu historico de compras aqui.", 
            "Em Desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void mostrarPerfil() {
        Cliente cliente = auth.getClienteLogado();
        
        StringBuilder sb = new StringBuilder();
        sb.append("MEU PERFIL\n");
        sb.append("=".repeat(50)).append("\n\n");
        sb.append("Nome: ").append(cliente.getNome()).append("\n");
        sb.append("Email: ").append(cliente.getEmail()).append("\n");
        sb.append("CPF: ").append(cliente.getCpf()).append("\n");
        sb.append("Endereco: ").append(cliente.getEndereco()).append("\n\n");
        sb.append("=".repeat(50)).append("\n");
        
        JOptionPane.showMessageDialog(frameAtual, sb.toString(), "Meu Perfil", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void mostrarRelatorios() {
        ReportService report = new ReportService();
        double totalVendas = report.totalVendas();
        
        StringBuilder sb = new StringBuilder();
        sb.append("RELATORIOS DO SISTEMA\n");
        sb.append("=".repeat(50)).append("\n\n");
        sb.append("Total de vendas: R$ ").append(String.format("%.2f", totalVendas)).append("\n");
        sb.append("\n(Relatorios detalhados em desenvolvimento)\n");
        
        JOptionPane.showMessageDialog(frameAtual, sb.toString(), "Relatorios", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void mostrarAjuda() {
        StringBuilder sb = new StringBuilder();
        sb.append("AJUDA - LIVRARIA ONLINE\n");
        sb.append("=".repeat(50)).append("\n\n");
        sb.append("Para teste, use:\n");
        sb.append("• CPF: 12345678901 | Senha: senha123\n");
        sb.append("• CPF: 98765432100 | Senha: senha456\n\n");
        sb.append("Funcionalidades disponiveis:\n");
        sb.append("• Cadastro de clientes\n");
        sb.append("• Login com senha\n");
        sb.append("• Catalogo de livros\n");
        sb.append("• Busca por titulo\n");
        sb.append("• Carrinho de compras\n");
        sb.append("• Finalizacao de compra\n");
        sb.append("• Geracao de nota fiscal\n");
        
        JOptionPane.showMessageDialog(frameAtual, sb.toString(), "Ajuda", JOptionPane.INFORMATION_MESSAGE);
    }
}