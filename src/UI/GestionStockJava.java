package UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.UnknownHostException;

public class GestionStockJava extends Application {

    Pane root = new Pane();
    Scene scene = new Scene(root, 1300, 800);
    
    GridPane grid;
    VBox container;
    Label title;
    
    Button category, product, client, sale;

    public void init(){
        grid = new GridPane();
        container = new VBox();
        title = new Label("Store Management");
        category = new Button("Gestion de Catégories");
        product = new Button("Gestion de Produits");
        client = new Button("Gestion de Clients");
        sale = new Button("Gestion de Ventes");
    }
    
    public void draw(){
        title.getStyleClass().add("app_title");
        
        grid.add(product, 0, 0);
        grid.add(category, 0, 1);
        grid.add(client, 0, 2);
        grid.add(sale, 0, 3);
        
        category.setPrefWidth(400);
        product.setPrefWidth(400);
        client.setPrefWidth(400);
        sale.setPrefWidth(400);
        
        category.setPrefHeight(100);
        product.setPrefHeight(100);
        client.setPrefHeight(100);
        sale.setPrefHeight(100);
        
        category.getStyleClass().add("menu_buttons");
        product.getStyleClass().add("menu_buttons");
        client.getStyleClass().add("menu_buttons");
        sale.getStyleClass().add("menu_buttons");
        
        grid.setHgap(10);
        grid.setVgap(10);
        
        grid.getStyleClass().add("magrid");
        
        container.getStyleClass().add("center_elem");
        container.setPrefWidth(1330);
        container.setPrefHeight(820);
//        container.setSpacing(50);
        
        container.getChildren().addAll(title, grid);
        root.getChildren().addAll(container);
    }
    
    public void actions(Stage window){
        category.setOnAction(e -> {
            Gestion_Categorie.IHM catIhm = new Gestion_Categorie.IHM();
            catIhm.start(window);
        });
        
        product.setOnAction(e -> {
            Gestion_Produit.IHM proIhm = new Gestion_Produit.IHM();
            proIhm.start(window);
        });
        
        client.setOnAction(e -> {
            Gestion_Client.IHM cliIhm = new Gestion_Client.IHM();
            cliIhm.start(window);
       });
        
        sale.setOnAction(e -> {
            Gestion_Vente.IHM venIhm = new Gestion_Vente.IHM();
            venIhm.start(window);
        });
    }
    
    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Gestion de magazin - Menu Principale");
        window.setResizable(false);
        
        init();
        draw();
        actions(window);
        
        scene.getStylesheets().add("style.css");
        window.setScene(scene);

        window.show();
    }

    public static void main(String[] args) throws UnknownHostException {
        launch(args);
    }
}
