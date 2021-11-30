package tela;
import utilizacao.MapaImagens;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Robson de Jesus
 */

public class CarroMalha {

    private int coluna;
    private int linha;
    private final BufferedImage image;

    public CarroMalha(int color, int coluna, int linha) {
        this.coluna = coluna;
        this.linha = linha;
        this.image = MapaImagens.substituirCor(MapaImagens.getImagem(MapaImagens.CARRO), color);
    }

    public void definirColuna(int coluna) {
        this.coluna = coluna;
    }

    public void definirLinha(int linha) {
        this.linha = linha;
    }

    public int obterColuna() {
        return coluna; }

    public int obterLinha() {
        return linha;
    }

    public void draw(Graphics g) {
        g.drawImage(this.image, 0, 0, null);
    }
}
