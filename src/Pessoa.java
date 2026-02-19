public class Pessoa {
    String nome;
    String genero;
    int idade;

    Pessoa(String nome, String genero, int idade) {
        this.nome = nome;
        this.genero = genero;
        this.idade = idade;
    }
    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public int getIdade() {
        return idade;
    }

}
