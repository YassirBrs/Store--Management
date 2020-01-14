package Gestion_Transfert;


import Gestion_Paiement.Paiement;

public class Transfert {
    private int id;
    private String nom;
    private String prenom;
    private String rib;
    private String bank;
    private double montant;
    private String date;

    public Transfert(String nom, String prenom, String rib, String bank, double montant, String date) {
        this.nom = nom;
        this.prenom = prenom;
        this.rib = rib;
        this.bank = bank;
        this.montant = montant;
        this.date = date;
    }

    public Transfert(int id, String nom, String prenom, String rib, String bank, double montant, String date) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.rib = rib;
        this.bank = bank;
        this.montant = montant;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
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

    @Override
    public String toString() {
        return "Transfert{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", rib='" + rib + '\'' +
                ", bank='" + bank + '\'' +
                ", montant=" + montant +
                ", date='" + date + '\'' +
                '}';
    }
}
