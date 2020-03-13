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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import sistema.Navegador;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;
import sqlite.Conexao;
import sqlite.CriarBancoDeDados;

public class FuncionariosInserir extends JPanel {
    
    JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelCargo, labelSalario, labelId;
    JTextField campoNome, campoSobrenome, campoEmail, campoCargo, campoId;
    JFormattedTextField campoDataNascimento, campoSalario;
    //JComboBox<Cargo> comboboxCargo;
    JButton botaoGravar;
    MaskFormatter mkSalario;
            
    public FuncionariosInserir(){
        criarComponentes();
        criarEventos();
        Navegador.habilitaMenu();
    }

    private void criarComponentes() {
        setLayout(null);
        
        labelTitulo = new JLabel("Cadastro de Funcionario", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelId = new JLabel("Id:", JLabel.LEFT);
        campoId = new JTextField();
        labelNome = new JLabel("Nome:", JLabel.LEFT);
        campoNome = new JTextField();     
        labelSobrenome = new JLabel("Sobrenome:", JLabel.LEFT);
        campoSobrenome = new JTextField();     
        labelDataNascimento = new JLabel("Data de Nascimento:", JLabel.LEFT);
        campoDataNascimento = new JFormattedTextField();
        try {
            MaskFormatter dateMask= new MaskFormatter("##/##/####");
            dateMask.install(campoDataNascimento);
        } catch (ParseException ex) {
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelEmail = new JLabel("E-mail:", JLabel.LEFT);
        campoEmail = new JTextField();     
        labelCargo = new JLabel("Cargo:", JLabel.LEFT);
        //comboboxCargo = new JComboBox();
        campoCargo = new JTextField();
        labelSalario = new JLabel("Salário:", JLabel.LEFT);
        DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        campoSalario = new JFormattedTextField(formatter);
        campoSalario.setValue(0.00);
        botaoGravar = new JButton("Adicionar");
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelId.setBounds(150, 100, 400, 20);
        campoId.setBounds(150, 120, 400, 40);
        
        labelNome.setBounds(150, 160, 400, 20);
        campoNome.setBounds(150, 180, 400, 40);
        labelSobrenome.setBounds(150, 220, 400, 20);
        campoSobrenome.setBounds(150, 240, 400, 40);
        labelDataNascimento.setBounds(150, 280, 400, 20);
        campoDataNascimento.setBounds(150, 300, 400, 40);
        labelEmail.setBounds(150, 340, 400, 20);
        campoEmail.setBounds(150, 360, 400, 40);
        labelCargo.setBounds(150, 400, 400, 20);
        //comboboxCargo.setBounds(150, 340, 400, 40);
        campoCargo.setBounds(150, 420, 400, 40);
        labelSalario.setBounds(150, 460, 400, 20);
        campoSalario.setBounds(150, 480, 400, 40);
        botaoGravar.setBounds(560, 480, 130, 40); 
        
        add(labelTitulo);
        add(labelId);
        add(campoId);
        add(labelNome);
        add(campoNome);
        add(labelSobrenome);
        add(campoSobrenome);
        add(labelDataNascimento);
        add(campoDataNascimento);
        add(labelEmail);
        add(campoEmail);
        add(labelCargo);
        //add(comboboxCargo);
        add(campoCargo);
        add(labelSalario);
        add(campoSalario);
        add(botaoGravar);
        
        sqlCarregarCargos();
        
        setVisible(true);
    }

    private void criarEventos() {
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setNome(campoNome.getText());
                novoFuncionario.setSobrenome(campoSobrenome.getText());
                novoFuncionario.setDataNascimento(campoDataNascimento.getText());
                novoFuncionario.setEmail(campoEmail.getText());
                novoFuncionario.setCargo(campoCargo.getText());
                novoFuncionario.setSalario(campoSalario.getText().replace(",", "."));
                
                sqlInserirFuncionario(novoFuncionario);   
                
                System.out.println("Inseriu: " + novoFuncionario.getNome());
            }
        });
    }

    private void sqlCarregarCargos() {  
    	//Navegador.inicio();
    }

    private void sqlInserirFuncionario(Funcionario novoFuncionario) {
    	
    	if(campoNome.getText().isEmpty() || campoSobrenome.getText().isEmpty() || campoEmail.getText().isEmpty() || 
    			campoCargo.getText().isEmpty() || campoId.getText().isEmpty() || campoDataNascimento.getText().isEmpty() || 
    			campoSalario.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Preencha todos os campo", "Validação", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	
    	// Validando Id
    	if(campoId.getText().length() == 0 && campoId.getText().length() > 2) {
    		JOptionPane.showMessageDialog(null, "Por favor, preencha o id corretamente.");
    		return;
    	}
        
        // validando nome
        if(campoNome.getText().length() <= 3){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente.");
            return;
        }
        
        // validando sobrenome
        if(campoSobrenome.getText().length() <= 3){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o sobrenome corretamente.");
            return;
        }
        
        // validando Salario
        /*
        if(Double.parseDouble(campoSalario.getText().replace(",", ".")) <= 100){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o salário corretamente.");
            return;
        }
        */
        
        // Validando Salario
        if (campoSalario.getText().length() < 3) {
        	JOptionPane.showMessageDialog(null, "Por favor, preencha o salário corretamente.");
            return;
        }
        
        // validando email
        /*
        Boolean emailValidado = false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(campoEmail.getText());
        emailValidado = m.matches();
        
        if(!emailValidado){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o email corretamente.");
            return;
        }
        */
        
        Funcionario funcionario = new Funcionario();
        // conexão
        Conexao conexao = new Conexao();
		CriarBancoDeDados criarBancoDeDados = new CriarBancoDeDados(conexao);
        // instrucao SQL
        PreparedStatement preparedStatement = null;
        // resultados
        ResultSet resultado = null;
        
        try {
    		String idFuncionario = campoId.getText();
    		funcionario.setId(idFuncionario);
    		String nomeFuncionario = campoNome.getText();
    		funcionario.setNome(nomeFuncionario);
    		String sobrenomeFuncionario = campoSobrenome.getText();
    		funcionario.setSobrenome(sobrenomeFuncionario);
    		String dataNascimentoFuncionario = campoDataNascimento.getText();
    		funcionario.setDataNascimento(dataNascimentoFuncionario);
    		String emailFuncionario = campoEmail.getText();
    		funcionario.setEmail(emailFuncionario);
    		String cargoFuncionario = campoCargo.getText();
    		funcionario.setCargo(emailFuncionario);
    		String salarioFuncionario = campoSalario.getText();
    		funcionario.setSalario(salarioFuncionario);
    		
			
			conexao.conectar();
			String sqlInsert = "INSERT INTO T_FUNCIONARIOS (id, nome, sobrenome, dataNascimento, email, cargo, salario) VALUES(?,?,?,?,?,?,?);";
			preparedStatement = conexao.criarPreparedStatement(sqlInsert);
			
			preparedStatement.setString(1, funcionario.getId());
			preparedStatement.setString(2, funcionario.getNome());
			preparedStatement.setString(3, funcionario.getSobrenome());
			preparedStatement.setString(4, funcionario.getDataNascimento());
			preparedStatement.setString(5, funcionario.getEmail());
			preparedStatement.setString(6, funcionario.getCargo());
			preparedStatement.setString(7, funcionario.getSalario());
			
			int resultados = preparedStatement.executeUpdate();
			if (resultados == 1) {
				String  message = String.format("Funcionário: %s\nCadastrado com sucesso", nomeFuncionario);
				JOptionPane.showMessageDialog(null, message, "Cadastro",JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Funcionário não inserido");
				return;
			}
            
			conexao.desconectar();
            
        } catch (SQLException ex) {
        	ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
