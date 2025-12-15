module LivrariaOnline {
    requires java.desktop;  // Para Swing/AWT
    
    // Exporta os pacotes que precisam ser acessíveis
    exports app;
    exports model;
    exports service;
    exports repository;
    exports util;
    exports gui;
}