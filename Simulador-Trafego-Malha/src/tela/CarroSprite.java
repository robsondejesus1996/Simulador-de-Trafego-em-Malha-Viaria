package tela;
import utilizacao.MapaImagens;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Robson de Jesus
 */

public class CarroSprite {

    private int column, row;
    private final BufferedImage image;

    public CarroSprite(int color, int column, int row) {
        this.column = column;
        this.row = row;
        this.image = MapaImagens.replaceColor(MapaImagens.getImagem(MapaImagens.CARRO), color);
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() { return column; }

    public int getRow() {
        return row;
    }

    public void draw(Graphics g) {
        g.drawImage(this.image, 0, 0, null);
    }
}
