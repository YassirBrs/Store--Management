package Gestion_Paiement;

import Gestion_Vente.Vente;

public class Paiement {

    private int id;
    private Vente vente;
    private double montant;
    private String date;
    private String status;
    private String type;

    public Paiement(int id, Vente vente, double montant, String date, String status, String type) {
        this.id = id;
        this.vente = vente;
        this.montant = montant;
        this.date = date;
        this.status = status;
        this.type = type;
    }

    public Paiement(Vente vente, double montant, String date, String status,  String type) {
        this.id = 0;
        this.vente = vente;
        this.montant = montant;
        this.date = date;
        this.status = status;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Paiement{" + "id=" + id + ", vente=" + vente + ", montant=" + montant + ", date=" + date + ", propietaire=" + status + ", type=" + type + '}';
    }

}
