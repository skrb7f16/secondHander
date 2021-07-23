package sample.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sample.database.MySqlOperations;
import sample.models.Requests;

import java.sql.SQLException;

public class SingleRequestMadeByMe extends ListCell<Requests> {
    MySqlOperations database;

    public SingleRequestMadeByMe(MySqlOperations database) {
        this.database = database;
    }

    @Override
    protected void updateItem(Requests requests, boolean b) {
        super.updateItem(requests, b);
        if(requests!=null){

            GridPane grid=new GridPane();
            grid.setAlignment(Pos.CENTER_LEFT);
            grid.setHgap(20);
            grid.setPrefWidth(550);
            //grid.setVgap(3);
            grid.setPadding(new Insets(10,10,10,10));


            Label toUserName =new Label("To:");
            Label toUserNameValue = null;
            try {
                toUserNameValue = new Label(database.getUsername(requests.getToUser()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            grid.add(toUserName,0,2);
            grid.add(toUserNameValue,1,2);

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
            Label priceValue =new Label(requests.getOfferedPrice()+"");
            grid.add(price,0,6);
            grid.add(priceValue,1,6);

            Label accepted =new Label("Accepted:");
            Label acceptedValue;
            if(requests.getIsAccepted()==1)
             acceptedValue=new Label("Yes");
            else
                 acceptedValue=new Label("No");
            grid.add(accepted,0,7);
            grid.add(acceptedValue,1,7);

            Button btn = new Button("View Details");
            HBox detailsBtn = new HBox(10);
            detailsBtn.setAlignment(Pos.BASELINE_LEFT);
            detailsBtn.getChildren().add(btn);
            grid.add(detailsBtn, 1, 8);

            toUserName.setStyle("-fx-font:normal bold 18 'serif' ");
            product.setStyle("-fx-font:normal bold 18 'serif' ");
            date.setStyle("-fx-font:normal bold 18 'serif' ");
            msg.setStyle("-fx-font:normal bold 18 'serif' ");
            price.setStyle("-fx-font:normal bold 18 'serif' ");
            accepted.setStyle("-fx-font:normal bold 18 'serif' ");
            setGraphic(grid);
        }
        else{
            setGraphic(null);
        }
    }
}
