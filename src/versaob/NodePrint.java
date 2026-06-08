package versaob;

/**
 * Nó do comando D (Dic) — impressão de inteiro na saída padrão.
 *
 * Formato LPS1: D <var|num>
 * Exemplo: "D a" → "printf(\"%d\\n\", a);"
 */
public class NodePrint implements Node {

    /** Valor a ser impresso — nome de variável ou literal numérico. */
    private String valor;

    public NodePrint(String valor) {
        this.valor = valor;
    }

    @Override
    public void generateC() {
        System.out.println("  // D " + valor);
        System.out.println("  printf(\"%d\\n\", " + valor + ");");
    }
}
