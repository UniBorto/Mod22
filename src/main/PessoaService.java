package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PessoaService {
    private List<Pessoa> pessoas;

    public PessoaService() {
        this.pessoas = new ArrayList<>();
    }

    public PessoaService(List<Pessoa> pessoas) {
        this.pessoas = new ArrayList<>(pessoas);
    }


    // BUSCAR NOME; GENERO;


    public List<Pessoa> buscarTodos() {
        return new ArrayList<>(pessoas);
    }

    public Optional<Pessoa> buscarPorNome(String nome) {
        return pessoas.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public List<Pessoa> buscarPorGenero(String genero) {
        return pessoas.stream()
                .filter(p -> p.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    public List<Pessoa> buscarMulheres() {
        return buscarPorGenero("F");
    }

    public List<Pessoa> buscarHomens() {
        return buscarPorGenero("M");
    }


    // BUSCAR ESPECÍFICO


    public List<Pessoa> buscarPorIdade(int idade) {
        return pessoas.stream()
                .filter(p -> p.getIdade() == idade)
                .collect(Collectors.toList());
    }

    public List<Pessoa> buscarPorFaixaEtaria(int idadeMin, int idadeMax) {
        return pessoas.stream()
                .filter(p -> p.getIdade() >= idadeMin && p.getIdade() <= idadeMax)
                .collect(Collectors.toList());
    }

    public List<Pessoa> buscarPorIdadeMaiorQue(int idade) {
        return pessoas.stream()
                .filter(p -> p.getIdade() > idade)
                .collect(Collectors.toList());
    }

    public List<Pessoa> buscarPorIdadeMenorQue(int idade) {
        return pessoas.stream()
                .filter(p -> p.getIdade() < idade)
                .collect(Collectors.toList());
    }

    public List<Pessoa> buscarPorNomeContendo(String termo) {
        return pessoas.stream()
                .filter(p -> p.getNome().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Pessoa> buscarPorIndice(int indice) {
        if (indice >= 0 && indice < pessoas.size()) {
            return Optional.of(pessoas.get(indice));
        }
        return Optional.empty();
    }

    public List<Pessoa> buscarPorGeneroEIdade(String genero, int idadeMin, int idadeMax) {
        return pessoas.stream()
                .filter(p -> p.getGenero().equalsIgnoreCase(genero))
                .filter(p -> p.getIdade() >= idadeMin && p.getIdade() <= idadeMax)
                .collect(Collectors.toList());
    }



    // ATUALIZAR



    public boolean atualizar(String nome, Pessoa pessoaAtualizada) {
        Optional<Pessoa> pessoaExistente = buscarPorNome(nome);

        if (pessoaExistente.isPresent()) {
            Pessoa pessoa = pessoaExistente.get();
            pessoa.setNome(pessoaAtualizada.getNome());
            pessoa.setGenero(pessoaAtualizada.getGenero());
            pessoa.setIdade(pessoaAtualizada.getIdade());
            return true;
        }
        return false;
    }

    public boolean atualizarPorIndice(int indice, Pessoa pessoaAtualizada) {
        Optional<Pessoa> pessoaExistente = buscarPorIndice(indice);

        if (pessoaExistente.isPresent()) {
            Pessoa pessoa = pessoaExistente.get();
            pessoa.setNome(pessoaAtualizada.getNome());
            pessoa.setGenero(pessoaAtualizada.getGenero());
            pessoa.setIdade(pessoaAtualizada.getIdade());
            return true;
        }
        return false;
    }

    public boolean atualizarNome(String nomeAntigo, String nomeNovo) {
        Optional<Pessoa> pessoa = buscarPorNome(nomeAntigo);
        if (pessoa.isPresent()) {
            pessoa.get().setNome(nomeNovo);
            return true;
        }
        return false;
    }

    public boolean atualizarIdade(String nome, int novaIdade) {
        Optional<Pessoa> pessoa = buscarPorNome(nome);
        if (pessoa.isPresent() && novaIdade >= 0) {
            pessoa.get().setIdade(novaIdade);
            return true;
        }
        return false;
    }

    public boolean atualizarGenero(String nome, String novoGenero) {
        Optional<Pessoa> pessoa = buscarPorNome(nome);
        if (pessoa.isPresent() && (novoGenero.equalsIgnoreCase("M") || novoGenero.equalsIgnoreCase("F"))) {
            pessoa.get().setGenero(novoGenero.toUpperCase());
            return true;
        }
        return false;
    }

    public int atualizarTodosPorGenero(String generoAtual, String novoGenero) {
        List<Pessoa> pessoasParaAtualizar = buscarPorGenero(generoAtual);
        pessoasParaAtualizar.forEach(p -> p.setGenero(novoGenero));
        return pessoasParaAtualizar.size();
    }


    // Excluir


    public boolean excluirPorNome(String nome) {
        return pessoas.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }

    public boolean excluirPorIndice(int indice) {
        if (indice >= 0 && indice < pessoas.size()) {
            pessoas.remove(indice);
            return true;
        }
        return false;
    }

    public int excluirPorGenero(String genero) {
        int tamanhoInicial = pessoas.size();
        pessoas.removeIf(p -> p.getGenero().equalsIgnoreCase(genero));
        return tamanhoInicial - pessoas.size();
    }

    public int excluirPorIdade(int idade) {
        int tamanhoInicial = pessoas.size();
        pessoas.removeIf(p -> p.getIdade() == idade);
        return tamanhoInicial - pessoas.size();
    }

    public int excluirPorFaixaEtaria(int idadeMin, int idadeMax) {
        int tamanhoInicial = pessoas.size();
        pessoas.removeIf(p -> p.getIdade() >= idadeMin && p.getIdade() <= idadeMax);
        return tamanhoInicial - pessoas.size();
    }

    public int excluirPorIdadeMenorQue(int idade) {
        int tamanhoInicial = pessoas.size();
        pessoas.removeIf(p -> p.getIdade() < idade);
        return tamanhoInicial - pessoas.size();
    }

    public int excluirPorIdadeMaiorQue(int idade) {
        int tamanhoInicial = pessoas.size();
        pessoas.removeIf(p -> p.getIdade() > idade);
        return tamanhoInicial - pessoas.size();
    }

    public boolean excluirPrimeiroPorNome(String nome) {
        Optional<Pessoa> pessoa = buscarPorNome(nome);
        if (pessoa.isPresent()) {
            pessoas.remove(pessoa.get());
            return true;
        }
        return false;
    }

    public void excluirTodos() {
        pessoas.clear();
    }

    public int quantidade() {
        return pessoas.size();
    }

    public boolean isEmpty() {
        return pessoas.isEmpty();
    }

    public void adicionar(Pessoa pessoa) {
        if (pessoa == null) {
            throw new IllegalArgumentException("Pessoa não pode ser nula");
        }
        pessoas.add(pessoa);
    }

    public void adicionarTodos(List<Pessoa> novasPessoas) {
        if (novasPessoas == null) {
            throw new IllegalArgumentException("Lista não pode ser nula");
        }
        pessoas.addAll(novasPessoas);
    }
}