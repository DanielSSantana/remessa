package model;

import java.io.IOException;
import java.sql.*;

public class Conexao {
	public static Connection criarConexao() throws IOException{
		Arquivo arq = new Arquivo();
		
		//String url = "jdbc:postgresql://127.0.0.1:5432/unico?user=postgres&password=postgres";
		String url = "jdbc:postgresql://" + arq.lerArquivo() + ":5432/unico?user=postgres&password=postgres";
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conexao;		
		//Classe que cria a conexão e retorna a conexao
	}	
	
	public static ResultSet selectSql(String sql) throws IOException {
		Connection conexao = Conexao.criarConexao();
		
		//Classe que recebe uma query na variavel sql e retorna o resultado
		
		Statement ps = null;
		try {
			ps = conexao.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet resultado = null;
		try {
			resultado = ps.executeQuery(sql);
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public static  void executaAtaulizacao(String sql) throws IOException, SQLException
	{
		Connection conexao = Conexao.criarConexao();
		
		Statement ps = null;
		try {
			ps = conexao.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ps.executeUpdate(sql);
	}
	
	
}
