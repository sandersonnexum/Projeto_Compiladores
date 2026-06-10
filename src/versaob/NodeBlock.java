package versaob;
import java.util.ArrayList;

// Nó de bloco: sequência de comandos delimitada por { }.
// Apenas delega generateC() para cada filho — não emite chaves extras,
// pois o If/While já as emite.
public class NodeBlock implements Node {
    private ArrayList<Node> comandos;
    public NodeBlock(ArrayList<Node> comandos) { this.comandos=comandos; }
    @Override public void generateC() { for (Node c : comandos) c.generateC(); }
}
