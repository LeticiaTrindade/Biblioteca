package gui;

import javax.swing.*;
import java.awt.*;
import database.DatabaseManager;

public class MainMenu extends JFrame {
//Este é o nosso menu principal
    public MainMenu() {
        setTitle("Sistema de Biblioteca");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com gradiente
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 200, 220); // Rosa claro
                Color color2 = Color.WHITE;
                GradientPaint gradiente = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradiente);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Sistema de Biblioteca", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(new Color(200, 80, 100)); // Rosa escuro
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel central com botões
        JPanel panelBotoes = new JPanel(new GridLayout(5, 1, 10, 10));
        panelBotoes.setOpaque(false); // Transparente para mostrar o gradiente
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnLivros = criarBotao("Gerenciar Livros", "°");
        JButton btnUsuarios = criarBotao("Gerenciar Usuários", "°");
        JButton btnEmprestimos = criarBotao("Registrar Empréstimos/Devoluções", "°");
        JButton btnRelatorios = criarBotao("Visualizar Relatórios", "°");
        JButton btnLimparBancoDeDados = criarBotao("Limpar Banco de Dados", "°");

        // Adiciona botões ao painel
        panelBotoes.add(btnLivros);
        panelBotoes.add(btnUsuarios);
        panelBotoes.add(btnEmprestimos);
        panelBotoes.add(btnRelatorios);
        panelBotoes.add(btnLimparBancoDeDados);

        panelPrincipal.add(panelBotoes, BorderLayout.CENTER);

        // Rodapé com informações
        JPanel panelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelRodape.setOpaque(false); // Transparente
        JLabel insta = new JLabel("Instagram: @leticiatrindadet");
        insta.setForeground(new Color(200, 80, 100));
        JLabel youtube = new JLabel("YouTube: @leticiatrindadet");
        youtube.setForeground(new Color(200, 80, 100));
        panelRodape.add(insta);
        panelRodape.add(youtube);
        panelPrincipal.add(panelRodape, BorderLayout.SOUTH);

        // Adiciona ações aos botões
        btnLivros.addActionListener(e -> abrirTela(new LivroGUI()));
        btnUsuarios.addActionListener(e -> abrirTela(new UsuarioGUI()));
        btnEmprestimos.addActionListener(e -> abrirTela(new EmprestimoGUI()));
        btnRelatorios.addActionListener(e -> abrirTela(new RelatoriosGUI()));
        btnLimparBancoDeDados.addActionListener(e -> {
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.limparBancoDeDados();
        });

        add(panelPrincipal);
        setVisible(true);
    }

    private JButton criarBotao(String texto, String icone) {
        JButton botao = new JButton(icone + " " + texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(255, 105, 180)); // Rosa forte
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(new Color(255, 150, 200), 2)); // Borda rosa claro
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(200, 50));
        return botao;
    }

    private void abrirTela(JFrame tela) {
        tela.setVisible(true);
    }
}
