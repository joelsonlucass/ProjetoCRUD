package com.ems.bdsqlitefull.pojo;

import java.io.Serializable;

public class Pedidos implements Serializable {

    private int id;
    private String cliente;
    private String data;
    private String descricao;
    private String valor;

    /**
     * Método construtor vazio
     */
    public Pedidos() {
    }

    /**
     * Método construtor da classe com assinatura
     */
    public Pedidos(String cliente, String data, String descricao, String valor) {
        this.cliente = cliente;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
    }

    /**
     * Método construtor da classe com assinatura
     */
    public Pedidos(int id, String cliente, String data, String descricao, String valor) {
        this.cliente = cliente;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.id = id;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Método sobrescrito para retornar o nome do cliente + id pedido na ListView
     */
    @Override
    public String toString() {
        return "Nº PEDIDO: " + id ;
    }

    /**
     * Método que retorna todos os dados de uma só vez
     *
     * @return
     */
    public String getDados() {
        return "ID: " + id + "\n" +
                "CLIENTE: " + cliente + "\n" +
                "DATA: " + data + "\n" +
                "DESCRICAO: " + descricao + "\n" +
                "VALOR: " + valor;
    }
}
