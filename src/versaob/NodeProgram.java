package versaob;
import java.util.ArrayList;

// Nó raiz da AST. Representa o programa inteiro e emite o esqueleto do main() em C.
public class NodeProgram implements Node {
    private ArrayList<Node> comandos;
    public NodeProgram(ArrayList<Node> comandos) { this.comandos=comandos; }
    @Override public void generateC() {
        System.out.println("#include <stdio.h>");
        System.out.println("");
        System.out.println("int main() {");
        System.out.println("  int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;");
        System.out.println("  char str[512];");
        for (Node c : comandos) c.generateC();
        System.out.println("}");
    }
}
