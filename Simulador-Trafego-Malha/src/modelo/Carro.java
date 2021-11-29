package modelo;

import controle.Controle;
import strategy.EnserirElementosMalha;
import utilizacao.MapaImagens;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import strategy.Movimentacao;
import modelo.InterfaceCasa;

/**
 *
 * @author Robson de Jesus
 */
public class Carro extends Thread implements InterfaceCarro {

    private boolean ativo;
    private final int cores;
    private InterfaceCasa casa;
    private Movimentacao rota;
    private final int velocidade;

    public Carro() {
        this.ativo = true;
        this.velocidade = 200 + new Random().nextInt(300);
        this.cores = MapaImagens.gerarCorAleatoriamente().getRGB();
    }

    @Override
    public void excluir() {
        this.ativo = false;
        Controle.getInstance().obterControleMalha().excluirCarroObservadores(this.getId());
    }

    @Override
    public int getCores() {
        return cores;
    }

    @Override
    public void buscarCaminho() {
        rota = this.casa.obterCaminho();
    }

    @Override
    public void definirCasa(InterfaceCasa newCasa) {
        this.casa = newCasa;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carro other = (Carro) obj;
        return this.getId() == other.getId();
    }

    @Override
    public InterfaceCasa obterCasa() {
        return casa;
    }

    @Override
    public int obterVelocidade() {
        return velocidade;
    }

    @Override
    public void sleep(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Carro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void inserirSimulacao(InterfaceCasa casaAleatoria) {
        rota = new EnserirElementosMalha(this, casaAleatoria);
        start();
    }

    @Override
    public void movimentar() {
        rota.executar();
        rota = null;
    }

    @Override
    public void run() {
        movimentar();
        sleep(velocidade);
        while (ativo) {
            buscarCaminho();
            if (rota == null) {
                break;
            }
            movimentar();
            sleep(velocidade);
        }
    }

}
