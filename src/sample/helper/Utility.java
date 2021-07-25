package sample.helper;

import javafx.stage.Stage;
import sample.Main;
import sample.database.MySqlOperations;
import sample.resources.Params;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class Utility {
    public static void checkloggedIn(MySqlOperations database) throws FileNotFoundException, SQLException {
        Scanner sc=new Scanner(new File(Params.authFolder+"token.txt"));
        while (sc.hasNextLine()){
            Params.token=sc.nextLine();
        }
        sc=new Scanner(new File(Params.authFolder+"username.txt"));
        while (sc.hasNextLine()){
            Params.username=sc.nextLine();
        }
        sc=new Scanner(new File(Params.authFolder+"userid.txt"));
        while (sc.hasNextLine()){
            Params.userId=sc.nextInt();
        }
        if(Params.token==null){
            Params.loogedIn=false;
            Params.currentUser=null;
        }
        else{
            Params.loogedIn=true;
            Params.currentUser=database.getUser();

        }
    }

    public static void logout(Stage stage,MySqlOperations database) throws Exception {
        FileOutputStream writer = new FileOutputStream(Params.authFolder+"token.txt");
        writer.write(("").getBytes());
        writer.close();
        writer=new FileOutputStream(Params.authFolder+"username.txt");
        writer.write(("").getBytes());
        writer.close();
        writer = new FileOutputStream(Params.authFolder+"userid.txt");
        writer.write(("").getBytes());
        writer.close();
        Params.currentUser=null;
        Params.loogedIn=false;
        Params.username=null;
        Params.token=null;
        stage.hide();
        Main main=new Main(database);
        main.start(stage);

    }

    public static int randomNumber(int max,int min){
        return (int)Math.random() * (max - min + 1) + min;
    }
}
