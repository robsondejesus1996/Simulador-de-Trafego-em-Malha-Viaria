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
            if (casa.alocacaoCasa()) {
                casa.definirCarro(carro);
                carro.definirCasa(casa);
            } else {
                casa = system.obterControleMalha().getRespawnAleatorio();
            }
        } while (carro.obterCasa()== null);
        system.malhaNotificacaoEntrada(carro);
    }

}
