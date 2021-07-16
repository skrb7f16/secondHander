package sample.posts;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.database.MySqlOperations;
import sample.models.Item;
import sample.resources.Params;

import java.sql.SQLException;

public class SinglePost extends ListCell<Item> {
    HBox root;
    VBox left,right;
    Label itemname,postedBy,datePosted,price,itemType;
    ImageView itemPic;
    MySqlOperations database;
    public SinglePost(MySqlOperations database){
        super();
        this.database=database;
        root=new HBox();
        root.getStylesheets().add(getClass().getResource("../resources/css/singlePost.css").toExternalForm());
        root.getStyleClass().add("root");
        root.setPrefWidth(600);
        left=new VBox();
        left.setPrefWidth(200);
        right=new VBox();
        right.setPrefWidth(400);
        itemname=new Label();
        itemType=new Label();
        postedBy=new Label();
        datePosted=new Label();
        price=new Label();
        itemPic=new ImageView();
        itemPic.setFitWidth(150);
        itemPic.setFitHeight(150);
        left.getChildren().add(itemPic);
        right.getChildren().addAll(itemname,itemType,postedBy,datePosted,price);
        right.setSpacing(5);
        root.getChildren().addAll(left,right);
        root.setSpacing(10);
    }

    @Override
    protected void updateItem(Item item, boolean b) {
        super.updateItem(item, b);
        if(item!=null && !b){

            itemname.setText(item.getItemName());
            price.setText(item.getPrice() +"/-");
            itemPic.setImage(new Image(getClass().getResource("../"+ Params.baseDirectoryForItemImage+item.getItemPic()+".jpg").toExternalForm()));
            datePosted.setText(item.getDatePosted());
            try {
                postedBy.setText("Posted by : "+database.getUsername(item.getPostedBy()));
                itemType.setText(database.getTypeName(item.getItemType()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            setGraphic(root);
        }
        else{
            setGraphic(null);
        }
    }
}
