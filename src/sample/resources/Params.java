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
    public static String CREATE_CATEGORY_TABLE="create table if not exists category(id int auto_increment primary key," +
            "     categoryName varchar(64) not null unique," +
            "     categoryAdded datetime default now()," +
            "     totalItem int default 0);";
    public static String CREATE_ITEM_TABLE="create table if not exists item(id int auto_increment primary key," +
            "     itemName varchar(64) not null," +
            "     itemPrice int not null," +
            "     itemType int not null," +
            "     datePosted datetime default now()," +
            "     isSold BOOLEAN default false," +
            "     soldTo int," +
            "     soldInPrice int," +
            "     itemDescription text not null," +
            "     postedBy int not null," +
            "     foreign key(postedBy) references user(id)," +
            "     foreign key(soldTo) references user(id)," +
            "     foreign key(itemType) references category(id)," +
            "     itemPic varchar(256));";

    public static String CREATE_REQUESTS_TABLE="create table if not exists requests(id int auto_increment primary key," +
            "     onProduct int not null," +
            "     fromUser int not null," +
            "     toUser int not null," +
            "     onDate datetime default now()," +
            "     isAccepted tinyint default 0," +
            "     message text not null," +
            "     priceOffered int not null," +
            "     foreign key(fromUser) references user(id)" +
            "     ,foreign key(toUser) references user(id)," +
            "     foreign key(onProduct) references item(id));";


    public static String authFolder="F:/java project/secondHanders/auth/";
    public static String token;
    public static String username;
    public static int userId;
    public static Boolean loogedIn;
    public static User currentUser;

    public static String categoryInitial="insert into category(categoryName) values(\"Mobiles\"),(\"Clothes\"),(\"Car\"),(\"Bike\");";
}
