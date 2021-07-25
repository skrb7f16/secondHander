package sample.posts;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import sample.models.Item;
import sample.resources.Params;
import sample.user.MyActivity;

import java.sql.SQLException;

public class PostPage extends Application {
    Item item;
    MySqlOperations database;
    String from;
    public PostPage(Item item, MySqlOperations database,String from) {
        this.item = item;
        this.database = database;
        this.from=from;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root=new VBox();
        root.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("../resources/css/PostPage.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root,600,750));
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
        Label itemPrice=new Label("Price : Rs. "+item.getPrice());
        Label postedBy=new Label("Posted by : "+database.getUsername(item.getPostedBy()));
        Label itemDesc=new Label(item.getItemDescription());
        itemDesc.setWrapText(true);
        Label itemType=new Label(database.getTypeName(item.getItemType()));
        Label datePosted=new Label("Posted at :-"+item.getDatePosted());
        datePosted.getStyleClass().add("date");
        TextArea makeOffer= new TextArea();
        makeOffer.setPromptText("Enter your offer as price or message");
        makeOffer.getStyleClass().add("offer");
        makeOffer.setPrefRowCount(2);
        Button submit=new Button("Make An offer");
        TextField moneyOffered=new TextField();
        moneyOffered.setPromptText("Enter your offer price");
        moneyOffered.getStyleClass().add("phone");
        Label soldTo=new Label();
        Label soldAt=new Label();
        if(item.getIsSold()==1) {
             soldTo = new Label("Sold to: " +database.getUsername(item.getSoldTo()));
             soldAt = new Label("Sold At: " +item.getSoldPrice());


        }

        if(item.getPostedBy()!=Params.userId && Params.loogedIn)
        root.getChildren().addAll(head,itemPic,itemType,postedBy,datePosted,itemPrice,itemDesc,makeOffer,moneyOffered,submit);
        else
            root.getChildren().addAll(head,itemPic,itemType,postedBy,datePosted,itemPrice,itemDesc,soldTo,soldAt);
        stage.show();
        root.setSpacing(5);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(from=="AllPost"){
                AllPosts main=new AllPosts(database);
                stage.hide();
                try {
                    main.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
                else if(from=="Userpage"){
                    MyActivity myActivity=new MyActivity(database);
                    try {
                        myActivity.start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(makeOffer.getText().length()<10 || moneyOffered.getText().length()==0){
                    AlertHelper.showAlert(Alert.AlertType.ERROR,stage,"Error","Cannot make an empty request");
                    return;
                }
                try {
                    int r=database.addRequest(makeOffer.getText(),Integer.parseInt(moneyOffered.getText()),
                            item.getPostedBy(),item.getId()
                            );
                    if(r==1){
                        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION,stage,"Success","Request has been made");
                        makeOffer.setText("");
                        moneyOffered.setText("");
                    }
                    else{
                        AlertHelper.showAlert(Alert.AlertType.ERROR,stage,"Error","Cannot make an request sorry :(");
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
