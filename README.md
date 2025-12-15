# 📚 Livraria Online – Projeto Java (Programação II)

![Java](https://img.shields.io/badge/Java-17-blue)
![JUnit](https://img.shields.io/badge/JUnit-5-red)
![License](https://img.shields.io/badge/License-MIT-green)
![Status](https://img.shields.io/badge/Status-100%25_Concluído-brightgreen)

Sistema completo de **Livraria Online** desenvolvido em Java, simulando um ambiente real com autenticação, catálogo, carrinho de compras, checkout, pagamentos (cartão, PIX, boleto), nota fiscal, controle de estoque e relatórios.  
Desenvolvido para a disciplina **Programação II**, com foco em **POO, modularização, camadas e persistência**.

---

## ✨ Funcionalidades Principais

### 🔐 Autenticação & Usuários
- Cadastro completo de clientes (CPF, nome, email, senha)
- Login seguro com verificação de senha
- Recuperação de senha via CPF/email
- Separação: Visitante (só visualiza) vs Cliente (compra)

### 📚 Catálogo Inteligente
- Listagem completa com estoque em tempo real
- Busca rápida por **ISBN** (Map para O(1))
- Busca por **título** ou **autor**
- Detalhes completos de cada livro

### 🛒 Carrinho de Compras
- Adicionar/remover livros com quantidades
- Cálculo automático do total
- Persistência durante a sessão
- Validação de estoque em tempo real

### 💳 Sistema de Pagamento
- **Cartão de Crédito** (validação de número)
- **PIX** com código QR textual
- **Boleto Bancário** simulado
- Processamento com 90% de aprovação simulada

### 📦 Gestão de Estoque
- Baixa automática após compras
- Bloqueio de vendas sem estoque
- Relatório de inventário atualizado

### 📊 Relatórios & Análises
- Histórico completo de vendas
- Relatório de estoque por ISBN
- Total faturado
- Pedidos por cliente

---

## 🚀 Funcionalidades Extras (além do exigido)

✅ **Sistema de pagamento completo** (Cartão, PIX, Boleto)  
✅ **Nota Fiscal** profissional com detalhes  
✅ **Recuperação de senha** simulada  
✅ **Interface colorida** (ANSI colors)  
✅ **Validações** de CPF, email, cartão  
✅ **Dados de exemplo** pré-cadastrados  
✅ **Menu intuitivo** com emojis e fluxo guiado

---

## 🛠️ Tecnologias Utilizadas

- **Java 17 (LTS)** – Linguagem principal
- **JUnit 5** – Testes unitários
- **Coleções Java** – List, Map, Set com uso justificado
- **Serialização** – Persistência em arquivos `.db`
- **ANSI Colors** – Interface colorida no terminal
- **Arquitetura em Camadas** – Separação MVC-like

---

## 🏗️ Arquitetura do Projeto

```
LivrariaOnline/
├── src/
│   ├── app/           # Interface com usuário (Main + menus)
│   ├── model/         # Entidades (Livro, Cliente, Pedido...)
│   ├── repository/    # Persistência em arquivos (.db)
│   ├── service/       # Regras de negócio
│   └── util/          # Utilitários
├── test/              # Testes unitários (JUnit 5)
├── .gitignore         # Configuração Git
├── LICENSE            # Licença MIT
└── README.md          # Esta documentação
```

---

## ▶️ Como Executar

### No Eclipse:
1. **Importe** o projeto: `File → Import → Existing Projects into Workspace`
2. **Execute** a classe: `app.Main.java`
3. **Use dados de exemplo**:
   - CPF: `12345678901`
   - Senha: `senha123`

### Via JAR Executável:
1. Exporte: `File → Export → Runnable JAR File`
2. Selecione: `app.Main` como Launch Configuration
3. Execute: `java -jar LivrariaOnline.jar`

---

## ✅ Checklist de Requisitos Atendidos

### Obrigatórios (exigidos pelo professor):
- [x] CRUD completo de livros/autores
- [x] Busca por ISBN e título
- [x] Carrinho de compras funcional
- [x] Checkout com geração de Pedido
- [x] Atualização automática de estoque
- [x] Relatórios de vendas e inventário
- [x] Persistência em arquivos
- [x] Uso justificado de List, Map, Set
- [x] Encapsulamento correto das classes
- [x] Testes unitários JUnit
- [x] JAR executável
- [x] Documentação completa

### Extras Implementados:
- [x] Sistema de autenticação com senha
- [x] Múltiplos métodos de pagamento
- [x] Nota fiscal eletrônica
- [x] Interface amigável com cores
- [x] Validações de dados de entrada
- [x] Recuperação de conta
- [x] Dados de exemplo para testes rápidos

---

## 👥 Autores

### 👨‍💻 ToniJosué Barbosa de Araújo
- **Curso:** Tecnologia em Telemática
- **GitHub:** [@Toni-araujo](https://github.com/Toni-araujo)
  
**Principais Contribuições:**
- Arquitetura completa do sistema
- Implementação de todas as funcionalidades core
- Sistema de pagamentos (cartão, PIX, boleto)
- Interface de usuário com menus coloridos
- Integração de serviços e persistência em arquivos
- Lógica de negócio completa
  
### 🤝 Contribuição & Revisão
**👨‍💻 Samuel Clementino da Costa**  
- **Curso:** Tecnologia em Telemática
- **GitHub:** [@SamuelClementino](https://github.com/SamuelClementino)
  
**Contribuições:**
- Revisão de código e documentação
- Testes auxiliares e validações
- Suporte na organização do projeto

---

## 📋 Modelo de Trabalho

O projeto foi desenvolvido seguindo metodologia de **desenvolvimento colaborativo**:
- **ToniJosué**: Responsável pelo desenvolvimento técnico principal
- **Samuel**: Atuou como revisor e validador dos requisitos
- **Ambos**: Participaram das definições de arquitetura e testes finais

---

## 📄 Licença

Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para detalhes.

*Projeto desenvolvido como trabalho acadêmico para a disciplina de Programação II do curso de Tecnologia em Telemática.*
