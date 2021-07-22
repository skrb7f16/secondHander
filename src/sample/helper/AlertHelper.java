package sample.helper;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.auth.Login;

public class AlertHelper {

    public static void showAlert(Alert.AlertType alertType, Stage owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();

        alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent dialogEvent) {
                if(alertType== Alert.AlertType.INFORMATION && message=="Your profile is updated please " +
                        "login again to see the changes"){
                    Login login=new Login();
                    try {
                        login.start(owner);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}