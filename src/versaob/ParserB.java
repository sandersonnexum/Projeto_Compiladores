package versaob;

import lexer.Token;
import lexer.TokenType;
import java.util.ArrayList;

/**
 * Analisador sintático — Versão B (construção de ASA).
 *
 * Consome a lista de tokens produzida pelo {@link lexer.Lexer} e constrói
 * uma ASA (Árvore Sintática Abstrata) composta por nós {@link Node}.
 * A geração de código C é feita separadamente, chamando
 * {@link NodeProgram#generateC()} na raiz retornada por {@link #parse()}.
 *
 * Fluxo:
 *   tokens → parse() → NodeProgram (raiz da ASA) → generateC() → código C
 *
 * Diferença em relação à Versão A: aqui o parser não emite nenhuma saída;
 * ele apenas monta a estrutura de dados. Isso separa análise de geração,
 * tornando o código mais fácil de estender (ex.: adicionar otimizações).
 */
public class ParserB {

    private ArrayList<Token> tokens;

    /** Índice do token sendo analisado no momento. */
    private int pos;

    /** Recebe a lista de tokens e inicia na posição 0. */
    public ParserB(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    /** Retorna o token atual sem avançar o cursor. */
    private Token atual() {
        return tokens.get(pos);
    }

    /** Retorna o token atual e avança o cursor para o próximo. */
    private Token consumir() {
        return tokens.get(pos++);
    }

    /**
     * Inicia a análise sintática e retorna o nó raiz da ASA.
     * Consome todos os tokens e cria um {@link NodeProgram} com a lista
     * de comandos de nível superior.
     */
    public NodeProgram parse() {
        ArrayList<Node> comandos = new ArrayList<>();
        while (pos < tokens.size()) {
            comandos.add(parseComando());
        }
        return new NodeProgram(comandos);
    }

    /**
     * Identifica o tipo do token atual e retorna o nó ASA correspondente.
     * É o núcleo do parser — corresponde à regra "comando" da gramática.
     */
    private Node parseComando() {
        Token t = atual();
        switch (t.tipo) {
            case LEGE:       return parseLeitura();
            case DIC:        return parseImpressao();
            case PONE:       return parseAtribuicao();
            case ADDE:       return parseAritmetica("+");
            case SUBTRAHE:   return parseAritmetica("-");
            case MULTIPLICA: return parseAritmetica("*");
            case DIVIDE:     return parseAritmetica("/");
            case MODULUS:    return parseAritmetica("%");
            case SI:         return parseSe();
            case REPETE:     return parseEnquanto();
            case SINISTRA:   return parseBloco();
            default:
                throw new RuntimeException("Comando inexistente: " + t.valor);
        }
    }

    /** Comando L: cria nó de leitura de teclado para a variável indicada. */
    private Node parseLeitura() {
        consumir(); // consome LEGE
        Token var = consumir(); // variável destino
        return new NodeGet(var.valor);
    }

    /** Comando D: cria nó de impressão para a variável ou literal indicado. */
    private Node parseImpressao() {
        consumir(); // consome DIC
        Token val = consumir(); // variável ou literal a imprimir
        return new NodePrint(val.valor);
    }

    /** Comando P: cria nó de atribuição (variável ← valor). */
    private Node parseAtribuicao() {
        consumir(); // consome PONE
        Token var = consumir(); // variável destino
        Token val = consumir(); // valor a atribuir
        return new NodeAssign(var.valor, val.valor);
    }

    /**
     * Comandos +, -, *, /, %: cria nó aritmético com destino e dois operandos.
     * Formato LPS1: <op> <dest> <val1> <val2>
     */
    private Node parseAritmetica(String op) {
        consumir(); // consome o token do operador
        Token dest = consumir(); // variável destino
        Token val1 = consumir(); // primeiro operando
        Token val2 = consumir(); // segundo operando
        return new NodeArith(op, dest.valor, val1.valor, val2.valor);
    }

    /**
     * Comando S: cria nó condicional if.
     * Formato LPS1: S <var> <op> <val> <comando>
     */
    private Node parseSe() {
        consumir(); // consome SI
        String varComp = consumir().valor; // variável da comparação
        String opC     = parseOperador(); // operador convertido para C
        String val     = consumir().valor; // valor comparado
        Node cmd       = parseComando();  // corpo do if
        return new NodeIf(varComp, opC, val, cmd);
    }

    /**
     * Comando R: cria nó de repetição while.
     * Formato LPS1: R <var> <op> <val> <bloco>
     */
    private Node parseEnquanto() {
        consumir(); // consome REPETE
        String varComp = consumir().valor; // variável da condição
        String opC     = parseOperador(); // operador convertido para C
        String val     = consumir().valor; // valor comparado
        Node corpo     = parseComando();  // corpo do while (geralmente um bloco)
        return new NodeWhile(varComp, opC, val, corpo);
    }

    /**
     * Bloco { }: consome comandos até encontrar '}' e retorna um NodeBlock.
     */
    private Node parseBloco() {
        consumir(); // consome SINISTRA {
        ArrayList<Node> comandos = new ArrayList<>();
        while (atual().tipo != TokenType.DEXTRA && pos < tokens.size()) {
            comandos.add(parseComando());
        }
        consumir(); // consome DEXTRA }
        return new NodeBlock(comandos);
    }

    /**
     * Converte o operador de comparação LPS1 para o equivalente em C.
     *   =  →  ==
     *   <  →  <
     *   #  →  !=
     */
    private String parseOperador() {
        Token op = consumir();
        switch (op.tipo) {
            case AEQUALIS: return "==";
            case MINOR:    return "<";
            case DIVERSUS: return "!=";
            default:
                throw new RuntimeException("Operador inválido: " + op.valor);
        }
    }
}
