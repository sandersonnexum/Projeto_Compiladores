# Compilador LPS1 — Projeto B (TCD de Compiladores)

## 📘 Sobre o Projeto

Este repositório contém a implementação de um **compilador para a linguagem LPS1 (Linguagem de Programação Simples 1)**, desenvolvido como parte do Trabalho de Conclusão da Disciplina (TCD) da matéria de **Compiladores** do Centro Universitário de Brasília — CEUB.

A linguagem LPS1 é minimalista:  
- Todas as palavras‑chave são compostas por **uma única letra maiúscula**.  
- Todas as variáveis são **letras minúsculas**, automaticamente declaradas como inteiros.  
- O único tipo existente é **int**.  

O projeto é dividido em duas versões:

### ✔ (a) Parser com geração de código embutida  
O analisador sintático gera o código C diretamente durante a análise.

### ✔ (b) Parser + ASA (Árvore Sintática Abstrata)  
O parser constrói a ASA, e cada nó da árvore possui métodos próprios para gerar código C.

Ambas as versões devem imprimir o código C gerado na saída padrão.

---

## 🧩 Estrutura da Linguagem LPS1

A linguagem possui comandos de:

- Atribuição  
- Entrada (Get)  
- Impressão  
- Operações aritméticas: `+`, `-`, `*`, `/`, `%`  
- Estruturas condicionais (`I`)  
- Estruturas de repetição (`W`)  
- Blocos compostos `{ ... }`

### ✔ Variáveis  
- Letras minúsculas (`a` a `z`)  
- Tipo único: **int**

### ✔ Números  
- Apenas **um dígito** na gramática  
- Em execução podem assumir valores maiores

### ✔ Operadores de comparação  
- `=` (igual)  
- `<` (menor)  
- `#` (diferente)

---


---

## 🏗 Estrutura do Compilador

O projeto contém:

### **1. Analisador Léxico**
Responsável por identificar tokens como:
- comandos (`G`, `P`, `W`, `I`, `+`, `-`, `*`, `/`, `0`, `=`)  
- variáveis  
- números  
- operadores  
- delimitadores `{` e `}`  

### **2. Analisador Sintático**
Implementado em duas versões:

#### ✔ (a) Parser com geração de código embutida  
O código C é produzido durante a análise.

#### ✔ (b) Parser + ASA  
O parser constrói uma Árvore Sintática Abstrata.  
Cada nó da ASA implementa um método `generateC()`.

### **3. Geração de Código C**
A saída deve conter:

- Declaração de todas as variáveis (`int a, b, c, ...;`)
- Buffer auxiliar para leitura (`char str[512];`)
- Tradução fiel dos comandos LPS1 para C

---

## 🧪 Exemplos Obrigatórios

O projeto deve incluir a tradução dos dois exemplos fornecidos no enunciado:

### ✔ Primeiro exemplo  
(Exemplo completo já demonstrado no PDF)

### ✔ Segundo exemplo  
O compilador deve gerar o código C correspondente e explicar o comportamento do programa.

---

## ⚠ Regras Importantes

- **Nenhuma verificação semântica** deve ser feita.  
- Mensagens de erro devem ser claras, como:  
  - `"Número esperado"`  
  - `"Comando inexistente"`  
- Implementação obrigatória em **Java**.  
- A saída deve ser **exatamente o código C gerado**.  
- Entregar:  
  - Folha de capa  
  - Código-fonte das versões (a) e (b)  
  - Código C gerado para os dois exemplos  

---

## 🧠 O que faz o segundo exemplo?

O segundo exemplo:

- Lê `n`
- Define `i = 2`
- Calcula `a = n % i`
- Executa um loop `while (i < n)`:
  - Se `a == 0`, executa um comando
  - Incrementa `i`
  - Recalcula `a = n % i`
- Após o loop:
  - Se `a == 0`, imprime `0`
  - Se `a != 0`, imprime `1`

👉 **Em resumo:**  
O programa testa divisores de `n` e imprime valores conforme o resto da divisão.  
É uma espécie de verificador simples baseado em módulo.

---

## 👨‍💻 Autor

Projeto desenvolvido como parte da disciplina **Compiladores** — CEUB.