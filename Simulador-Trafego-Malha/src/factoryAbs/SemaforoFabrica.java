package factoryAbs;

import modelo.Casa;
import modelo.CasaSemaforo;

/**
 *
 * @author Robson de Jesus
 */
public class SemaforoFabrica implements FabricaAbstrata {

    @Override
    public Casa createCasa(int valor, int column, int row) {

        return new CasaSemaforo(valor, column, row);
    }

}
