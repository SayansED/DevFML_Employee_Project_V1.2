/*
package sistema.entidades;

public class Cargo {
    // vari�vel destinado ao id do cargo
    private int id; 
    // vari�vel destinado ao nome do cargo
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }    
}
*/

//			Adapta��o
package sistema.entidades;

public class Cargo {
    // vari�vel destinado ao id do cargo
    private String id; 
    // vari�vel destinado ao nome do cargo
    private String nome;

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
    
    @Override
    public String toString() {
        return this.nome;
    }    
}

