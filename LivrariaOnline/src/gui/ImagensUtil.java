package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagensUtil {
    public static ImageIcon getIcone(String nome) {
        try {
            // Em sistema real, carregaria de arquivo
            // Para simplificar, criamos ícones com texto
            return criarIconeTexto(getTextoIcone(nome));
        } catch (Exception e) {
            return criarIconeTexto("?");
        }
    }
    
    private static String getTextoIcone(String nome) {
        switch (nome.toLowerCase()) {
            case "livro": return "L";
            case "carrinho": return "C";
            case "usuario": return "U";
            case "buscar": return "B";
            case "sair": return "X";
            default: return "?";
        }
    }
    
    private static ImageIcon criarIconeTexto(String texto) {
        // Criar uma imagem simples com o texto
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Fundo
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(0, 0, 32, 32);
        
        // Texto
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Centralizar texto
        FontMetrics fm = g2d.getFontMetrics();
        int x = (32 - fm.stringWidth(texto)) / 2;
        int y = (32 - fm.getHeight()) / 2 + fm.getAscent();
        
        g2d.drawString(texto, x, y);
        g2d.dispose();
        
        return new ImageIcon(img);
    }
}