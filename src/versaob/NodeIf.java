package versaob;

/**
 * Nó do comando S (Si) — condicional if.
 *
 * Formato LPS1: S <var> <op> <val> <comando>
 * Exemplo: "S a = 0 D 0" → "if (a == 0) { printf(\"%d\\n\", 0); }"
 *
 * Operadores suportados: = (igual), < (menor), # (diferente).
 */
public class NodeIf implements Node {

    /** Variável do lado esquerdo da comparação (ex: "a"). */
    private String varComp;
    /** Operador de comparação já convertido para C (ex: "==", "<", "!="). */
    private String operador;
    /** Valor do lado direito da comparação (ex: "0"). */
    private String valor;
    /** Comando (ou bloco) executado quando a condição for verdadeira. */
    private Node comando;

    public NodeIf(String varComp, String operador, String valor, Node comando) {
        this.varComp = varComp;
        this.operador = operador;
        this.valor = valor;
        this.comando = comando;
    }

    @Override
    public void generateC() {
        System.out.println("  if (" + varComp + " " + operador + " " + valor + ") {");
        comando.generateC();
        System.out.println("  }");
    }
}
