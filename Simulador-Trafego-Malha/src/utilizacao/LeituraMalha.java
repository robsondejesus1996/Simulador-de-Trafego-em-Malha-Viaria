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
            throw new Exception("Matrix não Intaciada");
        }
        return malhaMatriz;
    }

    private void leituraArquivo() throws FileNotFoundException, Exception {
        Scanner scanner = new Scanner(new File(arquivoCaminho));

        try {
            int linha = Integer.parseInt(scanner.nextLine());
            int coluna = Integer.parseInt(scanner.nextLine());
            malhaMatriz = new int[coluna][linha];
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Problema na leitura do arquivo, possivel problema no formato. ");
        }

        int linhaIndex = 0;

        while (scanner.hasNext()) {
            String l = scanner.nextLine();
            String[] lv = l.split("	");

            for (int i = 0; i < lv.length; i++) {
                malhaMatriz[i][linhaIndex] = Integer.parseInt(lv[i]);
            }
            linhaIndex++;
        }

        scanner.close();
    }

}
