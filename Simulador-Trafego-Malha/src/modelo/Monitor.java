/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robson de Jesus
 */
public class Monitor extends Casa {

    private Lock lock;

    public Monitor(int valor, int colunm, int row) {
        super(valor, colunm, row);
        this.lock = new ReentrantLock(true);
    }

    @Override
    public void mover(ICarro carro) {
        lock.lock();
        ICasa casaAnterior = carro.getCasa();
        if (casaAnterior != null) {
            casaAnterior.setCarro(null);
        }
        carro.setCasa(this);
        setCarro(carro);
    }

    @Override
    public void liberarRecurso() {
        lock.unlock();
    }

    //Necessita do Lock
    @Override
    public boolean reservarCasa() {
        try {
            return lock.tryLock(15, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
