package UI;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Navbar {
    private String activeWindow;
    private Stage window;
    Pane root = new Pane();
    HBox links;
    Button categories, products, clients, ventes;
    
    public Navbar(Stage window, String activeWindow){
        this.activeWindow = activeWindow;
        this.window = window;
    }
    
    public void initHeader(){
        links = new HBox();
        categories = new Button("Catégories");
        products = new Button("Produits");
        clients = new Button("Clients");
        ventes = new Button("Ventes");
    }
    
    public void applyStyles(){
        this.categories.getStyleClass().add("header_btn");
        this.products.getStyleClass().add("header_btn");
        this.clients.getStyleClass().add("header_btn");
        this.ventes.getStyleClass().add("header_btn");
        
        if(this.activeWindow.equals("category")){
            this.categories.getStyleClass().add("btn_active");
        }else if(this.activeWindow.equals("product")){
            this.products.getStyleClass().add("btn_active");
        }else if(this.activeWindow.equals("client")){
            this.clients.getStyleClass().add("btn_active");
        }else if(this.activeWindow.equals("sale") || this.activeWindow.equals("payement")){
            this.ventes.getStyleClass().add("btn_active");
        }
    }
    
    public void actions(){
        categories.setOnAction(e -> {
            Gestion_Categorie.IHM catIhm = new Gestion_Categorie.IHM();
            catIhm.start(window);
        });
        
        products.setOnAction(e -> {
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
        });
    }
    
    public void draw(){
        links.getChildren().addAll( products,categories, clients, ventes);
        root.getChildren().add(links);
    }

    public Pane getHeader(){
        initHeader();
        applyStyles();
        draw();
        actions();
        return root;
    }
    
}
