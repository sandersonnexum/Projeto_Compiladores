# Compilador LPS1 — Projeto B (TCD de Compiladores)

## Sobre o Projeto

Este repositório contém a implementação de um **compilador para a linguagem LPS1 (Linguagem de Programação Simples 1)** com vocabulário latino, desenvolvido como parte do Trabalho de Conclusão da Disciplina (TCD) da matéria de **Compiladores** do Centro Universitário de Brasília — CEUB.

A linguagem LPS1 é minimalista:
- Todas as palavras-chave são compostas por **uma única letra maiúscula**
- Todas as variáveis são **letras minúsculas**, automaticamente declaradas como inteiros
- O único tipo existente é **int**

O projeto é dividido em duas versões:

### (a) Parser com geração de código embutida
O analisador sintático gera o código C diretamente durante a análise, com as instruções de geração misturadas às do parser.

### (b) Parser + ASA (Árvore Sintática Abstrata)
O parser constrói a ASA e cada nó da árvore possui o método `generateC()` responsável pela geração do código C correspondente.

Ambas as versões imprimem o código C gerado na saída padrão via `System.out.println`.

---

## Como Compilar e Executar

### Compilar
```
javac -d bin src/App.java src/lexer/*.java src/versaoA/*.java src/versaob/*.java
```

### Executar e salvar saída
```
java -cp bin App > saida.txt
```

### Compilar e executar em um único comando
```
javac -d bin src/App.java src/lexer/*.java src/versaoA/*.java src/versaob/*.java ; java -cp bin App > saida.txt
```

O arquivo `saida.txt` conterá o código C gerado pelo compilador.

---

## Estrutura da Linguagem LPS1 (vocabulário latino)

### Comandos

| Token | Nome latino | Função | Equivalente C |
|-------|-------------|--------|---------------|
| `L` | *Lege* | Leitura do teclado | `{ gets(str); sscanf(str, "%d", &a); }` |
| `D` | *Dic* | Impressão na saída padrão | `printf("%d\n", a);` |
| `P` | *Pone* | Atribuição | `a = b;` |
| `S` | *Si* | Condicional (if) | `if (cond) { ... }` |
| `R` | *Repete* | Repetição (while) | `while (cond) { ... }` |

### Operadores aritméticos

| Token | Nome latino | Equivalente C |
|-------|-------------|---------------|
| `+` | *Adde* | `a = b + c;` |
| `-` | *Subtrahe* | `a = b - c;` |
| `*` | *Multiplica* | `a = b * c;` |
| `/` | *Divide* | `a = b / c;` |
| `%` | *Modulus* | `a = b % c;` |

### Operadores de comparação

| Token | Nome latino | Significado |
|-------|-------------|-------------|
| `=` | *Aequalis* | igual a |
| `<` | *Minor* | menor que |
| `#` | *Diversus* | diferente de |

### Variáveis e números
- Variáveis: letras minúsculas `a`–`z`, tipo `int` automático
- Números: dígito único na gramática (`0`–`9`)
- Blocos compostos: `{ ... }`

---

## Exemplos

### Exemplo próprio — *Summa*

Programa que lê `n` e calcula a soma de todos os inteiros de 1 até `n` (*Summa omnium*):

```
L n
P s 0
P i 1
R i # n {
  + s s i
  + i i 1
}
+ s s n
D s
```

Código C gerado:
```c
#include <stdio.h>

int main() {
  int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
  char str[512];
  // L n
  { gets(str); sscanf(str, "%d", &n); }
  // P s
  s = 0;
  // P i
  i = 1;
  while (i != n) {
  // + s s i
  s = s + i;
  // + i i 1
  i = i + 1;
  }
  // + s s n
  s = s + n;
  // D s
  printf("%d\n", s);
}
```

---

### Primeiro exemplo — múltiplos de p

Versão traduzida para a linguagem LPS1 latina:

```
L n
L p
P i 0
R i # n {
  * a p i
  D a
  + i i 1
}
```

Código C gerado:
```c
#include <stdio.h>

int main() {
  int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
  char str[512];
  // L n
  { gets(str); sscanf(str, "%d", &n); }
  // L p
  { gets(str); sscanf(str, "%d", &p); }
  // P i
  i = 0;
  while (i != n) {
  // * a p i
  a = p * i;
  // D a
  printf("%d\n", a);
  // + i i 1
  i = i + 1;
  }
}
```

Lê dois números `n` e `p` e imprime os `n` primeiros múltiplos de `p`, começando por 0.

---

### Segundo exemplo — verificador de primalidade

Versão traduzida para a linguagem LPS1 latina:

```
L n
P i 2
% a n i
R i < n {
  S a = 0 P i n
  + i i 1
  % a n i
}
S a = 0 D 0
S a # 0 D 1
```

Código C gerado:
```c
#include <stdio.h>

int main() {
  int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
  char str[512];
  // L n
  { gets(str); sscanf(str, "%d", &n); }
  // P i
  i = 2;
  // % a n i
  a = n % i;
  while (i < n) {
  if (a == 0) {
  // P i
  i = n;
  }
  // + i i 1
  i = i + 1;
  // % a n i
  a = n % i;
  }
  if (a == 0) {
  // D 0
  printf("%d\n", 0);
  }
  if (a != 0) {
  // D 1
  printf("%d\n", 1);
  }
}
```

**O que faz:** lê `n`, inicializa `i = 2` e testa divisores de `n` no intervalo `[2, n-1]`. A cada iteração calcula `a = n % i`. Se encontrar `a = 0` (divisor exato), atribui `i = n` para forçar o fim do loop. Ao final, imprime `0` se encontrou divisor (não é primo) ou `1` se não encontrou (é primo). É um verificador de primalidade simplificado.

---

## Estrutura do Projeto

```
compilador-lps1/
└── src/
    ├── lexer/
    │   ├── TokenType.java     ← enum com todos os tipos de token
    │   ├── Token.java         ← par (tipo, valor)
    │   └── Lexer.java         ← lê o texto e devolve lista de tokens
    ├── versaoA/
    │   └── ParserA.java       ← parser com geração de código embutida
    ├── versaob/
    │   ├── Node.java          ← interface com método generateC()
    │   ├── NodeGet.java       ← nó do comando L
    │   ├── NodePrint.java     ← nó do comando D
    │   ├── NodeAssign.java    ← nó do comando P
    │   ├── NodeArith.java     ← nó dos operadores + - * / %
    │   ├── NodeIf.java        ← nó do comando S
    │   ├── NodeWhile.java     ← nó do comando R
    │   ├── NodeBlock.java     ← nó do bloco { }
    │   ├── NodeProgram.java   ← nó raiz do programa
    │   └── ParserB.java       ← parser que constrói a ASA
    └── App.java               ← ponto de entrada
```

---

## Gramática da LPS1

```
Program         ::= Command { Command }
Command         ::= AssignCommand | GetCommand | AddCommand | SubCommand
                  | MultCommand | DivCommand | ModCommand | PrintCommand
                  | IfCommand | WhileCommand | CompositeCommand
AssignCommand   ::= P Variable Value
GetCommand      ::= L Variable
AddCommand      ::= + Variable Value Value
SubCommand      ::= - Variable Value Value
MultCommand     ::= * Variable Value Value
DivCommand      ::= / Variable Value Value
ModCommand      ::= % Variable Value Value
PrintCommand    ::= D Value
Comparison      ::= Variable Operator Value
IfCommand       ::= S Comparison Command
WhileCommand    ::= R Comparison Command
CompositeCommand::= { Command { Command } }
Value           ::= Variable | Number
```

---

## Decisões de Projeto

| ID | Decisão |
|----|---------|
| D-01 | Linguagem LPS1 com vocabulário latino |
| D-02 | Palavras-chave: `L`, `D`, `P`, `S`, `R` |
| D-03 | Operadores aritméticos: `+`, `-`, `*`, `/`, `%` (símbolos) |
| D-04 | Comparadores: `=`, `<`, `#` (mantidos do enunciado) |
| D-05 | Bloco composto: `{ }` (mantido) |
| D-06 | Variáveis: letras minúsculas, tipo `int` automático |
| D-07 | Exemplo próprio: *Summa* — soma de 1 até N |
| D-08 | Implementação em Java (obrigatório pelo enunciado) |
| D-09 | Saída via `System.out.println` (obrigatório pelo enunciado) |
| D-10 | Pacote único com subpacotes `lexer`, `versaoA`, `versaob` |
| D-11 | Exemplos do professor traduzidos para a linguagem LPS1 latina |
| D-12 | Saída padrão salva em `saida.txt` via redirecionamento no terminal |

---

## Regras Importantes

- Nenhuma verificação semântica é realizada
- Mensagens de erro indicam exatamente o problema: `"Caractere inválido"`, `"Comando inexistente"`
- Implementação obrigatória em **Java**
- A saída é **exatamente o código C gerado**

### Itens a entregar (impressos)
1. Folha de capa (disponível no SGI — obrigatória)
2. Listagem do código-fonte versão (a) — identificada com `// (a)` no início
3. Listagem do código-fonte versão (b) — identificada com `// (b)` no início
4. Código C gerado para os dois exemplos do enunciado
5. Código C gerado para o exemplo *Summa*
6. Explicação em português do que o segundo exemplo faz

---

## Autor

Projeto desenvolvido como parte da disciplina **Compiladores** — CEUB.
