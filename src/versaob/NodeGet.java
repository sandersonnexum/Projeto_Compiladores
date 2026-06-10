package versaob;

// Nó de leitura de teclado: lê um inteiro e armazena na variável (comando L / G).
public class NodeGet implements Node {
    private String variavel, prefixo;
    public NodeGet(String variavel)               { this(variavel, "L"); }
    public NodeGet(String variavel, String prefixo){ this.variavel = variavel; this.prefixo = prefixo; }
    @Override public void generateC() {
        System.out.println("  // " + prefixo + " " + variavel);
        System.out.println("  { gets(str); sscanf(str, \"%d\", &" + variavel + "); }");
    }
}
