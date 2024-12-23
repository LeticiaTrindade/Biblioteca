package com.mycompany.biblioteca;

import database.DatabaseManager;
import gui.MainMenu;

public class Biblioteca {

    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        new MainMenu();
        DatabaseManager.exibirDados("livros");
        DatabaseManager.exibirDados("usuarios");
        DatabaseManager.exibirDados("emprestimos");
    }

}
