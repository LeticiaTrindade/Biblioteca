package gui;
        
import javax.swing.*;
import java.awt.*;
import dao.EmprestimoDAO;
import dao.LivroDAO;
import dao.UsuarioDAO;
import models.Emprestimo;
import models.Livro;
import models.Usuario;
import java.util.List;

public class RelatoriosGUI extends JFrame {
//esta classe é o GUI dos relatórios
    
    private JTextArea textArea;
    private JButton btnLivrosDisponiveis;
    private JButton btnEmprestimosAtivos;
    private JButton btnUsuariosAtivos;

    public RelatoriosGUI() {
        setTitle("Relatórios");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuração do painel principal com gradiente
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

        // Área de texto para exibição de relatórios
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(new Color(150, 75, 100)); // Rosa escuro
        panelPrincipal.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Painel inferior com botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotoes.setOpaque(false); // Transparente para mostrar o gradiente

        btnLivrosDisponiveis = criarBotao("Livros Disponíveis");
        btnEmprestimosAtivos = criarBotao("Empréstimos Ativos");
        btnUsuariosAtivos = criarBotao("Usuários Ativos");

        panelBotoes.add(btnLivrosDisponiveis);
        panelBotoes.add(btnEmprestimosAtivos);
        panelBotoes.add(btnUsuariosAtivos);
        panelPrincipal.add(panelBotoes, BorderLayout.SOUTH);

        // Adiciona ações aos botões
        btnLivrosDisponiveis.addActionListener(e -> exibirLivrosDisponiveis());
        btnEmprestimosAtivos.addActionListener(e -> exibirEmprestimosAtivos());
        btnUsuariosAtivos.addActionListener(e -> exibirUsuariosAtivos());

        add(panelPrincipal);
        setVisible(true);
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(new Color(255, 105, 180)); // Rosa forte
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(new Color(255, 150, 200), 2)); // Borda rosa claro
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(180, 40));
        return botao;
    }

    private void exibirLivrosDisponiveis() {
        LivroDAO livroDAO = new LivroDAO();
        List<Livro> livros = livroDAO.listarLivrosDisponiveis();
        StringBuilder relatorio = new StringBuilder("Livros Disponíveis:\n");
        for (Livro livro : livros) {
            relatorio.append("ID: ").append(livro.getId())
                    .append(", Título: ").append(livro.getTitulo())
                    .append(", Autor: ").append(livro.getAutor())
                    .append(", Quantidade: ").append(livro.getQuantidade())
                    .append("\n");
        }
        textArea.setText(relatorio.toString());
    }

    private void exibirEmprestimosAtivos() {
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        List<Emprestimo> emprestimos = emprestimoDAO.listarEmprestimosDisponiveis();
        StringBuilder relatorio = new StringBuilder("Empréstimos Ativos:\n");
        for (Emprestimo emprestimo : emprestimos) {
            relatorio.append("ID: ").append(emprestimo.getId())
                    .append(", Livro ID: ").append(emprestimo.getIdLivro())
                    .append(", Usuário ID: ").append(emprestimo.getIdUsuario())
                    .append(", Data Empréstimo: ").append(emprestimo.getDataEmprestimo())
                    .append(", Data Devolução: ").append(emprestimo.getDataDevolucao())
                    .append(", Status: ").append(emprestimo.getStatus())
                    .append("\n");
        }
        textArea.setText(relatorio.toString());
    }

    private void exibirUsuariosAtivos() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.listarUsuariosDisponiveis();
        StringBuilder relatorio = new StringBuilder("Usuários Disponíveis:\n");
        for (Usuario usuario : usuarios) {
            relatorio.append("ID: ").append(usuario.getId())
                    .append(", Nome: ").append(usuario.getNome())
                    .append(", Telefone: ").append(usuario.getTelefone())
                    .append(", Email: ").append(usuario.getEmail())
                    .append("\n");
        }
        textArea.setText(relatorio.toString());
    }
}
