package lexer;

// Categorias de tokens da linguagem LPS1.
// Nomes em latim para evitar colisão com palavras-chave do Java.
public enum TokenType {
    // Comandos da linguagem
    LEGE,       // L / G  — leitura de teclado
    DIC,        // D      — impressão (dialeto latino)
    PONE,       // P      — atribuição ou impressão (dialeto do prof.)
    SI,         // S / I  — condicional (if)
    REPETE,     // R / W  — laço (while)

    // Operadores aritméticos
    ADDE, SUBTRAHE, MULTIPLICA, DIVIDE, MODULUS,

    // Delimitadores de bloco
    SINISTRA, DEXTRA,   // { e }

    // Operadores de comparação
    AEQUALIS, MINOR, DIVERSUS,  // =  <  #(!=)

    // Literais
    VARIABILIS, NUMERUS,

    EOF
}
