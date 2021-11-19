/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strategy;

import controle.SystemController;
import modelo.Carro;
import modelo.ICarro;
import modelo.ICasa;

/**
 *
 * @author Robson de Jesus
 */
public class EntraNaMalha implements Movimentacao {

    private ICasa casa;
    private ICarro carro;

    public EntraNaMalha(Carro carro, ICasa casa) {
        this.casa = casa;
        this.carro = carro;
    }

    @Override
    public void executar() {
        SystemController system = SystemController.getInstance();
        do {
            if (casa.reservarCasa()) {
                casa.setCarro(carro);
                carro.setCasa(casa);
            } else {
                casa = system.getMalhaController().getRespawnAleatorio();
            }
        } while (carro.getCasa() == null);
        system.notificarEntreiNaMalha(carro);
    }

}
