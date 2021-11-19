/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strategy;

import modelo.ICasa;

/**
 *
 * @author Robson de Jesus
 */
public class MoverUmaCasa implements Movimentacao {

    private ICasa origem;
    private ICasa destino;

    public MoverUmaCasa(ICasa origem, ICasa destino) {
        this.origem = origem;
        this.destino = destino;
    }

    @Override
    public void executar() {
        destino.mover(origem.getCarro());
        origem.setCarro(null);
        origem.liberarRecurso();
    }

}
