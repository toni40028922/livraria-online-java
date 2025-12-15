package gui;

import model.Livro;
import javax.swing.*;
import java.awt.*;

public class CardLivro extends JPanel {
    private Livro livro;
    
    public CardLivro(Livro livro) {
        this.livro = livro;
        initUI();
    }
    
    private void initUI() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setBackground(Color.WHITE);
        
        JLabel lblTitulo = new JLabel("<html><b>" + livro.getTitulo() + "</b></html>");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblPreco = new JLabel(String.format("R$ %.2f", livro.getPreco()));
        lblPreco.setFont(new Font("Arial", Font.BOLD, 14));
        lblPreco.setForeground(new Color(0, 150, 0));
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(lblTitulo, BorderLayout.CENTER);
        infoPanel.add(lblPreco, BorderLayout.SOUTH);
        
        add(infoPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(180, 80));
    }
    
    public Livro getLivro() {
        return livro;
    }
}