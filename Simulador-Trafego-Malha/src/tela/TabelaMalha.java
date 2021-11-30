package tela;

import controle.ControleArquivoMalha;
import controle.Controle;
import utilizacao.MapaImagens;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 *
 * @author Robson de Jesus
 */
public class TabelaMalha extends JTable implements ObservadorTabela {

    private ControleArquivoMalha controller;
    private JPanel parentPanel;
    private BufferedImage[][] orginalMalhaImages;
    private BufferedImage[][] canvas;
    private Map<Long, CarroMalha> sprites;

    public TabelaMalha(JPanel parent) {
        this.controller = Controle.getInstance().obterControleMalha();
        this.controller.anexarObservadores(this);
        Controle.getInstance().adicionarTabelaObservadores(this);
        this.parentPanel = parent;
        this.sprites = new HashMap<>();
        startBuffert();

        new Thread(() -> {
            // Game Loop: Nystrom, 2014

            long MS_PER_FRAME = 16; //  16 ms/frame = 60 FPS
            long last = System.currentTimeMillis();
            while (true) {

                long now = System.currentTimeMillis();

                if (now - last > MS_PER_FRAME) {
                    last = now;

                    paint();

                }

            }
        }).start();
    }

    public void startBuffert() {
        this.orginalMalhaImages = new BufferedImage[controller.pegarTamanhoColuna()][controller.pegarTamanhoLinha()];
        this.canvas = new BufferedImage[controller.pegarTamanhoColuna()][controller.pegarTamanhoLinha()];
        this.initializeProperties();
        initImages();

        parentPanel.repaint();
        parentPanel.revalidate();
        this.repaint();
        this.revalidate();
    }

    /**
     * Inicializa as propriedades da tabela.
     */
    private void initializeProperties() {
        this.setModel(new ManhaTableModel());
        this.setDefaultRenderer(Object.class, new BoardTableRenderer());
        this.setBackground(new Color(0, 0, 0, 0));
        this.setRowSelectionAllowed(false);
        this.setCellSelectionEnabled(true);
        this.setDragEnabled(false);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setTableHeader(null);
        this.setFillsViewportHeight(true);
        this.setOpaque(false);
        this.setShowGrid(false);
        this.setEnabled(false);
    }

    private void initImages() {
        for (int column = 0; column < controller.pegarTamanhoColuna(); column++) {
            for (int row = 0; row < controller.pegarTamanhoLinha(); row++) {
                orginalMalhaImages[column][row] = MapaImagens.obterImagem((int) controller.getCasaValue(column, row));
                canvas[column][row] = MapaImagens.obterImagem((int) controller.getCasaValue(column, row));
            }
        }
        this.repaint();
        this.revalidate();
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        Dimension size = parentPanel.getSize();
        if (size.getWidth() <= 0 || size.getHeight() <= 0) {
            return new Dimension(0, 0);
        }
        int inset = 15;
        size.height -= inset;
        size.width -= inset;
        float scaleX = (float) size.getWidth();
        float scaleY = (float) size.getHeight();
        if (scaleX > scaleY) {
            int width = (int) (scaleY / scaleX * size.getWidth());
            size = new Dimension(width, (int) size.getHeight());
        } else {
            int height = (int) (scaleX / scaleY * size.getHeight());
            size = new Dimension((int) size.getWidth(), height);
        }
        this.setRowHeight((int) size.getHeight() / this.getModel().getRowCount());
        return size;
    }

    @Override
    public void inserirCarro(long id, int color, int column, int row) {
        CarroMalha newCarro = new CarroMalha(color, column, row);
        this.sprites.put(id, newCarro);
    }

    @Override
    public void movimentarCarro(long id, int colunm, int row) {
        CarroMalha carro = this.sprites.get(id);
        carro.definirColuna(colunm);
        carro.definirLinha(row);
    }

    @Override
    public void excluirCarro(long id) {
        this.sprites.remove(id);
    }

    public void paint() {
        int width = canvas[0][0].getWidth();
        int height = canvas[0][0].getHeight();
        int type = canvas[0][0].getType();

        for (int column = 0; column < controller.pegarTamanhoColuna(); column++) {
            for (int row = 0; row < controller.pegarTamanhoLinha(); row++) {
                BufferedImage bi = new BufferedImage(width, height, type);
                Graphics2D g = bi.createGraphics();
                g.drawImage(orginalMalhaImages[column][row], 0, 0, null);
                g.dispose();

                this.canvas[column][row] = bi;
            }
        }

        List<CarroMalha> sprintesSeguret = new ArrayList<>(this.sprites.values());
        sprintesSeguret.forEach((sprite) -> {
            if (sprite.obterColuna() != -1 && sprite.obterLinha() != -1) {
                Graphics2D g = this.canvas[sprite.obterColuna()][sprite.obterLinha()].createGraphics();
                sprite.draw(g);
                g.dispose();
            }
        });

        this.repaint();
        parentPanel.repaint();
    }

    private static class BoardTableRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                BufferedImage img = (BufferedImage) value;
                AffineTransform transform = AffineTransform.getScaleInstance((float) table.getColumnModel().getColumn(column).getWidth() / img.getWidth(),
                        (float) table.getRowHeight() / img.getHeight());
                AffineTransformOp operator = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
                this.setIcon(new ImageIcon(operator.filter(img, null)));
            }
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            return this;
        }
    }

    private class ManhaTableModel extends AbstractTableModel {

        @Override
        public int getColumnCount() {
            return controller.pegarTamanhoColuna();
        }

        @Override
        public int getRowCount() {
            return controller.pegarTamanhoLinha();
        }

        @Override
        public Object getValueAt(int row, int col) {
            return canvas[col][row];
        }
    }


}
