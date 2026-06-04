package lexer;

public class Token {
    public TokenType tipo;
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
