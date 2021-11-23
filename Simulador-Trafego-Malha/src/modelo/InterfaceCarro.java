package modelo;

import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public interface InterfaceCarro {

    void desativar();

    void enterSimulation(InterfaceCasa casaAleatoria);

    @Override
    boolean equals(Object obj);

    int getRBG();

    void obterRota();

    void mover();

    void setCasa(InterfaceCasa newCasa);

    InterfaceCasa getCasa();

    long getId();

    void join() throws InterruptedException;

    void sleep(int nextInt);

    int getVelocidade();

}
