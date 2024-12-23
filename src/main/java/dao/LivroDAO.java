package dao;

import database.DatabaseManager;
import models.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LivroDAO {
// Esta classe possui os métodos dos livros
    
    //adiciona um novo livro
    public void adicionarLivro(Livro livro) {
        if (!isLivroValido(livro)) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            return;
        }

        String sql = "INSERT INTO livros (titulo, autor, genero, quantidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setInt(4, livro.getQuantidade());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar livro: " + e.getMessage());
        }
    }
//Lista todos os livros
    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";
        try (Connection conn = DatabaseManager.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getInt("quantidade")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar livros: " + e.getMessage());
        }
        return livros;
    }
//remove um livro pelo titulo
    public void removerLivro(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O título do livro não pode estar vazio.");
            return;
        }

        String sqlSelect = "SELECT id, quantidade FROM livros WHERE titulo = ?";
        String sqlDelete = "DELETE FROM livros WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
                PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {

            stmtSelect.setString(1, titulo);
            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int quantidade = rs.getInt("quantidade");

                if (quantidade > 1) {
                    try (PreparedStatement stmtUpdate = conn.prepareStatement(
                            "UPDATE livros SET quantidade = quantidade - 1 WHERE id = ?")) {
                        stmtUpdate.setInt(1, id);
                        stmtUpdate.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Quantidade reduzida para 1 livro.");
                    }
                } else {
                    try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {
                        stmtDelete.setInt(1, id);
                        stmtDelete.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Livro removido com sucesso!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Livro com título '" + titulo + "' não encontrado.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover livro: " + e.getMessage());
        }
    }
//atualiza o livro
  public void atualizarLivro(Livro livro) {
    String sql = "UPDATE livros SET titulo = ?, autor = ?, genero = ?, quantidade = ? WHERE id = ?";
    try (Connection conn = DatabaseManager.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, livro.getTitulo());
        stmt.setString(2, livro.getAutor());
        stmt.setString(3, livro.getGenero());
        stmt.setInt(4, livro.getQuantidade());
        stmt.setInt(5, livro.getId());
        stmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar livro: " + e.getMessage());
    }
}
//lista todos os livros disponíveis
    public List<Livro> listarLivrosDisponiveis() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE quantidade > 0";
        try (Connection conn = DatabaseManager.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getInt("quantidade")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar livros disponíveis: " + e.getMessage());
        }
        return livros;
    }
//verifica se o livro escolhido é válido
    private boolean isLivroValido(Livro livro) {
        return livro != null &&
                livro.getTitulo() != null && !livro.getTitulo().trim().isEmpty() &&
                livro.getAutor() != null && !livro.getAutor().trim().isEmpty() &&
                livro.getGenero() != null && !livro.getGenero().trim().isEmpty() &&
                livro.getQuantidade() >= 0;
    }
}
