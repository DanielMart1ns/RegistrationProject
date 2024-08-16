package domain;

import java.util.Objects;

public class Cliente {
    //Declaring properties
    private String nome;
    private Long cpf;
    private Long tel;
    private String endereco;
    private Integer numero;
    private String cidade;
    private String estado;

    //Constructor
    public Cliente(String nome, String cpf, String tel, String endereco, String numero, String cidade, String estado){
        this.nome = nome;
        this.cpf = Long.valueOf(cpf.trim());
        this.tel = Long.valueOf(tel.trim());
        this.endereco = endereco;
        this.numero = Integer.valueOf(numero.trim());
        this.cidade = cidade;
        this.estado = estado;
    }

    //Setters
    public void setNome(String nome) {this.nome = nome;}

    public void setCpf(Long cpf) {this.cpf = cpf;}

    public void setTel(Long tel) {this.tel = tel;}

    public void setEndereco(String endereco) {this.endereco = endereco;}

    public void setNumero(Integer numero) {this.numero = numero;}

    public void setCidade(String cidade) {this.cidade = cidade;}

    public void setEstado(String estado) {this.estado = estado;}

    //Getters
    public String getNome() {return nome;}

    public Long getCpf() {return cpf;}

    public Long getTel() {return tel;}

    public String getEndereco() {return endereco;}

    public Integer getNumero() {return numero;}

    public String getCidade() {return cidade;}

    public String getEstado() {return estado;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    public String toString(){
        return "Cliente: " + this.nome + ", CPF: " + this.cpf;
    }
}
