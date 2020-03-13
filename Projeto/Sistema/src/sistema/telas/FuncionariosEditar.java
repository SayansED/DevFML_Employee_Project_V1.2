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
import sqlite.Conexao;;

public class FuncionariosEditar extends JPanel {
    
    String funcionarioAtual;
    JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelCargo, labelSalario, labelId;
    JTextField campoNome, campoSobrenome, campoEmail, campoCargo, campoId;;
    JFormattedTextField campoDataNascimento, campoSalario;
    JButton botaoGravar;  
            
    public FuncionariosEditar(String funcionarioAtual){
        this.funcionarioAtual = funcionarioAtual;
        criarComponentes();
        criarEventos();
        Navegador.habilitaMenu();
    }

    private void criarComponentes() {
        setLayout(null);
        
        String textoLabel = "Editar Funcionario ";
        labelTitulo = new JLabel(textoLabel, JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
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
        campoCargo = new JTextField();
        labelId = new JLabel("Id:", JLabel.LEFT);
        campoId = new JTextField();
        labelSalario = new JLabel("Salário:", JLabel.LEFT);
        DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        campoSalario = new JFormattedTextField(formatter);
        campoSalario.setValue(0.00);
        botaoGravar = new JButton("Salvar");
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelId.setBounds(150, 80, 400, 20);
        campoId.setBounds(150, 100, 400, 40);
        labelNome.setBounds(150, 140, 400, 20);
        campoNome.setBounds(150, 160, 400, 40);
        labelSobrenome.setBounds(150, 200, 400, 20);
        campoSobrenome.setBounds(150, 220, 400, 40);
        labelDataNascimento.setBounds(150, 260, 400, 20);
        campoDataNascimento.setBounds(150, 280, 400, 40);
        labelEmail.setBounds(150, 320, 400, 20);
        campoEmail.setBounds(150, 340, 400, 40);
        labelCargo.setBounds(150, 380, 400, 20);
        campoCargo.setBounds(150, 400, 400, 40);
        labelSalario.setBounds(150, 440, 400, 20);
        campoSalario.setBounds(150, 460, 400, 40);
        botaoGravar.setBounds(560, 460, 130, 40); 
        
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
        add(campoCargo);
        add(labelSalario);
        add(campoSalario);
        add(botaoGravar);
        
        
        setVisible(true);
    }

    private void criarEventos() {
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Funcionario funcionario = new Funcionario();
            	// Passando valores
            	funcionario.setId(campoId.getText());
                funcionario.setNome(campoNome.getText());
                funcionario.setSobrenome(campoSobrenome.getText());
                funcionario.setDataNascimento(campoDataNascimento.getText());
                funcionario.setEmail(campoEmail.getText());
                funcionario.setCargo(campoCargo.getText());
                funcionario.setSalario(campoSalario.getText());
                // Validando campos
                
                sqlAtualizarFuncionario();
                System.out.println("Alterou: " + funcionario.getNome());
            }
        });
    }

    private void sqlAtualizarFuncionario() {
        
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
        
        // validando salario
        /*
        if(Double.parseDouble(campoSalario.getText().replace(",", ".")) <= 100){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o salário corretamente.");
            return;
        }
        */
        
        /*
        // validando email
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
        
        // conexão
        Conexao conexao = new Conexao();
        // instrucao SQL
        PreparedStatement preparedStatement = null;
        // resultados
        ResultSet resultado = null;
        
        try {
            // conectando ao banco de dados   
        	String busca = JOptionPane.showInputDialog("Digite o nome para a busca");
            String sqlUpdate = "UPDATE T_FUNCIONARIOS SET id=?,nome=?,sobrenome=?,dataNascimento=?,email=?,cargo=?,salario=? WHERE nome=?;";
            conexao.conectar();
            preparedStatement = conexao.criarPreparedStatement(sqlUpdate);
            preparedStatement.setString(1, campoId.getText());
            preparedStatement.setString(2, campoNome.getText());
            preparedStatement.setString(3, campoSobrenome.getText());
            preparedStatement.setString(4, campoDataNascimento.getText());
            preparedStatement.setString(5, campoEmail.getText());
            preparedStatement.setString(6,campoCargo.getText());
            preparedStatement.setString(7, campoSalario.getText().replace(",", "."));
            preparedStatement.setString(8, busca);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Funcionario atualizado com sucesso!");
            Navegador.inicio();
            conexao.desconectar();
        } catch (SQLException ex) {
        	ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o Funcionario.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
