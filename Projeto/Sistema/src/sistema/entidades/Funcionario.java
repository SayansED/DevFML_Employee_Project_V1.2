package sistema.entidades;

public class Funcionario {
    // variável destinado ao id do funcionário
    private String id; 
    // variável destinado ao nome do funcionário
    private String nome; 
    // variável destinado ao sobrenome do funcionário
    private String sobrenome; 
    // variável destinado a data de nascimento do funcionário
    private String dataNascimento;
    // variável destinado ao email do funcionário
    private String email;
    // variável destinado ao cargo do funcionário
    private String cargo; 
    // variável destinado ao salário atual do funcionário
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
