package versaoA;

import java.util.ArrayList;
import lexer.Token;
import lexer.TokenType;

public class ParserA {

    private ArrayList<Token> tokens;
    private int pos;

    // construtor: recebe a lista e começa na posição 0
    public ParserA(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    // devolve o token atual sem avançar
    private Token atual() {
        return tokens.get(pos);
    }

    // consome o token atual e avança para o próximo
    private Token consumir() {
        return tokens.get(pos++);
    }

    // método principal: começa a análise sintática
    public void parse() {
        gerarCabecalho();

        while (pos < tokens.size()) {
            parseComando();
            // Adicione lógica de parsing aqui
        }

        System.out.println("}");
    }

    private void gerarCabecalho() {
        System.out.println("#include <stdio.h>");
        System.out.println("");
        System.out.println("int main() {");
        System.out.println("  int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;");
        System.out.println("  char str[512];");
    }

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

    private void parseLeitura() {
        consumir(); // consome o LEGE
        Token var = consumir(); // consome a variável (ex: "n")
        System.out.println("  // L " + var.valor);
        System.out.println("  { gets(str); sscanf(str, \"%d\", &" + var.valor + "); }");
    }

    private void parseImpressao() {
        consumir(); // consome o DIC
        Token var = consumir(); // consome a variável (ex: "n")
        System.out.println("  // D " + var.valor);
        System.out.println("  printf(\"%d\\n\", " + var.valor + ");");
    }

    private void parseAtribuicao() {
        consumir(); // consome o PONE
        Token var = consumir(); // consome a variável (ex: "a")
        System.out.println("  // P " + var.valor);
        System.out.print("  " + var.valor + " = ");
        parseValor();
        System.out.println(";");
    }

    private void parseAritmetica(String op) {
        consumir(); // consome o operador
        Token dest = consumir(); // destino
        Token val1 = consumir(); // valor1
        Token val2 = consumir(); // valor2
        System.out.println("  // " + op + " " + dest.valor + " " + val1.valor + " " + val2.valor);
        System.out.println("  " + dest.valor + " = " + val1.valor + " " + op + " " + val2.valor + ";");
    }

    private void parseSe() {
        consumir(); // consome SI
        System.out.print("  if (");
        parseComparacao();
        System.out.println(") {");
        parseComando(); // o comando que vem depois
        System.out.println("  }");
    }

    private void parseEnquanto() {
        consumir(); // consome REPETE
        System.out.print("  while (");
        parseComparacao();
        System.out.println(") {");
        parseComando(); // será um parseBloco()
        System.out.println("  }");
    }

    private void parseBloco() {
        consumir(); // consome o DeXTRA
        while (atual().tipo != TokenType.DEXTRA && pos < tokens.size()) {
            parseComando();
        }
        consumir(); // consome o DeXTRA
    }

    private void parseValor() {
        Token t = atual();
        if (t.tipo == TokenType.VARIABILIS || t.tipo == TokenType.NUMERUS) {
            consumir(); // consome a variável ou número
            System.out.print(t.valor);
        } else {
            throw new RuntimeException("Expressão inválida: " + t.valor);
        }
    }

    private void parseComparacao() {
        Token var = consumir(); // variável esquerda (ex: "i")
        Token op = consumir(); // operador (=, <, #)
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
