package versaob;
import lexer.Token;
import lexer.TokenType;
import java.util.ArrayList;

// Versão B — compilação em duas etapas.
// Etapa 1 (parse): constrói a Árvore Sintática Abstrata (AST) a partir dos tokens.
// Etapa 2 (generateC): percorre a AST e gera o código C via polimorfismo (cada Node
//   sabe como se traduzir, sem lógica de geração espalhada no parser).
public class ParserB {
    private final ArrayList<Token> tokens;
    private int pos;
    public ParserB(ArrayList<Token> tokens) { this.tokens = tokens; this.pos = 0; }
    private Token atual() { return tokens.get(pos); }
    private Token consumir() { return tokens.get(pos++); }

    // Etapa 1: constrói e devolve o nó raiz da AST (NodeProgram).
    public NodeProgram parse() {
        ArrayList<Node> cmds = new ArrayList<>();
        while (pos < tokens.size()) cmds.add(parseComando());
        return new NodeProgram(cmds);
    }

    // Despacha o token atual para o construtor de nó correspondente.
    private Node parseComando() {
        Token t = atual();
        switch (t.tipo) {
            case LEGE:       return parseLeitura();
            case DIC:        return parseImpressao();
            case PONE:       return parseAtribuicaoOuImpressao(); // lookahead P
            case AEQUALIS:   return parseProfAssign();            // = como assign
            case ADDE:       return parseAritmetica("+");
            case SUBTRAHE:   return parseAritmetica("-");
            case MULTIPLICA: return parseAritmetica("*");
            case DIVIDE:     return parseAritmetica("/");
            case MODULUS:    return parseAritmetica("%");
            case SI:         return parseSe();
            case REPETE:     return parseEnquanto();
            case SINISTRA:   return parseBloco();
            default: throw new RuntimeException("Comando inexistente: " + t.valor);
        }
    }

    // L ou G — passa o prefixo original pro nó
    private Node parseLeitura() {
        Token cmd = consumir();
        Token var = consumir();
        return new NodeGet(var.valor, cmd.valor); // "L" ou "G"
    }
    private Node parseImpressao() {
        consumir(); Token val = consumir(); return new NodePrint(val.valor);
    }

    // P com lookahead: 2 valores → assign; 1 valor → print (notação prof)
    private Node parseAtribuicaoOuImpressao() {
        consumir(); // consume P
        boolean ehAtribuicao = (pos + 1 < tokens.size())
            && (tokens.get(pos + 1).tipo == TokenType.VARIABILIS
             || tokens.get(pos + 1).tipo == TokenType.NUMERUS);
        if (ehAtribuicao) {
            Token var = consumir(); Token val = consumir();
            return new NodeAssign(var.valor, val.valor);      // prefixo "P"
        } else {
            Token val = consumir();
            return new NodePrint(val.valor, "P");             // P como print (prof)
        }
    }

    // = como comando de assign (notação do professor)
    private Node parseProfAssign() {
        consumir(); // consume =
        Token var = consumir(); Token val = consumir();
        return new NodeAssign(var.valor, val.valor, "="); // prefixo "="
    }

    private Node parseAritmetica(String op) {
        consumir(); Token dest=consumir(); Token v1=consumir(); Token v2=consumir();
        return new NodeArith(op, dest.valor, v1.valor, v2.valor);
    }
    private Node parseSe() {
        consumir();
        String var=consumir().valor; String op=parseOperador(); String val=consumir().valor;
        Node cmd=parseComando();
        return new NodeIf(var, op, val, cmd);
    }
    private Node parseEnquanto() {
        consumir();
        String var=consumir().valor; String op=parseOperador(); String val=consumir().valor;
        Node corpo=parseComando();
        return new NodeWhile(var, op, val, corpo);
    }
    private Node parseBloco() {
        consumir();
        ArrayList<Node> cmds = new ArrayList<>();
        while (pos < tokens.size() && atual().tipo != TokenType.DEXTRA) cmds.add(parseComando());
        if (pos >= tokens.size()) throw new RuntimeException("Bloco não fechado: faltou '}'");
        consumir();
        return new NodeBlock(cmds);
    }
    private String parseOperador() {
        Token op = consumir();
        switch (op.tipo) {
            case AEQUALIS: return "==";
            case MINOR:    return "<";
            case DIVERSUS: return "!=";
            default: throw new RuntimeException("Operador inválido: " + op.valor);
        }
    }
}
