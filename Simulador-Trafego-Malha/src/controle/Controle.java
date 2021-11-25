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

    private static Controle instance;
    private ControleArquivoMalha malhaController;
    private FabricaAbstrata factory;
    private int numeroDeCarroObjetivo;
    private boolean simulationAtivo;
    private Map<Long, InterfaceCarro> carrosEmEspera;
    private Map<Long, InterfaceCarro> carrosEmMalha;
    private List<FramePrincipalObserver> framePrincipalObservers;
    private List<ObservadorTabela> malhaTablesObservers;

    private Controle() {
        this.carrosEmMalha = new HashMap<>();
        this.carrosEmEspera = new HashMap<>();
        this.framePrincipalObservers = new ArrayList<>();
        this.malhaTablesObservers = new ArrayList<>();
        this.simulationAtivo = false;

    }

    public static synchronized Controle getInstance() {
        if (instance == null) {
            instance = new Controle();
        }
        return instance;
    }

    /**
     * Ler Arquivo onde contem a matriz
     */
    public void readFile(String text) throws FileNotFoundException, Exception {
        LeituraMalha ler = new LeituraMalha(text);
        malhaController = new ControleArquivoMalha(ler.getMatrix());
    }

    public Object getCasa(int col, int row) {
        return malhaController.getCasaValue(col, row);
    }

    public ControleArquivoMalha getMalhaController() {
        return malhaController;
    }

    public FabricaAbstrata getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory.equals("Monitor") ? new MonitorFabrica() : new SemaforoFabrica();
    }

    /**
     * Rebute Malha
     */
    public void rebutMalha() {
        this.malhaController.removeObservers();
    }

    public void startSimulation(int numeroCarro) {
        this.numeroDeCarroObjetivo = numeroCarro;
        this.simulationAtivo = true;

        for (int i = 0; i < numeroDeCarroObjetivo; i++) {
            newCarroEmMalha();
        }

        Thread respawn = new Thread(this::addAutomatico);
        respawn.start();
    }

    private void newCarroEmMalha() {
        Carro carro = new Carro();
        carrosEmEspera.put(carro.getId(), carro);
        malhaTablesObservers.forEach((observer) -> observer.createCarro(carro.getId(), carro.getRBG(), -1, -1));
        carro.enterSimulation(malhaController.getRespawnAleatorio());
    }

    public void notificarEntreiNaMalha(InterfaceCarro carro) {
        carrosEmEspera.remove(carro.getId());
        carrosEmMalha.put(carro.getId(), carro);
        SwingUtilities.invokeLater(() -> framePrincipalObservers.forEach((observer) -> observer.notificarNumeroDeCarro(carrosEmMalha.size())
        ));
    }

    public void notificarCarroMorto(InterfaceCarro carro) {
        malhaTablesObservers.forEach((observer) -> observer.removeCarro(carro.getId()));
        carrosEmMalha.remove(carro.getId());
        SwingUtilities.invokeLater(() -> framePrincipalObservers.forEach((observer) -> observer.notificarNumeroDeCarro(carrosEmMalha.size())));
    }

    public void addFramePrincipalObserver(FramePrincipalObserver observer) {
        this.framePrincipalObservers.add(observer);
    }

    public void addMalhaTableObserver(ObservadorTabela observer) {
        this.malhaTablesObservers.add(observer);
    }

    //Runneable
    private void addAutomatico() {
        while (simulationAtivo) {
            for (int i = (carrosEmEspera.size() + carrosEmMalha.size()); i < numeroDeCarroObjetivo; i++) {
                newCarroEmMalha();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<InterfaceCarro> arrayList = new ArrayList<>();
        arrayList.addAll(carrosEmMalha.values());
        arrayList.addAll(carrosEmEspera.values());

        arrayList.forEach((value) -> {
            try {
                value.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        framePrincipalObservers.forEach(FramePrincipalObserver::notificarSimulacaoFinalizada);
    }

    public void pararRepawn() {
        simulationAtivo = false;
    }
}
