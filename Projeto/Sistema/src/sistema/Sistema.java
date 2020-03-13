package sistema;

import javax.swing.JFrame;
import javax.swing.JPanel;
import sistema.telas.*;
import sqlite.Conexao;
import sqlite.CriarBancoDeDados;

public class Sistema{

	/* Definimos uma variável do tipo JPanel, que armazenará a tela atual do nosso sistema. 
	Será do tipo estática, pois vamos utilizá-la em vários momentos. */
	public static JPanel tela;
	/* Definimos uma variável do tipo JFrame, que será a janela da nossa aplicação. 
	Será do tipo estática, pois vamos utilizá-la em vários momentos */
	public static JFrame frame;

	public static void main(String[] args){
		criarComponentes();
	}

	private static void criarComponentes(){
		/* Instanciamos o objeto JFrame e definimos algumas configurações */
		frame = new JFrame("Sistema");
		frame.setSize(700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		// Comando Temporario
		/*tela = new Login();
		tela.setVisible(true);
		frame.add(tela);

		frame.setVisible(true);*/

		Conexao conexao = new Conexao();
		conexao.conectar();
		CriarBancoDeDados criarBancoDeDados = new CriarBancoDeDados(conexao);
		criarBancoDeDados.createTableFuncionarios();
		criarBancoDeDados.createTableCargos();
		conexao.desconectar();

		// Abrir 01
		Navegador.login();
	}
}