package tela;

/**
 *
 * @author Robson de Jesus
 */
public interface ObservadorTabela {

    void createCarro(long id, int color, int column, int row);

    void moveCarro(long id, int colunm, int row);

    void removeCarro(long id);
}
