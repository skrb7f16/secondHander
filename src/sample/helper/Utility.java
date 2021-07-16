package sample.helper;

import sample.database.MySqlOperations;
import sample.resources.Params;

import java.io.File;
import java.io.FileNotFoundException;
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
}
