package sample.user;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.database.MySqlOperations;
import sample.helper.AlertHelper;
import sample.models.User;
import sample.resources.Params;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class EditProfile extends Application {
    MySqlOperations database;
    String path;

    public EditProfile(MySqlOperations database) {
        this.database = database;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root=new VBox();
        root.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("../resources/css/register.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root,800,800));
        stage.setTitle("Register");
        HBox head=new HBox();
        Image image=new Image(getClass().getResource("../resources/images/back_arrow_icon.png").toExternalForm());
        ImageView back=new ImageView(image);
        back.setFitWidth(50);
        back.setFitHeight(50);
        back.setPreserveRatio(false);
        Label heading=new Label("Edit form");
        heading.setPadding(new Insets(0,0,0,20));
        heading.getStyleClass().add("heading");
        head.getChildren().addAll(back,heading);


        Image pic=new Image(getClass().getResource("../"+Params.currentUser.getDp()).toExternalForm());
        Rectangle rectangle = new Rectangle(0, 0, 140, 110);
        rectangle.setArcWidth(50.0);
        rectangle.setArcHeight(50.0);
        ImagePattern pattern = new ImagePattern(pic);
        rectangle.setFill(pattern);
        rectangle.setEffect(new DropShadow(20, Color.BLACK));

        TextField fname=new TextField();
        fname.getStyleClass().add("fname");
        fname.setText(Params.currentUser.getFname());
        TextField lname=new TextField();
        lname.getStyleClass().add("lname");
        lname.setText(Params.currentUser.getLname());
        TextField username=new TextField();
        username.getStyleClass().add("username");
        username.setText(Params.currentUser.getUsername());
        TextField email=new TextField();
        email.getStyleClass().add("email");
        email.setText(Params.currentUser.getEmail());
        TextField phone=new TextField();
        phone.getStyleClass().add("phone");
        phone.setText(""+Params.currentUser.getPhoneNo());
        HBox passBox=new HBox();
        PasswordField password=new PasswordField();
        password.getStyleClass().add("password");
        password.setPromptText("Please enter the new Password you wanna change to");
        password.setText(Params.currentUser.getPassword());
        Button showPass=new Button("Show Password");
        passBox.getChildren().addAll(password,showPass);
        passBox.setSpacing(10);
        TextArea address=new TextArea();
        address.setText(Params.currentUser.getAddress());
        address.setPrefRowCount(2);
        address.setFocusTraversable(false);
        address.getStyleClass().add("address");
        HBox dpContainer=new HBox();
        Label imageName=new Label("");
        imageName.setTextFill(Color.WHITE);
        Button uploadYourImage=new Button("Upload your new image");
        dpContainer.getChildren().addAll(imageName,uploadYourImage);
        Button submit=new Button("Update ");
        root.setSpacing(20);
        root.getChildren().addAll(head,rectangle,fname,lname,username,email,phone,passBox,address,dpContainer,submit);
        stage.show();

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UserPage main=new UserPage(database);
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
                        Params.currentUser.getDp()
                        ,email.getText(), Long.parseLong(phone.getText()));
                try {
                    int r=database.updateProfile(user);
                    if(r==1){
                        saveImage(username.getText());
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION,stage,"Congrats","Your profile is " +
                                "updated please login again and restart application to see the changes");

                    }
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                    AlertHelper.showAlert(Alert.AlertType.ERROR,stage,"Error",throwables.getMessage());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }


            }
        });

        showPass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION,stage,"Your password is ",Params.currentUser.getPassword());
            }
        });
    }

    void saveImage(String username){
        if(path==null)return;
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
