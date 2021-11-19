/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractfactory;

import modelo.CasaMonitor;
import modelo.ICasa;

/**
 *
 * @author Robson de Jesus
 */
public class FactoryMonitor implements AbstractFactory{


    @Override
    public ICasa createCasa(int valor, int column, int row) {

        return new CasaMonitor(valor,column,row);
    }

}