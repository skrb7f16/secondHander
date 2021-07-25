package sample.auth;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.database.MySqlOperations;
import sample.helper.AlertHelper;
import sample.models.User;
import sample.resources.Params;
import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;


public class Register extends Application {
    MySqlOperations database;
    String path;

    public Register(MySqlOperations database) {
        this.database = database;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root=new VBox();
        root.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("../resources/css/register.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root,600,650));
        stage.setTitle("Register");
        HBox head=new HBox();
        Image image=new Image(getClass().getResource("../resources/images/back_arrow_icon.png").toExternalForm());
        ImageView back=new ImageView(image);
        back.setFitWidth(50);
        back.setFitHeight(50);
        back.setPreserveRatio(false);
        Label heading=new Label("Registeration form");
        heading.setPadding(new Insets(0,0,0,20));
        heading.getStyleClass().add("heading");
        head.getChildren().addAll(back,heading);




        TextField fname=new TextField();
        fname.getStyleClass().add("fname");
        fname.setPromptText("First name");
        TextField lname=new TextField();
        lname.getStyleClass().add("lname");
        lname.setPromptText("Last name");
        TextField username=new TextField();
        username.getStyleClass().add("username");
        username.setPromptText("Username");
        TextField email=new TextField();
        email.getStyleClass().add("email");
        email.setPromptText("Email");
        TextField phone=new TextField();
        phone.getStyleClass().add("phone");
        phone.setPromptText("Phone");
        PasswordField password=new PasswordField();
        password.getStyleClass().add("password");
        password.setPromptText("Password");
        TextArea address=new TextArea();
        address.setPromptText("Address");
        address.setPrefRowCount(2);
        address.setFocusTraversable(false);
        address.getStyleClass().add("address");
        HBox dpContainer=new HBox();
        Label imageName=new Label("");
        imageName.setTextFill(Color.WHITE);
        Button uploadYourImage=new Button("Upload your image");
        dpContainer.getChildren().addAll(imageName,uploadYourImage);
        Button submit=new Button("Register");
        root.setSpacing(20);
        root.getChildren().addAll(head,fname,lname,username,email,phone,password,address,dpContainer,submit);
        stage.show();

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main main=new Main();
                stage.hide();
                try {
                    main.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        uploadYourImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                FileChooser fileChooser=new FileChooser();
                //Set extension filter
                FileChooser.ExtensionFilter extFilterJPG
                        = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
                FileChooser.ExtensionFilter extFilterjpg
                        = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");

                fileChooser.getExtensionFilters()
                        .addAll(extFilterJPG, extFilterjpg );
                File file = fileChooser.showOpenDialog(null);
                imageName.setText(file.getPath());
                path=file.getPath();

            }
        });
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User user=new User(fname.getText(),lname.getText(),username.getText(),
                        password.getText(),address.getText(),
                        Params.baseDirectoryForDp+username.getText()+".jpg"
                        ,email.getText(), Long.parseLong(phone.getText()));
                try {
                    int r=database.register(user);
                    if(r==1){
                        saveImage(username.getText());
                        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION,stage,"Congrats",username.getText()+" is registered successfully restart your application to logins");
                        fname.setText("");
                        lname.setText("");
                        username.setText("");
                        password.setText("");
                        address.setText("");
                        email.setText("");
                        phone.setText("");

                    }
                } catch (SQLException throwables) {
                    AlertHelper.showAlert(Alert.AlertType.ERROR,stage,"Error",throwables.getMessage());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    void saveImage(String username){
        File file=new File(path);
        if (file != null) {
            File saveLocation = new File(Params.baseDirectoryForDpForCopying+username+".jpg");
            File saveLocation1 = new File(Params.baseDirectoryForDpForCopying2+username+".jpg");

            saveLocation.setWritable(true);
            try {
                InputStream is = new FileInputStream(file);
                OutputStream os = new FileOutputStream(saveLocation);
                OutputStream os2 = new FileOutputStream(saveLocation1);
                byte[] buf = new byte[1024];
                int byteReads;
                while ((byteReads = is.read(buf)) > 0) {
                    os.write(buf, 0, byteReads);
                    os2.write(buf, 0, byteReads);

                }
                is.close();
                os.close();
                os2.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
