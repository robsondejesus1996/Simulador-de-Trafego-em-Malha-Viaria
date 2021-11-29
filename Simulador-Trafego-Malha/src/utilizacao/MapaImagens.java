package utilizacao;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Robson de Jesus
 */
public class MapaImagens {

    private static Map<String, BufferedImage> imagems = new HashMap<>();
    //Geral
    public static final String ZERO = "img/0.png";
    public static final String CIMA = "img/1.png";
    public static final String BAIXO = "img/3.png";
    public static final String ESQUERDA = "img/4.png";
    public static final String DIREITA = "img/2.png";
    public static final String OUTROS = "img/outro.png";
    public static final String CARRO = "img/carro.png";

    private static Random aleatorio;

    private static void inicializacao() throws IOException {
        imagems.put(ZERO, ImageIO.read(new File(MapaImagens.ZERO)));
        imagems.put(CIMA, ImageIO.read(new File(MapaImagens.CIMA)));
        imagems.put(BAIXO, ImageIO.read(new File(MapaImagens.BAIXO)));
        imagems.put(ESQUERDA, ImageIO.read(new File(MapaImagens.ESQUERDA)));
        imagems.put(DIREITA, ImageIO.read(new File(MapaImagens.DIREITA)));
        imagems.put(OUTROS, ImageIO.read(new File(MapaImagens.OUTROS)));
        imagems.put(CARRO, ImageIO.read(new File(MapaImagens.CARRO)));
    }

    public static BufferedImage obterImagem(int in) {
        return switch (in) {
            case 0 -> getImagem(ZERO);
            case 1 -> getImagem(CIMA);
            case 2 -> getImagem(DIREITA);
            case 3 -> getImagem(BAIXO);
            case 4 -> getImagem(ESQUERDA);
            default -> getImagem(OUTROS);
        };
    }

    public synchronized static BufferedImage getImagem(String imagem) {
        if (imagems.isEmpty()) {
            try {
                inicializacao();
            } catch (IOException ex) {
                Logger.getLogger(MapaImagens.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imagems.get(imagem);
    }

    public static BufferedImage substituirCor(BufferedImage image, int preferred) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        int color;
        int target = Color.black.getRGB();
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                color = image.getRGB(i, j);
                if (color == target) {
                    newImage.setRGB(i, j, preferred);
                } else {
                    newImage.setRGB(i, j, color);
                }
            }
        }

        return newImage;
    }

    public static Color gerarCorAleatoriamente() {
        int r = obterAleatorio().nextInt(256);
        int g = obterAleatorio().nextInt(256);
        int b = obterAleatorio().nextInt(256);
        return new Color(r, g, b);
    }


    private synchronized static Random obterAleatorio() {
        if (aleatorio == null) {
            aleatorio = new Random();
        }
        return aleatorio;
    }
}
