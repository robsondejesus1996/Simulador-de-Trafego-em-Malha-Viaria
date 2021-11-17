/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robson de Jesus
 */
public class Semaforo extends Casa {

    private Semaphore mutex;

    public Semaforo(int valor, int colunm, int row) {
        super(valor, colunm, row);
        mutex = new Semaphore(1, true);
    }

    @Override
    public void mover(ICarro carro) {
        try {
            mutex.acquire();
            ICasa casaAnterior = carro.getCasa();
            if (casaAnterior != null) {
                casaAnterior.setCarro(null);
            }
            carro.setCasa(this);
            setCarro(carro);
        } catch (InterruptedException ex) {
            Logger.getLogger(Semaforo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void liberarRecurso() {
        mutex.release();
    }

    @Override
    public boolean reservarCasa() {
        try {
            return mutex.tryAcquire(15, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Semaforo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}