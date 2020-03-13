package sistema.entidades;

public class Funcionario {
    // vari�vel destinado ao id do funcion�rio
    private String id; 
    // vari�vel destinado ao nome do funcion�rio
    private String nome; 
    // vari�vel destinado ao sobrenome do funcion�rio
    private String sobrenome; 
    // vari�vel destinado a data de nascimento do funcion�rio
    private String dataNascimento;
    // vari�vel destinado ao email do funcion�rio
    private String email;
    // vari�vel destinado ao cargo do funcion�rio
    private String cargo; 
    // vari�vel destinado ao sal�rio atual do funcion�rio
    private String salario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }
    
    @Override
    public String toString() {
        return this.nome + " " + this.sobrenome;
    }    
}
