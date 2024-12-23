package gui;

import dao.EmprestimoDAO;
import models.Emprestimo;

import javax.swing.*;
import java.awt.*;

public class EmprestimoGUI extends JFrame {
//esta classe é o GUI dos emprestimos
    private EmprestimoDAO emprestimoDAO;
    private JTextField txtIdEmprestimo, txtIdLivro, txtIdUsuario, txtDataEmprestimo, txtDataDevolucao;
    private JComboBox<String> cbStatus;
//Tela Gerencimento de Emprestimos
    public EmprestimoGUI() {
        setTitle("Gerenciamento de Empréstimos");
        setSize(700, 600);
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
        JLabel titulo = new JLabel("Gerenciamento de Empréstimos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(200, 80, 100)); // Rosa escuro
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel central para campos de entrada
        JPanel panelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        panelCampos.setOpaque(false);
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        txtIdEmprestimo = criarCampoTexto();
        txtIdLivro = criarCampoTexto();
        txtIdUsuario = criarCampoTexto();
        txtDataEmprestimo = criarCampoTexto();
        txtDataDevolucao = criarCampoTexto();
        cbStatus = new JComboBox<>(new String[]{"Emprestado", "Devolvido"});
        cbStatus.setFont(new Font("Arial", Font.PLAIN, 16));
        cbStatus.setBackground(Color.WHITE);
        cbStatus.setBorder(BorderFactory.createLineBorder(new Color(200, 100, 150), 2));

        panelCampos.add(new JLabel("ID do Empréstimo:", SwingConstants.RIGHT));
        panelCampos.add(txtIdEmprestimo);
        panelCampos.add(new JLabel("ID do Livro:", SwingConstants.RIGHT));
        panelCampos.add(txtIdLivro);
        panelCampos.add(new JLabel("ID do Usuário:", SwingConstants.RIGHT));
        panelCampos.add(txtIdUsuario);
        panelCampos.add(new JLabel("Data de Empréstimo (YYYY-MM-DD):", SwingConstants.RIGHT));
        panelCampos.add(txtDataEmprestimo);
        panelCampos.add(new JLabel("Data de Devolução (YYYY-MM-DD):", SwingConstants.RIGHT));
        panelCampos.add(txtDataDevolucao);
        panelCampos.add(new JLabel("Status:", SwingConstants.RIGHT));
        panelCampos.add(cbStatus);

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        // Painel inferior com botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotoes.setOpaque(false);

        JButton btnAdicionar = criarBotao("Registrar", "+");
        JButton btnAtualizar = criarBotao("Atualizar", "-️");
        JButton btnRemover = criarBotao("Remover", "X");

        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnRemover);

        panelPrincipal.add(panelBotoes, BorderLayout.SOUTH);

        // Adiciona ações aos botões
        emprestimoDAO = new EmprestimoDAO();

        btnAdicionar.addActionListener(e -> adicionarEmprestimo());
        btnAtualizar.addActionListener(e -> atualizarEmprestimo());
        btnRemover.addActionListener(e -> removerEmprestimo());

        add(panelPrincipal);
        setVisible(true);
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setBorder(BorderFactory.createLineBorder(new Color(200, 100, 150), 2)); // Borda rosa
        campo.setForeground(new Color(80, 50, 80)); // Texto escuro
        campo.setBackground(Color.WHITE);
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
        botao.setPreferredSize(new Dimension(200, 40));
        return botao;
    }

    private void adicionarEmprestimo() {
        try {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setIdLivro(Integer.parseInt(txtIdLivro.getText()));
            emprestimo.setIdUsuario(Integer.parseInt(txtIdUsuario.getText()));
            emprestimo.setDataEmprestimo(txtDataEmprestimo.getText());
            emprestimo.setDataDevolucao(txtDataDevolucao.getText());
            emprestimo.setStatus(cbStatus.getSelectedItem().toString());

            emprestimoDAO.adicionarEmprestimo(emprestimo);
            JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!");
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar empréstimo: " + e.getMessage());
        }
    }

    private void atualizarEmprestimo() {
        try {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setId(Integer.parseInt(txtIdEmprestimo.getText()));
            emprestimo.setStatus(cbStatus.getSelectedItem().toString());

            emprestimoDAO.atualizarEmprestimoStatus(emprestimo.getId(), emprestimo.getStatus());
            JOptionPane.showMessageDialog(this, "Status atualizado com sucesso!");
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar status: " + e.getMessage());
        }
    }

    private void removerEmprestimo() {
        try {
            int idEmprestimo = Integer.parseInt(txtIdEmprestimo.getText());

            emprestimoDAO.removerEmprestimo(idEmprestimo);
            JOptionPane.showMessageDialog(this, "Empréstimo removido com sucesso!");
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover empréstimo: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtIdEmprestimo.setText("");
        txtIdLivro.setText("");
        txtIdUsuario.setText("");
        txtDataEmprestimo.setText("");
        txtDataDevolucao.setText("");
        cbStatus.setSelectedIndex(0);
    }
}
