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

    private Lock bloquear;

    public MonitoresCasas(int valor, int coluna, int linha) {
        super(valor, coluna, linha);
        this.bloquear = new ReentrantLock(true);
    }

    @Override
    public void movimentar(InterfaceCarro carro) {
        bloquear.lock();
        InterfaceCasa casaAnterior = carro.obterCasa();
        if (casaAnterior != null) {
            casaAnterior.definirCarro(null);
        }
        carro.definirCasa(this);
        definirCarro(carro);
    }

    @Override
    public void liberarRecurso() {
        bloquear.unlock();
    }

    @Override
    public boolean alocacaoCasa() {
        try {
            return bloquear.tryLock(15, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(MonitoresCasas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
