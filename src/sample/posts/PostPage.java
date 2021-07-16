package sample.posts;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import sample.database.MySqlOperations;
import sample.models.Item;
import sample.resources.Params;

public class PostPage extends Application {
    Item item;
    MySqlOperations database;

    public PostPage(Item item, MySqlOperations database) {
        this.item = item;
        this.database = database;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root=new VBox();
        root.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("../resources/css/PostPage.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root,500,650));
        stage.setTitle("Posts");
        HBox head=new HBox();
        Image image=new Image(getClass().getResource("../resources/images/back_arrow_icon.png").toExternalForm());
        ImageView back=new ImageView(image);
        back.setFitWidth(50);
        back.setFitHeight(50);
        back.setPreserveRatio(false);
        Label heading=new Label(item.getItemName());
        heading.setPadding(new Insets(0,0,0,20));
        heading.getStyleClass().add("heading");
        head.getChildren().addAll(back,heading);


        ImageView itemPic=new ImageView(new Image(getClass().getResource("../"+ Params.baseDirectoryForItemImage+item.getItemPic()+".jpg").toExternalForm()));
        itemPic.setFitHeight(200);
        itemPic.setFitWidth(200);
        Label itemPrice=new Label(""+item.getPrice());
        Label postedBy=new Label("Posted by : "+database.getUsername(item.getPostedBy()));
        Label itemDesc=new Label(item.getItemDescription());
        Label itemType=new Label(database.getTypeName(item.getItemType()));
        Label datePosted=new Label(item.getDatePosted());
        TextField makeOffer=new TextField();
        makeOffer.setPromptText("Enter your offer as price or message");
        makeOffer.getStyleClass().add("offer");
        Button submit=new Button("Make An offer");
        TextField phoneNo=new TextField();
        phoneNo.setPromptText("Enter your phone no");
        phoneNo.getStyleClass().add("phone");
        if(item.getPostedBy()!=Params.userId)
        root.getChildren().addAll(head,itemPic,itemType,postedBy,datePosted,itemPrice,itemDesc,makeOffer,phoneNo,submit);
        else
            root.getChildren().addAll(head,itemPic,itemType,postedBy,datePosted,itemPrice,itemDesc);
        stage.show();
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                AllPosts main=new AllPosts(database);
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
