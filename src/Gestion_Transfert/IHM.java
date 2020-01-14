package Gestion_Transfert;

import java.util.List;
import java.util.function.Predicate;

import Gestion_Paiement.Paiement;
import UI.Header;
import UI.Navbar;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class IHM extends Application {

    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1300, 800);

    private VBox boxTop;
    private HBox bottom;
    private VBox leftBox;
    private VBox centerPane;
    private VBox rightBox;
    private TextField searchTextField;


    TransfertDao dao;
    Label statusLabel;
    // Attributs de la table view
    TableView<Transfert> table;
    // Les columns   
    TableColumn<Transfert, Integer> idColumn;
    TableColumn<Transfert, Paiement> nameColumn;
    TableColumn<Transfert, Paiement> prenomColumn;
    TableColumn<Transfert, Paiement> compteColumn;
    TableColumn<Transfert, Paiement> bankColumn;
    TableColumn<Transfert, Paiement> montantColumn;
    TableColumn<Transfert, String> dateColumn;

    // Les resultats
    ObservableList<Transfert> listOfTransfers;
    List<Transfert> transfers;


    private void initPane() {
        this.bottom = new HBox();
        searchTextField = new TextField();
        this.centerPane = new VBox();
        this.boxTop = new VBox();
        this.leftBox = new VBox();
        this.rightBox = new VBox();
    }

    private void initElement(Stage window) {
        idColumn = new TableColumn<>("Id");
        nameColumn = new TableColumn<>("Nom");
        prenomColumn = new TableColumn<>("Prenom");
        compteColumn = new TableColumn<>("RIB");
        bankColumn = new TableColumn<>("Bank");
        montantColumn = new TableColumn<>("Montant");
        dateColumn = new TableColumn<>("Date");
        this.statusLabel = new Label("copyright © 2020 _ By :Yassir BOURAS");

        Pane header = Header.initt();
        boxTop.getChildren().addAll(header, (new Navbar(window, "transfert")).getHeader());
        boxTop.setAlignment(Pos.CENTER);


        centerPane.setPadding(new Insets(10));

        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.setPadding(new Insets(10));
        boxTop.getStyleClass().add("top_box_style");
        leftBox.getStyleClass().add("custom_back");

        this.bottom.getChildren().add(statusLabel);
//        this.rightBox.getChildren().add(searchTextField);
        this.rightBox.setPadding(new Insets(10));
        this.rightBox.getStyleClass().add("right");

        this.searchTextField.setPromptText("-----------Chercher------------");
        this.statusLabel.setAlignment(Pos.CENTER);
        this.statusLabel.getStyleClass().add("copyright");
        this.table = new TableView<>();

        idColumn.setPrefWidth(100);
        nameColumn.setPrefWidth(150);
        prenomColumn.setPrefWidth(150);
        compteColumn.setPrefWidth(200);
        bankColumn.setPrefWidth(200);
        montantColumn.setPrefWidth(200);
        dateColumn.setPrefWidth(200);


        centerPane.getChildren().addAll(searchTextField, table);
        bottom.setAlignment(Pos.CENTER);
        bottom.getStyleClass().add("button_box");
        this.transfers = dao.findAll();
        listOfTransfers = getTransfertList();
        root.getStyleClass().add("bg_coloring");


    }

    private ObservableList<Transfert> getTransfertList() {
        ObservableList<Transfert> list = FXCollections.observableArrayList();

        this.transfers = dao.findAll();
        for (Transfert t : transfers) {
            list.add(t);
            t.toString();
        }
        return list;
    }

    private void updateListItems() {

        listOfTransfers.removeAll(listOfTransfers);
        listOfTransfers.addAll(getTransfertList());

    }

    private void initTable() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        this.prenomColumn.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        this.compteColumn.setCellValueFactory(new PropertyValueFactory<>("rib"));
        this.bankColumn.setCellValueFactory(new PropertyValueFactory<>("Bank"));
        this.montantColumn.setCellValueFactory(new PropertyValueFactory<>("Montant"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        this.table.setItems(listOfTransfers);
        table.getColumns().addAll(idColumn, nameColumn, prenomColumn, compteColumn, bankColumn, montantColumn, dateColumn);
    }


    @Override
    public void start(Stage primaryStage) {
        this.dao = new TransfertDAOIMPL();
        initPane();
        initElement(primaryStage);
        initTable();
        root.setTop(boxTop);
        root.setLeft(leftBox);
        root.setCenter(centerPane);
        root.setRight(rightBox);
        root.setBottom(bottom);

        // Mise à jour de la table après la recherche
        FilteredList<Transfert> filteredReports = new FilteredList<>(listOfTransfers);
        searchTextField.textProperty().addListener((observavleValue, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                filteredReports.setPredicate(null);
            } else {
                filteredReports.setPredicate((Predicate<? super Transfert>) Transfert -> {
                    return Transfert.getNom().contains(newValue) || Transfert.getPrenom().contains(newValue) || Transfert.getBank().contains(newValue);
                });
            }
        });

        SortedList<Transfert> sortedTransfers = new SortedList<>(filteredReports);
        sortedTransfers.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedTransfers);

        primaryStage.setTitle("Store Management");

        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
