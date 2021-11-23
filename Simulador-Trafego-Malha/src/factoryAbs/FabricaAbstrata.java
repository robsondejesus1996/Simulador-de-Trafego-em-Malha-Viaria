package factoryAbs;

import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public interface FabricaAbstrata {

    public InterfaceCasa createCasa(int valor, int colunm, int row);
}
