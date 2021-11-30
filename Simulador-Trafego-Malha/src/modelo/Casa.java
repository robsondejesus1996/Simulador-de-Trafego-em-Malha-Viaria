package modelo;

import controle.Controle;
import strategy.Movimentacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Robson de Jesus
 */
public abstract class Casa implements InterfaceCasa {

    protected InterfaceCarro carro;
    protected int coluna, linha;
    protected int valor;
    protected Random random;
    protected List<Movimentacao> movimentacoes;

    public Casa(int valor, int coluna, int linha) {
        this.movimentacoes = new ArrayList<>();
        this.valor = valor;
        this.coluna = coluna;
        this.linha = linha;
    }

    @Override
    public void adicionarCaminho(Movimentacao command) {
        movimentacoes.add(command);
    }

    @Override
    public Movimentacao obterCaminho() {

        if (movimentacoes.size() > 1) {
            return movimentacoes.get(getRandom().nextInt(movimentacoes.size()));
        } else {
            return movimentacoes.get(0);
        }
    }

    @Override
    public int obterValor() {
        return valor;
    }

    @Override
    public int obterLinha() {
        return linha;
    }

    @Override
    public int obterColuna() {
        return coluna;
    }

    @Override
    public InterfaceCarro obterCarro() {
        return carro;
    }

    @Override
    public void definirCarro(InterfaceCarro carro) {
        if (carro != null) {
            Controle.getInstance().obterControleMalha().movimentarCarro(carro.getId(), coluna, linha);
        }
        this.carro = carro;
    }

    @Override
    public void excluirCarro() {
        InterfaceCarro p = carro;
        carro = null;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + coluna;
        hash = 59 * hash + linha;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Casa other = (Casa) obj;
        if (this.coluna != other.coluna) {
            return false;
        }
        return this.linha == other.linha;
    }

    @Override
    public String toString() {
        return "Casa{" + "coluna=" + coluna + ", linha=" + linha + ", valor=" + valor + '}';
    }

    private Random getRandom() {
        if (random == null) {
            random = new Random();
        }

        return random;
    }

}
