import lexer.Lexer;
import versaoA.ParserA;
import versaob.NodeProgram;
import versaob.ParserB;

// Ponto de entrada do compilador LPS1 → C.
// Demonstra as duas abordagens de compilação com os dois dialetos da linguagem:
//   Versão A — tradução direta (1 passagem, sem AST)
//   Versão B — AST + geração separada (2 passagens)
public class App {
    // Executa a Versão A: Lexer → ParserA (imprime C diretamente)
    static void rodaA(String titulo, String src) {
        System.out.println("// =============================");
        System.out.println("// VERSAO (a) — " + titulo);
        System.out.println("// =============================");
        new ParserA(new Lexer(src).tokenizar()).parse();
        System.out.println();
    }
    // Executa a Versão B: Lexer → ParserB (AST) → generateC()
    static void rodaB(String titulo, String src) {
        System.out.println("// =============================");
        System.out.println("// VERSAO (b) — " + titulo);
        System.out.println("// =============================");
        new ParserB(new Lexer(src).tokenizar()).parse().generateC();
        System.out.println();
    }

    public static void main(String[] args) {

        // ── Exemplos em LPS1 Latino (L/D/P/S/R) ──────────────────────
        String latino1 =
            "L n\n" + "L p\n" + "P i 0\n"
            + "R i # n {\n" + "  * a p i\n" + "  D a\n" + "  + i i 1\n" + "}\n";

        String latino2 =
            "L n\n" + "P i 2\n" + "% a n i\n"
            + "R i < n {\n" + "  S a = 0 P i n\n" + "  + i i 1\n" + "  % a n i\n" + "}\n"
            + "S a = 0 D 0\n" + "S a # 0 D 1\n";

        // ── Exemplos originais do professor (G/P(print)/=/W/I) ────────
        String prof1 =
            "G n\n" + "G p\n" + "= i 0\n"
            + "W i # n {\n" + "  * a p i\n" + "  P a\n" + "  + i i 1\n" + "}\n";

        String prof2 =
            "G n\n" + "= i 2\n" + "% a n i\n"
            + "W i < n {\n" + "  I a = 0 = i n\n" + "  + i i 1\n" + "  % a n i\n" + "}\n"
            + "I a = 0 P 0\n" + "I a # 0 P 1\n";

        rodaA("Exemplo 1 (latino)",   latino1);
        rodaA("Exemplo 2 (latino)",   latino2);
        rodaA("Exemplo 1 (professor)", prof1);
        rodaA("Exemplo 2 (professor)", prof2);

        rodaB("Exemplo 1 (latino)",   latino1);
        rodaB("Exemplo 2 (latino)",   latino2);
        rodaB("Exemplo 1 (professor)", prof1);
        rodaB("Exemplo 2 (professor)", prof2);
    }
}
