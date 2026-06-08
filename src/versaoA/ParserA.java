package versaoA;

import java.util.ArrayList;
import lexer.Token;
import lexer.TokenType;

/**
 * Analisador sintático — Versão A (geração de código embutida).
 *
 * Consome a lista de tokens produzida pelo {@link lexer.Lexer} e, durante
 * a própria análise sintática, emite o código C equivalente diretamente
 * via {@code System.out.println}. Não há estrutura intermediária (ASA).
 *
 * Fluxo:
 *   tokens → parseComando() → imprime código C na saída padrão
 *
 * Gramática reconhecida (simplificada):
 *   programa  ::= comando*
 *   comando   ::= leitura | impressão | atribuição | aritmética
 *               | condicional | repetição | bloco
 */
public class ParserA {

    private ArrayList<Token> tokens;

    /** Índice do token sendo analisado no momento. */
    private int pos;

    /** Recebe a lista de tokens e inicia na posição 0. */
    public ParserA(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    /** Retorna o token atual sem avançar o cursor. */
    private Token atual() {
        return tokens.get(pos);
    }

    /** Retorna o token atual e avança o cursor para o próximo. */
    private Token consumir() {
        return tokens.get(pos++);
    }

    /**
     * Inicia a análise sintática e a geração de código C.
     * Emite o cabeçalho C, analisa todos os comandos e fecha o main().
     */
    public void parse() {
        gerarCabecalho();
        while (pos < tokens.size()) {
            parseComando();
        }
        System.out.println("}");
    }

    /**
     * Emite o cabeçalho C padrão do programa gerado.
     * Declara todas as variáveis (a–z) como inteiros e um buffer de leitura.
     */
    private void gerarCabecalho() {
        System.out.println("#include <stdio.h>");
        System.out.println("");
        System.out.println("int main() {");
        System.out.println("  int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;");
        System.out.println("  char str[512];");
    }

    /**
     * Identifica o tipo do token atual e despacha para o método correto.
     * É o núcleo do parser — corresponde à regra "comando" da gramática.
     */
    private void parseComando() {
        Token t = atual();
        switch (t.tipo) {
            case LEGE:
                parseLeitura();
                break;
            case DIC:
                parseImpressao();
                break;
            case PONE:
                parseAtribuicao();
                break;
            case ADDE:
                parseAritmetica("+");
                break;
            case SUBTRAHE:
                parseAritmetica("-");
                break;
            case MULTIPLICA:
                parseAritmetica("*");
                break;
            case DIVIDE:
                parseAritmetica("/");
                break;
            case MODULUS:
                parseAritmetica("%");
                break;
            case SI:
                parseSe();
                break;
            case REPETE:
                parseEnquanto();
                break;
            case SINISTRA:
                parseBloco();
                break;
            default:
                throw new RuntimeException("Comando inexistente: " + t.valor);
        }
    }

    /** Comando L: lê um inteiro do teclado e armazena na variável. */
    private void parseLeitura() {
        consumir(); // consome o LEGE
        Token var = consumir(); // consome a variável (ex: "n")
        System.out.println("  // L " + var.valor);
        System.out.println("  { gets(str); sscanf(str, \"%d\", &" + var.valor + "); }");
    }

    /** Comando D: imprime o valor da variável na saída padrão. */
    private void parseImpressao() {
        consumir(); // consome o DIC
        Token var = consumir(); // consome a variável ou número (ex: "n")
        System.out.println("  // D " + var.valor);
        System.out.println("  printf(\"%d\\n\", " + var.valor + ");");
    }

    /** Comando P: atribui um valor (variável ou literal) a uma variável. */
    private void parseAtribuicao() {
        consumir(); // consome o PONE
        Token var = consumir(); // consome a variável destino (ex: "i")
        System.out.println("  // P " + var.valor);
        System.out.print("  " + var.valor + " = ");
        parseValor();
        System.out.println(";");
    }

    /**
     * Comandos +, -, *, /, %: operação aritmética com destino e dois operandos.
     * Formato LPS1: <op> <dest> <val1> <val2>
     * Exemplo: "* a p i" → "a = p * i;"
     */
    private void parseAritmetica(String op) {
        consumir(); // consome o operador
        Token dest = consumir(); // variável destino
        Token val1 = consumir(); // primeiro operando
        Token val2 = consumir(); // segundo operando
        System.out.println("  // " + op + " " + dest.valor + " " + val1.valor + " " + val2.valor);
        System.out.println("  " + dest.valor + " = " + val1.valor + " " + op + " " + val2.valor + ";");
    }

    /**
     * Comando S: condicional if.
     * Formato LPS1: S <var> <op> <val> <comando>
     * Exemplo: "S a = 0 D 0" → "if (a == 0) { printf(...); }"
     */
    private void parseSe() {
        consumir(); // consome SI
        System.out.print("  if (");
        parseComparacao();
        System.out.println(") {");
        parseComando(); // único comando (ou bloco {}) dentro do if
        System.out.println("  }");
    }

    /**
     * Comando R: repetição while.
     * Formato LPS1: R <var> <op> <val> <bloco>
     * Exemplo: "R i # n { ... }" → "while (i != n) { ... }"
     */
    private void parseEnquanto() {
        consumir(); // consome REPETE
        System.out.print("  while (");
        parseComparacao();
        System.out.println(") {");
        parseComando(); // geralmente um parseBloco()
        System.out.println("  }");
    }

    /**
     * Bloco composto { }: consome todos os comandos até encontrar '}'.
     * Usado como corpo de if e while.
     */
    private void parseBloco() {
        consumir(); // consome SINISTRA {
        while (atual().tipo != TokenType.DEXTRA && pos < tokens.size()) {
            parseComando();
        }
        consumir(); // consome DEXTRA }
    }

    /** Consome e imprime um valor simples: variável ou literal numérico. */
    private void parseValor() {
        Token t = atual();
        if (t.tipo == TokenType.VARIABILIS || t.tipo == TokenType.NUMERUS) {
            consumir();
            System.out.print(t.valor);
        } else {
            throw new RuntimeException("Expressão inválida: " + t.valor);
        }
    }

    /**
     * Analisa e imprime uma expressão de comparação.
     * Formato LPS1: <var> <op> <val>  onde op é =, < ou #
     * Converte para C: ==, < ou !=
     */
    private void parseComparacao() {
        Token var = consumir(); // variável esquerda (ex: "i")
        Token op  = consumir(); // operador LPS1 (=, <, #)
        Token val = consumir(); // valor direito (ex: "n")

        String opC;
        switch (op.tipo) {
            case AEQUALIS:
                opC = "==";
                break;
            case MINOR:
                opC = "<";
                break;
            case DIVERSUS:
                opC = "!=";
                break;
            default:
                throw new RuntimeException("Operador inválido: " + op.valor);
        }
        System.out.print(var.valor + " " + opC + " " + val.valor);
    }
}
