package lexer;

/**
 * Enumeração de todos os tipos de token da linguagem LPS1.
 *
 * Os nomes são derivados do latim, seguindo a convenção da linguagem:
 *   - Comandos são letras maiúsculas (L, D, P, S, R).
 *   - Variáveis são letras minúsculas (a–z).
 *   - Literais numéricos são sequências de dígitos.
 *   - Operadores e delimitadores são símbolos especiais.
 */
public enum TokenType {
    // --- Comandos (palavras-chave da linguagem LPS1) ---
    LEGE,       // L — leitura do teclado      (do latim "lege" = leia)
    DIC,        // D — impressão na saída       (do latim "dic"  = diga)
    PONE,       // P — atribuição de valor      (do latim "pone" = coloque)
    SI,         // S — condicional if           (do latim "si"   = se)
    REPETE,     // R — repetição while          (do latim "repete" = repita)

    // --- Operadores aritméticos ---
    ADDE,       // + — adição
    SUBTRAHE,   // - — subtração
    MULTIPLICA, // * — multiplicação
    DIVIDE,     // / — divisão
    MODULUS,    // % — módulo (resto da divisão)

    // --- Delimitadores de bloco ---
    SINISTRA,   // { — abre bloco              (do latim "sinistra" = esquerda)
    DEXTRA,     // } — fecha bloco             (do latim "dextra"   = direita)

    // --- Operadores de comparação ---
    AEQUALIS,   // = — igual a                 (do latim "aequalis" = igual)
    MINOR,      // < — menor que               (do latim "minor"    = menor)
    DIVERSUS,   // # — diferente de            (do latim "diversus" = diferente)

    // --- Literais e identificadores ---
    VARIABILIS, // letra minúscula (a–z) — nome de variável
    NUMERUS,    // sequência de dígitos (0–9)  — literal inteiro

    // --- Controle de fim de entrada ---
    EOF         // fim da entrada
}
