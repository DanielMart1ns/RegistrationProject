package dao;

import domain.Cliente;

import java.util.Collection;
import java.util.HashMap;

import java.util.Map;

public class ClienteMapDAO implements IClienteDAO {

    //Declarando o tipo do nosso map
    private Map<Long, Cliente> map;

    //Atribuindo ao construtor a nossa implementação
    public ClienteMapDAO() {
        this.map = new HashMap<Long, Cliente>();
    }

    @Override
    public Boolean cadastrar(Cliente cliente) {
        //se encontrar a mesma chave no map, retorna false
        if(this.map.containsKey(cliente.getCpf())){
            return false;
        }

        //se não, cadastramos nosso cliente, atribuindo o cpf como key e seu valor
        this.map.put(cliente.getCpf(), cliente);
        return true;
    }

    @Override
    public Cliente consultar(Long cpf) {
        return this.map.get(cpf);
    }

    @Override
    public Boolean excluir(Long cpf) {
        Cliente clienteCadastrado = this.map.get(cpf);

        if(clienteCadastrado != null){
            this.map.remove(clienteCadastrado.getCpf(), clienteCadastrado);
            return true;
        }

        return false;
    }

    @Override
    public void alterar(Long cpf, Cliente cliente) {
        //Atribuindo dados atuais ao meu objeto a ser alterado
        Cliente clienteCadastrado = this.map.get(cpf);

        //Removendo chave anterior e deletando esse meu objeto do map
        this.map.remove(cpf);

        //Sobrescrevendo novos dados ao objeto
        clienteCadastrado.setNome(cliente.getNome());
        clienteCadastrado.setCpf(cliente.getCpf());
        clienteCadastrado.setTel(cliente.getTel());
        clienteCadastrado.setNumero(cliente.getNumero());
        clienteCadastrado.setEndereco(cliente.getEndereco());
        clienteCadastrado.setCidade(cliente.getCidade());
        clienteCadastrado.setEstado(cliente.getEstado());

        //Substituindo para ao novo valor
        this.map.put(cliente.getCpf(), clienteCadastrado);
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return this.map.values();
    }
}
