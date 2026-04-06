package test.java;

import main.Pessoa;
import main.PessoaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class TesteUTL {

    private List<Pessoa> pessoas;
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        pessoas = List.of(
                new Pessoa("Ana", "F", 25),
                new Pessoa("Maria", "F", 30),
                new Pessoa("João", "M", 28),
                new Pessoa("Carlos", "M", 35),
                new Pessoa("Fernanda", "F", 22),
                new Pessoa("Pedro", "M", 40),
                new Pessoa("Beatriz", "F", 18)
        );

        pessoaService = new PessoaService(pessoas);
    }

    // BUSCA PESSOAS

    @Test
    void deveBuscarTodasAsPessoas() {
        List<Pessoa> resultado = pessoaService.buscarTodos();

        assertEquals(7, resultado.size());
        assertNotSame(pessoas, resultado);
    }

    @Test
    void deveBuscarPorNomeExistente() {
        Optional<Pessoa> pessoa = pessoaService.buscarPorNome("Ana");

        assertTrue(pessoa.isPresent());
        assertEquals("Ana", pessoa.get().getNome());
        assertEquals("F", pessoa.get().getGenero());
        assertEquals(25, pessoa.get().getIdade());
    }

    @Test
    void deveBuscarPorNomeIgnoreCase() {
        Optional<Pessoa> pessoa = pessoaService.buscarPorNome("ana");

        assertTrue(pessoa.isPresent());
        assertEquals("Ana", pessoa.get().getNome());
    }

    // BUSCA IDADE

    @Test
    void deveBuscarPorIdade() {
        List<Pessoa> resultado = pessoaService.buscarPorIdade(25);

        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.getFirst().getNome());
    }

    @Test
    void deveBuscarPorFaixaEtaria() {
        List<Pessoa> resultado = pessoaService.buscarPorFaixaEtaria(20, 30);

        assertEquals(4, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getIdade() >= 20 && p.getIdade() <= 30));
    }

    @Test
    void deveBuscarPorIdadeMaiorQue() {
        List<Pessoa> resultado = pessoaService.buscarPorIdadeMaiorQue(30);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getIdade() > 30));
    }

    @Test
    void deveBuscarPorIdadeMenorQue() {
        List<Pessoa> resultado = pessoaService.buscarPorIdadeMenorQue(25);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getIdade() < 25));
    }

    @Test
    void deveBuscarPorIndice() {
        Optional<Pessoa> pessoa = pessoaService.buscarPorIndice(0);

        assertTrue(pessoa.isPresent());
        assertEquals("Ana", pessoa.get().getNome());
    }

    @Test
    void deveRetornarOptionalVazioQuandoIndiceInvalido() {
        Optional<Pessoa> pessoa = pessoaService.buscarPorIndice(99);

        assertFalse(pessoa.isPresent());
    }

    @Test
    void deveBuscarPorGeneroEIdade() {
        List<Pessoa> resultado = pessoaService.buscarPorGeneroEIdade("F", 20, 25);

        assertEquals(2, resultado.size());
        assertEquals(List.of("Ana", "Fernanda"),
                resultado.stream().map(Pessoa::getNome).toList());
    }

    // ATUALIZA POR NOME, INDICE...

    @Test
    void deveAtualizarPessoaPorNome() {
        Pessoa pessoaAtualizada = new Pessoa("Ana Souza", "F", 26);
        boolean atualizado = pessoaService.atualizar("Ana", pessoaAtualizada);

        assertTrue(atualizado);
        Optional<Pessoa> pessoa = pessoaService.buscarPorNome("Ana Souza");
        assertTrue(pessoa.isPresent());
        assertEquals(26, pessoa.get().getIdade());
    }

    @Test
    void deveAtualizarPorIndice() {
        Pessoa pessoaAtualizada = new Pessoa("Ana Carolina", "F", 27);
        boolean atualizado = pessoaService.atualizarPorIndice(0, pessoaAtualizada);

        assertTrue(atualizado);
        Optional<Pessoa> pessoa = pessoaService.buscarPorNome("Ana Carolina");
        assertTrue(pessoa.isPresent());
        assertEquals(27, pessoa.get().getIdade());
    }

    @Test
    void deveAtualizarApenasONome() {
        boolean atualizado = pessoaService.atualizarNome("Maria", "Maria Silva");

        assertTrue(atualizado);
        Optional<Pessoa> pessoa = pessoaService.buscarPorNome("Maria Silva");
        assertTrue(pessoa.isPresent());
        assertEquals(30, pessoa.get().getIdade());
        assertEquals("F", pessoa.get().getGenero());
    }

    @Test
    void deveAtualizarApenasAIdade() {
        boolean atualizado = pessoaService.atualizarIdade("João", 29);

        assertTrue(atualizado);
        Optional<Pessoa> pessoa = pessoaService.buscarPorNome("João");
        assertTrue(pessoa.isPresent());
        assertEquals(29, pessoa.get().getIdade());
    }

    @Test
    void deveAtualizarApenasOGenero() {
        boolean atualizado = pessoaService.atualizarGenero("Carlos", "F");

        assertTrue(atualizado);
        Optional<Pessoa> pessoa = pessoaService.buscarPorNome("Carlos");
        assertTrue(pessoa.isPresent());
        assertEquals("F", pessoa.get().getGenero());
    }

    @Test
    void naoDeveAtualizarComIdadeInvalida() {
        boolean atualizado = pessoaService.atualizarIdade("Ana", -5);

        assertFalse(atualizado);
        Optional<Pessoa> pessoa = pessoaService.buscarPorNome("Ana");
        assertEquals(25, pessoa.get().getIdade());
    }

    @Test
    void naoDeveAtualizarComGeneroInvalido() {
        boolean atualizado = pessoaService.atualizarGenero("Ana", "X");

        assertFalse(atualizado);
        Optional<Pessoa> pessoa = pessoaService.buscarPorNome("Ana");
        assertEquals("F", pessoa.get().getGenero());
    }

    @Test
    void deveAtualizarTodosPorGenero() {
        int atualizados = pessoaService.atualizarTodosPorGenero("M", "Masculino");

        assertEquals(3, atualizados);
        List<Pessoa> homens = pessoaService.buscarPorGenero("Masculino");
        assertEquals(3, homens.size());
    }

    @Test
    void naoDeveAtualizarPessoaInexistente() {
        Pessoa pessoaAtualizada = new Pessoa("Novo Nome", "F", 20);
        boolean atualizado = pessoaService.atualizar("Inexistente", pessoaAtualizada);

        assertFalse(atualizado);
    }

    // EXCLUIR POR NOME, INDICE...

    @Test
    void deveExcluirPorNome() {
        boolean excluido = pessoaService.excluirPorNome("Ana");

        assertTrue(excluido);
        assertEquals(6, pessoaService.quantidade());
        assertFalse(pessoaService.buscarPorNome("Ana").isPresent());
    }

    @Test
    void deveExcluirPorIndice() {
        boolean excluido = pessoaService.excluirPorIndice(0);

        assertTrue(excluido);
        assertEquals(6, pessoaService.quantidade());
        assertFalse(pessoaService.buscarPorNome("Ana").isPresent());
    }

    @Test
    void naoDeveExcluirPorIndiceInvalido() {
        boolean excluido = pessoaService.excluirPorIndice(99);

        assertFalse(excluido);
        assertEquals(7, pessoaService.quantidade());
    }

    @Test
    void deveExcluirPorGenero() {
        int excluidos = pessoaService.excluirPorGenero("M");

        assertEquals(3, excluidos);
        assertEquals(4, pessoaService.quantidade());
        assertTrue(pessoaService.buscarHomens().isEmpty());
    }

    @Test
    void deveExcluirPorIdade() {
        int excluidos = pessoaService.excluirPorIdade(25);

        assertEquals(1, excluidos);
        assertEquals(6, pessoaService.quantidade());
        assertTrue(pessoaService.buscarPorIdade(25).isEmpty());
    }

    @Test
    void deveExcluirPorFaixaEtaria() {
        int excluidos = pessoaService.excluirPorFaixaEtaria(18, 25);

        assertEquals(3, excluidos);
        assertEquals(4, pessoaService.quantidade());
    }

    @Test
    void deveExcluirPorIdadeMenorQue() {
        int excluidos = pessoaService.excluirPorIdadeMenorQue(25);

        assertEquals(2, excluidos);
        assertEquals(5, pessoaService.quantidade());
    }

    @Test
    void deveExcluirPorIdadeMaiorQue() {
        int excluidos = pessoaService.excluirPorIdadeMaiorQue(30);

        assertEquals(2, excluidos);
        assertEquals(5, pessoaService.quantidade());
    }

    @Test
    void deveExcluirPrimeiroPorNome() {
        pessoaService.adicionar(new Pessoa("Ana", "F", 50));
        assertEquals(8, pessoaService.quantidade());

        boolean excluido = pessoaService.excluirPrimeiroPorNome("Ana");

        assertTrue(excluido);
        assertEquals(7, pessoaService.quantidade());
        assertTrue(pessoaService.buscarPorNome("Ana").isPresent());
    }

    @Test
    void deveExcluirTodos() {
        pessoaService.excluirTodos();

        assertEquals(0, pessoaService.quantidade());
        assertTrue(pessoaService.isEmpty());
    }

    @Test
    void naoDeveExcluirNomeInexistente() {
        boolean excluido = pessoaService.excluirPorNome("Inexistente");

        assertFalse(excluido);
        assertEquals(7, pessoaService.quantidade());
    }

    // TESTES COMBINADOS DE CRUD

    @Test
    void deveRealizarCicloCompletoCRUD() {
        // CREATE
        Pessoa novaPessoa = new Pessoa("Lucas", "M", 25);
        pessoaService.adicionar(novaPessoa);
        assertEquals(8, pessoaService.quantidade());

        // READ
        Optional<Pessoa> encontrada = pessoaService.buscarPorNome("Lucas");
        assertTrue(encontrada.isPresent());
        assertEquals(25, encontrada.get().getIdade());

        // UPDATE
        boolean atualizada = pessoaService.atualizarIdade("Lucas", 26);
        assertTrue(atualizada);
        encontrada = pessoaService.buscarPorNome("Lucas");
        assertEquals(26, encontrada.get().getIdade());

        // DELETE
        boolean excluida = pessoaService.excluirPorNome("Lucas");
        assertTrue(excluida);
        assertEquals(7, pessoaService.quantidade());
        assertFalse(pessoaService.buscarPorNome("Lucas").isPresent());
    }
}