package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.PersistenciaFornecedor;
import br.upe.lojao.persistencia.entidades.Fornecedor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe da camada de NEGÓCIOS responsável pelas regras de Fornecedor.
 * Implementa IOperacaoFornecedor, seguindo a orientação do professor.
 *
 * Usa PersistenciaFornecedor (do João) para ler/gravar os dados em
 * arquivo. O status do fornecedor é representado como String
 * ("Ativo"/"Inativo"), conforme a entidade Fornecedor já implementada.
 */
public class OperacaoFornecedor implements IOperacaoFornecedor {

    private PersistenciaFornecedor persistencia = new PersistenciaFornecedor();
    private List<Fornecedor> fornecedores;

    
    private int proximoId = 1;

    public OperacaoFornecedor() {
        this.fornecedores = persistencia.lerFornecedor();
        atualizarProximoId();
    }

    /**
     * Garante que o próximo ID gerado nunca repita um ID já existente
     * no arquivo (importante ao carregar fornecedores já salvos antes).
     */
    private void atualizarProximoId() {
        for (Fornecedor f : fornecedores) {
            if (f.getId() >= proximoId) {
                proximoId = f.getId() + 1;
            }
        }
    }

    @Override
    public boolean cadastrarFornecedor(String email, String telefone, String nome) {
        if (existeFornecedorComNome(nome)) {
            return false;
        }

        Fornecedor novo = new Fornecedor(proximoId, email, telefone, nome, "Ativo");
        fornecedores.add(novo);
        proximoId++;
        persistencia.atualizarFornecedor(fornecedores);
        return true;
    }

    /**
     * Verifica se já existe um fornecedor com o nome informado.
     * Comparação ignora maiúsculas/minúsculas, mesma lógica usada em
     * OperacaoCategoria para evitar duplicidade.
     */
    private boolean existeFornecedorComNome(String nome) {
        for (Fornecedor f : fornecedores) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Fornecedor> listarFornecedores() {
        return fornecedores;
    }

    @Override
    public Fornecedor buscarPorId(int id) {
        for (Fornecedor f : fornecedores) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    @Override
    public List<Fornecedor> buscarFornecedor(String nome) {
        List<Fornecedor> encontrados = new ArrayList<>();
        for (Fornecedor f : fornecedores) {
            if (f.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(f);
            }
        }
        return encontrados;
    }

    @Override
    public boolean editarFornecedor(int id, String novoEmail, String novoTelefone, String novoNome) {
        Fornecedor fornecedor = buscarPorId(id);
        if (fornecedor == null) {
            return false;
        }
        fornecedor.setEmail(novoEmail);
        fornecedor.setTelefone(novoTelefone);
        fornecedor.setNome(novoNome);
        persistencia.atualizarFornecedor(fornecedores);
        return true;
    }

    /**
     * Remove (de forma lógica) um fornecedor, marcando o status como
     * "Inativo" em vez de excluir de verdade.
     *
     * RN05 do enunciado (Integridade): não se deve excluir um registro
     * que possua histórico vinculado (neste caso, produtos fornecidos
     * por ele). Como o status já existe como String no Fornecedor,
     * usamos essa exclusão lógica em vez de remover da lista.
     */
    @Override
    public boolean deletarFornecedor(int id) {
        Fornecedor fornecedor = buscarPorId(id);
        if (fornecedor == null) {
            return false;
        }
        fornecedor.setStatus("Inativo");
        persistencia.atualizarFornecedor(fornecedores);
        return true;
    }
}