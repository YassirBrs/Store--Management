package Gestion_Bank;


public class Bank {
    private int id;
    private String nom;


    public Bank(int id, String nom) {
        this.id = id;
        this.nom = nom;
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

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}