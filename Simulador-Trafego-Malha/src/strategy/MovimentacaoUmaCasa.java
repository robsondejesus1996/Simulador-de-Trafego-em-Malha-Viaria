package strategy;

import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class MovimentacaoUmaCasa implements Movimentacao {

    private InterfaceCasa casaOrigem;
    private InterfaceCasa casaDestino;

    public MovimentacaoUmaCasa(InterfaceCasa origem, InterfaceCasa destino) {
        this.casaOrigem = origem;
        this.casaDestino = destino;
    }

    @Override
    public void run() {
        casaDestino.movimentar(casaOrigem.obterCarro());
        casaOrigem.definirCarro(null);
        casaOrigem.liberarRecurso();
    }

}
