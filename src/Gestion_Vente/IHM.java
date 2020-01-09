package Gestion_Vente;

import Gestion_Client.Client;
import Gestion_Client.ClientDAOIMPL;
import Gestion_Paiement.PaiementDAOIMPL;
import Gestion_Produit.Produit;
import Gestion_Produit.ProduitDAOIMPL;

import java.awt.event.KeyAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import UI.Notification;
import UI.Header;
import UI.Navbar;
import javafx.application.Application;

import static javafx.application.Application.launch;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class IHM extends Application {

    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1300, 800);

    private VBox boxTop;
    private HBox bottom, rightControls;
    private VBox leftBox;
    private GridPane centerPane, rightPane;
    private VBox rightBox, centerBox;
    private TextField searchTextField;
    //Format Date

    DateTimeFormatter timeFormatter;

    // Les labels
    Label idLabel;
    Label clientLabel;
    Label dateLabel;
    Label qteLabel;
    Label productLabel;

    // Les champs
    TextField idTextField;
    ComboBox<Client> clientComboBox;
    ComboBox<Produit> productsBox;
    DatePicker dateTextField;
    TextField qteTextField;



    // Les buttons
    Button addButton;
    Button editButton;
    Button deleteButton;
    Button showPaiements;

    Button addLigne, editLigne, deleteLigne;

    //ClientDAOIMPL dao;
    Label statusLabel;
    // Attributs de la table view
    TableView<Vente> table;
    TableView<LigneVente> ligneTable;
    // Les columns   
    TableColumn<Vente, Integer> idColumn;
    TableColumn<Vente, String> nameColumn;
    TableColumn<Vente, String> dateColumn;
    TableColumn<Vente, Double> totalColumn;
    /// pour ligneTable
    TableColumn<LigneVente, Integer> idLigneColumn;
    TableColumn<LigneVente, Integer> produitLigneColumn;
    TableColumn<LigneVente, Integer> prixLigneColumn;
    TableColumn<LigneVente, Integer> qteLigneColumn;
    TableColumn<LigneVente, Integer> totalLigneColumn;

    // Les resultats
    ObservableList<Vente> listOfVentes;
    ObservableList<LigneVente> listOfLignes;
    List<Vente> ventes;

    LigneVente existLigneVente;
    VenteDAOIMPL dao = new VenteDAOIMPL();
    LigneVenteDAOIMPL daoLigne = new LigneVenteDAOIMPL();

    Alert alert;

    public void initPane() {
        this.bottom = new HBox();
        this.searchTextField = new TextField();
        this.centerPane = new GridPane();
        this.boxTop = new VBox();
        this.leftBox = new VBox();
        this.idLabel = new Label("Id");
        this.rightBox = new VBox();
        this.centerBox = new VBox();
        this.productsBox = new ComboBox<>();
        this.qteTextField = new TextField();
        this.qteLabel = new Label("Quantité");
        this.productLabel = new Label("Produit");
        this.rightPane = new GridPane();
        this.rightControls = new HBox();
        this.addLigne = new Button("Ajouter");
        this.editLigne = new Button("Modifier");
        this.deleteLigne = new Button("Supprimer");
        this.alert = new Alert(Alert.AlertType.INFORMATION);
        this.alert.setHeaderText(null);
        this.alert.setTitle("Gestion magazin");
    }

    public void initElement(Stage window) {
        idColumn = new TableColumn<>("Id");
        nameColumn = new TableColumn<>("Nom Complet");
        dateColumn = new TableColumn<>("Date");
        totalColumn = new TableColumn<>("Total");

        idLigneColumn = new TableColumn<>("Id");
        produitLigneColumn = new TableColumn<>("Produit");
        prixLigneColumn = new TableColumn<>("Prix");
        qteLigneColumn = new TableColumn<>("Quantité");
        totalLigneColumn = new TableColumn<>("Total");

        this.statusLabel = new Label("copyright © 2020 _ By :Yassir BOURAS");
        this.idTextField = new TextField();
        this.timeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.dateTextField = new DatePicker();
        this.dateTextField.setPromptText("jj-mm-aaaa");
        this.clientComboBox = new ComboBox<>();

        this.addButton = new Button("Ajouter");
        this.editButton = new Button("Modifier");
        this.deleteButton = new Button("Supprimer");
        this.showPaiements = new Button("Paiements");
        this.clientLabel = new Label("Client");
        this.dateLabel = new Label("Date");
        Pane header = Header.initt();
        boxTop.getChildren().addAll(header, (new Navbar(window, "sale")).getHeader());
        boxTop.setAlignment(Pos.CENTER);

        dateTextField.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        qteTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    qteTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        leftBox.getChildren().addAll(addButton, editButton, deleteButton, showPaiements);
        leftBox.setSpacing(10);

        centerPane.add(idLabel, 0, 0);
        centerPane.add(idTextField, 1, 0);
        centerPane.add(clientLabel, 0, 1);
        centerPane.add(clientComboBox, 1, 1);
        centerPane.add(dateLabel, 0, 2);
        centerPane.add(dateTextField, 1, 2);

        rightPane.add(productLabel, 0, 0);
        rightPane.add(productsBox, 1, 0);
        rightPane.add(qteLabel, 0, 1);
        rightPane.add(qteTextField, 1, 1);

        centerPane.setPadding(new Insets(10));
        dateTextField.setStyle("-fx-min-width: 270px");

        addButton.getStyleClass().add("buttons");
        editButton.getStyleClass().add("buttons");
        deleteButton.getStyleClass().add("buttons");
        showPaiements.getStyleClass().add("buttons");

        centerPane.setVgap(10);
        centerPane.setHgap(10);

        addButton.setPrefWidth(100);
        editButton.setPrefWidth(100);
        deleteButton.setPrefWidth(100);
        editButton.setPrefHeight(40);
        showPaiements.setPrefWidth(100);
        addButton.setPrefHeight(40);
        deleteButton.setPrefHeight(40);
        showPaiements.setPrefHeight(40);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.setPadding(new Insets(10));
        boxTop.getStyleClass().add("top_box_style");
        leftBox.getStyleClass().add("custom_back");

        addLigne.getStyleClass().add("buttons");
        addLigne.setPrefWidth(100);
        editLigne.getStyleClass().add("buttons");
        editLigne.setPrefWidth(100);
        deleteLigne.getStyleClass().add("buttons");
        deleteLigne.setPrefWidth(100);
        rightControls.setSpacing(10);

        this.bottom.getChildren().add(statusLabel);
        this.rightBox.setPadding(new Insets(10));
        this.rightBox.getStyleClass().add("right");

        this.searchTextField.setPromptText("-----------Chercher------------");
        this.statusLabel.setAlignment(Pos.CENTER);
        this.statusLabel.getStyleClass().add("copyright");
        this.table = new TableView<>();
        this.ligneTable = new TableView<>();

        bottom.setAlignment(Pos.CENTER);
        bottom.getStyleClass().add("button_box");
        listOfVentes = getVenteList();

        listOfLignes = getLigneList(0);

        this.productsBox.getItems().addAll((new ProduitDAOIMPL()).findAll());

        rightControls.getChildren().addAll(addLigne, editLigne, deleteLigne);


        centerBox.getChildren().addAll(centerPane, searchTextField, table);
        rightBox.getChildren().addAll(rightPane, rightControls, ligneTable);
        root.getStyleClass().add("bg_coloring");
    }

    public ObservableList<Vente> getVenteList() {
        ObservableList<Vente> list = FXCollections.observableArrayList();
        list.addAll(dao.findAll());
        return list;
    }

    public ObservableList<LigneVente> getLigneList(int id) {
        ObservableList<LigneVente> list = FXCollections.observableArrayList();
        list.addAll(daoLigne.findAll(id));
        return list;
    }

    public void updateListItems() {
        listOfVentes.removeAll(listOfVentes);
        listOfVentes.addAll(getVenteList());
    }

    public void initTable() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        this.table.setItems(listOfVentes);
        table.getColumns().addAll(idColumn, nameColumn, dateColumn, totalColumn);

        this.idLigneColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.produitLigneColumn.setCellValueFactory(new PropertyValueFactory<>("produit"));
        this.prixLigneColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        this.qteLigneColumn.setCellValueFactory(new PropertyValueFactory<>("qte"));
        this.totalLigneColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        this.ligneTable.setItems(listOfLignes);
        this.ligneTable.getColumns().addAll(idLigneColumn, produitLigneColumn, prixLigneColumn, qteLigneColumn, totalLigneColumn);
    }

    public void refrechLigneTable(int id) {
        int selected = table.getSelectionModel().getSelectedIndex();
        updateListItems();
        table.getSelectionModel().select(selected);
        listOfLignes.removeAll(listOfLignes);
        listOfLignes.addAll(getLigneList(table.getSelectionModel().getSelectedItem().getId()));
    }

    public void clearFields() {
        this.idTextField.setText("");
        this.dateTextField.setValue(null);
        this.clientComboBox.setValue(null);
    }

    public void clearLigneFields() {
        this.productsBox.setValue(null);
        this.qteTextField.setText("");
    }

    public void initComboClients() {
        this.clientComboBox.getItems().addAll((new ClientDAOIMPL()).findAll());
    }

    @Override
    public void start(Stage primaryStage) {
        initPane();
        initElement(primaryStage);
        initTable();
        initComboClients();
        root.setTop(boxTop);
        root.setLeft(leftBox);
        root.setCenter(centerBox);
        root.setRight(rightBox);
        root.setBottom(bottom);

        // Mise à jour de la table après la recherche
        FilteredList<Vente> filteredReports = new FilteredList<>(listOfVentes);
        searchTextField.textProperty().addListener((observavleValue, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                filteredReports.setPredicate(null);
            } else {
                final String lowerCaseFilter = newValue.toLowerCase();

                filteredReports.setPredicate((t) -> {
                    return t.getClient().getNom().contains(newValue) || t.getClient().getPrenom().contains(newValue)
                            || t.getDate().contains(newValue);
                });
            }
        });
        idTextField.setDisable(true);

        SortedList<Vente> sortedClients = new SortedList<>(filteredReports);
        sortedClients.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedClients);
        // Récupération de la ligne courante
        table.setRowFactory(tv -> {
            TableRow<Vente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Vente rowData = row.getItem();
                    idTextField.setText(Long.toString(rowData.getId()));
                    dateTextField.setValue(LocalDate.parse(rowData.getDate(), timeFormatter));
                    clientComboBox.setValue(rowData.getClient());
                    refrechLigneTable(rowData.getId());
                }
            });
            return row;
        });

        ligneTable.setRowFactory(tl -> {
            TableRow<LigneVente> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 1 && (!row.isEmpty())) {
                    LigneVente rowData = row.getItem();
                    qteTextField.setText(rowData.getQte() + "");
                    productsBox.setValue(rowData.getProduit());
                }
            });
            return row;
        });

        addButton.setOnAction(e -> {
            if (clientComboBox.getValue() != null && dateTextField.getValue()!=null) {
                Vente v = new Vente(0,
                        clientComboBox.getSelectionModel().getSelectedItem(),
                        dateTextField.getValue().format(timeFormatter));
                dao.create(v);
                clearFields();
                updateListItems();
            } else {
                alert.setContentText("Veuillez Sélectionner un client et une date pour ajouter la vente");
                alert.showAndWait();
            }
        });

        editButton.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedIndex() >= 0) {
                Vente venteToEdit = dao.find(Integer.parseInt(idTextField.getText()));
                venteToEdit.setClient(clientComboBox.getSelectionModel().getSelectedItem());
                venteToEdit.setDate(dateTextField.getValue().format(timeFormatter));
                dao.update(venteToEdit);
                updateListItems();
                clearFields();
                idTextField.setDisable(false);
            } else {
                alert.setContentText("Veuillez Sélectionner une vente");
                alert.showAndWait();
            }
        });

        deleteButton.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedIndex() >= 0) {
                if ((new Notification("ventes")).confirm("Êtes vous sûr de supprimer cette vente?")) {

                    int beforeSelected = table.getSelectionModel().getSelectedIndex() - 1;
                    Vente rs = dao.find(Integer.parseInt(idTextField.getText()));
                    dao.delete(rs);
                    updateListItems();
                    if (beforeSelected != 0) {
                        table.getSelectionModel().select(beforeSelected);
                    } else {
                        table.getSelectionModel().select(0);
                    }
                    refrechLigneTable(beforeSelected);
                    clearFields();
                }
            } else {
                alert.setContentText("Veuillez Sélectionner une vente ");
                alert.showAndWait();
            }
        });

        addLigne.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedIndex() >= 0) {
                existLigneVente = null;
                if (productsBox.getValue() != null && !qteTextField.getText().equals("")) {
                    int idvente = table.getSelectionModel().getSelectedItem().getId();
                    LigneVente lv = new LigneVente(0, dao.find(idvente),
                            productsBox.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(qteTextField.getText()));

                    ligneTable.getItems().forEach(item -> {
                        if (item.getProduit().getId() == lv.getProduit().getId() && item.getVente().getId() == lv.getVente().getId()) {
                            existLigneVente = item;
                        }
                    });
                    if (existLigneVente == null) {
                        daoLigne.create(lv);
                    } else {
                        existLigneVente.setQte(existLigneVente.getQte() + lv.getQte());
                        daoLigne.update(existLigneVente);
                    }
                    refrechLigneTable(idvente);
                    clearLigneFields();
                } else {
                    alert.setContentText("Sélectionner un produit et définir une quantité");
                    alert.showAndWait();
                }
            } else {
                alert.setContentText("Veuillez séléctionner une vente");
                alert.showAndWait();
            }

        });

        editLigne.setOnAction(e -> {
            if (ligneTable.getSelectionModel().getSelectedIndex() >= 0) {
                int idvente = ligneTable.getSelectionModel().getSelectedItem().getId();
                LigneVente lv = daoLigne.find(idvente);
                PaiementDAOIMPL p=new PaiementDAOIMPL();
                int qt=Integer.parseInt(qteTextField.getText());
                double total=table.getSelectionModel().getSelectedItem().getTotal()-ligneTable.getSelectionModel().getSelectedItem().getSubtotal();
                double ligne=productsBox.getSelectionModel().getSelectedItem().getPrix()*qt;
                double paiement=p.calculTotal(table.getSelectionModel().getSelectedItem().getId());
                System.out.println("total :"+total);
                System.out.println("new sub :"+ligne);
                System.out.println("paiement :"+paiement);

                if (total+ligne>paiement) {
                    lv.setProduit(productsBox.getSelectionModel().getSelectedItem());
                    lv.setQte(qt);
                    daoLigne.update(lv);
                    refrechLigneTable(idvente);
                    clearLigneFields();
                }else {
                    Notification warning=new Notification("Modification Ligne de commande");
                    warning.setType(Alert.AlertType.ERROR);
                    warning.shows("Impossible de Modifier ! Commande deja payer");
                }
            } else {
                alert.setContentText("Veuillez séléctionner une ligne de vente");
                alert.showAndWait();
            }
        });

        deleteLigne.setOnAction(e -> {
            if (ligneTable.getSelectionModel().getSelectedIndex() >= 0) {
                if ((new Notification("ventes")).confirm("Êtes vous sûr de supprimer cette ligne de vente?")) {

                    int idvente = ligneTable.getSelectionModel().getSelectedItem().getVente().getId();
                    PaiementDAOIMPL p = new PaiementDAOIMPL();
                    double totalPaiement = p.calculTotal(idvente);
                    if (table.getSelectionModel().getSelectedItem().getTotal() - ligneTable.getSelectionModel().getSelectedItem().getSubtotal() > totalPaiement) {
                        LigneVente lv = ligneTable.getSelectionModel().getSelectedItem();
                        daoLigne.delete(lv);
                        refrechLigneTable(idvente);
                        clearLigneFields();
                    } else {
                        Notification warning = new Notification("Ligne de Commande");
                        warning.setType(Alert.AlertType.ERROR);
                        warning.shows("Impossible de Supprimer ! Commande deja payer ");
                    }
                }
            } else {
                alert.setContentText("Veuillez séléctionner une ligne de vente");
                alert.showAndWait();
            }
        });

        showPaiements.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedIndex() >= 0) {
                if (table.getSelectionModel().getSelectedItem().getTotal() > 0) {
                    int idvente = table.getSelectionModel().getSelectedItem().getId();
                    Gestion_Paiement.IHM paiementIHM = new Gestion_Paiement.IHM(Integer.parseInt(idTextField.getText()));
                    paiementIHM.start(primaryStage);
                } else {
                    alert.setContentText("Aucune ligne de vente trouvé");
                    alert.showAndWait();
                }
            } else {
                alert.setContentText("Veuillez séléctionner une vente");
                alert.showAndWait();
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
