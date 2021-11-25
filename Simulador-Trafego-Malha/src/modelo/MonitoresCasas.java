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
public class MonitoresCasas extends Casa {

    private Lock lock;

    public MonitoresCasas(int valor, int colunm, int row) {
        super(valor, colunm, row);
        this.lock = new ReentrantLock(true);
    }

    @Override
    public void mover(InterfaceCarro carro) {
        lock.lock();
        InterfaceCasa casaAnterior = carro.getCasa();
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
            Logger.getLogger(MonitoresCasas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
