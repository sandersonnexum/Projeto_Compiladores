package versaob;

/**
 * Interface que todo nó da ASA (Árvore Sintática Abstrata) deve implementar.
 *
 * Cada nó representa um construto da linguagem LPS1 (comando, expressão,
 * bloco, etc.). A travessia da árvore é feita chamando {@code generateC()}
 * na raiz ({@link NodeProgram}), que propaga recursivamente para os filhos.
 *
 * Padrão de projeto utilizado: Visitor simplificado / Composite.
 */
public interface Node {
    /** Gera e imprime o trecho de código C equivalente a este nó. */
    void generateC();
}
