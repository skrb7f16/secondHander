package sample.user;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.database.MySqlOperations;
import sample.resources.Params;

public class MyProfile extends Application {
    MySqlOperations database;

    public MyProfile(MySqlOperations database) {
        this.database = database;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root=new VBox();
        root.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("../resources/css/login.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root,500,700));
        stage.setTitle("User Panel");
        HBox head=new HBox();
        Image image=new Image(getClass().getResource("../resources/images/back_arrow_icon.png").toExternalForm());
        ImageView back=new ImageView(image);
        back.setFitWidth(50);
        back.setFitHeight(50);
        back.setPreserveRatio(false);
        Label heading=new Label(Params.username);
        heading.setPadding(new Insets(0,0,0,30));
        heading.getStyleClass().add("heading");
        heading.setPrefWidth(320);
        heading.setAlignment(Pos.CENTER);
        head.getChildren().addAll(back,heading);
        root.getChildren().add(head);

    }
}
