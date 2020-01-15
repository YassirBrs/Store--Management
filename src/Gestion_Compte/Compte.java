package Gestion_Compte;

import Gestion_Client.Client;

import java.io.Serializable;

public class Compte implements Serializable {
    private int id;
    public Client client;
    private Double solde;

    public Compte(Client client, Double solde) {
        this.client = client;
        this.solde = solde;
    }

    public Compte(int id, Double solde) {
        this.id = id;
        this.solde = solde;
    }

    public Compte(int id, Client client, Double solde) {
        this.id = id;
        this.client = client;
        this.solde = solde;
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

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }
}
