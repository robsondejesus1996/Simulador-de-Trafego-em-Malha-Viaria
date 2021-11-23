package modelo;


import strategy.Movimentacao;

/**
 *
 * @author Robson de Jesus
 */
public interface InterfaceCasa {

    void addRota(Movimentacao command);

    Movimentacao getRota();

    int getColunm();

    int getRow();

    int getValor();

    void liberarRecurso();

    void mover(InterfaceCarro carro);

    boolean reservarCasa();

    void setCarro(InterfaceCarro carro);

    InterfaceCarro getCarro();

    void removerCarro();
}
