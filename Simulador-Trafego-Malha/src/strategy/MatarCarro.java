/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strategy;

import controle.SystemController;
import modelo.ICarro;
import modelo.ICasa;

/**
 *
 * @author Robson de Jesus
 */
public class MatarCarro implements Movimentacao {

    private final ICasa origem;

    public MatarCarro(ICasa origem) {
        this.origem = origem;
    }

    @Override
    public void executar() {
        ICarro carro = origem.getCarro();
        origem.setCarro(null);
        origem.liberarRecurso();
        carro.desativar();
        SystemController.getInstance().notificarCarroMorto(carro);
    }

}

