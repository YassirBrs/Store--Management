package UI;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Navbar {
    private String activeWindow;
    private Stage window;
    Pane root = new Pane();
    HBox liens;
    Button categories, produits, clients, ventes,transfert;
    
    public Navbar(Stage window, String activeWindow){
        this.activeWindow = activeWindow;
        this.window = window;
    }
    
    public void initHeader(){
        liens = new HBox();
        categories = new Button("Catégories");
        produits = new Button("Produits");
        clients = new Button("Clients");
        ventes = new Button("Ventes");
        transfert = new Button("Transactions");
    }
    
    public void applyStyles(){
        this.categories.getStyleClass().add("navbar_btn");
        this.produits.getStyleClass().add("navbar_btn");
        this.clients.getStyleClass().add("navbar_btn");
        this.ventes.getStyleClass().add("navbar_btn");
        this.transfert.getStyleClass().add("navbar_btn");

        if(this.activeWindow.equals("categorie")){
            this.categories.getStyleClass().add("btn_active");
        }else if(this.activeWindow.equals("produit")){
            this.produits.getStyleClass().add("btn_active");
        }else if(this.activeWindow.equals("client")){
            this.clients.getStyleClass().add("btn_active");
        }else if(this.activeWindow.equals("vente") || this.activeWindow.equals("payement")){
            this.ventes.getStyleClass().add("btn_active");
        }else if(this.activeWindow.equals("transfert") ){
            this.transfert.getStyleClass().add("btn_active");
        }
    }
    
    public void buttons(){
        categories.setOnAction(e -> {
            Gestion_Categorie.IHM catIhm = new Gestion_Categorie.IHM();
            catIhm.start(window);
        });
        
        produits.setOnAction(e -> {
            Gestion_Produit.IHM proIhm = new Gestion_Produit.IHM();
            proIhm.start(window);
        });
        
        clients.setOnAction(e -> {
            Gestion_Client.IHM cliIhm = new Gestion_Client.IHM();
            cliIhm.start(window);
        });
        
        ventes.setOnAction(e -> {
            Gestion_Vente.IHM venIhm = new Gestion_Vente.IHM();
            venIhm.start(window);
        });transfert.setOnAction(e -> {
            Gestion_Transfert.IHM transfertIhm = new Gestion_Transfert.IHM();
            transfertIhm.start(window);
        });
    }
    
    public void draw(){
        liens.getChildren().addAll( produits,categories, clients, ventes,transfert);
        root.getChildren().add(liens);
    }

    public Pane getHeader(){
        initHeader();
        applyStyles();
        draw();
        buttons();
        return root;
    }
    
}
