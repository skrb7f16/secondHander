package sample.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sample.database.MySqlOperations;
import sample.models.Requests;

import java.sql.SQLException;

public class SingleRequestsOnMyProduct extends ListCell<Requests> {
    MySqlOperations database;

    public SingleRequestsOnMyProduct(MySqlOperations database) {
        this.database = database;
    }

    @Override
    protected void updateItem(Requests requests, boolean b) {
        super.updateItem(requests, b);
        if(requests!=null){
            GridPane grid=new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(15);
            //grid.setVgap(3);
            grid.setPadding(new Insets(1,1,1,1));


            Label fromUserName =new Label("From:");
            Label fromUserNameValue = null;
            try {
                fromUserNameValue = new Label(database.getUsername(requests.getFromUser()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            grid.add(fromUserName,0,2);
            grid.add(fromUserNameValue,1,2);

            Label product=new Label("onProduct:");
            Label productValue = null;
            try {
                productValue = new Label(database.getProductName(requests.getOnProduct()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            grid.add(product,0,3);
            grid.add(productValue,1,3);

            Label date =new Label("onDate:");
            Label dateValue =new Label(requests.getOnDate());
            grid.add(date,0,4);
            grid.add(dateValue,1,4);

            Label msg =new Label("Message:");
            Label msgValue =new Label(requests.getMessage());
            grid.add(msg,0,5);
            grid.add(msgValue,1,5);


            Label price =new Label("Price:");
            Label priceValue =new Label(""+requests.getOfferedPrice());
            grid.add(price,0,6);
            grid.add(priceValue,1,6);

            HBox hb = new HBox();
            hb.setPadding(new Insets(15, 12, 15, 12));
            hb.setSpacing(10);

            Button btn1 = new Button();
            btn1.setText("Accept");
            hb.getChildren().add(btn1);

            Button btn2 = new Button();
            btn2.setText("Deny");
            hb.getChildren().add(btn2);

            Button btn3 = new Button();
            btn3.setText("View User Details");
            hb.getChildren().add(btn3);

            grid.add(hb, 1, 7);

            fromUserName.setStyle("-fx-font:normal bold 15px 'serif' ");
            product.setStyle("-fx-font:normal bold 15px 'serif' ");
            date.setStyle("-fx-font:normal bold 15px 'serif' ");
            msg.setStyle("-fx-font:normal bold 15px 'serif' ");
            price.setStyle("-fx-font:normal bold 15px 'serif' ");
            setGraphic(grid);
        }
        else{
            setGraphic(null);
        }
    }
}
