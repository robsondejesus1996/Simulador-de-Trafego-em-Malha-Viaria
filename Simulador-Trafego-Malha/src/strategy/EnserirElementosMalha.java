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

    private InterfaceCasa malhaCasa;
    private InterfaceCarro malhaCarro;

    public EnserirElementosMalha(Carro carro, InterfaceCasa casa) {
        this.malhaCasa = casa;
        this.malhaCarro = carro;
    }

    @Override
    public void run() {
        Controle system = Controle.getInstance();
        do {
            if (malhaCasa.alocacaoCasa()) {
                malhaCasa.definirCarro(malhaCarro);
                malhaCarro.definirCasa(malhaCasa);
            } else {
                malhaCasa = system.obterControleMalha().getRespawnAleatorio();
            }
        } while (malhaCarro.obterCasa() == null);
        system.malhaNotificacaoEntrada(malhaCarro);
    }

}
