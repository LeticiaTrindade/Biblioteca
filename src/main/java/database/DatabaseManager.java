package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DatabaseManager {
//esta classe configura nosso banco de dados
    private static final String DB_URL = "jdbc:sqlite:biblioteca.db";
//fazendo a conexão do banco de dados
    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
            return null;
        }
    }
//exibe os dados no console
    public static void exibirDados(String tabela) {
        String sql = "SELECT * FROM " + tabela;
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            System.out.println("--- " + tabela + " ---");

            while (rs.next()) {
                int colunas = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= colunas; i++) {
                    System.out.print(rs.getMetaData().getColumnName(i) + ": " + rs.getString(i) + " | ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao exibir dados: " + e.getMessage());
        }
    }
//cria as tabelas
    public static void initializeDatabase() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {

            // Tabela livros
            String createLivrosTable = """
                CREATE TABLE IF NOT EXISTS livros (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo TEXT NOT NULL,
                    autor TEXT NOT NULL,
                    genero TEXT,
                    quantidade INTEGER NOT NULL
                );
            """;

            // Tabela usuarios
            String createUsuariosTable = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    telefone TEXT,
                    email TEXT
                );
            """;

            // Tabela emprestimos
            String createEmprestimosTable = """
                CREATE TABLE IF NOT EXISTS emprestimos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    id_livro INTEGER NOT NULL,
                    id_usuario INTEGER NOT NULL,
                    data_emprestimo TEXT,
                    data_devolucao TEXT,
                    status TEXT,
                    FOREIGN KEY (id_livro) REFERENCES livros(id),
                    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
                );
            """;

            stmt.execute(createLivrosTable);
            stmt.execute(createUsuariosTable);
            stmt.execute(createEmprestimosTable);

            System.out.println("Banco de dados inicializado.");
        } catch (SQLException e) {
            System.out.println("Erro ao inicializar o banco: " + e.getMessage());
        }
    }
//limpa os dados do banco de dados
    public void limparBancoDeDados() {
        String[] sqls = {
            "DELETE FROM emprestimos",
            "DELETE FROM usuarios",
            "DELETE FROM livros"
        };

        try (Connection conn = DatabaseManager.connect()) {
            for (String sql : sqls) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(null, "Banco de dados limpo com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao limpar banco de dados: " + e.getMessage());
        }
    }

}
