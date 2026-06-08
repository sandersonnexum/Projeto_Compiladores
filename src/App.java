/**
 * Ponto de entrada do compilador LPS1 (Latin Programming System 1).
 *
 * LPS1 é uma linguagem de programação simples com palavras-chave de origem
 * latina. Este compilador realiza as duas etapas clássicas da compilação:
 *
 *   1. Análise Léxica  — o Lexer lê o código-fonte e produz uma lista de tokens.
 *   2. Análise Sintática — o Parser consome os tokens e gera código C.
 *
 * Há duas versões do parser, demonstradas aqui:
 *   Versão A — geração de código C inline, durante a própria análise.
 *   Versão B — construção de uma ASA (Árvore Sintática Abstrata) e depois
 *              travessia da árvore para emitir o código C.
 *
 * Tabela de comandos da linguagem LPS1:
 *   L <var>              — leitura do teclado (Lege)
 *   D <var|num>          — impressão na saída (Dic)
 *   P <var> <val>        — atribuição (Pone)
 *   + <dest> <a> <b>     — adição
 *   - <dest> <a> <b>     — subtração
 *   * <dest> <a> <b>     — multiplicação
 *   / <dest> <a> <b>     — divisão
 *   % <dest> <a> <b>     — módulo
 *   S <var> <op> <val> <cmd>  — condicional (Si)
 *   R <var> <op> <val> <cmd>  — repetição (Repete)
 *   { ... }              — bloco de comandos
 */
import lexer.Lexer;
import versaoA.ParserA;
import versaob.NodeProgram;
import versaob.ParserB;

public class App {

    public static void main(String[] args) {

        // Exemplo 1: lê dois números e imprime a multiplicação em loop
        // LPS1: L n / L p / P i 0 / R i # n { * a p i / D a / + i i 1 }
        String exemplo1 =
                "L n\n"
                + "L p\n"
                + "P i 0\n"
                + "R i # n {\n"
                + "  * a p i\n"
                + "  D a\n"
                + "  + i i 1\n"
                + "}\n";

        // Exemplo 2: lê n e decide se é par ou ímpar usando módulo
        // LPS1: L n / P i 2 / % a n i / R i < n { S a = 0 P i n / ... }
        String exemplo2 =
                "L n\n"
                + "P i 2\n"
                + "% a n i\n"
                + "R i < n {\n"
                + "  S a = 0 P i n\n"
                + "  + i i 1\n"
                + "  % a n i\n"
                + "}\n"
                + "S a = 0 D 0\n"
                + "S a # 0 D 1\n";

        // =========================================================
        // VERSÃO (a) — Parser com geração de código C embutida
        // O parser analisa os tokens e emite o código C diretamente,
        // sem construir nenhuma estrutura intermediária.
        // =========================================================
        System.out.println("// =============================");
        System.out.println("// VERSAO (a) — Exemplo 1");
        System.out.println("// =============================");
        new ParserA(new Lexer(exemplo1).tokenizar()).parse();

        System.out.println();

        System.out.println("// =============================");
        System.out.println("// VERSAO (a) — Exemplo 2");
        System.out.println("// =============================");
        new ParserA(new Lexer(exemplo2).tokenizar()).parse();

        System.out.println();

        // =========================================================
        // VERSÃO (b) — Parser que constrói uma ASA (Árvore Sintática
        // Abstrata) e depois percorre a árvore para gerar o código C.
        // Cada nó da árvore implementa a interface Node com generateC().
        // =========================================================
        System.out.println("// =============================");
        System.out.println("// VERSAO (b) — Exemplo 1");
        System.out.println("// =============================");
        NodeProgram raiz1 = new ParserB(new Lexer(exemplo1).tokenizar()).parse();
        raiz1.generateC();

        System.out.println();

        System.out.println("// =============================");
        System.out.println("// VERSAO (b) — Exemplo 2");
        System.out.println("// =============================");
        NodeProgram raiz2 = new ParserB(new Lexer(exemplo2).tokenizar()).parse();
        raiz2.generateC();
    }
}
