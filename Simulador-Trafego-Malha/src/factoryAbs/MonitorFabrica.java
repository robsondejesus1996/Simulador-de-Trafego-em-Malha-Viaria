package factoryAbs;

import modelo.MonitoresCasas;
import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class MonitorFabrica implements FabricaAbstrata{


    @Override
    public InterfaceCasa createCasa(int valor, int column, int row) {

        return new MonitoresCasas(valor,column,row);
    }

}
