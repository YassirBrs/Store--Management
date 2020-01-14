package UI;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.UnknownHostException;

public class HomePage extends javafx.application.Application {

    Pane root = new Pane();
    Scene scene = new Scene(root, 1300, 800);

    GridPane grid;
    VBox container;
    Label title;

    Button categorie, produit, client, vente, tranfert;

    public void init() {
        grid = new GridPane();
        container = new VBox();
        title = new Label("Store Management");
        categorie = new Button("Gestion de Catégories");
        produit = new Button("Gestion de Produits");
        client = new Button("Gestion de Clients");
        vente = new Button("Gestion de Ventes");
        tranfert = new Button("Gestion de Transactions");
    }

    public void draw() {
        title.getStyleClass().add("app_title");

        grid.add(produit, 0, 0);
        grid.add(categorie, 0, 1);
        grid.add(client, 0, 2);
        grid.add(vente, 0, 3);
        grid.add(tranfert, 0, 4);

        categorie.setPrefWidth(400);
        produit.setPrefWidth(400);
        client.setPrefWidth(400);
        vente.setPrefWidth(400);
        tranfert.setPrefWidth(400);

        categorie.setPrefHeight(100);
        produit.setPrefHeight(100);
        client.setPrefHeight(100);
        vente.setPrefHeight(100);
        tranfert.setPrefHeight(100);

        categorie.getStyleClass().add("menu_buttons");
        produit.getStyleClass().add("menu_buttons");
        client.getStyleClass().add("menu_buttons");
        vente.getStyleClass().add("menu_buttons");
        tranfert.getStyleClass().add("menu_buttons");

        grid.setHgap(10);
        grid.setVgap(10);

        grid.getStyleClass().add("magrid");

        container.getStyleClass().add("center_elem");
        container.setPrefWidth(1330);
        container.setPrefHeight(820);

        container.getChildren().addAll(title, grid);
        root.getChildren().addAll(container);
    }

    public void actions(Stage window) {
        categorie.setOnAction(e -> {
            Gestion_Categorie.IHM catIhm = new Gestion_Categorie.IHM();
            catIhm.start(window);
        });

        produit.setOnAction(e -> {
            Gestion_Produit.IHM proIhm = new Gestion_Produit.IHM();
            proIhm.start(window);
        });

        client.setOnAction(e -> {
            Gestion_Client.IHM cliIhm = new Gestion_Client.IHM();
            cliIhm.start(window);
        });

        vente.setOnAction(e -> {
            Gestion_Vente.IHM venIhm = new Gestion_Vente.IHM();
            venIhm.start(window);
        });
        tranfert.setOnAction(e -> {
            Gestion_Transfert.IHM transfertIhm = new Gestion_Transfert.IHM();
            transfertIhm.start(window);
        });
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Store Management");
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
