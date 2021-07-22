package sample.user;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
        root.getStylesheets().add(getClass().getResource("../resources/css/myprofile.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root,500,700));
        stage.setTitle("User Panel");
        HBox head=new HBox();
        Image image=new Image(getClass().getResource("../resources/images/back_arrow_icon.png").toExternalForm());
        ImageView back=new ImageView(image);
        back.setFitWidth(50);
        back.setFitHeight(50);
        back.setPreserveRatio(false);
        Image pic=new Image(getClass().getResource("../"+Params.currentUser.getDp()).toExternalForm());
        Rectangle rectangle = new Rectangle(0, 0, 280, 220);
        rectangle.setArcWidth(100.0);
        rectangle.setArcHeight(100.0);
        ImagePattern pattern = new ImagePattern(pic);
        rectangle.setFill(pattern);
        rectangle.setEffect(new DropShadow(20, Color.BLACK));
        head.getChildren().addAll(back,rectangle);
        head.setSpacing(30);

        stage.setTitle("Your Credentials");
        GridPane grid=new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25,25,25,25));

        Text scenetitle =new Text("Welcome "+Params.username);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL,30));
        grid.add(scenetitle,0,0,2,1);

        Label userName =new Label("Firstname");
        Label userNameValue=new Label(Params.currentUser.getFname());
        grid.add(userName,0,1);
        grid.add(userNameValue,1,1);


        Label userNameL =new Label("Lastname");
        Label userNameLValue =new Label(Params.currentUser.getLname());
        grid.add(userNameL,0,2);
        grid.add(userNameLValue,1,2);

        Label pw=new Label("Password");
        Label pwValue =new Label(Params.currentUser.getPassword());
        grid.add(pw,0,3);
        grid.add(pwValue,1,3);

        Label mailId =new Label("Email");
        Label mailIdValue =new Label(Params.currentUser.getEmail());
        grid.add(mailId,0,4);
        grid.add(mailIdValue,1,4);

        Label location =new Label("Location");
        Label locationValue =new Label(Params.currentUser.getAddress());
        grid.add(location,0,5);
        grid.add(locationValue,1,5);


        Label itemsTotal =new Label("Posts");
        Label itemsValue =new Label(""+Params.currentUser.getTotalPost());
        grid.add(itemsTotal,0,6);
        grid.add(itemsValue,1,6);

        final Text actiontarget=new Text();
        grid.add(actiontarget,1,7);

        root.getChildren().addAll(head,grid);
        root.setPadding(new Insets(10,10,10,10));
        root.setAlignment(Pos.CENTER);


        stage.show();
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UserPage main=new UserPage(database);
                stage.hide();
                try {
                    main.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
