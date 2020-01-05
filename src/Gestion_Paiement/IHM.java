package Gestion_Paiement;

import Gestion_Vente.VenteDAOIMPL;

import java.util.List;
import UI.FormValidator;
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
    private VBox leftBox, centerBox;
    private GridPane centerPane;
    private VBox rightBox;
    Label gestionLabel;
    Label idVenteLabel, idLabel, montantLabel, dateLabel, payerLabel, dateEffetLabel, typeLabel;
    TextField idVenteTextField, idTextField, montantTextField, dateTextField, dateEffetTextField;
    ObservableList<String> payerList = FXCollections.observableArrayList("Payer", "Non Payer");
    ComboBox<String> payerCombobox = new ComboBox<>();
    ObservableList<String> typeList = FXCollections.observableArrayList("Espèce", "Chèque","Traite","Onligne");
    ComboBox<String> typesBox= new ComboBox<>();
    Button addButton;
    Button editButton;
    Button deleteButton;
    PaiementDAOIMPL dao;
    Label statusLabel;
    Label resteLabel, resteValueLabel, paidLabel, paidValueLabel, totalValueLabel;
    // Attributs de la table view
    TableView<Paiement> table;
    // Les columns   
    TableColumn<Paiement, Integer> idColumn;
    TableColumn<Paiement, Double> montantColumn;
    TableColumn<Paiement, String> dateColumn;
    TableColumn<Paiement, String> typeColumn;
    TableColumn<Paiement, String> dateEffetColumn;
    TableColumn<Paiement, String> statusColumn;

    ObservableList<Paiement> listOfPaiements;
    List<Paiement> paiements;

    int idVenteFromSales;

    FormValidator forms = new FormValidator("paiements");

    public IHM(int idVenteFromSales) {
        this.idVenteFromSales = idVenteFromSales;
    }

    public void initRest(List<Paiement> paiements) {
        double total, paid = 0, reste;
        for (Paiement p : paiements) {
            if (p.getStatus().equals("Payer")) {
                paid += p.getMontant();
            }
        }
        total = (new VenteDAOIMPL()).find(idVenteFromSales).getTotal();
        reste = total - paid;
        this.totalValueLabel.setText(Double.toString(total));
        this.paidValueLabel.setText(Double.toString(paid));
        this.resteValueLabel.setText(Double.toString(reste));
    }

    public void initPane() {
        this.bottom = new HBox();
        this.centerPane = new GridPane();
        this.boxTop = new VBox();
        this.leftBox = new VBox();
        this.idLabel = new Label("Id");
        this.rightBox = new VBox();
        this.centerBox = new VBox();
    }

    public void initElement(Stage window) {
        idColumn = new TableColumn<>("Id");
        montantColumn = new TableColumn<>("Montant");
        dateColumn = new TableColumn<>("Date");
        typeColumn = new TableColumn<>("Type");
        dateEffetColumn = new TableColumn<>("Date Effet");
        statusColumn = new TableColumn<>("Status");
        this.statusLabel = new Label();
        this.gestionLabel = new Label("Gestion de Magasin");
        this.idVenteLabel = new Label("Id vente");
        this.idVenteTextField = new TextField(idVenteFromSales + "");
        this.idVenteTextField.setDisable(true);
        this.idTextField = new TextField();
        this.idTextField.setDisable(true);
        this.montantTextField = new TextField();
        this.dateTextField = new TextField();
        this.dateTextField.setPromptText("jj/mm/aaaa");
        this.dateEffetTextField = new TextField();
        this.dateEffetTextField.setPromptText("jj/mm/aaaa");
        this.addButton = new Button("Ajouter");
        this.editButton = new Button("Modifier");
        this.deleteButton = new Button("Supprimer");
        this.montantLabel = new Label("Montant");
        this.dateLabel = new Label("Date");
        this.payerLabel = new Label("Status");
        this.dateEffetLabel = new Label("Date Effet");
        this.typeLabel = new Label("Type");
        this.resteLabel = new Label("  Reste :  ");
        this.resteValueLabel = new Label();
        this.totalValueLabel = new Label();
        this.paidLabel = new Label("  Payer :  ");
        this.paidValueLabel = new Label();
        payerCombobox.setItems(payerList);
        typesBox.setItems(typeList);
        payerCombobox.getSelectionModel().select(1);
        Pane header= Header.initt();
        boxTop.getChildren().addAll(header, (new Navbar(window, "payement")).getHeader());
        boxTop.setAlignment(Pos.CENTER);
//        keyReleasedProperty();


        leftBox.getChildren().addAll(addButton, editButton, deleteButton);
        leftBox.setSpacing(10);

        centerPane.add(idVenteLabel, 0, 0);
        centerPane.add(idVenteTextField, 1, 0);
        centerPane.add(idLabel, 0, 1);
        centerPane.add(idTextField, 1, 1);
        centerPane.add(montantLabel, 0, 2);
        centerPane.add(montantTextField, 1, 2);
        centerPane.add(typeLabel, 0, 3);
        centerPane.add(typesBox, 1, 3);
        centerPane.add(dateLabel, 0, 4);
        centerPane.add(dateTextField, 1, 4);
        centerPane.add(payerLabel, 0, 5);
        centerPane.add(payerCombobox, 1, 5);
        centerPane.add(dateEffetLabel, 0, 6);
        centerPane.add(dateEffetTextField, 1, 6);
        centerPane.setPadding(new Insets(10));

        gestionLabel.getStyleClass().add("gestion_label");

        idVenteLabel.getStyleClass().add("labels");
        idLabel.getStyleClass().add("labels");
        montantLabel.getStyleClass().add("labels");
        dateLabel.getStyleClass().add("labels");
        dateEffetLabel.getStyleClass().add("labels");
        payerLabel.getStyleClass().add("labels");
        typeLabel.getStyleClass().add("labels");

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

        GridPane resteBox = (new GridPane());
        resteBox.add(paidLabel, 0, 1);
        resteBox.add(paidValueLabel, 1, 1);
        resteBox.add(resteLabel, 5, 1);
        resteBox.add(resteValueLabel, 6, 1);
        resteBox.getStyleClass().add("resteBox");
        resteBox.setHgap(20);
        resteBox.setVgap(27);

        paidLabel.getStyleClass().add("resteLabels");
        paidValueLabel.getStyleClass().add("payer");
        resteLabel.getStyleClass().add("resteLabels");
        resteValueLabel.getStyleClass().add("reste");

        this.centerBox.getChildren().addAll(centerPane);

        this.bottom.getChildren().add(statusLabel);
        this.rightBox.setPadding(new Insets(10));
        this.rightBox.getStyleClass().add("right");

        this.statusLabel.setAlignment(Pos.CENTER);
        this.table = new TableView<>();

        rightBox.getChildren().addAll(table,resteBox);
        bottom.setAlignment(Pos.CENTER);
        bottom.getStyleClass().add("button_box");
        this.paiements = dao.findAll(this.idVenteFromSales);
        listOfPaiements = getPaiementsList();
        root.getStyleClass().add("bg_coloring");
        initRest(paiements);
    }

    public ObservableList<Paiement> getPaiementsList() {
        ObservableList<Paiement> list = FXCollections.observableArrayList();

        this.paiements = dao.findAll(this.idVenteFromSales);
        for (Paiement c : paiements) {
            list.add(c);
        }
        return list;
    }

    public void updateListItems() {
        listOfPaiements.removeAll(listOfPaiements);
        listOfPaiements.addAll(getPaiementsList());
    }

    public void initTable() {

        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.dateEffetColumn.setCellValueFactory(new PropertyValueFactory<>("dateEffet"));
        this.statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
//        if (this.statusColumn.getText().equals("Payer")){
//            this.statusColumn.setStyle("-fx-background-color: green;");
//        }else{
//            this.statusColumn.setStyle("-fx-background-color: red;");
//            System.out.println(this.st);
//        }

        this.table.setItems(listOfPaiements);
        table.getColumns().addAll(idColumn, montantColumn, dateColumn, typeColumn, dateEffetColumn, statusColumn);
    }
//    public void keyReleasedProperty(){
//        String u=this.proprietaireTextField.getText();
//        boolean isDisabled =(u=="help");
//        this.dateTextField.setDisable(isDisabled);
//    }

    public void clearFields() {
        this.idTextField.setText("");
        this.montantTextField.setText("");
        this.dateTextField.setText("");
        this.payerCombobox.setValue(null);
        this.dateEffetTextField.setText("");
        this.typesBox.setValue(null);
    }

    @Override
    public void start(Stage primaryStage) {
        this.dao = new PaiementDAOIMPL();
        initPane();
        initElement(primaryStage);
        initTable();
        root.setTop(boxTop);
        root.setLeft(leftBox);
        root.setCenter(centerBox);
        root.setRight(rightBox);
        root.setBottom(bottom);

        // Mise à jour de la table après la recherche
        FilteredList<Paiement> filteredReports = new FilteredList<>(listOfPaiements);

        SortedList<Paiement> sortedProducts = new SortedList<>(filteredReports);
        sortedProducts.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedProducts);
        // Récupération de la ligne courante

        table.setRowFactory(tv -> {
            TableRow<Paiement> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Paiement rowData = row.getItem();
                    idTextField.setText(rowData.getId() + "");
                    montantTextField.setText(rowData.getMontant() + "");
                    dateTextField.setText(rowData.getDate());
                    payerCombobox.setValue(rowData.getStatus());
                    dateEffetTextField.setText(rowData.getDateEffet());
                    typesBox.setValue(rowData.getType());
                }
            });
            return row;
        });
        statusColumn.setCellFactory(column -> new TableCell<Paiement, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty); //This is mandatory
//                    setText(empty ? null : statusColumn.getText());
                if (item == null) { //If the cell is empty
                    try {
                        setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("heeelp");
                    }
                } else {
                    setText(item);
                    TableRow currt = getTableRow();
                    if (item.equals("Payer")) {
//                            setTextFill(Color.RED); //The text in red

                        currt.setStyle("-fx-background-color: lightgreen"); //The background of the cell in yellow
                    } else {
                        currt.setStyle("-fx-background-color: rgba(255,0,0,0.78)"); //The background of the cell in yellow

                    }
                }

            }
        });

        addButton.setOnAction(e -> {
            if (!forms.isEmptyFields(dateTextField, dateEffetTextField, montantTextField) && typesBox.getValue() != null && payerCombobox.getValue() != null) {
                double paid, total, montant;
                paid = Double.parseDouble(paidValueLabel.getText());
                total = Double.parseDouble(totalValueLabel.getText());
                montant = Double.parseDouble(montantTextField.getText());
                if (paid < total) {
                    if ((montant + paid) <= total) {
                        Paiement p = new Paiement(
                                (new VenteDAOIMPL()).find(Integer.parseInt(idVenteTextField.getText())),
                                Double.parseDouble(montantTextField.getText()),
                                dateTextField.getText(),
                                payerCombobox.getValue(),
                                dateEffetTextField.getText(),
                                typesBox.getValue()
                        );
                        dao.create(p);
                        clearFields();
                        this.statusLabel.setText("Le paiement a été ajouté avec succès!");
                        this.statusLabel.getStyleClass().add("custom_message");
                        updateListItems();
                        initRest(paiements);
                    } else {
                        forms.shout("Vous avez dépassé le montant total de la vente!");
                    }
                } else {
                    forms.shout("Cette vente est déja réglé");
                }
            } else {
                forms.shout("Merci de remplir tous les champs");
            }
        });

        editButton.setOnAction(e -> {
            if (!forms.isEmptyFields(idTextField, dateTextField, dateEffetTextField, montantTextField) && typesBox.getValue() != null && payerCombobox.getValue() != null) {
                double paid, total, montant;
                paid = Double.parseDouble(paidValueLabel.getText());
                total = Double.parseDouble(totalValueLabel.getText());
                montant = Double.parseDouble(montantTextField.getText());
                if ((montant + (paid - table.getSelectionModel().getSelectedItem().getMontant())) <= total) {
                    Paiement p = (new PaiementDAOIMPL()).find(Integer.parseInt(idTextField.getText()));
                    p.setMontant(Double.parseDouble(montantTextField.getText()));
                    p.setDate(dateTextField.getText());
                    p.setStatus(payerCombobox.getValue());
                    p.setDateEffet(dateEffetTextField.getText());
                    p.setType(typesBox.getValue());
                    dao.update(p);
                    clearFields();
                    this.statusLabel.setText("Le paiement est bien modifée !");
                    this.statusLabel.getStyleClass().add("custom_message");
                    updateListItems();
                    initRest(paiements);
                } else {
                    forms.shout("Vous avez dépassé le montant total de la vente!");
                }
            } else {
                forms.shout("Merci de séléctionner un paiement et remplir tous les champs");
            }
        });

        deleteButton.setOnAction(e -> {
            if (!forms.isEmptyFields(idTextField)) {
                if (forms.confirm("Êtes vous sûr de supprimer ce paiement?")) {
                    Paiement p = (new PaiementDAOIMPL()).find(Integer.parseInt(idTextField.getText()));
                    dao.delete(p);
                    clearFields();
                    this.statusLabel.setText("Le paiement est bien supprimé !");
                    this.statusLabel.getStyleClass().add("custom_message");
                    updateListItems();
                    initRest(paiements);
                }
            } else {
                forms.shout("Merci de séléctionner un paiement à supprimer");
            }
        });

        primaryStage.setTitle("Gestion des paiements");

        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public Scene getScene() {
        return this.scene;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
