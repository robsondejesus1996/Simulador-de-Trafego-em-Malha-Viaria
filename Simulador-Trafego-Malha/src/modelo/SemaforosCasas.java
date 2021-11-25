package modelo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import strategy.Movimentacao;

/**
 *
 * @author Robson de Jesus
 */
public class SemaforosCasas extends Casa {

    private Semaphore mutex;

    public SemaforosCasas(int valor, int colunm, int row) {
        super(valor, colunm, row);
        mutex = new Semaphore(1, true);
    }

    @Override
    public void mover(InterfaceCarro carro) {
        try {
            mutex.acquire();
            InterfaceCasa casaAnterior = carro.getCasa();
            if (casaAnterior != null) {
                casaAnterior.setCarro(null);
            }
            carro.setCasa(this);
            setCarro(carro);
        } catch (InterruptedException ex) {
            Logger.getLogger(SemaforosCasas.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SemaforosCasas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
