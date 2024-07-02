package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ConexaoBanco {
    public static void main(String[] args) { // main principal para testar a conexão

        String link = "jdbc:mysql://localhost:3306/bancopokemon"; // endereço do banco de dados
        String admin = "root"; // permissão do usuário
        String senha = "unesc"; // senha do usuário

        // testar conexão com o banco
        try (Connection conexao = DriverManager.getConnection(link, admin, senha)) {

            if (conexao != null) { // verifica se a conexão está correta
                System.out.println("CONEXÃO FEITA COM SUCESSO!");

                // objeto Statement para executar comandos SQL
                Statement statement = conexao.createStatement();
                String sql = "SELECT *FROM tb_pokemon";

                // executa as instruções e armazena os resultados no ResultSet
                ResultSet resultSet = statement.executeQuery(sql);
           
                // condições sobre os resultados da consulta
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String pokemon = resultSet.getString("pokemon");
                    String tipo = resultSet.getString("tipo");

                    // como este é um teste de conexão, se for estabelecida, será exibida a listagem
                    // de todos os pokémons da tabela principal
                    System.out.println("ID do pokemon: " + id);
                    System.out.println("Nome do pokemon: " + pokemon);                    
                    System.out.println("Tipo do pokemon: " + tipo);
                    System.out.println("-----------------------------------");
                }
            } else { // caso a conexão tenha falhado
               System.out.println("FALHA NA CONEXÃO!");
            }
        } catch (SQLException e) { // caso a conexão não execute nada, significa que falta algo no banco
            System.out.println("ERRO AO CONECTAR COM O BANCO DE DADOS: ");
            e.printStackTrace();
        }
    }
}