package versaob;

/**
 * Nó do comando L (Lege) — leitura de inteiro do teclado.
 *
 * Formato LPS1: L <var>
 * Exemplo: "L n" → "{ gets(str); sscanf(str, \"%d\", &n); }"
 *
 * Usa gets + sscanf para ler a variável, seguindo o padrão do compilador.
 */
public class NodeGet implements Node {

    /** Variável que receberá o valor lido (ex: "n"). */
    private String variavel;

    public NodeGet(String variavel) {
        this.variavel = variavel;
    }

    @Override
    public void generateC() {
        System.out.println("  // L " + variavel);
        System.out.println("  { gets(str); sscanf(str, \"%d\", &" + variavel + "); }");
    }
}
