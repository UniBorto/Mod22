import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Pessoa> pessoas = List.of(
                new Pessoa("Ana", "F", 25),
                new Pessoa("Maria", "F", 30),
                new Pessoa("João", "M", 28),
                new Pessoa("Carlos", "M", 35),
                new Pessoa("Fernanda", "F", 22)
        );

        List<Pessoa> mulheres = pessoas.stream()
                .filter(p -> p.genero.equalsIgnoreCase("F"))
                .toList();

        if (mulheres.isEmpty()) {
            System.out.println("Não há mulheres na lista selecionada");
        } else {
            mulheres.forEach(p -> System.out.println(
                    "\"" + p.nome + "\", " +
                            "\"" + p.idade + "\""
            ));
        }
    }
}