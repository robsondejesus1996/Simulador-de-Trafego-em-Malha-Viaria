/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import strategy.Movimentacao;

/**
 *
 * @author Robson de Jesus
 */
public interface ICasa {

    
    void addRota(Movimentacao command);

    Movimentacao getRota();

    int getColunm();

    int getRow();

    int getValor();

    void liberarRecurso();

    void mover(ICarro carro);

    boolean reservarCasa();

    void setCarro(ICarro carro);

    ICarro getCarro();

    void removerCarro();
}
