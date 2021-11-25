package strategy;

import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class MovimentacaoUmaCasa implements Movimentacao {

    private InterfaceCasa origem;
    private InterfaceCasa destino;

    public MovimentacaoUmaCasa(InterfaceCasa origem, InterfaceCasa destino) {
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
