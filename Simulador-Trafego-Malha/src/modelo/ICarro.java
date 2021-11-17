/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Robson de Jesus
 */
public interface ICarro {

    void desativar();

    void enterSimulation(ICasa casaAleatoria);

    @Override
    boolean equals(Object obj);

    int getRBG();

    void obterRota();

    void mover();

    void setCasa(ICasa newCasa);

    ICasa getCasa();

    long getId();

    void join() throws InterruptedException;

    void sleep(int nextInt);

    int getVelocidade();

}