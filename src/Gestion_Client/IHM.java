package Gestion_Client;

import java.util.List;
import java.util.function.Predicate;

import UI.Notification;
import UI.Header;
import UI.Navbar;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    // Les labels
    Label idLabel;
    Label nameLabel;
    Label lnameLabel;
    Label telephoneLabel;
    Label cityLabel;

    // Les champs
    TextField idTextField;
    TextField nameTextField;
    TextField lnameTextField;
    TextField telephoneTextField;
    TextField cityTextField;

    // Les buttons
    Button addButton;
    Button editButton;
    Button deleteButton;
    ClientDAOIMPL dao;
    Label statusLabel;
    // Attributs de la table view
    TableView<Client> table;
    // Les columns   
    TableColumn<Client, Integer> idColumn;
    TableColumn<Client, String> nameColumn;
    TableColumn<Client, String> lnameColumn;
    TableColumn<Client, String> telephoneColumn;
    TableColumn<Client, String> cityColumn;

    // Les resultats
    ObservableList<Client> listOfClients;
    List<Client> clients;

    Notification forms = new Notification("clients");

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
        idColumn = new TableColumn<>("Id");
        nameColumn = new TableColumn<>("Nom");
        lnameColumn = new TableColumn<>("Prenom");
        telephoneColumn = new TableColumn<>("Telephone");
        cityColumn = new TableColumn<>("Ville");
        this.statusLabel = new Label("copyright © 2020 _ By :Yassir BOURAS");
        this.idTextField = new TextField();
        this.nameTextField = new TextField();
        this.lnameTextField = new TextField();
        this.telephoneTextField = new TextField();
        this.cityTextField = new TextField();

        this.addButton = new Button("Ajouter");
        this.editButton = new Button("Modifier");
        this.deleteButton = new Button("Supprimer");
        this.nameLabel = new Label("Nom");
        this.lnameLabel = new Label("Prenom");
        this.telephoneLabel = new Label("Telephone");
        this.cityLabel = new Label("Ville");
        Pane header= Header.initt();
        boxTop.getChildren().addAll(header, (new Navbar(window, "client")).getHeader());
        boxTop.setAlignment(Pos.CENTER);



        leftBox.getChildren().addAll(addButton, editButton, deleteButton);
        leftBox.setSpacing(10);

        centerPane.add(idLabel, 0, 0);
        centerPane.add(idTextField, 1, 0);
        centerPane.add(nameLabel, 0, 1);
        centerPane.add(nameTextField, 1, 1);
        centerPane.add(lnameLabel, 0, 2);
        centerPane.add(lnameTextField, 1, 2);
        centerPane.add(telephoneLabel, 0, 3);
        centerPane.add(telephoneTextField, 1, 3);
        centerPane.add(cityLabel, 0, 4);
        centerPane.add(cityTextField, 1, 4);

        centerPane.setPadding(new Insets(10));


        idLabel.getStyleClass().add("labels");
        nameLabel.getStyleClass().add("labels");
        lnameLabel.getStyleClass().add("labels");
        telephoneLabel.getStyleClass().add("labels");
        cityLabel.getStyleClass().add("labels");

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

        telephoneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    telephoneTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        rightBox.getChildren().add(table);
        bottom.setAlignment(Pos.CENTER);
        bottom.getStyleClass().add("button_box");
        this.clients = dao.findAll();
        listOfClients = getUserList();
        root.getStyleClass().add("bg_coloring");

        idTextField.setDisable(true);


    }

    private ObservableList<Client> getUserList() {
        ObservableList<Client> list = FXCollections.observableArrayList();

        this.clients = dao.findAll();
        for (Client c : clients) {
            list.add(c);
        }
        return list;
    }

    private void updateListItems() {

        listOfClients.removeAll(listOfClients);
        listOfClients.addAll(getUserList());

    }

    private void initTable() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        this.lnameColumn.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        this.telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
        this.cityColumn.setCellValueFactory(new PropertyValueFactory<>("Ville"));

        this.table.setItems(listOfClients);
        table.getColumns().addAll(idColumn, nameColumn, lnameColumn, telephoneColumn, cityColumn);
    }

    private void clearFields() {
        this.idTextField.setText("");
        this.lnameTextField.setText("");
        this.nameTextField.setText("");
        this.telephoneTextField.setText("");
        this.cityTextField.setText("");

    }

    @Override
    public void start(Stage primaryStage) {
        this.dao = new ClientDAOIMPL();
        initPane();
        initElement(primaryStage);
        initTable();
        root.setTop(boxTop);
        root.setLeft(leftBox);
        root.setCenter(centerPane);
        root.setRight(rightBox);
        root.setBottom(bottom);

        // Mise à jour de la table après la recherche
        FilteredList<Client> filteredReports = new FilteredList<>(listOfClients);
        searchTextField.textProperty().addListener((observavleValue, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                filteredReports.setPredicate(null);
            } else {
                filteredReports.setPredicate((Predicate<? super Client>) Client -> {
                    return Client.getPrenom().contains(newValue) || Client.getNom().contains(newValue) || Client.getTelephone().contains(newValue) || Client.getVille().contains(newValue);
                });
            }
        });

        SortedList<Client> sortedClients = new SortedList<>(filteredReports);
        sortedClients.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedClients);
        // Récupération de la ligne courante

        table.setRowFactory(tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Client rowData = row.getItem();
                    idTextField.setText(Long.toString(rowData.getId()));
                    idTextField.setDisable(true);
                    nameTextField.setText(rowData.getNom());
                    lnameTextField.setText(rowData.getPrenom());
                    telephoneTextField.setText(rowData.getTelephone());
                    cityTextField.setText(rowData.getVille());
                }
            });
            return row;
        });
        addButton.setOnAction(e -> {
            if(! Notification.isEmptyFields(nameTextField, lnameTextField, telephoneTextField, cityTextField)){
                Client p = new Client(0, nameTextField.getText(), lnameTextField.getText(), telephoneTextField.getText(), cityTextField.getText());
                dao.create(p);
                clearFields();
                updateListItems();
            }else{
                forms.setType(Alert.AlertType.WARNING);
                forms.shows("Veuillez remplir tous les champs");
            }
        });

        editButton.setOnAction(e -> {
            if(! Notification.isEmptyFields(idTextField, nameTextField, lnameTextField, telephoneTextField, cityTextField)){
                Client produtResult = dao.find(Integer.parseInt(idTextField.getText()));
                dao.update(produtResult, nameTextField.getText(), lnameTextField.getText(), telephoneTextField.getText(), cityTextField.getText());
                updateListItems();
                clearFields();
                idTextField.setDisable(true);
            }else{
                forms.setType(Alert.AlertType.WARNING);
                forms.shows("Veuillez séléctionner un client et remplir tous les champs");
            }
        });

        deleteButton.setOnAction(e -> {
            if(! Notification.isEmptyFields(idTextField)){
                if(forms.confirm("Êtes vous sûr de supprimer ce client?")){
                    Client rs = dao.find(Integer.parseInt(idTextField.getText()));
                    dao.delete(rs);
                    updateListItems();
                    clearFields();
                }
            }else{
                forms.setType(Alert.AlertType.WARNING);
                forms.shows("Veuillez Séléctionner un client ");
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
