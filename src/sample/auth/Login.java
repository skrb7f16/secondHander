package sample.auth;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import sample.database.MySqlOperations;
import sample.helper.AlertHelper;
import sample.models.User;
import java.io.IOException;
import java.sql.SQLException;

public class Login extends Application {
    MySqlOperations database;

    public Login(MySqlOperations database) {
        this.database = database;
    }

    public Login() {
        database=new MySqlOperations();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root=new VBox();
        root.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("../resources/css/login.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root,400,400));
        stage.setTitle("Login");
        HBox head=new HBox();
        Image image=new Image(getClass().getResource("../resources/images/back_arrow_icon.png").toExternalForm());
        ImageView back=new ImageView(image);
        back.setFitWidth(50);
        back.setFitHeight(50);
        back.setPreserveRatio(false);
        Label heading=new Label("Login form");
        heading.setPadding(new Insets(0,0,0,20));
        heading.getStyleClass().add("heading");
        head.getChildren().addAll(back,heading);

        TextField username=new TextField();
        username.getStyleClass().add("username");
        username.setPromptText("Username");
        PasswordField password=new PasswordField();
        password.getStyleClass().add("password");
        password.setPromptText("Password");
        Button submit=new Button("Login");
        root.setSpacing(20);
        root.getChildren().addAll(head,username,password,submit);
        stage.show();

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main main=new Main();
                stage.hide();
                try {
                    main.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(username.getText().length()<3||password.getText().length()<3){
                    AlertHelper.showAlert(Alert.AlertType.WARNING,stage,"Sorry",
                            "Please check username and password too small");
                    return;
                }
                try {
                    User user=database.login(username.getText(),password.getText());
                    if(user==null){
                        AlertHelper.showAlert(Alert.AlertType.ERROR,stage,"Sorry",
                                "Wrong credentials");
                        return;
                    }
                    username.setText("");
                    password.setText("");
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION,stage,"Congrats",
                            "you are logged in");
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }
}
