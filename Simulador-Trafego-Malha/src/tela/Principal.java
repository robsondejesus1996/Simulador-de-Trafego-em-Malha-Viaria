package tela;

import controle.Controle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

/**
 *
 * @author Robson de Jesus
 */
public class Principal extends JFrame implements FramePrincipalObserver {

    private static Dimension sizePrefesss = new Dimension(800, 800);
    private JPanel painelConfiguracao;
    private JPanel painelTabela;
    private JButton botaoPara;
    private JButton botaoIniciar;

    private JButton botaoNovaMalha;
    private JSpinner qtdCarros;
    private JLabel labelLimiteQtdCarros;
    private JLabel labelQtdAtualSimulacaoCarros;
    private Controle controle;
    private GridBagConstraints cons;
    private TabelaMalha tabela;

    @Override
    public void notificarQtdCarros(int numCarro) {
        labelQtdAtualSimulacaoCarros.setText("" + numCarro);
    }

    @Override
    public void notificarFinalizacao() {
        botaoNovaMalha.setEnabled(true);
        botaoIniciar.setEnabled(true);
        qtdCarros.setEnabled(true);
    }

    public Principal() {
        controle = Controle.getInstance();
        inicializarPropriedades();
        inicializarComponentes();
        inicializarListeners();
        controle.adicionarObservadoresTela(this);
    }

    private void inicializarPropriedades() {
        this.setSize(sizePrefesss);
        this.setMinimumSize(sizePrefesss);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Simulação");
        this.getContentPane().setLayout(new BorderLayout(1, 2));
    }

    private void inicializarComponentes() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Dimension size;
        Container contentPane = this.getContentPane();
        this.painelConfiguracao = new JPanel();
        this.painelTabela = new JPanel();

        size = new Dimension(0, 150);
        definirTamalho(painelConfiguracao, size);
        this.painelConfiguracao.setMaximumSize(size);

        inicializarConfiguracoesComponentes();

        contentPane.add(painelConfiguracao, BorderLayout.NORTH);
        contentPane.add(painelTabela, BorderLayout.CENTER);

        painelConfiguracao.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.painelTabela.setBackground(new Color(0, 0, 0));
    }

    private void inicializarConfiguracoesComponentes() {
        Dimension size;
        size = new Dimension(700, 140);
        JPanel jPConfingII = new JPanel();
        definirTamalho(jPConfingII, size);
        jPConfingII.setMaximumSize(size);
        jPConfingII.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        this.painelConfiguracao.add(jPConfingII);
        jPConfingII.setLayout(new GridBagLayout());
        cons = new GridBagConstraints();

        JPanel jpConfingIII = new JPanel();
        jpConfingIII.setLayout(new GridBagLayout());
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        jPConfingII.add(jpConfingIII, cons);

        this.botaoPara = new JButton("Aguardar e Encerrar");
        this.botaoIniciar = new JButton("Iniciar");
        this.qtdCarros = new JSpinner();
        qtdCarros.setModel(new SpinnerNumberModel(1, 1, controle.obterControleMalha().obterQuantidadeCasasValida(), 1));
        this.labelLimiteQtdCarros = new JLabel("Informe a quantidade de carros: ");

        Insets insets = new Insets(0, 10, 0, 10);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = insets;
        jpConfingIII.add(this.labelLimiteQtdCarros, cons);
        jpConfingIII.setBorder(BorderFactory.createLineBorder(new Color(102, 102, 0)));

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = insets;
        jpConfingIII.add(this.qtdCarros, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.ipadx = 25;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = insets;
        jpConfingIII.add(this.botaoIniciar, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.ipadx = 25;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = insets;
        this.botaoPara.setEnabled(false);
        jpConfingIII.add(this.botaoPara, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.ipadx = 25;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = insets;
        botaoNovaMalha = new JButton("Importar nova malha");
        jpConfingIII.add(botaoNovaMalha, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.NONE;
        cons.anchor = GridBagConstraints.CENTER;
        jPConfingII.add(new JLabel("Quantidade de carros na simulação atualmente: "), cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.NONE;
        labelQtdAtualSimulacaoCarros = new JLabel("   ");
        jPConfingII.add(labelQtdAtualSimulacaoCarros, cons);
        inicializarTabela();
    }

    private void definirTamalho(JComponent c, Dimension d) {
        c.setSize(d);
        c.setMinimumSize(d);
        c.setPreferredSize(d);
    }

    private void inicializarListeners() {
        this.botaoIniciar.addActionListener((e) -> botaoIniciarListeners());
        this.botaoPara.addActionListener((e) -> botaopararListeners());
        this.botaoNovaMalha.addActionListener((e) -> botaoNovaMalhaListeners());
    }

    public void inicializarTabela() {
        painelTabela.removeAll();
        qtdCarros.setModel(new SpinnerNumberModel(1, 1, controle.obterControleMalha().obterQuantidadeCasasValida(), 1));
        painelTabela.setLayout(new BoxLayout(painelTabela, BoxLayout.PAGE_AXIS));
        tabela = new TabelaMalha(painelTabela);
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(tabela);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pane.setBackground(new Color(0, 0, 0, 0));
        pane.setOpaque(false);
        pane.getViewport().setOpaque(true);
        pane.setViewportBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pane.getViewport().setBackground(new Color(0, 0, 0, 0));
        painelTabela.add(pane);
        painelTabela.repaint();
        painelTabela.revalidate();
    }

    private void botaopararListeners() {
        botaoPara.setEnabled(false);
        controle.stopReaparecimento();
    }

    private void botaoIniciarListeners() {
        qtdCarros.setEnabled(false);
        botaoIniciar.setEnabled(false);
        botaoPara.setEnabled(true);
        botaoNovaMalha.setEnabled(false);

        int numeroCarro = (int) qtdCarros.getValue();
        controle.iniciarSimulacao(numeroCarro);
    }

    private void botaoNovaMalhaListeners() {
        EventQueue.invokeLater(() -> new Configuracoes(this).setVisible(true));
    }

}
