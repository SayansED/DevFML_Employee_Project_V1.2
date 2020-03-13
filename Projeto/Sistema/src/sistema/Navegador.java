package sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;
import sistema.telas.CargosConsultar;
import sistema.telas.CargosEditar;
import sistema.telas.CargosInserir;
import sistema.telas.FuncionariosConsultar;
import sistema.telas.FuncionariosEditar;
import sistema.telas.FuncionariosInserir;
import sistema.telas.Inicio;
import sistema.telas.Login;
import sistema.telas.RelatoriosCargos;
import sistema.telas.RelatoriosSalarios;

public class Navegador {
    
    // Menu
    private static boolean menuConstruido;
    private static boolean menuHabilitado;
    private static JMenuBar menuBar;
    private static JMenu menuArquivo, menuFuncionarios, menuCargos, menuRelatorios;
    private static JMenuItem miSair, miFuncionariosConsultar, miFuncionariosCadastrar, miCargosConsultar;
    private static JMenuItem miCargosCadastrar, miRelatoriosCargos, miRelatoriosSalarios;
    
    public static void login(){
        Sistema.tela = new Login();
        Sistema.frame.setTitle("Funcion�rios Company SA");
        Navegador.atualizarTela();
    }
    
    public static void inicio(){
        Sistema.tela = new Inicio();
        Sistema.frame.setTitle("Funcion�rios Company SA");
        Navegador.atualizarTela();
    }
    
    public static void funcionariosCadastrar(){
        Sistema.tela = new FuncionariosInserir();   
        Sistema.frame.setTitle("Funcion�rios Company SA - Cadastrar funcion�rios");     
        Navegador.atualizarTela();
    }
    
    public static void funcionariosConsultar(){
        Sistema.tela = new FuncionariosConsultar();
        Sistema.frame.setTitle("Funcion�rios Company SA - Consultar funcion�rios");     
        Navegador.atualizarTela();
    }
    
    public static void funcionariosEditar(String funcionarioAtual){
        Sistema.tela = new FuncionariosEditar(funcionarioAtual);  
        Sistema.frame.setTitle("Funcion�rios Company SA - Editar funcion�rios");           
        Navegador.atualizarTela();
    }
    
    public static void cargosCadastrar(){
        Sistema.tela = new CargosInserir();
        Sistema.frame.setTitle("Funcion�rios Company SA - Cadastrar cargos");
        Navegador.atualizarTela();
    }
    
    public static void cargosConsultar(){
        Sistema.tela = new CargosConsultar();  
        Sistema.frame.setTitle("Funcion�rios Company SA - Consultar cargos");      
        Navegador.atualizarTela();
    }
    
    public static void cargosEditar(Cargo cargoAtual){
        Sistema.tela = new CargosEditar(cargoAtual);      
        Sistema.frame.setTitle("Funcion�rios Company SA - Editar cargos");  
        Navegador.atualizarTela();
    }
    
    public static void relatoriosCargos(){
    	/*
        Sistema.tela = new RelatoriosCargos();
        Sistema.frame.setTitle("Funcion�rios Company SA - Relat�rios");    
        Navegador.atualizarTela();
        */
    	JOptionPane.showMessageDialog(null, "EM MANUTEN��O", "SORRY", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void relatoriosSalarios(){
    	/*
        Sistema.tela = new RelatoriosSalarios();
        Sistema.frame.setTitle("Funcion�rios Company SA - Relat�rios");    
        Navegador.atualizarTela();
        */
    	JOptionPane.showMessageDialog(null, "EM MANUTEN��O", "SORRY", JOptionPane.ERROR_MESSAGE);
    }
    
    // M�todo que remove a tela atual e adiciona a pr�xima tela
    private static void atualizarTela(){
        Sistema.frame.getContentPane().removeAll();
        Sistema.tela.setVisible(true);
        Sistema.frame.add(Sistema.tela);
        
        Sistema.frame.setVisible(true);
    }
    
    private static void construirMenu(){
        if(!menuConstruido){
            menuConstruido = true;
            
            menuBar = new JMenuBar();
            
            // menu Arquivo
            menuArquivo = new JMenu("Arquivo");
            menuBar.add(menuArquivo);
            miSair = new JMenuItem("Sair");
            menuArquivo.add(miSair);
            
            // menu Funcion�rios
            menuFuncionarios = new JMenu("Funcion�rios");
            menuBar.add(menuFuncionarios);
            miFuncionariosCadastrar = new JMenuItem("Cadastrar");
            menuFuncionarios.add(miFuncionariosCadastrar);
            miFuncionariosConsultar = new JMenuItem("Consultar");
            menuFuncionarios.add(miFuncionariosConsultar);
            
            // menu Cargos
            menuCargos = new JMenu("Cargos");
            menuBar.add(menuCargos);
            miCargosCadastrar = new JMenuItem("Cadastrar");
            menuCargos.add(miCargosCadastrar);
            miCargosConsultar = new JMenuItem("Consultar");
            menuCargos.add(miCargosConsultar);
            
            // menu Relat�rios
            menuRelatorios = new JMenu("Relat�rios");
            menuBar.add(menuRelatorios);
            miRelatoriosCargos = new JMenuItem("Funcion�rios por cargos");
            menuRelatorios.add(miRelatoriosCargos);
            miRelatoriosSalarios = new JMenuItem("Sal�rios dos funcion�rios");
            menuRelatorios.add(miRelatoriosSalarios);
            
            criarEventosMenu();
            
        }
    }
    
    public static void habilitaMenu(){
        if(!menuConstruido) construirMenu();
        if(!menuHabilitado){
            menuHabilitado = true;
            Sistema.frame.setJMenuBar(menuBar);
        }
    }
    
    public static void desabilitaMenu(){
        if(menuHabilitado){
            menuHabilitado = false;
            Sistema.frame.setJMenuBar(null);
        }        
    }

    private static void criarEventosMenu() {
        miSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Funcionario
        miFuncionariosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionariosCadastrar();
            }
        });
        miFuncionariosConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionariosConsultar();
            }
        });
        
        // Cargos
        miCargosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargosCadastrar();
            }
        });
        miCargosConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargosConsultar();
            }
        });
        
        miRelatoriosCargos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                relatoriosCargos();
            }
        });
        
        miRelatoriosSalarios.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                relatoriosSalarios();
            }
        });
    }
}
