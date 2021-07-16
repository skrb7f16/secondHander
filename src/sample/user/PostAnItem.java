package sample.user;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.Main;
import sample.database.MySqlOperations;
import sample.helper.AlertHelper;
import sample.models.Category;
import sample.models.Item;
import sample.resources.Params;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostAnItem extends Application {
    MySqlOperations database;
    ArrayList<Category>categories=new ArrayList<>();
    String path;
    public PostAnItem(MySqlOperations database) {
        this.database = database;
    }

    @Override
    public void start(Stage stage) throws Exception {
        categories=database.getCategories();
        VBox root=new VBox();
        root.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("../resources/css/itempost.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(new Scene(root,600,650));
        stage.setTitle("Register");
        HBox head=new HBox();
        Image image=new Image(getClass().getResource("../resources/images/back_arrow_icon.png").toExternalForm());
        ImageView back=new ImageView(image);
        back.setFitWidth(50);
        back.setFitHeight(50);
        back.setPreserveRatio(false);
        Label heading=new Label("Post an Item form");
        heading.setPadding(new Insets(0,0,0,20));
        heading.getStyleClass().add("heading");
        head.getChildren().addAll(back,heading);

        TextField itemName=new TextField();
        itemName.setPromptText("Item name");
        itemName.getStyleClass().add("itemName");
        TextField itemPrice=new TextField();
        itemPrice.setPromptText("Item Price");
        itemPrice.getStyleClass().add("itemPrice");
        TextArea itemDesc=new TextArea();
        itemDesc.setPromptText("Item Description");
        itemDesc.setPrefRowCount(2);
        itemDesc.getStyleClass().add("desc");
        ComboBox<Category> type=new ComboBox<>();
        type.setItems(FXCollections.observableList(categories));
        type.setCellFactory(new Callback<ListView<Category>, ListCell<Category>>() {
            @Override
            public ListCell<Category> call(ListView<Category> categoryListView) {
                ListCell<Category> listCell=new ListCell<>(){
                    @Override
                    protected void updateItem(Category category, boolean b) {
                        super.updateItem(category, b);
                        if(category!=null){
                            setText(category.getCategoryName());
                        }
                        else{
                            setText(null);
                        }
                    }

                };
                return listCell;
            }
        });

        type.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                if(category==null)return null;
                else return category.getCategoryName();
            }

            @Override
            public Category fromString(String s) {
                return null;
            }
        });
        type.setPrefWidth(200);
        type.setPromptText("Please select a category");
        type.getStyleClass().add("type");
        Button addCat=new Button("Add a category");
        addCat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextInputDialog inputDialog=new TextInputDialog();
                inputDialog.setTitle("Enter your category name");
                inputDialog.show();
                inputDialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
                    @Override
                    public void handle(DialogEvent dialogEvent) {
                        try {
                            int r=database.addCat(inputDialog.getEditor().getText());
                            if(r==1){
                                categories= database.getCategories();
                                type.setItems(FXCollections.observableList(categories));
                                type.setValue(categories.get(categories.size()-1));
                            }
                        } catch (SQLException throwables) {
                            AlertHelper.showAlert(Alert.AlertType.ERROR,stage,"Cannot add",throwables.getMessage());
                        }
                    }
                });
            }
        });
        HBox cat=new HBox();
        cat.getChildren().addAll(type,addCat);
        cat.setAlignment(Pos.CENTER);
        cat.setSpacing(20);

        HBox itemImage=new HBox();
        Label imageName=new Label("");
        imageName.setTextFill(Color.WHITE);
        Button uploadYourImage=new Button("Upload item image");
        itemImage.getChildren().addAll(imageName,uploadYourImage);
        Button submit=new Button("Submit");
        root.getChildren().addAll(head,itemName,itemPrice,itemDesc,cat,itemImage,submit);
        root.setSpacing(20);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UserPage u=new UserPage(database);
                stage.hide();
                try {
                    u.start(stage);
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
                if(itemName.getText().length()==0){
                    return;
                }
                try {
                    Item item=new Item(type.getValue().getCategoryId(),Params.userId,Integer.parseInt(itemPrice.getText()),itemName.getText(),itemDesc.getText());
                    int r=database.addAnItem(item);
                    if(r==1){
                        saveImage(item.getItemPic());
                        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION,stage,"congrats","Your post is uploaded");
                        itemName.setText("");
                        itemPrice.setText("");
                        itemDesc.setText("");
                        imageName.setText("");
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }


    void saveImage(String itemPic){
        File file=new File(path);
        if (file != null) {
            File saveLocation = new File(Params.baseDirectoryForItemImageForCopying+itemPic+".jpg");

            saveLocation.setWritable(true);
            try {
                InputStream is = new FileInputStream(file);
                OutputStream os = new FileOutputStream(saveLocation);
                byte[] buf = new byte[1024];
                int byteReads;
                while ((byteReads = is.read(buf)) > 0) {
                    os.write(buf, 0, byteReads);
                }
                is.close();
                os.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
