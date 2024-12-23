package dao;

import database.DatabaseManager;
import models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO {
// Esta classe possui os métodos dos usuarios
    // Adiciona um novo usuário
    public void adicionarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nome, telefone, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getTelefone());
            stmt.setString(3, usuario.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar Usuario: " + e.getMessage());
        }
    }

    // Retorna uma lista de todos os usuários
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        try (Connection conn = DatabaseManager.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    // Remove um usuário pelo ID
    public void removerUsuario(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O nome do usuário não pode estar vazio.");
            return;
        }

        String sqlSelect = "SELECT id FROM usuarios WHERE nome = ?";
        String sqlDelete = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseManager.connect(); PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {

            stmtSelect.setString(1, nome);
            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");

                try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {
                    stmtDelete.setInt(1, id);
                    stmtDelete.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao remover usuário: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Atualiza as informações de um usuário existente
    public void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuarios SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getTelefone());
            stmt.setString(3, usuario.getEmail());
            stmt.setInt(4, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }
//lista todos os usuarios disponíveis
    public List<Usuario> listarUsuariosDisponiveis() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DatabaseManager.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar livros disponíveis: " + e.getMessage());
        }
        return usuarios;
    }
}
