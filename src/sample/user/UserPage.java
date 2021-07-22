package sample.user;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.Main;
import sample.database.MySqlOperations;
import sample.helper.Utility;
import sample.models.User;
import sample.resources.Params;

public class UserPage extends Application {
    MySqlOperations databasse;
    User u;
    public UserPage(MySqlOperations database) {
        this.databasse=database;
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
        Image pic=new Image(getClass().getResource("../"+Params.currentUser.getDp()).toExternalForm());
        Rectangle rectangle = new Rectangle(0, 0, 280, 220);
        rectangle.setArcWidth(100.0);
        rectangle.setArcHeight(100.0);
        ImagePattern pattern = new ImagePattern(pic);
        rectangle.setFill(pattern);
        rectangle.setEffect(new DropShadow(20, Color.BLACK));

        Button seeMyActivity=new Button("My Activity");
        Button postAnItem=new Button("Post an Item");
        Button logout=new Button("Logout");
        Button seeProfile=new Button("See my profile");
        Button editProfile=new Button("Edit my Profile");
        Button seeMyRequests=new Button("My requests");
        Button madeRequests=new Button("Requests on my products");
        root.setSpacing(20);
        root.getChildren().addAll(head,rectangle,seeMyActivity,postAnItem,seeProfile,editProfile,seeMyRequests,madeRequests,logout);
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
        postAnItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PostAnItem post=new PostAnItem(databasse);
                try {
                    post.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        seeMyActivity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MyActivity m=new MyActivity(databasse);
                stage.hide();
                try {
                    m.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Utility.logout(stage,databasse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        seeProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MyProfile myProfile=new MyProfile(databasse);
                try {
                    myProfile.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        editProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EditProfile e=new EditProfile(databasse);
                stage.hide();
                try {
                    e.start(stage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
