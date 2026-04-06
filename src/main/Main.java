package main;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa("Ana", "F", 25));
        pessoas.add(new Pessoa("Maria", "F", 30));
        pessoas.add(new Pessoa("João", "M", 28));
        pessoas.add(new Pessoa("Carlos", "M", 35));
        pessoas.add(new Pessoa("Fernanda", "F", 22));

        List<Pessoa> mulheres = pessoas.stream()
                .filter(p -> p.genero.equalsIgnoreCase("F"))
                .toList();

        if (mulheres.isEmpty()) {
            System.out.println("Não há mulheres na lista selecionada");
        } else {
            mulheres.forEach(p -> System.out.println(
                    "\"" + p.nome + "\", " +
                            "\"" + p.genero + "\", " +
                                "\"" + p.idade + "\", "
            ));
        }
    }
}