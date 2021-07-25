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

public class MyRewards extends Application {
    MySqlOperations database;

    public MyRewards(MySqlOperations database) {
        this.database = database;
    }

    @Override
    public void start(Stage stage) throws Exception {
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

        Label rewardAmoount=new Label("Total Rewards earned : "+45);
        ImageView rewardImage=new ImageView(new Image(getClass().getResource("../resources/images/coin.png").toExternalForm()));
        rewardImage.setFitWidth(50);
        rewardImage.setFitHeight(50);
        rewards.getChildren().addAll(rewardAmoount,rewardImage);
        rewards.setSpacing(20);
        rewards.setPrefWidth(500);
        rewardAmoount.getStyleClass().add("heading");
        rewards.setAlignment(Pos.CENTER);
        root.getChildren().addAll(head,rewards);
        stage.show();
    }
}
