package strategy;

import controle.Controle;
import modelo.InterfaceCarro;
import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class MatarCarro implements Movimentacao {

    private final InterfaceCasa origem;

    public MatarCarro(InterfaceCasa origem) {
        this.origem = origem;
    }

    @Override
    public void executar() {
        InterfaceCarro carro = origem.getCarro();
        origem.setCarro(null);
        origem.liberarRecurso();
        carro.desativar();
        Controle.getInstance().notificarCarroMorto(carro);
    }

}
