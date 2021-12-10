package tela;

import controle.Controle;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.logging.Logger;

/**
 *
 * @author Robson de Jesus
 */
public class Configuracoes extends JFrame {

    private String caminho;
    private Dimension tamanho;
    private JLabel labelArquivo;
    private JLabel labelAreaCritica;
    private JTextField textArquivo;
    private JButton botaoBuscar;
    private JButton botaoCriarMalha;
    private JButton botaoCancelar;
    private JRadioButton monitorMonitor;
    private JRadioButton monitorSemaforo;
    private ButtonGroup radioGrupo;
    private GridBagConstraints cons;
    private Controle controle;
    private Principal telaPrincipal;

    public Configuracoes() {
        this.controle = Controle.getInstance();
        inicializarFrameConfiguracao();
        inicializarComponentes();
        inicializarListeners();
    }

    public Configuracoes(Principal framePrincipal) {
        this();
        this.telaPrincipal = framePrincipal;
    }

    private void inicializarFrameConfiguracao() {
        this.setTitle("Configurações do Sistema de Trafego");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        tamanho = new Dimension(525, 210);
        this.setSize(tamanho);
        this.setPreferredSize(tamanho);
        this.setMinimumSize(tamanho);
        this.setMaximumSize(tamanho);
        setLocationRelativeTo(null);
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
            Logger.getLogger(Configuracoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        this.labelArquivo = new JLabel("Malha: ");
        this.labelAreaCritica = new JLabel("Informe o tipo de controle de visualização: ");
        this.textArquivo = new JTextField();
        this.botaoBuscar = new JButton("Caminho");
        this.botaoCancelar = new JButton("Cancelar");
        this.botaoCriarMalha = new JButton("Inserir malha");
        this.monitorMonitor = new JRadioButton("MONITOR");
        this.monitorSemaforo = new JRadioButton("SEMAFORO");
        this.radioGrupo = new ButtonGroup();
        radioGrupo.add(monitorMonitor);
        radioGrupo.add(monitorSemaforo);
        inicializarPosicao();
    }

    private void definirTamanho(JComponent c, Dimension d) {
        c.setSize(d);
        c.setMinimumSize(d);
        c.setPreferredSize(d);
    }

    private void inicializarPosicao() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        JPanel jpArquivo = new JPanel();
        contentPane.setBackground(jpArquivo.getBackground());
        tamanho = new Dimension(450, 50);
        definirTamanho(jpArquivo, tamanho);
        jpArquivo.setMaximumSize(tamanho);
        jpArquivo.setBackground(this.getBackground());
        jpArquivo.add(labelArquivo);
        tamanho = new Dimension(250, 24);
        definirTamanho(textArquivo, tamanho);
        textArquivo.setEditable(false);
        jpArquivo.add(textArquivo);
        jpArquivo.add(botaoBuscar);

        cons = new GridBagConstraints();
        cons.insets = new Insets(-10, 0, 0, 0);
        contentPane.add(jpArquivo, cons);

        JPanel p = new JPanel(new GridBagLayout());
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 2;
        p.add(labelAreaCritica, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        p.add(monitorMonitor, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 2;
        p.add(monitorSemaforo, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        p.add(botaoCriarMalha, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        p.add(botaoCancelar, cons);

        cons = new GridBagConstraints();
        cons.gridy = 1;

        cons.insets = new Insets(7, 0, 0, 0);
        contentPane.add(p, cons);
    }

    private void inicializarListeners() {
        botaoBuscar.addActionListener((e) -> botaoAbrirListeners());
        botaoCancelar.addActionListener((e) -> botaoCancelarListeners());
        botaoCriarMalha.addActionListener((e) -> botaoCriarListeners());
    }

    private void botaoAbrirListeners() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        FileFilter fileFilter = new FileNameExtensionFilter("txt", "txt");
        fileChooser.setFileFilter(fileFilter);
        int retorno = fileChooser.showOpenDialog(this);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            String absolutePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                controle.carregarArquivo(absolutePath);

                caminho = absolutePath;
                textArquivo.setText(caminho);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void botaoCancelarListeners() {
        System.exit(0);
    }

    private void botaoCriarListeners() {
        if (caminho == null || caminho.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Arquivo de Malha Não Informado");
            return;
        }
        if (radioGrupo.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "Selecione O Tipo de Controller de Regiao Critica");
            return;
        }

        if (monitorMonitor.isSelected()) {
            controle.definirFabrica("Monitor");
        } else if (monitorSemaforo.isSelected()) {
            controle.definirFabrica("Semafaro");
        }

        controle.obterControleMalha().iniciarMalha();

        if (telaPrincipal != null) {
            controle.redefinirMalhaObservadores();
            telaPrincipal.inicializarTabela();
        } else {
            EventQueue.invokeLater(() -> new Principal().setVisible(true));
        }
        this.dispose();
    }

}
