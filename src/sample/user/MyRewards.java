package sample.user;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import sample.database.MySqlOperations;
import sample.models.Item;
import sample.models.Rewards;
import sample.posts.SinglePost;
import sample.resources.Params;

import java.util.ArrayList;

public class MyRewards extends Application {
    MySqlOperations database;
    ArrayList<Rewards> items;
    public MyRewards(MySqlOperations database) {
        this.database = database;

    }

    @Override
    public void start(Stage stage) throws Exception {
        items=database.getAllRewards();
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
        Label heading=new Label("Your Rewards");
        heading.setPadding(new Insets(0,0,0,20));
        heading.getStyleClass().add("heading");
        head.getChildren().addAll(back,heading);
        HBox rewards=new HBox();

        Label rewardAmoount=new Label("Total Rewards earned : "+ Params.currentUser.getTotalReward());
        ImageView rewardImage=new ImageView(new Image(getClass().getResource("../resources/images/coin.png").toExternalForm()));
        rewardImage.setFitWidth(40);
        rewardImage.setFitHeight(40);
        rewardImage.setPreserveRatio(false);

        rewards.getChildren().addAll(rewardAmoount,rewardImage);
        rewards.setSpacing(20);
        rewards.setPrefWidth(500);
        rewardAmoount.getStyleClass().add("heading");
        rewards.setAlignment(Pos.CENTER);


        ListView<Rewards> itemListView=new ListView<>();
        itemListView.setItems(FXCollections.observableList(items));
        itemListView.setPrefHeight(600);
        itemListView.setCellFactory(new Callback<ListView<Rewards>, ListCell<Rewards>>() {

            @Override
            public ListCell<Rewards> call(ListView<Rewards> itemListView) {
                return new SingleReward(database,stage);
            }
        });
        itemListView.setStyle("-fx-control-inner-background:  #0f2027");

        root.getChildren().addAll(head,rewards,itemListView);
        root.setSpacing(20);
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
