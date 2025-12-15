package gui;

import javax.swing.*;
import java.awt.*;

public class JanelaPersonalizada {
    
    public static JButton criarBotao(String texto, Color corFundo, Color corTexto) {
        JButton botao = new JButton(texto);
        botao.setBackground(corFundo);
        botao.setForeground(corTexto);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        return botao;
    }
    
    public static JPanel criarPainelComBorda(String titulo) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1), titulo));
        return painel;
    }
    
    public static JLabel criarLabelTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(0, 100, 200));
        return label;
    }
}