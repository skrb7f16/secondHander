package sample.user;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;
import sample.database.MySqlOperations;
import sample.models.Item;
import sample.posts.PostPage;
import sample.posts.SinglePost;

import java.util.ArrayList;

public class MyActivity extends Application {
    MySqlOperations database;
    ArrayList<Item> items;
    public MyActivity(MySqlOperations database) {
        this.database = database;
    }
    @Override
    public void start(Stage stage) throws Exception {
        items=database.getAllMyPosts();
        VBox root=new VBox();
        root.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("../resources/css/register.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root,800,750));
        stage.setTitle("Posts");
        HBox head=new HBox();
        Image image=new Image(getClass().getResource("../resources/images/back_arrow_icon.png").toExternalForm());
        ImageView back=new ImageView(image);
        back.setFitWidth(50);
        back.setFitHeight(50);
        back.setPreserveRatio(false);
        Label heading=new Label("Latest posts");
        heading.setPadding(new Insets(0,0,0,20));
        heading.getStyleClass().add("heading");
        head.getChildren().addAll(back,heading);


        ListView<Item> itemListView=new ListView<>();
        itemListView.setItems(FXCollections.observableList(items));
        itemListView.setPrefHeight(600);
        itemListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> itemListView) {
                return new SinglePost(database);
            }
        });
        itemListView.setStyle("-fx-control-inner-background:  #0f2027");
        root.getChildren().addAll(head,itemListView);
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

        itemListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Item item=itemListView.getSelectionModel().getSelectedItem();
                PostPage postPage=new PostPage(item,database);
                stage.hide();
                try {
                    postPage.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
