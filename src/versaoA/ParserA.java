package versaoA;
import java.util.ArrayList;
import lexer.Token;
import lexer.TokenType;

// Versão A — tradução direta (sem AST).
// O parser percorre os tokens e imprime o código C equivalente imediatamente,
// em uma única passagem. Mais simples, mas sem separação entre análise e geração.
public class ParserA {
    private ArrayList<Token> tokens;
    private int pos;
    public ParserA(ArrayList<Token> tokens) { this.tokens = tokens; this.pos = 0; }
    private Token atual() { return tokens.get(pos); }
    private Token consumir() { return tokens.get(pos++); }

    // Ponto de entrada: emite cabeçalho C, processa todos os comandos e fecha o main().
    public void parse() {
        gerarCabecalho();
        while (pos < tokens.size()) parseComando();
        System.out.println("}");
    }

    // Emite o cabeçalho C: include, main() e declaração de todas as variáveis.
    private void gerarCabecalho() {
        System.out.println("#include <stdio.h>");
        System.out.println("");
        System.out.println("int main() {");
        System.out.println("  int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;");
        System.out.println("  char str[512];");
    }
    // Despacha o token atual para o método de tradução correspondente.
    private void parseComando() {
        Token t = atual();
        switch (t.tipo) {
            case LEGE:       parseLeitura();               break;
            case DIC:        parseImpressao();             break;
            case PONE:       parseAtribuicaoOuImpressao(); break; // P = assign (2 ops) ou print (1 op)
            case AEQUALIS:   parseProfAssign();            break; // = como comando de assign (notação prof)
            case ADDE:       parseAritmetica("+");         break;
            case SUBTRAHE:   parseAritmetica("-");         break;
            case MULTIPLICA: parseAritmetica("*");         break;
            case DIVIDE:     parseAritmetica("/");         break;
            case MODULUS:    parseAritmetica("%");         break;
            case SI:         parseSe();                    break;
            case REPETE:     parseEnquanto();              break;
            case SINISTRA:   parseBloco();                 break;
            default: throw new RuntimeException("Comando inexistente: " + t.valor);
        }
    }

    // L ou G: leitura de teclado
    private void parseLeitura() {
        Token cmd = consumir();          // guarda "L" ou "G" pro comentário
        Token var = consumir();
        System.out.println("  // " + cmd.valor + " " + var.valor);
        System.out.println("  { gets(str); sscanf(str, \"%d\", &" + var.valor + "); }");
    }

    // D: impressão (dialeto latino)
    private void parseImpressao() {
        consumir();
        Token val = consumir();
        System.out.println("  // D " + val.valor);
        System.out.println("  printf(\"%d\\n\", " + val.valor + ");");
    }

    // P com lookahead: 2 valores → assign (P i 0); 1 valor → print (P a, notação prof)
    private void parseAtribuicaoOuImpressao() {
        consumir(); // consume P
        boolean ehAtribuicao = (pos + 1 < tokens.size())
            && (tokens.get(pos + 1).tipo == TokenType.VARIABILIS
             || tokens.get(pos + 1).tipo == TokenType.NUMERUS);
        if (ehAtribuicao) {
            Token var = consumir();
            Token val = consumir();
            System.out.println("  // P " + var.valor + " " + val.valor);
            System.out.println("  " + var.valor + " = " + val.valor + ";");
        } else {
            Token val = consumir();
            System.out.println("  // P " + val.valor);
            System.out.println("  printf(\"%d\\n\", " + val.valor + ");");
        }
    }

    // = como comando de atribuição (notação do professor: = i 0)
    private void parseProfAssign() {
        consumir(); // consume =
        Token var = consumir();
        Token val = consumir();
        System.out.println("  // = " + var.valor + " " + val.valor);
        System.out.println("  " + var.valor + " = " + val.valor + ";");
    }

    private void parseAritmetica(String op) {
        consumir(); Token dest = consumir(); Token v1 = consumir(); Token v2 = consumir();
        System.out.println("  // " + op + " " + dest.valor + " " + v1.valor + " " + v2.valor);
        System.out.println("  " + dest.valor + " = " + v1.valor + " " + op + " " + v2.valor + ";");
    }
    private void parseSe() {
        consumir();
        System.out.print("  if ("); parseComparacao(); System.out.println(") {");
        parseComando();
        System.out.println("  }");
    }
    private void parseEnquanto() {
        consumir();
        System.out.print("  while ("); parseComparacao(); System.out.println(") {");
        parseComando();
        System.out.println("  }");
    }
    private void parseBloco() {
        consumir();
        while (pos < tokens.size() && atual().tipo != TokenType.DEXTRA) parseComando();
        if (pos >= tokens.size()) throw new RuntimeException("Bloco não fechado: faltou '}'");
        consumir();
    }
    private void parseValor() {
        Token t = atual();
        if (t.tipo == TokenType.VARIABILIS || t.tipo == TokenType.NUMERUS) { consumir(); System.out.print(t.valor); }
        else throw new RuntimeException("Número esperado, encontrado: " + t.valor);
    }
    private void parseComparacao() {
        Token var = consumir(); Token op = consumir(); Token val = consumir();
        String opC;
        switch (op.tipo) {
            case AEQUALIS: opC = "=="; break;
            case MINOR:    opC = "<";  break;
            case DIVERSUS: opC = "!="; break;
            default: throw new RuntimeException("Operador inválido: " + op.valor);
        }
        System.out.print(var.valor + " " + opC + " " + val.valor);
    }
}
