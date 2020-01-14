package Gestion_Categorie;

import java.util.List;
import java.util.function.Predicate;

import UI.Notification;
import UI.Header;
import UI.Navbar;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
    Label nomLabel;
    Label descLabel;
    TextField idTextField;
    TextField nomTextField;
    TextField descTextField;
    Button addButton;
    Button editButton;
    Button deleteButton;
    CategorieDAOIMPL dao;
    Label statusLabel;
    // Attributs de la table view
    TableView<Categorie> table;
    // Les columns   
    TableColumn<Categorie, Integer> idColumn;
    TableColumn<Categorie, String> nomColumn;
    TableColumn<Categorie, String> descColumn;

    ObservableList<Categorie> listOfCategories;
    List<Categorie> categories;
    Notification forms = new Notification("catégories");

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
        idColumn = new TableColumn<Categorie, Integer>("Id");
        nomColumn = new TableColumn<Categorie, String>("Nom");
        descColumn = new TableColumn<Categorie, String>("Description");
        nomColumn.setPrefWidth(170);
        descColumn.setPrefWidth(270);
        this.statusLabel = new Label("copyright © 2020 _ By :Yassir BOURAS");
        this.idTextField = new TextField();
        this.descTextField = new TextField();
        this.nomTextField = new TextField();
        this.addButton = new Button("Ajouter");
        this.editButton = new Button("Modifier");
        this.deleteButton = new Button("Supprimer");
        this.descLabel = new Label("Description");
        this.nomLabel = new Label("Nom");
        Pane header= Header.initt();
        boxTop.getChildren().addAll(header, (new Navbar(window, "categorie")).getHeader());
        boxTop.setAlignment(Pos.CENTER);

        leftBox.getChildren().addAll(addButton, editButton, deleteButton);
        leftBox.setSpacing(10);

        centerPane.add(idLabel, 0, 3);
        centerPane.add(idTextField, 1, 3);
        centerPane.add(nomLabel, 0, 4);
        centerPane.add(nomTextField, 1, 4);
        centerPane.add(descLabel, 0, 5);
        centerPane.add(descTextField, 1, 5);
        centerPane.setPadding(new Insets(10));


        idLabel.getStyleClass().add("labels");
        nomLabel.getStyleClass().add("labels");
        descLabel.getStyleClass().add("labels");
        idTextField.setDisable(true);

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

        rightBox.getChildren().add(table);
        bottom.setAlignment(Pos.CENTER);
        bottom.getStyleClass().add("button_box");
        this.categories = dao.findAll();
        listOfCategories = getUserList();
        root.getStyleClass().add("bg_coloring");

    }

    private ObservableList<Categorie> getUserList() {
        ObservableList<Categorie> list = FXCollections.observableArrayList();

        this.categories = dao.findAll();
        for (Categorie c : categories) {
            list.add(c);
        }
        return list;
    }

    private void updateListItems() {

        listOfCategories.removeAll(listOfCategories);
        listOfCategories.addAll(getUserList());

    }

    private void initTable() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nomColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        this.descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        this.table.setItems(listOfCategories);
        table.getColumns().addAll(idColumn, nomColumn, descColumn);
    }

    private void clearFields() {
        this.idTextField.setText("");
        this.descTextField.setText("");
        this.nomTextField.setText("");
    }

    @Override
    public void start(Stage primaryStage) {
        this.dao = new CategorieDAOIMPL();
        initPane();
        initElement(primaryStage);
        initTable();
        root.setTop(boxTop);
        root.setLeft(leftBox);
        root.setCenter(centerPane);
        root.setRight(rightBox);
        root.setBottom(bottom);

        // Mise à jour de la table après la recherche
        FilteredList<Categorie> filteredReports = new FilteredList<>(listOfCategories);
        searchTextField.textProperty().addListener((observavleValue, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                filteredReports.setPredicate(null);
            } else {
                final String lowerCaseFilter = newValue.toLowerCase();

                filteredReports.setPredicate((Predicate<? super Categorie>) Category -> {
                    return Category.getNom().contains(newValue) || Category.getDescription().contains(newValue);
                });
            }
        });

        SortedList<Categorie> sortedProducts = new SortedList<>(filteredReports);
        sortedProducts.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedProducts);
        // Récupération de la ligne courante

        table.setRowFactory(tv -> {
            TableRow<Categorie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Categorie rowData = row.getItem();
                    idTextField.setText(Long.toString(rowData.getId()));
                    idTextField.setDisable(true);
                    nomTextField.setText(rowData.getNom());
                    descTextField.setText(rowData.getDescription());
                }
            });
            return row;
        });
        addButton.setOnAction(e -> {
            if(! Notification.isEmptyFields(nomTextField, descTextField)){
                Categorie p = new Categorie(0, nomTextField.getText(), descTextField.getText());
                System.out.println(p.getNom());
                dao.create(p);
                clearFields();
                updateListItems();
            }else{
                forms.setType(Alert.AlertType.WARNING);
                forms.shows("Veuillez remplir tous les champs");
            }
        });

        editButton.setOnAction(e -> {
            if(! Notification.isEmptyFields(idTextField, nomTextField, descTextField)){
                Categorie produtResult = dao.find(Integer.parseInt(idTextField.getText()));
                dao.update(produtResult, nomTextField.getText(), descTextField.getText());
                updateListItems();
                clearFields();
            }else{
                forms.setType(Alert.AlertType.WARNING);
                forms.shows("Veuillez remplir tous les champs");
            }
        });

        deleteButton.setOnAction(e -> {
                if(! Notification.isEmptyFields(idTextField)){
                    if(forms.confirm("Êtes vous sûr de supprimer cette catégorie?")){
                        Categorie rs = dao.find(Integer.parseInt(idTextField.getText()));
                        dao.delete(rs);
                        updateListItems();
                        clearFields();
                    }
                }else{
                    forms.setType(Alert.AlertType.WARNING);
                    forms.shows("Veuillez Séléctionner une catégorie");
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
