package sample.user;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.database.MySqlOperations;
import sample.models.Rewards;
import sample.resources.Params;

import java.sql.SQLException;

public class SingleReward extends ListCell<Rewards> {
    MySqlOperations database;
    Stage stage;
    public SingleReward(MySqlOperations database, Stage stage) {
        this.database = database;
        this.stage=stage;
    }

    @Override
    protected void updateItem(Rewards rewards, boolean b) {
        super.updateItem(rewards, b);
        if(rewards!=null){
            GridPane grid=new GridPane();
            grid.setAlignment(Pos.CENTER_LEFT);
            grid.setHgap(20);
            grid.setPrefWidth(550);
            grid.setVgap(10);
            grid.setPadding(new Insets(10,10,10,10));

            Label product=new Label("On Product:");
            Label productValue = null;
            try {
                productValue = new Label(database.getProductName(rewards.getOnProduct()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            grid.add(product,0,0);
            grid.add(productValue,1,0);
            Label date =new Label("onDate:");
            Label dateValue =new Label(rewards.getDateRecieved());
            grid.add(date,0,1);
            grid.add(dateValue,1,1);
            Label price =new Label("Rewards:");
            Label priceValue =new Label(""+rewards.getAmount()+" Points");
            grid.add(price,0,2);
            grid.add(priceValue,1,2);
            Button btn = new Button("Redeem");
            grid.add(btn,2,0,1,3);

            product.setStyle("-fx-font:normal bold 18 'serif' ");
            date.setStyle("-fx-font:normal bold 18 'serif' ");

            price.setStyle("-fx-font:normal bold 18 'serif' ");

            if(rewards.getRedeemed()==1){
                btn.setDisable(true);
            }
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Params.currentUser.setTotalReward(Params.currentUser.getTotalReward()+rewards.getAmount());
                        int r=database.redeemReward(rewards);
                        if(r==1){
                            MyRewards myRewards=new MyRewards(database);
                            myRewards.start(stage);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            setGraphic(grid);
        }
        else{
            setGraphic(null);
        }
    }
}
