/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strategy;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import modelo.ICarro;
import modelo.ICasa;

/**
 *
 * @author Robson de Jesus
 */
public class MoverNCasa implements Movimentacao {

    //Começo de Tudo
    private final ICasa origem;
    //Fim de Tudo
    private final ICasa destino;

    //Armazena todos o caminho INCLUSIVEL O destino
    private final List<ICasa> caminho;
    //random
    private final Random random;

    public MoverNCasa(ICasa origem, ICasa destino, List<ICasa> caminho) {
        this.origem = origem;
        this.destino = destino;
        this.caminho = Collections.unmodifiableList(caminho);
        this.random = new Random();
    }

    @Override
    public void executar() {
        ICarro carro = origem.getCarro();
        int saidaInvalida = 0;
        int limiteDeTentativas = random.nextInt(10) + 1;
        boolean liberado;
        do {
            liberado = true;
            //Vai tentar pegar o recurso de todas!!
            if (destino.reservarCasa()) {

                for (int i = 0; i < caminho.size() - 1; i++) {
                    ICasa casa = caminho.get(i);
                    if (!casa.reservarCasa()) {
                        destino.liberarRecurso();
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
                carro.sleep(100 + random.nextInt(200));
            }

        } while (!liberado);

        if (saidaInvalida < limiteDeTentativas) {
            int velocidade = carro.getVelocidade();

            origem.removerCarro();

            ICasa primeiracasa = caminho.get(0);
            primeiracasa.setCarro(carro);
            carro.setCasa(primeiracasa);

            origem.liberarRecurso();
            carro.sleep(velocidade);

            for (int i = 0; i < caminho.size() - 1; i++) {
                //saindo da casa
                ICasa casaAtual = caminho.get(i);
                casaAtual.removerCarro();

                //entrando na casa
                ICasa novaCasa = caminho.get(i + 1);
                novaCasa.setCarro(carro);
                carro.setCasa(novaCasa);

                casaAtual.liberarRecurso();

                carro.sleep(velocidade);
            }
        }
    }

    private boolean liberarRecursos(int i) {
        for (int j = (i - 1); j >= 0; j--) {
            caminho.get(j).liberarRecurso();
        }
        return false;
    }

}