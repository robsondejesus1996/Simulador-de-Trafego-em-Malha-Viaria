package modelo;

import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public interface InterfaceCarro {

    void excluir();

    void inserirSimulacao(InterfaceCasa casaAleatoria);

    @Override
    boolean equals(Object obj);

    int getCores(); 

    void buscarCaminho();

    void movimentar();

    void definirCasa(InterfaceCasa newCasa);

    InterfaceCasa obterCasa();

    long getId();

    void join() throws InterruptedException;

    void sleep(int nextInt);

    int obterVelocidade();

}
