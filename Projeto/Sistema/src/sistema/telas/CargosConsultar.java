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
import sistema.Navegador;
import sistema.entidades.Cargo;
import sqlite.Conexao;
import sqlite.CriarBancoDeDados;

public class CargosConsultar extends JPanel {

	Cargo cargoAtual;
	JLabel labelTitulo, labelCargo;
	JTextField campoCargo;
	JButton botaoPesquisar, botaoEditar, botaoExcluir;
	DefaultListModel<Cargo> listasCargosModelo = new DefaultListModel();
	JList<Cargo> listaCargos;

	public CargosConsultar(){
		criarComponentes();
		criarEventos();
		Navegador.habilitaMenu();
	}

	private void criarComponentes() {
		setLayout(null);

		labelTitulo = new JLabel("Consulta de Cargos", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
		labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
		campoCargo = new JTextField();
		botaoPesquisar = new JButton("Pesquisar Cargo");
		botaoEditar = new JButton("Editar Cargo");
		//botaoEditar.setEnabled(false);
		botaoExcluir = new JButton("Excluir Cargo");
		//botaoExcluir.setEnabled(false);
		botaoEditar.setEnabled(true);
		botaoExcluir.setEnabled(true);
		/*
		listasCargosModelo = new DefaultListModel();
		listaCargos = new JList();
		listaCargos.setModel(listasCargosModelo);
		listaCargos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		 */


		labelTitulo.setBounds(20, 20, 660, 40);
		labelCargo.setBounds(150, 120, 400, 20);
		campoCargo.setBounds(150, 140, 400, 40);
		botaoPesquisar.setBounds(560, 140, 130, 40); 
		//listaCargos.setBounds(150, 200, 400, 240);
		botaoEditar.setBounds(560, 360, 130, 40); 
		botaoExcluir.setBounds(560, 400, 130, 40);

		add(labelTitulo);
		add(labelCargo);
		add(campoCargo);
		//add(listaCargos);
		add(botaoPesquisar);
		add(botaoEditar);
		add(botaoExcluir);

		setVisible(true);
	}

	private void criarEventos() {

		// Pesquisar
		botaoPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(campoCargo.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Preencha o campo", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
				sqlPesquisarCargos(campoCargo.getText());
			}
		});

		// Editar
		botaoEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Navegador.cargosEditar(cargoAtual);
			}
		});

		// Excluir
		botaoExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Validando campo e nome
				if(campoCargo.getText().isEmpty() || campoCargo.getText().length() <= 3) {
					JOptionPane.showMessageDialog(null, "Preencha o campo corretamente", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				sqlDeletarCargo();
			}
		});
		/*
		listaCargos.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				cargoAtual = listaCargos.getSelectedValue();
				if(cargoAtual == null){
					botaoEditar.setEnabled(false);
					botaoExcluir.setEnabled(false);
				}else{
					botaoEditar.setEnabled(true);
					botaoExcluir.setEnabled(true);
				}
			}
		});
		 */
	}

	private void sqlPesquisarCargos(String nome) {
		
		// Validando campo
		if(campoCargo.getText().length() <= 3) {
			JOptionPane.showMessageDialog(null, "Por favor inserir o cargo");
			return;
		}
		
		// Conexao BD
		Conexao conexao = new Conexao();
		// Instrucao SQL
		PreparedStatement preparedStatement = null;
		// Resultados
		ResultSet resultado = null;

		try {
			String sqlSelect = "SELECT id,nome FROM T_CARGOS WHERE nome = ?;";
			conexao.conectar();
			preparedStatement = conexao.criarPreparedStatement(sqlSelect);
			preparedStatement.setString(1, nome);
			resultado = preparedStatement.executeQuery();

			if(resultado.next()){
				// Next == true "Encontoru os dados"
				JOptionPane.showMessageDialog(null, "Consulta realizado com sucesso", "Consulta", JOptionPane.INFORMATION_MESSAGE);
				do{
					JOptionPane.showMessageDialog(null, "Cargos" + "\n"
							+ "Id: " + resultado.getString("id") + "\n"
							+ "Nome: " + resultado.getString("nome"));
				}while(resultado.next());
			}else{
				// Next == false
				JOptionPane.showMessageDialog(null, "Cargo não encontrado","Mensagem", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			Navegador.inicio();
			resultado.close();
			conexao.desconectar();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar cargos");
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

	private void sqlDeletarCargo() {
		int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o Cargo " + campoCargo.getText() + "?", "Excluir", JOptionPane.YES_NO_OPTION);
		if(confirmacao == JOptionPane.YES_OPTION) {
			// conexão
			Conexao conexao = new Conexao();
			// instrucao SQL
			Statement statement = null;
			// resultados
			ResultSet resultados = null;

			String nomeCargo = campoCargo.getText();

			try {
				// Conectando - Driver
				conexao.conectar();
				// Instrução SQL
				statement = conexao.criarStatement();
				String sqlDelete = "DELETE FROM T_CARGOS WHERE nome=?;";
				PreparedStatement preparedStatement = conexao.criarPreparedStatement(sqlDelete);
				preparedStatement.setString(1, nomeCargo);
				int resultado = preparedStatement.executeUpdate();
				if (resultado == 1) {
					String  message = String.format("Cargo: %s\nDeletado com sucesso", nomeCargo);
					JOptionPane.showMessageDialog(null, message, "Excluir",JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Cargo não encontrado");
					return;
				}

				Navegador.inicio();
				conexao.desconectar();

			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o Cargo.");
				Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
			}
			Navegador.inicio();
			return;
		}
	}
}
