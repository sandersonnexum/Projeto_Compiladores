package versaob;

/**
 * Nó do comando P (Pone) — atribuição de valor.
 *
 * Formato LPS1: P <var> <val>
 * Exemplo: "P i 0" → "i = 0;"
 */
public class NodeAssign implements Node {

    /** Variável que receberá o valor (ex: "i"). */
    private String variavel;
    /** Valor atribuído — variável ou literal (ex: "0"). */
    private String valor;

    public NodeAssign(String variavel, String valor) {
        this.variavel = variavel;
        this.valor = valor;
    }

    @Override
    public void generateC() {
        System.out.println("  // P " + variavel);
        System.out.println("  " + variavel + " = " + valor + ";");
    }
}
