package versaob;

import java.util.ArrayList;

/**
 * Nó raiz da ASA — representa o programa completo.
 *
 * É o ponto de partida da geração de código C. Ao chamar {@code generateC()},
 * emite o cabeçalho C (include, main, declarações) e depois delega para cada
 * nó filho da lista de comandos, fechando o main() ao final.
 */
public class NodeProgram implements Node {

    /** Lista de comandos de nível superior do programa. */
    private ArrayList<Node> comandos;

    public NodeProgram(ArrayList<Node> comandos) {
        this.comandos = comandos;
    }

    /**
     * Gera o programa C completo: cabeçalho + corpo + fechamento do main.
     * Declara todas as variáveis (a–z) e o buffer de leitura.
     */
    @Override
    public void generateC() {
        System.out.println("#include <stdio.h>");
        System.out.println("");
        System.out.println("int main() {");
        System.out.println("  int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;");
        System.out.println("  char str[512];");
        for (Node comando : comandos) {
            comando.generateC();
        }
        System.out.println("}");
    }
}
