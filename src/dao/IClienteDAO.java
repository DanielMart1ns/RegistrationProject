package dao;

import domain.Cliente;

import java.util.Collection;

public interface IClienteDAO {
    public Boolean cadastrar(Cliente cliente);

    public Boolean excluir(Long cpf);

    public void alterar(Long cpf ,Cliente cliente);

    public Cliente consultar(Long cpf);

    public Collection<Cliente> buscarTodos();
}
