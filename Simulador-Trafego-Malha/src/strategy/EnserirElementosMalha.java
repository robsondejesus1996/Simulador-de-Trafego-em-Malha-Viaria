package strategy;

import controle.Controle;
import modelo.Carro;
import modelo.InterfaceCarro;
import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class EnserirElementosMalha implements Movimentacao {

    private InterfaceCasa casa;
    private InterfaceCarro carro;

    public EnserirElementosMalha(Carro carro, InterfaceCasa casa) {
        this.casa = casa;
        this.carro = carro;
    }

    @Override
    public void executar() {
        Controle system = Controle.getInstance();
        do {
            if (casa.reservarCasa()) {
                casa.setCarro(carro);
                carro.setCasa(casa);
            } else {
                casa = system.obterControleMalha().getRespawnAleatorio();
            }
        } while (carro.getCasa() == null);
        system.malhaNotificacaoEntrada(carro);
    }

}
