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
        InterfaceCarro carro = origem.obterCarro();
        origem.definirCarro(null);
        origem.liberarRecurso();
        carro.excluir();
        Controle.getInstance().carroDesativarNotivacacao(carro);
    }

}
