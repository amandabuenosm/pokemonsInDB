package consultas;

import java.sql.*;

public class InsercoesPokemons {
    private static final String link = "jdbc:mysql://localhost:3306/bancopokemon"; // endereço do banco de dados
    private static final String admin = "root"; // usuário permitido
    private static final String senha = "unesc"; // senha permitido

    public static void main(String[] args) { // main principal
        // chama o método para inserir os pokémons nas tabelas conforme seus tipos
        atualizar("fogo", "tb_pokemonFogo", "id_pFogo", "pokemonFogo", "tipoF");
        atualizar("voador", "tb_pokemonVoador", "id_pVoador", "pokemonVoador", "tipoV");
        atualizar("eletrico", "tb_pokemonEletrico", "id_pEletrico", "pokemonEletrico", "tipoE");

        // chama método para atualizar totais e inserir na tabela de totalizadores
        TotalPokemons.atualizarTotal();  

        // chama método para apagar pokémons repetidos da tabela principal e inserir esses deletados na própria tabela
        TotalDeletados.deletarRepetidos(args);
    }

    // método que consulta e atualiza a tabela conforme o tipo de pokémon
    private static void atualizar(String tipo, String tabDest, String IDcoluna, String pokemonColuna, String tipoColuna) {
        System.out.println("POKÉMONS DO TIPO " + tipo + ":");

        String sqlSelect = "SELECT id, pokemon, tipo FROM tb_pokemon WHERE tipo = ?";
        String sqlDelete = "DELETE FROM " + tabDest;
        String sqlInsert = "INSERT INTO " + tabDest + " (" + IDcoluna +", " + pokemonColuna +", " + tipoColuna +") VALUES (?, ?, ?)";

        try (Connection conexao = DriverManager.getConnection(link, admin, senha)) {
            excluirDados(conexao, sqlDelete); // executar as exclusões dos dados
            inserirDados(conexao, sqlSelect, sqlInsert, tipo); // inserir novos dados
        } catch (SQLException e) { // exibir mensagem de erro caso ocorra
            System.out.println("ERRO NA CONEXÃO COM O BANCO DE DADOS: " +e.getMessage());
        }
    }

    // método para executar exclusão de dados antigos da tabela de destino 
    private static void excluirDados(Connection conexao, String sqlDelete) {
        try (PreparedStatement stmtDelete = conexao.prepareStatement(sqlDelete)) {
	    stmtDelete.executeUpdate(); // executa a exclusão dos dados antigos
	    } catch (SQLException e) { // exibir mensagem de erro caso tenha falha na exclusão
	    System.out.println("ERRO AO EXCLUIR DADOS: " + e.getMessage());
	    }
    }

    // método para inserir novos dados na tabela de destino
    private static void inserirDados(Connection conexao, String sqlSelect, String sqlInsert, String tipo) {
        try (PreparedStatement stmtSelect = conexao.prepareStatement(sqlSelect);
             PreparedStatement stmtInsert = conexao.prepareStatement(sqlInsert)) {

             stmtSelect.setString(1, tipo); // define o tipo de pokémon a ser selecionado
             ResultSet rs = stmtSelect.executeQuery(); // executa a consulta da seleção

             // repetir instruções sobre o resultado da consulta
             while(rs.next()) {
                 int id = rs.getInt("id"); // obtém ID do pokémon
                 String pokemon = rs.getString("pokemon"); // obtém o nome do pokémon
                 String tipopokemon = rs.getString("tipo"); // obtém o tipo do pokémon

                 // definir os valores para começar a inserção
                 stmtInsert.setInt(1, id);
                 stmtInsert.setString(2, pokemon);
                 stmtInsert.setString(3, tipopokemon);

                 stmtInsert.executeUpdate(); // executar a inserção dos dados
                 System.out.println("- Pokémon " + pokemon + " foi inserido!");
             }
        } catch (SQLException e) { // exibir mensagem de erro de falha na inserção
            System.out.println("ERRO NA INSERÇÃO: " + e.getMessage());
            System.out.println("----------------------------------------");
        }
    } 
}
