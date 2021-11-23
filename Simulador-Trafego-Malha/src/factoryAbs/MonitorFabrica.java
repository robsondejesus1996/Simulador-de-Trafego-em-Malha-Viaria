package factoryAbs;

import modelo.CasaMonitor;
import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class MonitorFabrica implements FabricaAbstrata{


    @Override
    public InterfaceCasa createCasa(int valor, int column, int row) {

        return new CasaMonitor(valor,column,row);
    }

}
