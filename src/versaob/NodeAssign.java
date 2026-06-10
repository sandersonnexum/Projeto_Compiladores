package versaob;

// Nó de atribuição: variavel = valor  (comando P do latino ou = do professor)
public class NodeAssign implements Node {
    private String variavel, valor, prefixo;
    public NodeAssign(String variavel, String valor)                { this(variavel, valor, "P"); }
    public NodeAssign(String variavel, String valor, String prefixo){ this.variavel=variavel; this.valor=valor; this.prefixo=prefixo; }
    @Override public void generateC() {
        System.out.println("  // " + prefixo + " " + variavel + " " + valor);
        System.out.println("  " + variavel + " = " + valor + ";");
    }
}
