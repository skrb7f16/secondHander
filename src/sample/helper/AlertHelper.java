package sample.helper;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;
import sample.auth.Login;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

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
                        "login again and restart application to see the changes"){
                    Login login=new Login();
                    try {
                        login.start(owner);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(alertType== Alert.AlertType.CONFIRMATION && message=="Your post is uploaded please launch and again to see your posts"){

                    try {





                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    public static void restart() throws IOException, InterruptedException {
        StringBuilder cmd = new StringBuilder();
        cmd.append("\"C:\\Program Files\\Java\\jdk-14.0.1\\bin\\java.exe\" --module-path C:\\java\\javafx-sdk-11.0.2\\lib --add-modules javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA 2021.1.3\\lib\\idea_rt.jar=57551:C:\\Program Files\\JetBrains\\IntelliJ IDEA 2021.1.3\\bin\" -Dfile.encoding=UTF-8 -classpath \"F:\\java project\\secondHanders\\out\\production\\secondHanders;C:\\java\\javafx-sdk-16\\lib\\src.zip;C:\\java\\javafx-sdk-16\\lib\\javafx-swt.jar;C:\\java\\javafx-sdk-16\\lib\\javafx.web.jar;C:\\java\\javafx-sdk-16\\lib\\javafx.base.jar;C:\\java\\javafx-sdk-16\\lib\\javafx.fxml.jar;C:\\java\\javafx-sdk-16\\lib\\javafx.media.jar;C:\\java\\javafx-sdk-16\\lib\\javafx.swing.jar;C:\\java\\javafx-sdk-16\\lib\\javafx.controls.jar;C:\\java\\javafx-sdk-16\\lib\\javafx.graphics.jar;C:\\java\\mysql-connector-java-8.0.25.jar\" sample.Main");
        Runtime.getRuntime().exec(cmd.toString());
        Thread.sleep(1000);
        System.exit(0);

    }
}