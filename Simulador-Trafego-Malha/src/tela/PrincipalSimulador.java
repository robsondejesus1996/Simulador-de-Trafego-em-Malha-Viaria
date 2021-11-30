package tela;

import tela.Configuracoes;
import java.awt.EventQueue;

/**
 *
 * @author Robson de Jesus
 */
public class PrincipalSimulador {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Configuracoes().setVisible(true);
        });
    }
}
