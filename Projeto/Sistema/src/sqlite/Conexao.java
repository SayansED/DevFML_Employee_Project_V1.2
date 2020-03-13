package sqlite;

import java.sql.*;

public class Conexao {

	private Connection conexao;

	//Conectar
	public boolean conectar() {
		try {
			String url = "jdbc:sqlite:BancoDeDados/SQLiteSistema.db";
			this.conexao = DriverManager.getConnection(url);
			System.out.println("Criou Banco com sucesso");
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		//JOptionPane.showMessageDialog(null, "Conectou");
		System.out.println("Conectou");
		return true;
	}

	//Desconectar
	public boolean desconectar() {

		try {
			if (this.conexao.isClosed() == false) {
				this.conexao.close();
			}

		} 
		catch (SQLException e) {

			System.err.println(e.getMessage());
			return false;
		}
		System.out.println("Desconectou");
		return true;
	}

	//Statement lê as string e concatena para codigo sql
	//Criar Statement para o sql ser execuado
	//Em outras palavras, o Statement é o responsável por executar os teus códigos sql no banco de dados
	public Statement criarStatement() {
		try {
			return this.conexao.createStatement();
		} 
		catch (SQLException e) {
			return null;
		}
	}

	// Metodo que prepara os parametros do sql e separa eles 
	public PreparedStatement criarPreparedStatement(String sql) {
		try {
			return this.conexao.prepareStatement(sql);
		} 
		catch (SQLException e) {
			return null;
		}
	}

	public Connection getConexao() {
		return this.conexao;
	}
}