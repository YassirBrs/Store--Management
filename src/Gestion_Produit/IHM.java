package Gestion_Produit;

import Gestion_Categorie.Categorie;
import java.util.List;
import java.util.function.Predicate;

import UI.Notification;
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
    private GridPane centerPane;
    private VBox rightBox;
    private TextField searchTextField;
    Label idLabel;
    Label desLabel;
    Label prixLabel;
    Label categorieLabel;
    ComboBox<Categorie> categorieComboBox;
    TextField idTextField;
    TextField desTextField;
    TextField prixTextField;
    Button addButton;
    Button editButton;
    Button deleteButton;
    ProduitDAOIMPL dao;
    ObservableList<Categorie> categories;
    Label statusLabel;
    // Attributs de la table view
    TableView<Produit> table;
    // Les columns   
    TableColumn<Produit, Integer> idColumn;
    TableColumn<Produit, String> desColumn;
    TableColumn<Produit, Double> prixColumn;
    TableColumn<Produit, Categorie> categoryColumn;

    ObservableList<Produit> listOfProduits;
    List<Produit> produits;

    Notification forms = new Notification("produits");

    private void initPane() {
        this.bottom = new HBox();
        searchTextField = new TextField();
        this.centerPane = new GridPane();
        this.boxTop = new VBox();
        this.leftBox = new VBox();
        this.idLabel = new Label("Id");
        this.rightBox = new VBox();
    }

    private void initElement(Stage window) {
        idColumn = new TableColumn<Produit, Integer>("Id");
        desColumn = new TableColumn<Produit, String>("Désignation");
        prixColumn = new TableColumn<Produit, Double>("Prix");
        categoryColumn = new TableColumn<Produit, Categorie>("Catégorie");
        desColumn.setPrefWidth(130);
        prixColumn.setPrefWidth(130);
        categoryColumn.setPrefWidth(130);

        this.categorieLabel = new Label("Catégorie");
        this.statusLabel = new Label("copyright © 2020 _ By :Yassir BOURAS");
        this.idTextField = new TextField();
        this.desTextField = new TextField();
        this.prixTextField = new TextField();
        this.addButton = new Button("Ajouter");
        this.editButton = new Button("Modifier");
        this.deleteButton = new Button("Supprimer");
        this.desLabel = new Label("Designation");
        this.prixLabel = new Label("Prix");
        idTextField.setDisable(true);
        Pane header= Header.initt();
        boxTop.getChildren().addAll(header, (new Navbar(window, "product")).getHeader());
        boxTop.setAlignment(Pos.CENTER);

        leftBox.getChildren().addAll(addButton, editButton, deleteButton);
        leftBox.setSpacing(10);

        centerPane.add(idLabel, 0, 2);
        centerPane.add(idTextField, 1, 2);
        centerPane.add(desLabel, 0, 3);
        centerPane.add(desTextField, 1, 3);
        centerPane.add(prixLabel, 0, 4);
        centerPane.add(prixTextField, 1, 4);
        centerPane.add(categorieLabel, 0, 5);
        categorieComboBox = new ComboBox<>();
        categories = FXCollections.observableList(dao.getCategories());
        categorieComboBox.setItems(categories);
        categorieComboBox.getSelectionModel().select(2);
        centerPane.add(categorieComboBox, 1, 5);

        centerPane.setPadding(new Insets(10));


        idLabel.getStyleClass().add("labels");
        desLabel.getStyleClass().add("labels");
        prixLabel.getStyleClass().add("labels");

        addButton.getStyleClass().add("buttons");
        editButton.getStyleClass().add("buttons");
        deleteButton.getStyleClass().add("buttons");

        centerPane.setVgap(10);
        centerPane.setHgap(10);
        addButton.setPrefWidth(100);
        editButton.setPrefWidth(100);
        deleteButton.setPrefWidth(100);
        editButton.setPrefHeight(40);
        addButton.setPrefHeight(40);
        deleteButton.setPrefHeight(40);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.setPadding(new Insets(10));
        boxTop.getStyleClass().add("top_box_style");
        leftBox.getStyleClass().add("custom_back");
        this.bottom.getChildren().add(statusLabel);
        this.rightBox.getChildren().add(searchTextField);
        this.rightBox.setPadding(new Insets(10));
        this.rightBox.getStyleClass().add("right");

        this.searchTextField.setPromptText("-----------Chercher------------");
        this.statusLabel.setAlignment(Pos.CENTER);
        this.statusLabel.getStyleClass().add("copyright");
        this.table = new TableView<>();

        table.getColumns().addAll(idColumn, desColumn, prixColumn, categoryColumn);
        rightBox.getChildren().add(table);
        bottom.setAlignment(Pos.CENTER);
        bottom.getStyleClass().add("button_box");
        this.produits = dao.findAll();
        listOfProduits = getUserList();

        root.getStyleClass().add("bg_coloring");
    }

    private ObservableList<Produit> getUserList() {
        ObservableList<Produit> list = FXCollections.observableArrayList();

        this.produits = dao.findAll();

        produits.forEach((p) -> {
            list.add(p);
        });
        return list;
    }

    private void updateListItems() {

        listOfProduits.removeAll(listOfProduits);
        listOfProduits.addAll(getUserList());

    }

    private void initTable() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.desColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        this.prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        this.categoryColumn.setCellValueFactory(new PropertyValueFactory<>("catid"));
        this.table.setItems(listOfProduits);
    }

    private void clearFields() {
        this.idTextField.setText("");
        this.desTextField.setText("");
        this.prixTextField.setText("");
    }

    @Override
    public void start(Stage primaryStage) {
        this.dao = new ProduitDAOIMPL();
        initPane();
        initElement(primaryStage);
        initTable();
        root.setTop(boxTop);
        root.setLeft(leftBox);
        root.setCenter(centerPane);
        root.setRight(rightBox);
        root.setBottom(bottom);

        // Récupération de la ligne courante
        table.setRowFactory(tv -> {
            TableRow<Produit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Produit rowData = row.getItem();
                    idTextField.setText(Long.toString(rowData.getId()));
                    idTextField.setDisable(true);
                    desTextField.setText(rowData.getDesignation());
                    prixTextField.setText(Double.toString(rowData.getPrix()));
                    for (Categorie c : categorieComboBox.getItems()) {
                        if (c.getId() == rowData.getCatid().getId()) {
                            categorieComboBox.getSelectionModel().select(c);
                        }
                    }
                }
            });
            return row;
        });

        // Mise à jour de la table après la recherche
        FilteredList<Produit> filteredReports = new FilteredList<>(listOfProduits);
        searchTextField.textProperty().addListener((observavleValue, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                filteredReports.setPredicate(null);
            } else {
                final String lowerCaseFilter = newValue.toLowerCase();

                filteredReports.setPredicate((Predicate<? super Produit>) Product -> {
                    return Product.getDesignation().contains(newValue);
                });
            }
        });
        SortedList<Produit> sortedProduits = new SortedList<>(filteredReports);
        sortedProduits.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedProduits);

        addButton.setOnAction(e -> {
            if(! Notification.isEmptyFields(desTextField, prixTextField) && categorieComboBox.getValue() != null){
                Produit p = new Produit(0, desTextField.getText(), Double.parseDouble(prixTextField.getText()), categorieComboBox.getValue());
                dao.create(p);
                clearFields();
                updateListItems();
            }else{
                forms.setType(Alert.AlertType.WARNING);
                forms.shows("Veuillez remplir tous les champs");
            }
        });

        editButton.setOnAction(e -> {
            if(! Notification.isEmptyFields(idTextField, desTextField, prixTextField) && categorieComboBox.getValue() != null){
                Produit produtResult = dao.find(Integer.parseInt(idTextField.getText()));
                dao.update(produtResult, desTextField.getText(), Double.parseDouble(prixTextField.getText()), categorieComboBox.getValue());
                updateListItems();
                clearFields();
            }else{
                forms.setType(Alert.AlertType.WARNING);
                forms.shows("Veuillez séléctionner un produit et remplir tous les champs");
            }
        });

        deleteButton.setOnAction(e -> {
            if(! Notification.isEmptyFields(idTextField)){
                if(forms.confirm("Êtes vous sûr de supprimer c produit?")){
                    Produit rs = dao.find(Integer.parseInt(idTextField.getText()));
                    dao.delete(rs);
                    updateListItems();
                    clearFields();
                }
            }else{
                forms.setType(Alert.AlertType.WARNING);
                forms.shows("Veuillez séléctionner un produit");
            }
        });

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
