package controle;

import strategy.MovimentacaoCasa;
import strategy.DesativarCarro;
import strategy.MovimentacaoUmaCasa;
import modelo.Casa;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import modelo.InterfaceCasa;
import factoryAbs.FabricaAbstrata;
import tela.ObservadorTabela;
/**
 *
 * @author Robson de Jesus
 */
public class ControleArquivoMalha {
    
    //saida controle -- system controller

    private int[][] malhaMatriz;
    private int qtdCasasValidas;
    private InterfaceCasa[][] malhaMatrizCasa;

    private List<InterfaceCasa> reaparecimentoCasa; 
    private List<InterfaceCasa> MorteCasas;

    private List<ObservadorTabela> observadores;

    private final Random aleatorioRandom;

    public ControleArquivoMalha(int[][] matrix) {
        this.malhaMatriz = matrix;
        this.observadores = new ArrayList<>();
        this.malhaMatrizCasa = new Casa[matrix.length][matrix[0].length];
        this.MorteCasas = new ArrayList<>();
        this.reaparecimentoCasa = new ArrayList<>();
        this.qtdCasasValidas = 0;
        this.aleatorioRandom = new Random();
    }

    public void iniciarMalha() {
        iniciarCasas();
        definirExtremidadeCasa();
        definirComandos();
    }

    public void anexarObservadores(ObservadorTabela observer) {
        this.observadores.add(observer);
    }

    public int pegarTamanhoColuna() {
        return malhaMatrizCasa.length;
    }

    public int pegarTamanhoLinha() {
        return malhaMatrizCasa[0].length;
    }

    public Object getCasaValue(int col, int row) {
        return malhaMatrizCasa[col][row].getValor();
    }

    private void iniciarCasas() {
        FabricaAbstrata factory = Controle.getInstance().obterFabrica();
        for (int row = 0; row < malhaMatriz[0].length; row++) {
            for (int coluna = 0; coluna < malhaMatriz.length; coluna++) {
                malhaMatrizCasa[coluna][row] = factory.construirCasa(malhaMatriz[coluna][row], coluna, row);
                int valor = malhaMatrizCasa[coluna][row].getValor();
                if (valor == 1 || valor == 2 || valor == 3 || valor == 4) {
                    qtdCasasValidas++;
                }
            }
        }
    }

    private void definirExtremidadeCasa() {
        MorteCasas.clear();
        reaparecimentoCasa.clear();
        for (int linha = 0; linha < pegarTamanhoLinha(); linha++) {
            for (int coluna = 0; coluna < pegarTamanhoColuna(); coluna++) {
                //Se houver valor na casa significa que a mesma faz parte da malha
                if (malhaMatrizCasa[coluna][linha].getValor() != 0) {
                    int lado = 0;
                    int valorCasa = malhaMatrizCasa[coluna][linha].getValor();

                    if (coluna == 0) {
                        lado = 1;
                    } else if (linha == 0) {
                        lado = 2;
                    } else if (coluna == pegarTamanhoColuna() - 1) {
                        lado = 3;
                    } else if (linha == pegarTamanhoLinha() - 1) {
                        lado = 4;
                    }
                    //lado == 1 - Primeira Coluna
                    switch (lado) {
                        case 1:
                            switch (valorCasa) {
                                case 2:
                                    this.reaparecimentoCasa.add(malhaMatrizCasa[coluna][linha]);
                                    //add casasRespawn
                                    break;
                                case 4:
                                    this.MorteCasas.add(malhaMatrizCasa[coluna][linha]);
                                    //add casasDeath
                                    break;
                            }
                            break;
                        case 2:
                            switch (valorCasa) {
                                case 1:
                                    this.MorteCasas.add(malhaMatrizCasa[coluna][linha]);
                                    //add casasDeath
                                    break;
                                case 3:
                                    this.reaparecimentoCasa.add(malhaMatrizCasa[coluna][linha]);
                                    //add casasRespawn
                                    break;
                            }
                            break;
                        case 3:
                            switch (valorCasa) {
                                case 2:
                                    this.MorteCasas.add(malhaMatrizCasa[coluna][linha]);
                                    //add casasDeath
                                    break;
                                case 4:
                                    this.reaparecimentoCasa.add(malhaMatrizCasa[coluna][linha]);
                                    //add casasRespawn
                                    break;
                            }
                            break;
                        case 4:
                            switch (valorCasa) {
                                case 1:
                                    this.reaparecimentoCasa.add(malhaMatrizCasa[coluna][linha]);
                                    //add casasRespawn
                                    break;
                                case 3:
                                    this.MorteCasas.add(malhaMatrizCasa[coluna][linha]);
                                    //add casasDeath
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void definirComandos() {
        //Adicionar os commands dentro da casa
        //Add Pontos de Morte
        for (InterfaceCasa iCasa : MorteCasas) {
            iCasa.addRota(new DesativarCarro(iCasa));
        }
        //Agora percorre a estrutura para encontrar as possíveis rotas de cada casa
        for (int linha = 0; linha < pegarTamanhoLinha(); linha++) {
            for (int coluna = 0; coluna < pegarTamanhoColuna(); coluna++) {
                //Se houver valor na casa significa que a mesma faz parte da malha
                if (malhaMatrizCasa[coluna][linha].getValor() != 0) {
                    InterfaceCasa destino;
                    InterfaceCasa origem = malhaMatrizCasa[coluna][linha];
                    if (!MorteCasas.contains(origem)) {
                        switch (malhaMatrizCasa[coluna][linha].getValor()) {
                            case 1 -> {
                                destino = malhaMatrizCasa[coluna][linha - 1];
                                if (verificarCruzamento(destino)) {
                                    definirCaminhoCruz(origem);
                                } else {
                                    origem.addRota(new MovimentacaoUmaCasa(origem, destino));
                                }
                            }
                            case 2 -> {
                                destino = malhaMatrizCasa[coluna + 1][linha];
                                if (verificarCruzamento(destino)) {
                                    definirCaminhoCruz(origem);
                                } else {
                                    origem.addRota(new MovimentacaoUmaCasa(origem, destino));
                                }
                            }
                            case 3 -> {
                                destino = malhaMatrizCasa[coluna][linha + 1];
                                if (verificarCruzamento(destino)) {
                                    definirCaminhoCruz(origem);
                                } else {
                                    origem.addRota(new MovimentacaoUmaCasa(origem, destino));
                                }
                            }
                            case 4 -> {
                                destino = malhaMatrizCasa[coluna - 1][linha];
                                if (verificarCruzamento(destino)) {
                                    definirCaminhoCruz(origem);
                                } else {
                                    origem.addRota(new MovimentacaoUmaCasa(origem, destino));
                                }
                            }
                            default -> origem.addRota(new DesativarCarro(origem));
                        }
                    }
                }
            }
        }
    }

    public boolean verificarCruzamento(InterfaceCasa casa) {
        int valor = casa.getValor();
        return !(valor == 1 || valor == 2 || valor == 3 || valor == 4);
    }

    public int obterQuantidadeCasasValida() {//
        return qtdCasasValidas;
    }

    public void removerObservadores() {
        observadores.clear();
    }

    public InterfaceCasa getRespawnAleatorio() {
        return reaparecimentoCasa.get(aleatorioRandom.nextInt(reaparecimentoCasa.size()));
    }

    public void movimentarCarro(long id, int colunm, int row) {
        observadores.forEach((observer) -> observer.moveCarro(id, colunm, row));
    }

    public void excluirCarroObservadores(long id) {
        observadores.forEach((observer) -> observer.removeCarro(id));
    }

    private void definirCaminhoCruz(InterfaceCasa origem) {
        InterfaceCasa saida1;
        InterfaceCasa saida2;
        InterfaceCasa saida3;
        InterfaceCasa Movimento1;
        InterfaceCasa Movimento2;
        InterfaceCasa Movimento3;
        //Captura os possíveis movimentos
        //Entrada 1
        //Entrada 2
        //Entrada 3
        //Captura os possíveis movimentos
        //Entrada 1
        //Entrada 2
        //Entrada 3
        //Captura os possíveis movimentos
        //Entrada 1
        //Entrada 2
        //Entrada 3
        //Captura os possíveis movimentos
        //Entrada 1
        //Entrada 2
        //Entrada 3
        switch (origem.getValor()) {
            case 1 -> {
                Movimento1 = malhaMatrizCasa[origem.getColunm()][origem.getRow() - 1];
                Movimento2 = malhaMatrizCasa[origem.getColunm()][origem.getRow() - 2];
                Movimento3 = malhaMatrizCasa[origem.getColunm() - 1][origem.getRow() - 2];
                saida1 = malhaMatrizCasa[origem.getColunm() + 1][origem.getRow() - 1];
                if (verificarCasaValida(saida1)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida1, Arrays.asList(Movimento1, saida1)));
                }
                saida2 = malhaMatrizCasa[origem.getColunm()][origem.getRow() - 3];
                if (verificarCasaValida(saida2)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida2, Arrays.asList(Movimento1, Movimento2, saida2)));
                }
                saida3 = malhaMatrizCasa[origem.getColunm() - 2][origem.getRow() - 2];
                if (verificarCasaValida(saida3)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida3, Arrays.asList(Movimento1, Movimento2, Movimento3, saida3)));
                }
            }
            case 2 -> {
                Movimento1 = malhaMatrizCasa[origem.getColunm() + 1][origem.getRow()];
                Movimento2 = malhaMatrizCasa[origem.getColunm() + 2][origem.getRow()];
                Movimento3 = malhaMatrizCasa[origem.getColunm() + 2][origem.getRow() - 1];
                saida1 = malhaMatrizCasa[origem.getColunm() + 1][origem.getRow() + 1];
                if (verificarCasaValida(saida1)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida1, Arrays.asList(Movimento1, saida1)));
                }
                saida2 = malhaMatrizCasa[origem.getColunm() + 3][origem.getRow()];
                if (verificarCasaValida(saida2)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida2, Arrays.asList(Movimento1, Movimento2, saida2)));
                }
                saida3 = malhaMatrizCasa[origem.getColunm() + 2][origem.getRow() - 2];
                if (verificarCasaValida(saida3)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida3, Arrays.asList(Movimento1, Movimento2, Movimento3, saida3)));
                }
            }
            case 3 -> {
                Movimento1 = malhaMatrizCasa[origem.getColunm()][origem.getRow() + 1];
                Movimento2 = malhaMatrizCasa[origem.getColunm()][origem.getRow() + 2];
                Movimento3 = malhaMatrizCasa[origem.getColunm() + 1][origem.getRow() + 2];
                saida1 = malhaMatrizCasa[origem.getColunm() - 1][origem.getRow() + 1];
                if (verificarCasaValida(saida1)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida1, Arrays.asList(Movimento1, saida1)));
                }
                saida2 = malhaMatrizCasa[origem.getColunm()][origem.getRow() + 3];
                if (verificarCasaValida(saida2)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida2, Arrays.asList(Movimento1, Movimento2, saida2)));
                }
                saida3 = malhaMatrizCasa[origem.getColunm() + 2][origem.getRow() + 2];
                if (verificarCasaValida(saida3)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida3, Arrays.asList(Movimento1, Movimento2, Movimento3, saida3)));
                }
            }
            case 4 -> {
                Movimento1 = malhaMatrizCasa[origem.getColunm() - 1][origem.getRow()];
                Movimento2 = malhaMatrizCasa[origem.getColunm() - 2][origem.getRow()];
                Movimento3 = malhaMatrizCasa[origem.getColunm() - 2][origem.getRow() + 1];
                saida1 = malhaMatrizCasa[origem.getColunm() - 1][origem.getRow() - 1];
                if (verificarCasaValida(saida1)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida1, Arrays.asList(Movimento1, saida1)));
                }
                saida2 = malhaMatrizCasa[origem.getColunm() - 3][origem.getRow()];
                if (verificarCasaValida(saida2)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida2, Arrays.asList(Movimento1, Movimento2, saida2)));
                }
                saida3 = malhaMatrizCasa[origem.getColunm() - 2][origem.getRow() + 2];
                if (verificarCasaValida(saida3)) {
                    origem.addRota(new MovimentacaoCasa(origem, saida3, Arrays.asList(Movimento1, Movimento2, Movimento3, saida3)));
                }
            }

        }
    }

    public boolean verificarCasaValida(InterfaceCasa casa) {
        return casa.getValor() != 0;
    }
}
