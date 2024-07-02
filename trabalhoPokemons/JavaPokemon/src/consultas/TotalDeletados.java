package consultas;

import java.sql.*;
import java.util.ArrayList;

public class TotalDeletados extends InsercoesPokemons {

    public static void deletarRepetidos(String[] args) {
        String link = "jdbc:mysql://localhost:3306/bancopokemon";
        String admin = "root";
        String senha = "unesc";

        // testa a conexão ao banco de dados
        try { Connection atualDeletados = DriverManager.getConnection(link, admin, senha);

            // consulta para localizar duplicatas
            String verDuplicatas = "SELECT p1.id, p1.pokemon, p1.tipo FROM tb_pokemon p1 " +
                                   "INNER JOIN tb_pokemon p2 ON p1.pokemon = p2.pokemon " +
                                   "AND p1.tipo = p2.tipo WHERE p1.id > p2.id";

            // executar a consulta de localização de duplicatas                       
            Statement stmt = atualDeletados.createStatement();
            ResultSet rs = stmt.executeQuery(verDuplicatas);

            // inserção de duplicatas na tabela de deletados (tabela própria)
            String inserirDeletados = "INSERT INTO tb_pokemon_deletados (id, pokemon, tipo) VALUES (?, ?, ?)";

            // função a ser executada para inserir os deletados, dentro da condição
            PreparedStatement insertDel = atualDeletados.prepareStatement(inserirDeletados);

            while (rs.next()) { // condições sobre resultados da consulta
                int id = rs.getInt("id");
                String pokemon = rs.getString("pokemon");
                String tipo = rs.getString("tipo");

                insertDel.setInt(1, id);
                insertDel.setString(2, pokemon);
                insertDel.setString(3, tipo);
                insertDel.executeUpdate(); // inserir deletados na tabela própria
            }

            // consulta para localizar os IDs das duplicatas
            String totalDuplicadas = "SELECT p1.id FROM tb_pokemon p1 " +
                                     "INNER JOIN tb_pokemon p2 ON p1.pokemon = p2.pokemon " +
                                     "AND p1.tipo = p2.tipo WHERE p1.id > p2.id";
            
            // executar a consulta de localização de IDs de duplicatas
            Statement stmtDel = atualDeletados.createStatement();
            ResultSet rsDel = stmtDel.executeQuery(totalDuplicadas);

            // lista de armazenamento de IDs de duplicatas
            ArrayList<Integer> listaID = new ArrayList<Integer>();

            // condição sobre os resultados da consulta
            while (rsDel.next()) {
                listaID.add(rsDel.getInt("id"));
            }

            // condição sobre a lista de IDs
            for (Integer i : listaID) {
                // excluir duplicatas da tabela principal
                String excluirDuplicados = "DELETE FROM bancopokemon.tb_pokemon WHERE id = "+i;
                stmtDel.executeUpdate(excluirDuplicados);
            } atualDeletados.close(); // fechar a conexão

            System.out.println("-----------------------------------------------------");
            System.out.println("Duplicatas excluídas da tabela principal com sucesso!");
            System.out.println("Deletados da tabela principal inseridos na tabela própria com sucesso!");
            System.out.println("----------------------------------------------------------------------");
        } catch (SQLException e) { // caso haja erros de execução das instruções no banco
            System.out.println("ERRO AO EXECUTAR INSERÇÃO/EXCLUSÃO DE DELETADOS: " + e.getMessage());
        }
    }
}