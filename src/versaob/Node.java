package versaob;

// Interface que todos os nós da AST implementam.
// generateC() é chamado na Etapa 2 para emitir o trecho de código C correspondente.
public interface Node { void generateC(); }
