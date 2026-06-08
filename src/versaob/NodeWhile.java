package versaob;

/**
 * Nó do comando R (Repete) — repetição while.
 *
 * Formato LPS1: R <var> <op> <val> <bloco>
 * Exemplo: "R i # n { ... }" → "while (i != n) { ... }"
 *
 * Operadores suportados: = (igual), < (menor), # (diferente).
 */
public class NodeWhile implements Node {

    /** Variável do lado esquerdo da condição de parada (ex: "i"). */
    private String varComp;
    /** Operador de comparação já convertido para C (ex: "!=", "<", "=="). */
    private String operador;
    /** Valor do lado direito da condição de parada (ex: "n"). */
    private String valor;
    /** Bloco de comandos executado enquanto a condição for verdadeira. */
    private Node corpo;

    public NodeWhile(String varComp, String operador, String valor, Node corpo) {
        this.varComp = varComp;
        this.operador = operador;
        this.valor = valor;
        this.corpo = corpo;
    }

    @Override
    public void generateC() {
        System.out.println("  while (" + varComp + " " + operador + " " + valor + ") {");
        corpo.generateC();
        System.out.println("  }");
    }
}
