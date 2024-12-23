package gui;

import dao.UsuarioDAO;
import models.Usuario;

import javax.swing.*;
import java.awt.*;

public class UsuarioGUI extends JFrame {

    //esta classe é o GUI dos Usuários
    private UsuarioDAO usuarioDAO;
    private JTextField txtId, txtNome, txtTelefone, txtEmail;

    public UsuarioGUI() {
        setTitle("Gerenciamento de Usuários");
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
        JLabel titulo = new JLabel("Gerenciamento de Usuários", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(200, 80, 100)); // Rosa escuro
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel central para campos de entrada
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setOpaque(false);
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        txtId = criarCampoTexto("ID:");
        txtNome = criarCampoTexto("Nome:");
        txtTelefone = criarCampoTexto("Telefone:");
        txtEmail = criarCampoTexto("E-mail:");

        panelCampos.add(new JLabel("ID:", SwingConstants.RIGHT));
        panelCampos.add(txtId);
        panelCampos.add(new JLabel("Nome:", SwingConstants.RIGHT));
        panelCampos.add(txtNome);
        panelCampos.add(new JLabel("Telefone:", SwingConstants.RIGHT));
        panelCampos.add(txtTelefone);
        panelCampos.add(new JLabel("E-mail:", SwingConstants.RIGHT));
        panelCampos.add(txtEmail);

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        // Painel inferior com botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotoes.setOpaque(false);

        JButton btnAdicionar = criarBotao("Adicionar", "+");
        JButton btnAtualizar = criarBotao("Atualizar", "-️");
        JButton btnRemover = criarBotao("Remover", "X");

        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnRemover);

        panelPrincipal.add(panelBotoes, BorderLayout.SOUTH);

        // Adiciona ações aos botões
        usuarioDAO = new UsuarioDAO();

        btnAdicionar.addActionListener(e -> adicionarUsuario());
        btnAtualizar.addActionListener(e -> atualizarUsuario());
        btnRemover.addActionListener(e -> removerUsuario());

        add(panelPrincipal);
        setVisible(true);
    }

    private JTextField criarCampoTexto(String label) {
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
        botao.setPreferredSize(new Dimension(180, 40));
        return botao;
    }

    private void adicionarUsuario() {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(txtNome.getText());
            usuario.setTelefone(txtTelefone.getText());
            usuario.setEmail(txtEmail.getText());

            usuarioDAO.adicionarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuário adicionado com sucesso!");
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar usuário: " + e.getMessage());
        }
    }

    private void atualizarUsuario() {
        try {
            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(txtId.getText()));
            usuario.setNome(txtNome.getText());
            usuario.setTelefone(txtTelefone.getText());
            usuario.setEmail(txtEmail.getText());

            usuarioDAO.atualizarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    private void removerUsuario() {
        try {
            String nome = txtNome.getText();

            usuarioDAO.removerUsuario(nome);
            JOptionPane.showMessageDialog(this, "Usuário removido com sucesso!");
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover usuário: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
    }
}
