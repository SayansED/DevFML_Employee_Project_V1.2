package sqlite;

import java.sql.SQLException;
import java.sql.Statement;

import sqlite.Conexao;

public class CriarBancoDeDados {

	
	// Adapatação
	//private Conexao conexao = new Conexao();

	private final Conexao conexao;

    //Construtor da classe
  	//Pq está usando uma variavel do tipo "final"
    public CriarBancoDeDados(Conexao pConexaoSQLite) {
        this.conexao = pConexaoSQLite;
    }

	//Metodo
	public void createTableFuncionarios() {

		//Codigo para crias tabela
		String sql = "CREATE TABLE IF NOT EXISTS T_FUNCIONARIOS"
				+ "("
				+ "id text NOT NULL,"
				+ "nome text NOT NULL, "
				+ "sobrenome text NOT NULL, "
				+ "dataNascimento text NOT NULL, "
				+ "email text NOT NULL, "
				+ "cargo text NOT NULL, "
				+ "salario text NOT NULL"
				+ ");";

		// Executando o sql criar tabela
		boolean conectou = false;

		try {
			//conectou = this.conexaoSQLite.conectar(); // Chama o metodo de outra classe, para conectar-se do BD
			conectou = this.conexao.conectar();
			Statement stmt = this.conexao.criarStatement(); // Estancia o Statement para usa-lo
			stmt.execute(sql);
			System.out.println("Tabela Funcionários criada!");
		} 
		catch (SQLException e) { //Erro na tabela
			System.out.println("Erro na criação da Tabela Funcionários");
		} finally {
			if(conectou){
				this.conexao.desconectar(); // Chama o metodo de outra classe, para desconectar-se do BD
			}
		}

	}

	public void createTableCargos() {
		String sql = "CREATE TABLE IF NOT EXISTS T_CARGOS"
				+ "("
				+ "id text NOT NULL,"
				+ "nome text NOT NULL"
				+ ");";

		boolean conectou = false;

		try {
			conectou = this.conexao.conectar();
			Statement stmt = this.conexao.criarStatement();
			stmt.execute(sql);
			System.out.println("Tabela Cargos criada!");
		} catch (SQLException ex) {
			// TODO: handle exception
			System.out.println("Erro na criação da Tabela Cargos");
		} finally {
			if(conectou) {
				this.conexao.desconectar();
			}
		}
	}

}
