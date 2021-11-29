package modelo;


import strategy.Movimentacao;

/**
 *
 * @author Robson de Jesus
 */
public interface InterfaceCasa {

    void adicionarCaminho(Movimentacao command);

    Movimentacao obterCaminho();

    int obterColuna();

    int obterLinha();

    int obterValor();

    void liberarRecurso();

    void movimentar(InterfaceCarro carro);

    boolean alocacaoCasa();//reserva de casa

    void definirCarro(InterfaceCarro carro);

    InterfaceCarro obterCarro();

    void excluirCarro();
}
