package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import sqlite.Conexao;
import sqlite.CriarBancoDeDados;
import sistema.entidades.Cargo;

public class CargosInserir extends JPanel {

	JLabel labelTitulo, labelNomeCargo, labelIdCargo;
	JTextField campoNomeCargo, campoIdCargo;
	JButton botaoGravar;

	public CargosInserir() {
		criarComponentes();
		criarEventos();
	}

	private void criarComponentes() {

		setLayout(null);

		labelTitulo = new JLabel("Cadastro de Cargo", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelNomeCargo = new JLabel("Nome do cargo", JLabel.LEFT);
		campoNomeCargo = new JTextField();
		labelIdCargo = new JLabel("Id do cargo", JLabel.LEFT);
		campoIdCargo = new JTextField();
		botaoGravar = new JButton("Adcionar Cargo");

		labelTitulo.setBounds(20, 20, 660, 40);
		labelNomeCargo.setBounds(150, 120, 400, 20);
		campoNomeCargo.setBounds(150, 140, 400, 40);
		labelIdCargo.setBounds(150, 180, 400, 20);
		campoIdCargo.setBounds(150, 200, 400, 40);
		botaoGravar.setBounds(250, 380, 200, 40);
		
		//campoIdCargo.enable(false);;

		add(labelTitulo);
		add(labelNomeCargo);
		add(campoNomeCargo);
		add(campoIdCargo);
		add(labelIdCargo);
		add(botaoGravar);

		setVisible(true);
	}

	public void criarEventos() {
		botaoGravar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Cargo novoCargo = new Cargo();
				novoCargo.setNome(campoNomeCargo.getText());
				novoCargo.setId(campoIdCargo.getText());

				sqlInserirCargo(novoCargo);
				campoNomeCargo.setText("");
				campoIdCargo.setText("");
			}
		});
	}

	private void sqlInserirCargo(Cargo novoCargo) {
		
		Conexao conexao = new Conexao();
		PreparedStatement preparedStatement = null;
		CriarBancoDeDados criarBancoDeDados = new CriarBancoDeDados(conexao);
		Cargo cargo = new Cargo();
		
		// Validando nome
		if(campoNomeCargo.getText().length() <= 3) {
			JOptionPane.showMessageDialog(null, "Por favor inserir o nome completo");
			return;
		}
		
		try {
			// SQLite	
			String nomeCargo = campoNomeCargo.getText();
			cargo.setNome(nomeCargo);
			String idCargo = campoIdCargo.getText();
			cargo.setId(idCargo);
			
			conexao.conectar();
			String sqlInsert = "INSERT INTO T_CARGOS (id, nome) VALUES(?,?);";
			preparedStatement = conexao.criarPreparedStatement(sqlInsert);
			
			preparedStatement.setString(1, cargo.getId());
			preparedStatement.setString(2, cargo.getNome());
			
			int resultado = preparedStatement.executeUpdate();
			if (resultado == 1) {
				String  message = String.format("Cargo: %s\nCadastrado com sucesso", nomeCargo);
				JOptionPane.showMessageDialog(null, message, "Cadastro",JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Cargo não inserido");
			}
			
			conexao.desconectar();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar", "ERRO", JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
		} 
	}
}
