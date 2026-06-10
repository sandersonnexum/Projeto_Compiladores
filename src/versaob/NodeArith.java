package versaob;

// Nó de operação aritmética: dest = val1 op val2  (ex: + a b c → a = b + c)
public class NodeArith implements Node {
    private String operador, dest, val1, val2;
    public NodeArith(String operador, String dest, String val1, String val2) { this.operador=operador; this.dest=dest; this.val1=val1; this.val2=val2; }
    @Override public void generateC() {
        System.out.println("  // " + operador + " " + dest + " " + val1 + " " + val2);
        System.out.println("  " + dest + " = " + val1 + " " + operador + " " + val2 + ";");
    }
}
