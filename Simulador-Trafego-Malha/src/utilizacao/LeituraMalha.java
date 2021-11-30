package utilizacao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Robson de Jesus
 */
public class LeituraMalha {

    private int[][] malhaMatriz;
    private String arquivoCaminho;

    public LeituraMalha(String caminho) throws FileNotFoundException, Exception {
        this.arquivoCaminho = caminho;
        leituraArquivo();
    }

    public int[][] obterMatriz() throws Exception {
        if (malhaMatriz == null) {
            throw new Exception("ERRO NA INICIALIZAÇÃO DA MATRIZ");
        }
        return malhaMatriz;
    }

    private void leituraArquivo() throws FileNotFoundException, Exception {
        Scanner entrada = new Scanner(new File(arquivoCaminho));

        try {
            int linha = Integer.parseInt(entrada.nextLine());
            int coluna = Integer.parseInt(entrada.nextLine());
            malhaMatriz = new int[coluna][linha];
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERRO PROBLEMA NA LEITURA DO ARQUIVO, POSSÍVEL PROBLEMA NO FORMATO.");
        }

        int linhaIndex = 0;

        while (entrada.hasNext()) {
            String l = entrada.nextLine();
            String[] lv = l.split("	");

            for (int i = 0; i < lv.length; i++) {
                malhaMatriz[i][linhaIndex] = Integer.parseInt(lv[i]);
            }
            linhaIndex++;
        }

        entrada.close();
    }

}
