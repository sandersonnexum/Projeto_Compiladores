
import java.util.ArrayList;
import lexer.Lexer;
import lexer.Token;
import versaoA.ParserA;

public class App {

    public static void main(String[] args) {
        String programa
                = "L n\n"
                + "P i 2\n"
                + "% a n i\n"
                + "R i < n {\n"
                + "  S a = 0 P i n\n"
                + "  + i i 1\n"
                + "  % a n i\n"
                + "}\n"
                + "S a = 0 D 0\n"
                + "S a # 0 D 1\n";

        Lexer lexer = new Lexer(programa);
        ArrayList<Token> tokens = lexer.tokenizar();

        ParserA parser = new ParserA(tokens);
        parser.parse();
    }
}
