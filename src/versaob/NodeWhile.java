package versaob;

// Nó de laço: while (varComp op valor) corpo  (comando R / W)
public class NodeWhile implements Node {
    private String varComp, operador, valor; private Node corpo;
    public NodeWhile(String varComp, String operador, String valor, Node corpo) { this.varComp=varComp; this.operador=operador; this.valor=valor; this.corpo=corpo; }
    @Override public void generateC() {
        System.out.println("  while (" + varComp + " " + operador + " " + valor + ") {");
        corpo.generateC();
        System.out.println("  }");
    }
}
