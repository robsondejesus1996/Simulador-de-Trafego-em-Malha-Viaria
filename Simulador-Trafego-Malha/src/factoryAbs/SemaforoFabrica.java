package factoryAbs;

import modelo.Casa;
import modelo.SemaforosCasas;

/**
 *
 * @author Robson de Jesus
 */
public class SemaforoFabrica implements FabricaAbstrata {

    @Override
    public Casa construirCasa(int valor, int column, int row) {

        return new SemaforosCasas(valor, column, row);
    }

}
