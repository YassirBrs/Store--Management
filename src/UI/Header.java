package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Header {
    public static Pane initt(){
        HBox box=new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(box, Priority.ALWAYS);
        Text txtuser=new Text("Yassir BOURAS     ");
        txtuser.setFill(Color.WHITE);
        txtuser.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/user.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imgview =new ImageView(image);
        imgview.setFitHeight(80);
        imgview.setFitWidth(80);
        imgview.setStyle("-fx-padding: 30px");
        box.setSpacing(20);
        box.setPadding(new Insets(50));
        box.getChildren().addAll(txtuser,imgview);
        Pane pane=new HBox();
        pane.setPrefHeight(100);
        ((HBox)pane).setAlignment(Pos.CENTER_LEFT);
        Text titre=new Text("         Store Management");
        titre.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        titre.setFill(Color.WHITE);

        pane.getChildren().add(titre);
        pane.getChildren().add(box);

        return pane;
    }
}
