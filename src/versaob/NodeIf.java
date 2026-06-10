package versaob;

// Nó condicional: if (varComp op valor) comando  (comando S / I)
public class NodeIf implements Node {
    private String varComp, operador, valor; private Node comando;
    public NodeIf(String varComp, String operador, String valor, Node comando) { this.varComp=varComp; this.operador=operador; this.valor=valor; this.comando=comando; }
    @Override public void generateC() {
        System.out.println("  if (" + varComp + " " + operador + " " + valor + ") {");
        comando.generateC();
        System.out.println("  }");
    }
}
