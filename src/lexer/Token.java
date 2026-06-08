package lexer;

/**
 * Representa um token produzido pelo analisador léxico.
 *
 * Um token é a unidade mínima de significado da linguagem LPS1.
 * Por exemplo, o caractere 'L' vira Token(LEGE, "L") e o número
 * "42" vira Token(NUMERUS, "42").
 */
public class Token {

    /** Categoria gramatical do token (ex: LEGE, NUMERUS, VARIABILIS). */
    public TokenType tipo;

    /** Texto original do token extraído do código-fonte. */
    public String valor;

    public Token(TokenType type, String value) {
        this.tipo =  type;
        this.valor = value;
    }

    @Override
    public String toString() {
        return "Token(" + tipo + ", " + valor + ")";
    }
}
