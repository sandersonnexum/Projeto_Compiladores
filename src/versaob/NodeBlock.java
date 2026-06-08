package versaob;

import java.util.ArrayList;

/**
 * Nó de bloco composto { }.
 *
 * Agrupa zero ou mais comandos entre chaves. É usado como corpo de
 * estruturas R (while) e S (if) quando há mais de um comando a executar.
 *
 * Exemplo LPS1: "{ * a p i / D a / + i i 1 }"
 */
public class NodeBlock implements Node {

    /** Comandos contidos dentro do bloco, em ordem de execução. */
    private ArrayList<Node> comandos;

    public NodeBlock(ArrayList<Node> comandos) {
        this.comandos = comandos;
    }

    /** Gera o código C de cada comando do bloco, sem adicionar chaves extras
     *  (as chaves já são emitidas pelo nó pai, ex: NodeWhile ou NodeIf). */
    @Override
    public void generateC() {
        for (Node comando : comandos) {
            comando.generateC();
        }
    }
}
