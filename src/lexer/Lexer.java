package lexer;
import java.util.ArrayList;

// Análise léxica: percorre o código-fonte caractere a caractere e produz tokens.
// Aceita os dois dialetos da linguagem (latino e do professor) mapeando
// letras sinônimas para o mesmo TokenType.
public class Lexer {
    private String input;
    private int pos;
    public Lexer(String input) { this.input = input; this.pos = 0; }

    // Retorna o próximo token, ignorando espaços e quebras de linha.
    public Token nextToken() {
        // Pula espaços em branco
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) pos++;
        if (pos >= input.length()) return new Token(TokenType.EOF, "");

        char c = input.charAt(pos);

        // Número inteiro (um ou mais dígitos)
        if (Character.isDigit(c)) {
            StringBuilder sb = new StringBuilder();
            while (pos < input.length() && Character.isDigit(input.charAt(pos))) sb.append(input.charAt(pos++));
            return new Token(TokenType.NUMERUS, sb.toString());

        // Letra maiúscula → comando da linguagem
        } else if (Character.isUpperCase(c)) {
            pos++;
            switch (c) {
                case 'L': return new Token(TokenType.LEGE,    "L");
                case 'G': return new Token(TokenType.LEGE,    "G"); // alias do prof
                case 'D': return new Token(TokenType.DIC,     "D");
                case 'P': return new Token(TokenType.PONE,    "P");
                case 'S': return new Token(TokenType.SI,      "S");
                case 'I': return new Token(TokenType.SI,      "I"); // alias do prof
                case 'R': return new Token(TokenType.REPETE,  "R");
                case 'W': return new Token(TokenType.REPETE,  "W"); // alias do prof
                default: throw new RuntimeException("Comando inexistente: " + c);
            }

        // Letra minúscula → nome de variável (a-z)
        } else if (Character.isLowerCase(c)) {
            pos++;
            return new Token(TokenType.VARIABILIS, String.valueOf(c));

        // Símbolo → operador aritmético, comparação ou delimitador
        } else {
            pos++;
            switch (c) {
                case '+': return new Token(TokenType.ADDE,      "+");
                case '-': return new Token(TokenType.SUBTRAHE,  "-");
                case '*': return new Token(TokenType.MULTIPLICA,"*");
                case '/': return new Token(TokenType.DIVIDE,    "/");
                case '%': return new Token(TokenType.MODULUS,   "%");
                case '<': return new Token(TokenType.MINOR,     "<");
                case '=': return new Token(TokenType.AEQUALIS,  "=");
                case '#': return new Token(TokenType.DIVERSUS,  "#");  // != em LPS1
                case '{': return new Token(TokenType.SINISTRA,  "{");
                case '}': return new Token(TokenType.DEXTRA,    "}");
                default: throw new RuntimeException("Caractere inválido: " + c);
            }
        }
    }

    // Consome todos os tokens até EOF e devolve a lista (sem o EOF).
    public ArrayList<Token> tokenizar() {
        ArrayList<Token> tokens = new ArrayList<>();
        Token t;
        do { t = nextToken(); if (t.tipo != TokenType.EOF) tokens.add(t); } while (t.tipo != TokenType.EOF);
        return tokens;
    }
}
