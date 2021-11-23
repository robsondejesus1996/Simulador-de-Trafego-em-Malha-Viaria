package tela;

import tela.FrameConfig;
import java.awt.EventQueue;

/**
 *
 * @author Robson de Jesus
 */
public class SimuladorTransito {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new FrameConfig().setVisible(true);
        });
    }
}
