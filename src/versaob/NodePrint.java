package versaob;

// Nó de impressão: printf de um valor inteiro (comando D do latino ou P do professor).
public class NodePrint implements Node {
    private String valor, prefixo;
    public NodePrint(String valor)               { this(valor, "D"); }
    public NodePrint(String valor, String prefixo){ this.valor=valor; this.prefixo=prefixo; }
    @Override public void generateC() {
        System.out.println("  // " + prefixo + " " + valor);
        System.out.println("  printf(\"%d\\n\", " + valor + ");");
    }
}
