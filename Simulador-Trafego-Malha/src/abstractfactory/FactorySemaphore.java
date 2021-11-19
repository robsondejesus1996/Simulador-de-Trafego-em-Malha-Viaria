/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractfactory;

import modelo.Casa;
import modelo.CasaSemaforo;

/**
 *
 * @author Robson de Jesus
 */
public class FactorySemaphore implements AbstractFactory {

    @Override
    public Casa createCasa(int valor, int column, int row) {

        return new CasaSemaforo(valor, column, row);
    }

}
