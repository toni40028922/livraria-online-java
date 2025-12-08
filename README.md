# 📚 Livraria Online – Projeto Java (Programação II)

![Java](https://img.shields.io/badge/Java-17-blue)
![JUnit](https://img.shields.io/badge/JUnit-5-red)
![License](https://img.shields.io/badge/License-MIT-green)
![Status](https://img.shields.io/badge/Status-100%25_Concluído-brightgreen)

Sistema completo de **Livraria Online** desenvolvido em Java, simulando um ambiente real com autenticação, catálogo, carrinho de compras, checkout, pagamentos (cartão, PIX, boleto), nota fiscal, controle de estoque e relatórios.  
Desenvolvido para a disciplina **Programação II**, com foco em **POO, modularização, camadas e persistência**.

---

# ✨ Funcionalidades

## 🔐 Autenticação
- Cadastro de clientes  
- Login por email e senha  
- Recuperação de senha via **CPF** ou **email**  
- Níveis de acesso (visitante vs usuário logado)

## 📚 Catálogo de Livros
- Listagem completa  
- Busca por **título**  
- Busca por **ISBN**  
- Detalhes do livro

## 🛒 Carrinho de Compras
- Adicionar itens por ISBN  
- Alterar quantidades  
- Remover itens  
- Calcular total  
- Persistência durante a sessão

## 💳 Checkout e Pagamentos
- Pagamento via **Cartão de Crédito**  
- Pagamento via **PIX** (QR code textual fictício)  
- Pagamento via **Boleto Bancário**  
- Desconto automático para PIX  
- Geração de **Nota Fiscal** pós-compra

## 📦 Estoque
- Baixa automática após compra  
- Bloqueio se o estoque estiver insuficiente  
- Relatório de inventário

## 📊 Relatórios
- Relatório de vendas  
- Relatório do estoque  
- Histórico do cliente  
- Total faturado

---

# 🛠️ Tecnologias

- **Java 17 (LTS)**  
- **JUnit 5** (testes)  
- **Coleções Java:** List, Map, Set  
- **Serialização de objetos**  
- **Arquitetura em camadas (MVC simplificado)**  
- **ANSI colors** para UI interativa

---

# 🏗️ Arquitetura do Projeto
