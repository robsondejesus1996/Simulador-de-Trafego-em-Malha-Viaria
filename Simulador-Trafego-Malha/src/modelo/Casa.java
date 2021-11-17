/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import java.util.Random;
import strategy.Movimentacao;

/**
 *
 * @author Robson de Jesus
 */
public class Casa {
    
    protected ICarro carro;
    protected int colunm, row;
    protected int valor;
    protected Random random;
    protected List<Movimentacao> movimentacoes;
    
}
