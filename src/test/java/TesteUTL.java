import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TesteUTL {

    @Test
    void deveFiltrarSomenteMulheres() {
        List<Pessoa> pessoas = List.of(
                new Pessoa("Carlos", "M", 30),
                new Pessoa("Ana", "F", 20)
        );

        List<Pessoa> mulheres = pessoas.stream()
                .filter(p -> p.getGenero().equalsIgnoreCase("F"))
                .toList();

        assertEquals(1, mulheres.size());
        assertEquals("Ana", mulheres.get(0).getNome());
    }
}