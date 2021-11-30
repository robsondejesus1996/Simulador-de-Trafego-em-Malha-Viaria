package tela;

/**
 *
 * @author Robson de Jesus
 */
public interface ObservadorTabela {

    void inserirCarro(long id, int color, int column, int row);

    void movimentarCarro(long id, int colunm, int row);

    void excluirCarro(long id);
}
