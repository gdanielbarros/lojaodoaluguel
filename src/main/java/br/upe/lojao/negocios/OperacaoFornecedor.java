package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.PersistenciaFornecedor;
import br.upe.lojao.persistencia.entidades.Fornecedor;

import java.util.ArrayList;
import java.util.List;


public class OperacaoFornecedor implements IOperacaoFornecedor {

    private PersistenciaFornecedor persistencia = new PersistenciaFornecedor();
    private List<Fornecedor> fornecedores;

    
    private int proximoId = 1;

    public OperacaoFornecedor() {
        this.fornecedores = persistencia.lerFornecedor();
        atualizarProximoId();
    }

    
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