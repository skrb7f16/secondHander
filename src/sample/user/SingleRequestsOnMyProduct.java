package sample.user;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.database.MySqlOperations;
import sample.models.Requests;
import sample.models.User;

import java.sql.SQLException;

public class SingleRequestsOnMyProduct extends ListCell<Requests> {
    MySqlOperations database;
    Stage stage;
    public SingleRequestsOnMyProduct(MySqlOperations database,Stage stage) {
        this.database = database;
        this.stage=stage;
    }

    @Override
    protected void updateItem(Requests requests, boolean b) {
        super.updateItem(requests, b);
        if(requests!=null){
            GridPane grid=new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(15);
            grid.setVgap(10);
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



            Button btn1 = new Button();
            btn1.setText("Accept");


            Button btn2 = new Button();
            btn2.setText("Deny");


            Button btn3 = new Button();
            btn3.setText("View User Details");


            grid.add(btn1, 3, 2);
            grid.add(btn2, 3, 3);
            grid.add(btn3, 3, 4);
            if(requests.getIsAccepted()==0){
                btn3.setVisible(false);
            }
            else{
                btn1.setDisable(true);
                btn2.setDisable(true);
            }
            fromUserName.setStyle("-fx-font:normal bold 15px 'serif' ");
            product.setStyle("-fx-font:normal bold 15px 'serif' ");
            date.setStyle("-fx-font:normal bold 15px 'serif' ");
            msg.setStyle("-fx-font:normal bold 15px 'serif' ");
            price.setStyle("-fx-font:normal bold 15px 'serif' ");
            setGraphic(grid);

            btn3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        User u=database.getUser(requests.getFromUser());
                        UserProfile userProfile=new UserProfile(database,u,2);
                        stage.hide();
                        userProfile.start(stage);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            btn1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        int r=database.acceptRequest(requests);
                        if(r==1){
                            btn3.setVisible(true);
                            btn1.setDisable(true);
                            btn2.setDisable(true);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            btn2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        int r=database.deleteRequest(requests);
                        if(r==1){
                            stage.hide();
                            RequestsOnMyProduct re=new RequestsOnMyProduct(database);
                            re.start(stage);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        else{
            setGraphic(null);
        }
    }
}
