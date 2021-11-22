/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Robson de Jesus
 */
public class LerArquivoMatrix {

    private int[][] matrix;
    private String caminho;

    public LerArquivoMatrix(String caminho) throws FileNotFoundException, Exception {
        this.caminho = caminho;
        lerAquivo();
    }

    public int[][] getMatrix() throws Exception {
        if (matrix == null) {
            throw new Exception("Matrix não Intaciada");
        }
        return matrix;
    }

    private void lerAquivo() throws FileNotFoundException, Exception {
        Scanner scanner = new Scanner(new File(caminho));

        try {
            int linha = Integer.parseInt(scanner.nextLine());
            int coluna = Integer.parseInt(scanner.nextLine());
            matrix = new int[coluna][linha];
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Arquivo Sem Formatação Adequada");
        }

        int linhaIndex = 0;

        while (scanner.hasNext()) {
            String l = scanner.nextLine();
            String[] lv = l.split("	");

            for (int i = 0; i < lv.length; i++) {
                matrix[i][linhaIndex] = Integer.parseInt(lv[i]);
            }
            linhaIndex++;
        }

        scanner.close();
    }

}

