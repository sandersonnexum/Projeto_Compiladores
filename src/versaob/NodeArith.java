package versaob;

/**
 * Nó de operação aritmética: +, -, *, /, %.
 *
 * Formato LPS1: <op> <dest> <val1> <val2>
 * Exemplo: "* a p i" → "a = p * i;"
 */
public class NodeArith implements Node {

    /** Operador em C (ex: "+", "*"). */
    private String operador;
    /** Variável que recebe o resultado (ex: "a"). */
    private String dest;
    /** Primeiro operando — variável ou literal (ex: "p"). */
    private String val1;
    /** Segundo operando — variável ou literal (ex: "i"). */
    private String val2;

    public NodeArith(String operador, String dest, String val1, String val2) {
        this.operador = operador;
        this.dest = dest;
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public void generateC() {
        System.out.println("  // " + operador + " " + dest + " " + val1 + " " + val2);
        System.out.println("  " + dest + " = " + val1 + " " + operador + " " + val2 + ";");
    }
}
