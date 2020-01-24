package Gestion_Paiement;

import Gestion_Transfert.Transfert;
import Gestion_Transfert.TransfertDAOIMPL;
import Gestion_Vente.VenteDAOIMPL;

import java.awt.event.ActionEvent;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import UI.Notification;
import UI.Header;
import UI.Navbar;
import javafx.application.Application;
import javafx.application.Platform;
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
    private VBox leftBox, centerBox;
    private GridPane centerPane;
    private VBox rightBox;
    Label gestionLabel;
    Label idVenteLabel, idLabel, montantLabel, dateLabel, payerLabel, typeLabel, ribLabel, bankLabel;
    TextField idVenteTextField, idTextField, montantTextField, ribTextField, bankTextField;
    DatePicker dateTextField;
    DateTimeFormatter timeFormatter;
    ObservableList<String> payerList = FXCollections.observableArrayList("Payer", "Non Payer");
    ComboBox<String> payerCombobox = new ComboBox<>();
    ObservableList<String> typeList = FXCollections.observableArrayList("Espèce", "Chèque", "Traite", "Virement");
    ComboBox<String> typesBox = new ComboBox<>();
    Button addButton;
    Button editButton;
    Button deleteButton;
    PaiementDAOIMPL dao;
    TransfertDAOIMPL daoo;
    Label statusLabel;
    Label resteLabel, resteValueLabel, paidLabel, paidValueLabel, totalValueLabel;
    // Attributs de la table view
    TableView<Paiement> table;
    // Les columns   
    TableColumn<Paiement, Integer> idColumn;
    TableColumn<Paiement, Double> montantColumn;
    TableColumn<Paiement, String> dateColumn;
    TableColumn<Paiement, String> typeColumn;
    TableColumn<Paiement, String> statusColumn;

    ObservableList<Paiement> listOfPaiements;
    List<Paiement> paiements;

    int idVenteFromSales;

    Notification warning = new Notification("paiements");
    Socket client = null;

    MyThread detector;
    boolean serverState = false;

    public IHM(int idVenteFromSales) {
        this.idVenteFromSales = idVenteFromSales;
        detector = new MyThread();
        detector.setServerDetected((socket) -> {
            System.out.println(socket);
            client = socket;
            if (socket == null) serverState = false;
            else serverState = true;
            Platform.runLater(() -> {
                setServerState(serverState);
            });
        });
        detector.start();
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
        statusColumn = new TableColumn<>("Status");
        this.statusLabel = new Label("copyright © 2020 _ By :Yassir BOURAS");
        this.gestionLabel = new Label("Gestion de Magasin");
        this.idVenteLabel = new Label("Id vente");
        this.idVenteTextField = new TextField(idVenteFromSales + "");
        this.idVenteTextField.setDisable(true);
        this.idTextField = new TextField();
        this.idTextField.setDisable(true);
        this.montantTextField = new TextField();
        this.ribTextField = new TextField();
        this.bankTextField = new TextField();
        this.dateTextField = new DatePicker();
        this.dateTextField.setPromptText("dd-mm-yyyy");
        this.timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.addButton = new Button("Ajouter");
        this.editButton = new Button("Modifier");
        this.deleteButton = new Button("Supprimer");
        this.montantLabel = new Label("Montant");
        this.dateLabel = new Label("Date");
        this.payerLabel = new Label("Status");
        this.typeLabel = new Label("Type");
        this.ribLabel = new Label("RIB");
        this.bankLabel = new Label("BANK");
        this.resteLabel = new Label("  Reste :  ");
        this.resteValueLabel = new Label();
        this.totalValueLabel = new Label();
        this.paidLabel = new Label("  Payer :  ");
        this.paidValueLabel = new Label();
        payerCombobox.setItems(payerList);
        typesBox.setItems(typeList);
        payerCombobox.getSelectionModel().select(1);
        typesBox.getSelectionModel().select(1);
        Pane header = Header.initt();
        boxTop.getChildren().addAll(header, (new Navbar(window, "payement")).getHeader());
        boxTop.setAlignment(Pos.CENTER);

        this.dateTextField.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        montantTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    montantTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        ribTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    montantTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        leftBox.getChildren().addAll(addButton, editButton, deleteButton);
        leftBox.setSpacing(10);

        centerPane.add(idVenteLabel, 0, 1);
        centerPane.add(idVenteTextField, 1, 1);
        centerPane.add(idLabel, 0, 2);
        centerPane.add(idTextField, 1, 2);
        centerPane.add(montantLabel, 0, 3);
        centerPane.add(montantTextField, 1, 3);
        centerPane.add(typeLabel, 0, 4);
        centerPane.add(typesBox, 1, 4);
        centerPane.add(dateLabel, 0, 5);
        centerPane.add(dateTextField, 1, 5);
        centerPane.add(payerLabel, 0, 6);
        centerPane.add(payerCombobox, 1, 6);
        centerPane.add(bankLabel, 0, 7);
        centerPane.add(bankTextField, 1, 7);
        centerPane.add(ribLabel, 0, 8);
        centerPane.add(ribTextField, 1, 8);
        centerPane.setPadding(new Insets(10));

        gestionLabel.getStyleClass().add("gestion_label");
        dateTextField.setStyle("-fx-min-width: 270px");


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

        idLabel.getStyleClass().add("labels");
        idVenteLabel.getStyleClass().add("labels");
        dateLabel.getStyleClass().add("labels");
        montantLabel.getStyleClass().add("labels");
        typeLabel.getStyleClass().add("labels");
        payerLabel.getStyleClass().add("labels");
        ribLabel.getStyleClass().add("labels");
        bankLabel.getStyleClass().add("labels");

        paidLabel.getStyleClass().add("resteLabels");
        paidValueLabel.getStyleClass().add("payer");
        resteLabel.getStyleClass().add("resteLabels");
        resteValueLabel.getStyleClass().add("reste");

        this.centerBox.getChildren().addAll(centerPane);

        this.bottom.getChildren().add(statusLabel);
        this.rightBox.setPadding(new Insets(10));
        this.rightBox.getStyleClass().add("right");

        this.statusLabel.setAlignment(Pos.CENTER);
        this.statusLabel.getStyleClass().add("copyright");
        this.table = new TableView<>();

        rightBox.getChildren().addAll(table, resteBox);
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
        this.statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        this.table.setItems(listOfPaiements);
        table.getColumns().addAll(idColumn, montantColumn, dateColumn, typeColumn, statusColumn);
    }

    public void clearFields() {
        this.idTextField.setText("");
        this.montantTextField.setText("");
        this.dateTextField.setValue(null);
        this.payerCombobox.setValue(null);
//        this.typesBox.setValue(null);
    }

    public void savePaiement(Paiement p, Transfert t) {
        dao = new PaiementDAOIMPL();
        dao.create(p);
        daoo = new TransfertDAOIMPL();
        daoo.create(t);
        clearFields();
        updateListItems();
        initRest(paiements);
    }

    public void sendPaiement(Paiement p, Transfert t) throws Exception {
        PrintStream ps = new PrintStream(this.client.getOutputStream());
        ps.println(Double.toString(p.getMontant()));
        ps.flush();
        Scanner sc = new Scanner(this.client.getInputStream());
        String response = sc.nextLine();
        if (response.equals("ok")) {
            savePaiement(p, t);
            ObjectOutputStream objout = new ObjectOutputStream(this.client.getOutputStream());
            objout.writeObject(p);
            objout.flush();
            String msg = sc.nextLine();
            warning.setType(Alert.AlertType.CONFIRMATION);
            warning.shows("Transfert a été effectuer.\n" + msg);
        } else {
            warning.setType(Alert.AlertType.WARNING);
            warning.shows("Sold insuffisant !!");
        }
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
                    dateTextField.setValue(LocalDate.parse(rowData.getDate(), timeFormatter));
                    payerCombobox.setValue(rowData.getStatus());
                    typesBox.setValue(rowData.getType());
                }
            });
            return row;
        });

        try {
            statusColumn.setCellFactory(column -> new TableCell<Paiement, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty); //This is mandatory
                    if (item == null || empty) { //If the cell is empty
                        setText("");
                    } else if (item.equals("Payer")) {
                        setText(item);
                        TableRow currt = getTableRow();
//                        try {
                            currt.setStyle("-fx-background-color: lightgreen"); //The background of the cell in yellow
//                        } catch (Exception e) {
//                        }
                    } else if (item.equals("Non Payer")) {
                        setText(item);
                        TableRow currt = getTableRow();
                        if (currt != null) {
                            currt.setStyle("-fx-background-color: rgba(255,0,0,0.78)"); //The background of the cell in yellow
                        }

                    }
                    //                }

                }
            });
        } catch (Exception e) {
            System.out.println("hello");
        }

        addButton.setOnAction(e -> {
            if (!Notification.isEmptyFields(montantTextField) && typesBox.getValue() != null && dateTextField.getValue() != null && payerCombobox.getValue() != null) {
                double paid, total, montant;
                paid = Double.parseDouble(paidValueLabel.getText());
                total = Double.parseDouble(totalValueLabel.getText());
                montant = Double.parseDouble(montantTextField.getText());

                if (paid < total) {
                    if ((montant + paid) <= total) {
                        if (typesBox.getValue().equals("Virement")) {
                            if (!ribTextField.getText().isEmpty() && !bankTextField.getText().isEmpty()) {
                                Paiement p = new Paiement(
                                        (new VenteDAOIMPL()).find(Integer.parseInt(idVenteTextField.getText())),
                                        Double.parseDouble(montantTextField.getText()),
                                        dateTextField.getValue().format(timeFormatter),
                                        payerCombobox.getValue(),
                                        typesBox.getValue()
                                );
                                Transfert t = new Transfert(
                                        p.getVente().getClient().getNom(),
                                        p.getVente().getClient().getPrenom(),
                                        ribTextField.getText(),
                                        bankTextField.getText(),
                                        Double.parseDouble(montantTextField.getText()),
                                        dateTextField.getValue().format(timeFormatter)
                                );
                                if (this.serverState) {

                                    try {
                                        sendPaiement(p, t);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                } else {
                                    warning.shows("Le serveur de banque est désactivé!!");
                                }
//                                TransfertDAOIMPL ddao=new TransfertDAOIMPL();
//                                ddao.create(t);
//                                dao.create(p);
//                                clearFields();
//                                updateListItems();
//                                initRest(paiements);
                            } else {
                                warning.setType(Alert.AlertType.WARNING);
                                warning.shows("Veuillez remplir tous les champs");
                            }
                        } else {
                            Paiement p = new Paiement(
                                    (new VenteDAOIMPL()).find(Integer.parseInt(idVenteTextField.getText())),
                                    Double.parseDouble(montantTextField.getText()),
                                    dateTextField.getValue().format(timeFormatter),
                                    payerCombobox.getValue(),
                                    typesBox.getValue()
                            );
                            dao.create(p);
                            clearFields();
                            updateListItems();
                            initRest(paiements);
                        }
                    } else {
                        warning.setType(Alert.AlertType.ERROR);
                        warning.shows("Vous avez dépassé le montant total de la vente!");
                    }
                } else {
                    warning.setType(Alert.AlertType.ERROR);
                    warning.shows("Cette vente est déja réglé");
                }
            } else {
                warning.setType(Alert.AlertType.WARNING);
                warning.shows("Veuillez remplir tous les champs");
            }
        });

        editButton.setOnAction(e -> {
            if (!Notification.isEmptyFields(idTextField, montantTextField) && typesBox.getValue() != null && dateTextField.getValue() != null && payerCombobox.getValue() != null) {
                double paid, total, montant;
                paid = Double.parseDouble(paidValueLabel.getText());
                total = Double.parseDouble(totalValueLabel.getText());
                montant = Double.parseDouble(montantTextField.getText());
                if ((montant + (paid - table.getSelectionModel().getSelectedItem().getMontant())) <= total) {
                    Paiement p = (new PaiementDAOIMPL()).find(Integer.parseInt(idTextField.getText()));
                    p.setMontant(Double.parseDouble(montantTextField.getText()));
                    p.setDate(dateTextField.getValue().format(timeFormatter));
                    p.setStatus(payerCombobox.getValue());
                    p.setType(typesBox.getValue());
                    dao.update(p);
                    clearFields();
                    updateListItems();
                    initRest(paiements);
                } else {
                    warning.setType(Alert.AlertType.ERROR);
                    warning.shows("Vous avez dépassé le montant total de la vente!");
                }
            } else {
                warning.setType(Alert.AlertType.WARNING);
                warning.shows("Veuillez séléctionner un paiement et remplir tous les champs");
            }
        });

        deleteButton.setOnAction(e -> {
            if (!Notification.isEmptyFields(idTextField)) {
                if (warning.confirm("Êtes vous sûr de supprimer ce paiement?")) {
                    Paiement p = (new PaiementDAOIMPL()).find(Integer.parseInt(idTextField.getText()));
                    dao.delete(p);
                    clearFields();
                    updateListItems();
                    initRest(paiements);
                }
            } else {
                warning.setType(Alert.AlertType.WARNING);
                warning.shows("Veuillez séléctionner un paiement");
            }
        });
        typesBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                    boolean en = newValue.equals("Virement");
                    ribLabel.setDisable(!en);
                    ribTextField.setDisable(!en);
                    bankLabel.setDisable(!en);
                    bankTextField.setDisable(!en);

                }
        );


        primaryStage.setTitle("Store Management");

        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    static class MyThread extends Thread {
        Socket socket = null;

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(2000);
                    if (socket != null)
                        continue;
                    socket = new Socket("localhost", 1500);
                    if (serverDetected != null)
                        serverDetected.onReceive(socket);
                } catch (Exception e) {
                    serverDetected.onReceive(null);
                }
            }
        }

        public void setServerDetected(OnServerDetected serverDetected) {
            this.serverDetected = serverDetected;
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }

        interface OnServerDetected {
            void onReceive(Socket socket);
        }

        OnServerDetected serverDetected;
    }

    private void setServerState(boolean state) {
        if (state) System.out.println("Le serveur est activé");
        else System.out.println("Le serveur est desactivé");

    }

    public Scene getScene() {
        return this.scene;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
