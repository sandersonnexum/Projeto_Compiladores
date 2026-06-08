package lexer;
import java.util.ArrayList;

/**
 * Analisador léxico (Lexer) da linguagem LPS1.
 *
 * Recebe o código-fonte como uma String e o transforma em uma sequência
 * de tokens ({@link Token}), que serão consumidos pelo parser na etapa
 * seguinte da compilação.
 *
 * Regras de tokenização:
 *   - Espaços e quebras de linha são ignorados.
 *   - Letras maiúsculas (L, D, P, S, R) viram tokens de comando.
 *   - Letras minúsculas (a–z) viram tokens VARIABILIS.
 *   - Sequências de dígitos viram tokens NUMERUS.
 *   - Símbolos (+, -, *, /, %, <, =, #, {, }) viram os operadores correspondentes.
 */
public class Lexer {

    /** Código-fonte completo a ser tokenizado. */
    private String input;

    /** Posição atual de leitura dentro de {@code input}. */
    private int pos;

    public Lexer(String input) {
        this.input = input;
        this.pos = 0;
    }

    /**
     * Lê e retorna o próximo token da entrada.
     *
     * Avança {@code pos} até o próximo caractere não-branco e determina
     * o tipo do token pelo primeiro caractere encontrado:
     *   - dígito       → NUMERUS (consome todos os dígitos consecutivos)
     *   - maiúscula    → token de comando (L, D, P, S, R)
     *   - minúscula    → VARIABILIS
     *   - símbolo      → operador ou delimitador
     *   - fim de string → EOF
     */
    public Token nextToken() {
        // pula espaços, tabulações e quebras de linha
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }

        // fim do código-fonte
        if (pos >= input.length()) {
            return new Token(TokenType.EOF, "");
        }

        char currentChar = input.charAt(pos);

        // --- literal numérico: consome todos os dígitos consecutivos ---
        if (Character.isDigit(currentChar)) {
            StringBuilder number = new StringBuilder();
            while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                number.append(input.charAt(pos));
                pos++;
            }
            return new Token(TokenType.NUMERUS, number.toString());

        // --- comando: letra maiúscula mapeada para palavra-chave LPS1 ---
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

        // --- variável: qualquer letra minúscula (a–z) ---
        } else if (Character.isLowerCase(currentChar)) {
            pos++;
            return new Token(TokenType.VARIABILIS, String.valueOf(currentChar));

        // --- operadores e delimitadores ---
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

    /**
     * Tokeniza toda a entrada e retorna a lista completa de tokens.
     *
     * Chama {@link #nextToken()} repetidamente até encontrar EOF.
     * O token EOF em si não é incluído na lista.
     */
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
