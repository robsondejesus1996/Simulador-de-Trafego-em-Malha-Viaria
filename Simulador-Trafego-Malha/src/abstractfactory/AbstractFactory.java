/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractfactory;

import modelo.ICasa;

/**
 *
 * @author Robson de Jesus
 */
public interface AbstractFactory {

    public ICasa createCasa(int valor, int colunm, int row);
}

