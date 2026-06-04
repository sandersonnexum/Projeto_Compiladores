package lexer;
import java.util.ArrayList;

public class Lexer {

    private String input;
    private int pos;

    public Lexer(String input) {
        this.input = input;
        this.pos = 0;
    }

    public Token nextToken() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }
        if (pos >= input.length()) {
            return new Token(TokenType.EOF, "");
        }
        char currentChar = input.charAt(pos);
        if (Character.isDigit(currentChar)) {
            StringBuilder number = new StringBuilder();
            while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                number.append(input.charAt(pos));
                pos++;
            }
            return new Token(TokenType.NUMERUS, number.toString());
        } else if (Character.isUpperCase(currentChar)) {
            pos++;
            switch (currentChar) {
                case 'L':
                    return new Token(TokenType.LEGE, "L");
                case 'D':
                    return new Token(TokenType.DIC, "D");
                case 'P':
                    return new Token(TokenType.PONE, "P");
                case 'S':
                    return new Token(TokenType.SI, "S");
                case 'R':
                    return new Token(TokenType.REPETE, "R");
                default:
                    throw new RuntimeException("Unexpected character: " + currentChar);
            }
        } else if (Character.isLowerCase(currentChar)) {
            pos++;
            return new Token(TokenType.VARIABILIS, String.valueOf(currentChar));
        } else {
            pos++;
            switch (currentChar) {
                case '+':
                    return new Token(TokenType.ADDE, "+");
                case '-':
                    return new Token(TokenType.SUBTRAHE, "-");
                case '*':
                    return new Token(TokenType.MULTIPLICA, "*");
                case '/':
                    return new Token(TokenType.DIVIDE, "/");
                case '%':
                    return new Token(TokenType.MODULUS, "%");
                case '<':
                    return new Token(TokenType.MINOR, "<");
                case '=':
                    return new Token(TokenType.AEQUALIS, "=");
                case '#':
                    return new Token(TokenType.DIVERSUS, "#");
                case '{':
                    return new Token(TokenType.SINISTRA, "{");
                case '}':
                    return new Token(TokenType.DEXTRA, "}");
                default:
                    throw new RuntimeException("Caractere inválido: " + currentChar);
            }
        }
    }

    public ArrayList<Token> tokenizar() {
        ArrayList<Token> tokens = new ArrayList<>();
        Token token;
        do {
            token = nextToken();
            if (token.tipo != TokenType.EOF) {
                tokens.add(token);
            }
        } while (token.tipo != TokenType.EOF);
        return tokens;
    }
}
