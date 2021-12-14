package strategy;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import modelo.InterfaceCarro;
import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class MovimentacaoCasa implements Movimentacao {

    private final InterfaceCasa casaOrigem;
    private final InterfaceCasa casaDestino;
    private final List<InterfaceCasa> movimentacaoCaminho;
    private final Random aleatorio;

    public MovimentacaoCasa(InterfaceCasa origem, InterfaceCasa destino, List<InterfaceCasa> caminho) {
        this.casaOrigem = origem;
        this.casaDestino = destino;
        this.movimentacaoCaminho = Collections.unmodifiableList(caminho);
        this.aleatorio = new Random();
    }

    private boolean liberarRecursos(int i) {
        for (int j = (i - 1); j >= 0; j--) {
            movimentacaoCaminho.get(j).liberarRecurso();
        }
        return false;
    }

    @Override
    public void run() {
        InterfaceCarro carro = casaOrigem.obterCarro();
        int saidaInvalida = 0;
        int limiteDeTentativas = aleatorio.nextInt(10) + 1;
        boolean liberado;
        do {
            liberado = true;
            if (casaDestino.alocacaoCasa()) {

                for (int i = 0; i < movimentacaoCaminho.size() - 1; i++) {
                    InterfaceCasa casa = movimentacaoCaminho.get(i);
                    if (!casa.alocacaoCasa()) {
                        casaDestino.liberarRecurso();
                        liberado = liberarRecursos(i);
                        break;
                    }
                }

            } else {
                saidaInvalida++;
                liberado = false;
            }

            if (saidaInvalida >= limiteDeTentativas) {
                break;
            }

            if (!liberado) {
                carro.sleep(100 + aleatorio.nextInt(200));
            }

        } while (!liberado);

        if (saidaInvalida < limiteDeTentativas) {
            int velocidade = carro.obterVelocidade();

            casaOrigem.excluirCarro();

            InterfaceCasa primeiracasa = movimentacaoCaminho.get(0);
            primeiracasa.definirCarro(carro);
            carro.definirCasa(primeiracasa);

            casaOrigem.liberarRecurso();
            carro.sleep(velocidade);

            for (int i = 0; i < movimentacaoCaminho.size() - 1; i++) {
                InterfaceCasa casaAtual = movimentacaoCaminho.get(i);
                casaAtual.excluirCarro();

                InterfaceCasa novaCasa = movimentacaoCaminho.get(i + 1);
                novaCasa.definirCarro(carro);
                carro.definirCasa(novaCasa);

                casaAtual.liberarRecurso();

                carro.sleep(velocidade);
            }
        }
    }

}
