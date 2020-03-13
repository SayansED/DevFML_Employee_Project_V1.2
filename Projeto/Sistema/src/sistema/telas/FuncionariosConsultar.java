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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sqlite.Conexao;
import sistema.Navegador;
import sistema.entidades.Funcionario;

public class FuncionariosConsultar extends JPanel {

	JLabel labelTitulo, labelFuncionario;
	JTextField campoFuncionario;
	JButton botaoPesquisar, botaoEditar, botaoExcluir;
	DefaultListModel<Funcionario> listaFuncionariosModelo =  new DefaultListModel();
	JList<Funcionario> listaFuncionarios;

	public FuncionariosConsultar(){
		criarComponentes();
		criarEventos();
		Navegador.habilitaMenu();
	}

	public void criarComponentes() {
		setLayout(null);

		labelTitulo = new JLabel("Consulta de Funcion�rio", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelFuncionario = new JLabel("Nome do Funcion�rio", JLabel.LEFT);
		campoFuncionario = new JTextField();
		botaoPesquisar = new JButton("Pesquisar Funcion�rio");
		botaoEditar = new JButton("Editar Funcion�rio");
		//botaoEditar.setEnabled(false);
		botaoEditar.setEnabled(true);
		botaoExcluir = new JButton("Excluir Funcion�rio");
		//botaoExcluir.setEnabled(false);
		botaoExcluir.setEnabled(true);
		listaFuncionariosModelo = new DefaultListModel();
		listaFuncionarios = new JList();
		listaFuncionarios.setModel(listaFuncionariosModelo);
		listaFuncionarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		labelTitulo.setBounds(20, 20, 660, 40);
		labelFuncionario.setBounds(150, 120, 400, 20);
		campoFuncionario.setBounds(150, 140, 400, 40);
		botaoPesquisar.setBounds(560, 140, 130, 40);
		listaFuncionarios.setBounds(150, 200, 400, 240);
		botaoEditar.setBounds(560, 360, 130, 40);
		botaoExcluir.setBounds(560, 400, 130, 40);

		add(labelTitulo);
		add(labelFuncionario);
		add(campoFuncionario);
		add(listaFuncionarios);
		add(botaoPesquisar);
		add(botaoEditar);
		add(botaoExcluir);

		setVisible(true);
	}

	public void criarEventos() {

		botaoPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(campoFuncionario.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Preencha o campo", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
				sqlPesquisarFuncionarios(campoFuncionario.getText());
			}
		});

		// ERRO
		botaoEditar.addActionListener(new ActionListener() {
			String funcionarioAtual = campoFuncionario.getText();
			@Override
			public void actionPerformed(ActionEvent e) {
				Navegador.funcionariosEditar(funcionarioAtual);
			}
		});

		botaoExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Validando campo e nome
				if(campoFuncionario.getText().isEmpty() || campoFuncionario.getText().length() <= 3) {
					JOptionPane.showMessageDialog(null, "Preencha o campo corretamente", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				sqlDeletarFuncionario();
			}
		});

		/*listaFuncionarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                funcionarioAtual = listaFuncionarios.getSelectedValue();
                if(funcionarioAtual == null) {
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                } else {
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });*/
	}

	private void sqlPesquisarFuncionarios(String nome) {

		// Validando campo
		if(campoFuncionario.getText().length() <= 3) {
			JOptionPane.showMessageDialog(null, "Por favor inserir o Funcion�rio correto");
			return;
		}

		// Conex�o
		//Connection conexao;
		Conexao conexao = new Conexao();
		// Instru��o SQL
		PreparedStatement preparedStatement;
		// Resultados
		ResultSet resultado = null;

		try {
			// Criando instru��o SQL
			String sqlSelect = "SELECT id, nome, sobrenome, dataNascimento, email, cargo, salario FROM T_FUNCIONARIOS WHERE nome = ?;";
			// Conex�o - Driver
			conexao.conectar();
			preparedStatement = conexao.criarPreparedStatement(sqlSelect);
			preparedStatement.setString(1, nome);
			System.out.println("Sucesso");
			resultado = preparedStatement.executeQuery();

			if (resultado.next()) {
				// Next == true "Encontoru os dados"
				JOptionPane.showMessageDialog(null, "Consulta realizado com sucesso", "Consulta", JOptionPane.INFORMATION_MESSAGE);
				do {
					JOptionPane.showMessageDialog(null, "Funcion�rios" + "\n"
							+ "Id: " + resultado.getString("id") + "\n"
							+ "Nome: " + resultado.getString("nome") + "\n" 
							+ "Sobrenome: " + resultado.getString("sobrenome") + "\n"
							+ "Data de Nascimento: " + resultado.getString("dataNascimento") + "\n"
							+ "Email: " + resultado.getString("email") + "\n"
							+ "Cargo: " + resultado.getString("cargo") + "\n"
							+ "Sal�rio: " + resultado.getString("salario"));
				} while(resultado.next());
			} else {
				// Next == false
				JOptionPane.showMessageDialog(null, "Funcion�rio n�o encontrado","Mensagem", JOptionPane.INFORMATION_MESSAGE);
				Navegador.inicio();
			}

			Navegador.inicio();
			resultado.close();
			conexao.desconectar();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar funcion�rios");
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally {
			try {
				resultado.close();
				conexao.desconectar();
				System.out.println("Tudo com Sucesso");
			} catch (SQLException ez) {
				ez.printStackTrace(); 
			}
		}
	}

	private void sqlDeletarFuncionario() {
		String nomeFuncionario = campoFuncionario.getText();
		String pergunta = "Deseja realmente excluir o Funcion�rio " + nomeFuncionario + "?";
		int confirmacao = JOptionPane.showConfirmDialog(null, pergunta,"Excluir", JOptionPane.YES_NO_OPTION);

		if(confirmacao == JOptionPane.YES_OPTION) {
			// Conex�o
			//Connection conexao;
			Conexao conexao = new Conexao();
			// Instru��o SQL
			Statement statement;
			// Resultados
			int resultado;

			try {

				// Conectando - Driver
				conexao.conectar();
				// Instru��o SQL
				statement = conexao.criarStatement();
				String sqlDelete = "DELETE FROM T_FUNCIONARIOS WHERE nome=?;";
				PreparedStatement preparedStatement = conexao.criarPreparedStatement(sqlDelete);
				preparedStatement.setString(1, nomeFuncionario);
				resultado = preparedStatement.executeUpdate();
				if (resultado == 1) {
					String  message = String.format("Funcion�rio: %s\nDeletado com sucesso", nomeFuncionario);
					JOptionPane.showMessageDialog(null, message, "Excluir",JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Funcion�rio n�o encontrado");
					Navegador.inicio();
				}

				Navegador.inicio();
				conexao.desconectar();

			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir Funcion�rio");
				Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Navegador.inicio();
		return;
	}
}