package sample.resources;

import sample.models.User;

public class Params {
    public static String appName="Second Handers";
    public static String databaseName=" secondhander";
    public static String baseDirectoryForDp="../images/dp/";
    public static String baseDirectoryForDpForCopying="F:\\java project\\secondHanders\\src\\images\\dp\\";
    public static String baseDirectoryForItemImage="../images/posts/";
    public static String baseDirectoryForItemImageForCopying="F:\\java project\\secondHanders\\src\\images\\posts\\";
    public static String url = "jdbc:mysql://localhost:3306";
    public static String urlAfterCreating = "jdbc:mysql://localhost:3306/"+databaseName;
    public static String USERNAME_FOR_DATABASE = "root";
    public static String PASSWORD_FOR_DATABASE = "7216";
    public static String CREATE_DATABASE="create database if not exists "+databaseName;
    public static String CREATE_USER_TABLE="create table if not exists user" +
            "(id int auto_increment primary key," +
            "fname varchar(64) not null," +
            "lname varchar(64) not null," +
            "username varchar(64) unique not null," +
            "password varchar(256) not null," +
            "dp varchar(256)," +
            "phoneNo bigint not null," +
            "email varchar(256)," +
            "address varchar(256) not null," +
            "dateJoined datetime default now()," +
            "token varchar(256) not null,"+
            "totalPost int default 0);";
    public static String authFolder="F:/java project/secondHanders/auth/";
    public static String token;
    public static String username;
    public static int userId;
    public static Boolean loogedIn;
    public static User currentUser;

    public static String categoryInitial="insert into category(categoryName) values(\"Mobiles\"),(\"Clothes\"),(\"Car\"),(\"Bike\");";
}
