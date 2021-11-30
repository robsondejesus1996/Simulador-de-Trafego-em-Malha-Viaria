package strategy;

import controle.Controle;
import modelo.InterfaceCarro;
import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class DesativarCarro implements Movimentacao {

    /**
     * Matar o carro
     */
    private final InterfaceCasa casaOrigem;

    public DesativarCarro(InterfaceCasa origem) {
        this.casaOrigem = origem;
    }

    @Override
    public void run() {
        InterfaceCarro carro = casaOrigem.obterCarro();
        casaOrigem.definirCarro(null);
        casaOrigem.liberarRecurso();
        carro.excluir();
        Controle.getInstance().carroDesativarNotivacacao(carro);
    }

}
