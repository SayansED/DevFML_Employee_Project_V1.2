package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sistema.Navegador;
import sistema.entidades.Cargo;
import sqlite.Conexao;

public class CargosEditar extends JPanel {

	Cargo cargoAtual = new Cargo();
	JLabel labelTitulo, labelCargo;
	JTextField campoCargo;
	JButton botaoGravar;        

	public CargosEditar(Cargo cargo){
		cargoAtual = cargo;
		criarComponentes();
		criarEventos();
		Navegador.habilitaMenu();
	}

	private void criarComponentes() {
		setLayout(null);

		labelTitulo = new JLabel("Editar de Cargo", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
		labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
		campoCargo = new JTextField();
		botaoGravar = new JButton("Salvar");


		labelTitulo.setBounds(20, 20, 660, 40);
		labelCargo.setBounds(150, 120, 400, 20);
		campoCargo.setBounds(150, 140, 400, 40);
		botaoGravar.setBounds(250, 380, 200, 40); 

		add(labelTitulo);
		add(labelCargo);
		add(campoCargo);
		add(botaoGravar);

		setVisible(true);
	}

	private void criarEventos() {
		botaoGravar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Validando nome e campo
				if(campoCargo.getText().isEmpty() || campoCargo.getText().length() <= 3) {
					JOptionPane.showMessageDialog(null, "Preencha o campo corretamente", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				sqlAtualizarCargo();         
			}
		});
	}

	private void sqlAtualizarCargo() {  
		// conexão
		Conexao conexao = new Conexao();
		// instrucao SQL
		Statement statement;
		PreparedStatement preparedStatement;
		// resultados
		int resultado;

		try {
			// conectando ao banco de dados
			conexao.conectar();
			// criando a instrução SQL
			String srt = campoCargo.getText();
			String alterando = JOptionPane.showInputDialog("Digite o novo nome para ser inserido");
			String sqlUpdate = "UPDATE T_CARGOS SET nome = ? WHERE nome = ?;"; // Codigo de atualização
			preparedStatement = conexao.criarPreparedStatement(sqlUpdate);
			preparedStatement.setString(1, alterando);
			preparedStatement.setString(2, srt);
			resultado = preparedStatement.executeUpdate(); // Executando o UPDATE
			if(resultado==0) {
				JOptionPane.showMessageDialog(null, "Cargo não encontrado: " + srt, "Mensagem", JOptionPane.INFORMATION_MESSAGE);;
				return;
			}
			JOptionPane.showMessageDialog(null, "Cargo alterado com sucesso", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
			Navegador.inicio();
			conexao.desconectar();

		} catch (SQLException ew) {
			ew.printStackTrace();
		} finally {
			conexao.desconectar();
		}
	}
}
