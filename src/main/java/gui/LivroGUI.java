package gui;

import dao.LivroDAO;
import models.Livro;

import javax.swing.*;
import java.awt.*;

public class LivroGUI extends JFrame {
//esta classe é o GUI dos livros
    private LivroDAO livroDAO;
    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtGenero;
    private JTextField txtQuantidade;

    public LivroGUI() {
        setTitle("Gerenciamento de Livros");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        JLabel titulo = new JLabel("Gerenciamento de Livros", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(new Color(200, 80, 100)); // Rosa escuro
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel de formulário
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setOpaque(false); // Transparente para mostrar o gradiente
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        txtId = criarCampoTexto("ID");
        txtTitulo = criarCampoTexto("Título");
        txtAutor = criarCampoTexto("Autor");
        txtGenero = criarCampoTexto("Gênero");
        txtQuantidade = criarCampoTexto("Quantidade");

        panelFormulario.add(new JLabel("ID:", SwingConstants.RIGHT));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Título:", SwingConstants.RIGHT));
        panelFormulario.add(txtTitulo);
        panelFormulario.add(new JLabel("Autor:", SwingConstants.RIGHT));
        panelFormulario.add(txtAutor);
        panelFormulario.add(new JLabel("Gênero:", SwingConstants.RIGHT));
        panelFormulario.add(txtGenero);
        panelFormulario.add(new JLabel("Quantidade:", SwingConstants.RIGHT));
        panelFormulario.add(txtQuantidade);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotoes.setOpaque(false); // Transparente para mostrar o gradiente
        JButton btnAdicionar = criarBotao("Adicionar Livro", "+");
        JButton btnAtualizar = criarBotao("Atualizar Livro", "-️");
        JButton btnRemover = criarBotao("Remover Livro", "X️");

        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnRemover);

        panelPrincipal.add(panelBotoes, BorderLayout.SOUTH);

        // Adiciona ações aos botões
        livroDAO = new LivroDAO();
        btnAdicionar.addActionListener(e -> adicionarLivro());
        btnAtualizar.addActionListener(e -> atualizarLivro());
        btnRemover.addActionListener(e -> removerLivro());

        add(panelPrincipal);
        setVisible(true);
    }

    private JTextField criarCampoTexto(String dica) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 100, 150), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campo.setToolTipText(dica);
        return campo;
    }

    private JButton criarBotao(String texto, String icone) {
        JButton botao = new JButton(icone + " " + texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(255, 105, 180)); // Rosa forte
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(new Color(255, 150, 200), 2)); // Borda rosa claro
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(180, 40));
        return botao;
    }

    private void adicionarLivro() {
        try {
            Livro livro = new Livro();
            livro.setTitulo(txtTitulo.getText());
            livro.setAutor(txtAutor.getText());
            livro.setGenero(txtGenero.getText());
            livro.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            livroDAO.adicionarLivro(livro);
            JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso!");
            resetCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
        }
    }

    private void atualizarLivro() {
        try {
            Livro livro = new Livro();
            livro.setId(Integer.parseInt(txtId.getText()));
            livro.setTitulo(txtTitulo.getText());
            livro.setAutor(txtAutor.getText());
            livro.setGenero(txtGenero.getText());
            livro.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            livroDAO.atualizarLivro(livro);
            JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
            resetCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Dados inválidos!");
        }
    }

    private void removerLivro() {
        String titulo = txtTitulo.getText();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Insira o título do livro para remover.");
            return;
        }
        livroDAO.removerLivro(titulo);
        JOptionPane.showMessageDialog(this, "Livro removido com sucesso!");
        resetCampos();
    }

    private void resetCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtGenero.setText("");
        txtQuantidade.setText("");
    }
}
