package Gestion_Vente;

import Gestion_Client.Client;

import java.io.Serializable;

public class Vente implements Serializable {

    private int id;
    private Client client;
    private String date;
    private double total;
    private String imprimer;

    public String getImprimer() {
        return imprimer;
    }

    public void setImprimer(String imprimer) {
        this.imprimer = imprimer;
    }

    public Vente(int id, Client client, String date) {
        this.id = id;
        this.client = client;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total){
        this.total = total;
    }
    
}
