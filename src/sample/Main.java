package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.auth.Login;
import sample.auth.Register;
import sample.database.MySqlOperations;
import sample.helper.Utility;
import sample.posts.AllPosts;
import sample.resources.Params;
import sample.user.UserPage;


public class Main extends Application {
    MySqlOperations database;
    public static String [] argss;
    public Main(MySqlOperations database) {
        this.database = database;
    }

    public Main() {
        database=new MySqlOperations();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);

        Utility.checkloggedIn(database);
        VBox root=new VBox();
        root.getStyleClass().add("root");
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(root, 800, 600));

        Label heading=new Label(Params.appName);
        heading.getStyleClass().add("heading");


        HBox semiRoot=new HBox();
        VBox left=new VBox();
        VBox right=new VBox();
        left.setPrefWidth(400);
        left.setPrefHeight(500);
        right.setPrefWidth(400);
        left.setAlignment(Pos.CENTER);
        Image img=new Image(getClass().getResource("resources/images/front-page-img.jpg").toExternalForm());
        ImageView frontPageImage=new ImageView(img);
        frontPageImage.setFitHeight(400);
        frontPageImage.setFitWidth(350);
        left.getChildren().addAll(frontPageImage);

        Button login=new Button("Login");
        login.setPrefWidth(100);
        Button register=new Button("Register");
        register.setPrefWidth(100);
        Button posts=new Button("Posts");
        posts.setPrefWidth(100);
        Button user=new Button("User");
        user.setPrefWidth(100);
        right.getChildren().addAll(login,register,posts,user);
        if(Params.loogedIn){
            login.setVisible(false);
            register.setVisible(false);
        }
        else{
            user.setVisible(false);
        }
        right.setAlignment(Pos.CENTER);
        right.setSpacing(20);
        semiRoot.getChildren().addAll(left,right);


        root.getChildren().addAll(heading,semiRoot);
        root.setAlignment(Pos.TOP_CENTER);
        primaryStage.show();

        root.getStylesheets().add(getClass().getResource("resources/css/style.css").toExternalForm());


        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Register r=new Register(database);
                primaryStage.hide();
                try {
                    r.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Login r=new Login(database);
                primaryStage.hide();
                try {
                    r.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        user.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UserPage r=new UserPage(database);
                primaryStage.hide();
                try {
                    r.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        posts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AllPosts p=new AllPosts(database);
                try {
                    p.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) {
        argss=args;
        launch(args);
    }
}
