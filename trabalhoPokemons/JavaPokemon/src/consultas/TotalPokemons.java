package consultas;

import java.sql.*;

public class TotalPokemons extends InsercoesPokemons { // funções a serem mostradas no main da classe InsercoesPokemons
    
    static String link = "jdbc:mysql://localhost:3306/bancopokemon"; // endereço do banco de dados 
    static String admin = "root"; // usuário permitido para acessar banco
    static String senha = "unesc"; // senha do usuário permitido

    public static void atualizarTotal() { // calcular e atualizar os totais da tabela de totalizadores
        // selecionar e contar o total de pokémons conforme seu tipo
        String sqlSelect = "SELECT tipo, COUNT(*) as total FROM tb_pokemon GROUP BY tipo";
        String sqlDelete = "DELETE FROM tb_pokemon_totalizador";
        String sqlInsert = "INSERT INTO tb_pokemon_totalizador (idtb_totalizador, total_eletrico, total_fogo, total_voador, tb_pokemon_id) VALUES (?, ?, ?, ?, ?)";

        // conexão com o banco de dados
        try(Connection conexao = DriverManager.getConnection(link, admin, senha)) {
            excluirInfo(conexao, sqlDelete); // caso haja dados cadastrados, executa a exclusão deles
            inserirTotais(conexao, sqlSelect, sqlInsert); // inserir os totais na tabela
        } catch (SQLException e) { // exibir mensagem de erro caso não conecte com o banco de dados
            System.out.println("ERRO NA CONEXÃO COM O BANCO DE DADOS: " + e.getMessage());
        }
    }

    // executar a exclusão de dados existentes na tabela totalizadora
    private static void excluirInfo(Connection conexao, String sqlDelete) {
        try (PreparedStatement stmtDelete = conexao.prepareStatement(sqlDelete)) {
            stmtDelete.executeUpdate(); // executar função de exclusão dos dados antigos
        } catch (SQLException e) { // exibir mensagem caso ocorra um erro ao excluir dados
            System.out.println("ERRO AO EXECUTAR A EXCLUSÃO DOS DADOS: " + e.getMessage());
        }
    } 

    // função de inserção dos totalizadores na tabela
    private static void inserirTotais(Connection conexao, String sqlSelect, String sqlInsert) {
        int totaleletrico = 0;
        int totalfogo = 0;
        int totalvoador = 0;

        try (PreparedStatement stmtSelect = conexao.prepareStatement(sqlSelect); 
             PreparedStatement stmtInsert = conexao.prepareStatement(sqlInsert)) {

            ResultSet rs = stmtSelect.executeQuery(); // fazer a consulta da seleção

            // repetir instruções do resultado da consulta
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int total = rs.getInt("total");

                // caso a consulta traga determinados tipos, será mostrado o total conforme os mesmos
                switch (tipo) { 
                    case "eletrico":
                        totaleletrico = total;
                        break;
                    case "fogo":
                        totalfogo = total;
                        break;
                    case "voador":
                        totalvoador = total;
                        break;
                }
            }

            // definir valores para executar a inserção
            stmtInsert.setInt(1, 1); // pode ser alterado conforme necessário
            stmtInsert.setInt(2, totaleletrico);
            stmtInsert.setInt(3, totalfogo);
            stmtInsert.setInt(4, totalvoador);
            stmtInsert.setInt(5, 1); // pode ser alterado conforme necessário

            stmtInsert.executeUpdate(); // executar as inserções dos dados
            System.out.println("------------------------------------");
            System.out.println("TOTALIZADORES INSERIDOS COM SUCESSO!");
            System.out.println("------------------------------------");
            System.out.println("Total de Pokémons Elétricos = " + totaleletrico);
            System.out.println("Total de Pokémons Fogos = " + totalfogo);
            System.out.println("Total de Pokemóns Voadores = " + totalvoador);
        } catch (SQLException e) { // exibir mensagem de erro caso haja falha na inserção
            System.out.println("ERRO NA INSERÇÃO: " + e.getMessage());
        }
    }
}