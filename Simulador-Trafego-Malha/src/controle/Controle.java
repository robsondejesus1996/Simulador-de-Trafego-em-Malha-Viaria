package controle;

import factoryAbs.MonitorFabrica;
import factoryAbs.SemaforoFabrica;
import modelo.Carro;
import utilizacao.LeituraMalha;
import tela.FramePrincipalObserver;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.InterfaceCarro;
import factoryAbs.FabricaAbstrata;
import tela.ObservadorTabela;

/**
 *
 * @author Robson de Jesus
 */
public class Controle {

    private static Controle singleton;
    private ControleArquivoMalha controleMalha;
    private FabricaAbstrata fact;
    private int qtdCarros;
    private boolean ativadaSimulacao;
    private Map<Long, InterfaceCarro> qtdCarrosEsperando;
    private Map<Long, InterfaceCarro> qtdCarrosMalha;
    private List<FramePrincipalObserver> observadoresTela;
    private List<ObservadorTabela> tabelaObservadoresMalha;

    private Controle() {
        this.qtdCarrosMalha = new HashMap<>();
        this.qtdCarrosEsperando = new HashMap<>();
        this.observadoresTela = new ArrayList<>();
        this.tabelaObservadoresMalha = new ArrayList<>();
        this.ativadaSimulacao = false;

    }

    public static synchronized Controle getInstance() {
        if (singleton == null) {
            singleton = new Controle();
        }
        return singleton;
    }

    /**
     * Ler Arquivo onde contem a matriz
     */
    public void carregarArquivo(String text) throws FileNotFoundException, Exception {
        LeituraMalha ler = new LeituraMalha(text);
        controleMalha = new ControleArquivoMalha(ler.getMatrix());
    }

    public Object obterCasa(int col, int row) {
        return controleMalha.getCasaValue(col, row);
    }

    public ControleArquivoMalha obterControleMalha() {
        return controleMalha;
    }

    public FabricaAbstrata obterFabrica() {
        return fact;
    }

    public void definirFabrica(String factory) {
        this.fact = factory.equals("Monitor") ? new MonitorFabrica() : new SemaforoFabrica();
    }

    /**
     * Rebute Malha
     */
    public void redefinirMalhaObservadores() {
        this.controleMalha.removerObservadores();
    }

    public void iniciarSimulacao(int numeroCarro) {
        this.qtdCarros = numeroCarro;
        this.ativadaSimulacao = true;

        for (int i = 0; i < qtdCarros; i++) {
            novoCarroMalha();
        }

        Thread respawn = new Thread(this::addAutomatico);
        respawn.start();
    }

    private void novoCarroMalha() {
        Carro carro = new Carro();
        qtdCarrosEsperando.put(carro.getId(), carro);
        tabelaObservadoresMalha.forEach((observer) -> observer.createCarro(carro.getId(), carro.getRBG(), -1, -1));
        carro.enterSimulation(controleMalha.getRespawnAleatorio());
    }

    public void malhaNotificacaoEntrada(InterfaceCarro carro) {
        qtdCarrosEsperando.remove(carro.getId());
        qtdCarrosMalha.put(carro.getId(), carro);
        SwingUtilities.invokeLater(() -> observadoresTela.forEach((observer) -> observer.notificarNumeroDeCarro(qtdCarrosMalha.size())
        ));
    }

    public void carroDesativarNotivacacao(InterfaceCarro carro) { // notificar carro morto 
        tabelaObservadoresMalha.forEach((observer) -> observer.removeCarro(carro.getId()));
        qtdCarrosMalha.remove(carro.getId());
        SwingUtilities.invokeLater(() -> observadoresTela.forEach((observer) -> observer.notificarNumeroDeCarro(qtdCarrosMalha.size())));
    }

    public void adicionarObservadoresTela(FramePrincipalObserver observer) {
        this.observadoresTela.add(observer);
    }

    public void adicionarTabelaObservadores(ObservadorTabela observer) {
        this.tabelaObservadoresMalha.add(observer);
    }

    //Runneable
    private void addAutomatico() {
        while (ativadaSimulacao) {
            for (int i = (qtdCarrosEsperando.size() + qtdCarrosMalha.size()); i < qtdCarros; i++) {
                novoCarroMalha();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<InterfaceCarro> arrayList = new ArrayList<>();
        arrayList.addAll(qtdCarrosMalha.values());
        arrayList.addAll(qtdCarrosEsperando.values());

        arrayList.forEach((value) -> {
            try {
                value.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        observadoresTela.forEach(FramePrincipalObserver::notificarSimulacaoFinalizada);
    }

    public void stopReaparecimento() {
        ativadaSimulacao = false;
    }
}
