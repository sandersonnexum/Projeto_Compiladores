import java.util.ArrayList;
import lexer.Lexer;
import lexer.Token;


public class App {
    public static void main(String[] args) {
        String programa = "L n\n= s 0\nR i # n {\n+ s s i\n}";
        Lexer lexer = new Lexer(programa);
        ArrayList<Token> tokens = lexer.tokenizar();
        for (Token t : tokens) {
            System.out.println(t);
        }
    }
}