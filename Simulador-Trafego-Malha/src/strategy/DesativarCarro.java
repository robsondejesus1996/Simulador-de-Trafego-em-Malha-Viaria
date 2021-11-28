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

    private final InterfaceCasa origem;

    public DesativarCarro(InterfaceCasa origem) {
        this.origem = origem;
    }

    @Override
    public void executar() {
        InterfaceCarro carro = origem.getCarro();
        origem.setCarro(null);
        origem.liberarRecurso();
        carro.desativar();
        Controle.getInstance().carroDesativarNotivacacao(carro);
    }

}
