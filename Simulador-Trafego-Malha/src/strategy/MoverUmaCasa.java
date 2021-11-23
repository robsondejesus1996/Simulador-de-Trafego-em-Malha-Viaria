package strategy;

import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class MoverUmaCasa implements Movimentacao {

    private InterfaceCasa origem;
    private InterfaceCasa destino;

    public MoverUmaCasa(InterfaceCasa origem, InterfaceCasa destino) {
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
