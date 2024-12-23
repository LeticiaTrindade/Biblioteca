package dao;

import database.DatabaseManager;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Emprestimo;

public class EmprestimoDAO {
// Esta classe possui os métodos dos emprestimos
    
    // Adiciona um novo empréstimo
    public void adicionarEmprestimo(Emprestimo emprestimo) {
        if (!isLivroDisponivel(emprestimo.getIdLivro())) {
             JOptionPane.showMessageDialog(null, "O livro não está disponível.");
            return;
        }

        if (!isUsuarioValido(emprestimo.getIdUsuario())) {
           JOptionPane.showMessageDialog(null, "O usuário não é válido.");
            return;
        }
        if (!isDataValida(emprestimo.getDataEmprestimo(), emprestimo.getDataDevolucao())) {
            JOptionPane.showMessageDialog(null, "Data de devolução não pode ser anterior à data de empréstimo.");
            return;
        }

        String sql = "INSERT INTO emprestimos (id_livro, id_usuario, data_emprestimo, data_devolucao, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getIdLivro());
            stmt.setInt(2, emprestimo.getIdUsuario());
            stmt.setString(3, emprestimo.getDataEmprestimo());
            stmt.setString(4, emprestimo.getDataDevolucao());
            stmt.setString(5, emprestimo.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar Emprestimo: " + e.getMessage());
        }
    }

// Verifica se o livro escolhido esta disponível
    private boolean isLivroDisponivel(int idLivro) {
        String sql = "SELECT quantidade FROM livros WHERE id = ? AND quantidade > 0";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLivro);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar disponibilidade do livro: " + e.getMessage());
        }
        return false;
    }
//Verifica se o usuário escolhido está disponível
    private boolean isUsuarioValido(int idUsuario) {
        String sql = "SELECT id FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar validade do usuário: " + e.getMessage());
        }
        return false;
    }

    // Retorna uma lista de todos os empréstimos
    public List<Emprestimo> listarEmprestimos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";
        try (Connection conn = DatabaseManager.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        rs.getInt("id"),
                        rs.getInt("id_livro"),
                        rs.getInt("id_usuario"),
                        rs.getString("data_emprestimo"),
                        rs.getString("dataDevolucao"),
                        rs.getString("status")
                );
                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar empréstimos: " + e.getMessage());
        }
        return emprestimos;
    }

    // Remove um empréstimo pelo ID
    public void removerEmprestimo(int id) {
        String sql = "DELETE FROM emprestimos WHERE id = ?";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover Emprestimo: " + e.getMessage());
        }
    }

    // Atualiza as informações de um empréstimo existente
    public void atualizarEmprestimo(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimos SET id_livro = ?, id_usuario = ?, data_emprestimo = ?, data_devolucao = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getIdLivro());
            stmt.setInt(2, emprestimo.getIdUsuario());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo())); // Ajusta para o formato adequado de data
            stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucao())); // Ajusta para o formato adequado de data
            stmt.setString(5, emprestimo.getStatus());
            stmt.setInt(6, emprestimo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Erro ao atualizar empréstimo: " + e.getMessage());
        }
    }
    //Atualiza o status do emprestimo pelo id
    public void atualizarEmprestimoStatus(int idEmprestimo, String novoStatus) {
    String sql = "UPDATE emprestimos SET status = ? WHERE id = ?";
    try (Connection conn = DatabaseManager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, novoStatus);
        stmt.setInt(2, idEmprestimo);
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Status do empréstimo atualizado com sucesso!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar status do empréstimo: " + e.getMessage());
    }
}
    //Lista todos os emprestimos
    public List<Emprestimo> listarEmprestimosDisponiveis() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT emprestimos.id, emprestimos.id_livro, emprestimos.id_usuario, emprestimos.data_emprestimo, emprestimos.data_devolucao, emprestimos.status "
                + "FROM emprestimos "
                + "JOIN livros ON emprestimos.id_livro = livros.id "
                + "JOIN usuarios ON emprestimos.id_usuario = usuarios.id";
        try (Connection conn = DatabaseManager.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        rs.getInt("id"),
                        rs.getInt("id_livro"),
                        rs.getInt("id_usuario"),
                        rs.getString("data_emprestimo"),
                        rs.getString("data_devolucao"),
                        rs.getString("status")
                );
                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar empréstimos2: " + e.getMessage());
        }
        return emprestimos;
    }
    //Verifica se as datas escolhidas são válidas
    private boolean isDataValida(String dataEmprestimo, String dataDevolucao) {
        try {
            LocalDate emprestimo = LocalDate.parse(dataEmprestimo);
            LocalDate devolucao = LocalDate.parse(dataDevolucao);
            return !devolucao.isBefore(emprestimo);
        } catch (DateTimeParseException e) {
           JOptionPane.showMessageDialog(null, "Formato de data inválido: " + e.getMessage());
            return false;
        }
    }

}
