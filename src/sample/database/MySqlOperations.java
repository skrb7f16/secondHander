package sample.database;

import sample.models.Category;
import sample.models.Item;
import sample.models.User;
import sample.resources.Params;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;

public class MySqlOperations {
    Connection con;
    public MySqlOperations() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(Params.url,Params.USERNAME_FOR_DATABASE,Params.PASSWORD_FOR_DATABASE);
            Statement stmt=con.createStatement();
            stmt.execute(Params.CREATE_DATABASE);
            con=DriverManager.getConnection(Params.urlAfterCreating,Params.USERNAME_FOR_DATABASE,Params.PASSWORD_FOR_DATABASE);
            stmt=con.createStatement();
            stmt.execute(Params.CREATE_USER_TABLE);
            stmt.execute(Params.CREATE_CATEGORY_TABLE);
            stmt.execute(Params.CREATE_ITEM_TABLE);
        }catch(Exception e){ System.out.println(e);}
    }

    public int register(User user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        PreparedStatement pstm=con.prepareStatement(
                "insert into user(fname,lname,username,password,dp,phoneNo,email,address,token) values" +
                        "(?,?,?,?,?,?,?,?,?);"
        );
        pstm.setString(1,user.getFname());
        pstm.setString(2,user.getLname());
        pstm.setString(3,user.getUsername());
        pstm.setString(4,user.getPassword());
        pstm.setString(5,user.getDp());
        pstm.setLong(6,user.getPhoneNo());
        pstm.setString(7,user.getEmail());
        pstm.setString(8,user.getAddress());

        byte[] salt = new byte[16];
        KeySpec spec = new PBEKeySpec((user.getUsername()+user.getPassword()).toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<hash.length;i++){
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        pstm.setString(9, sb.toString());
        int r=pstm.executeUpdate();
        return r;
    }

    public User login(String username,String password) throws SQLException, IOException {
        PreparedStatement pstm=con.prepareStatement("select * from user where username=? and password=?");
        pstm.setString(1,username);
        pstm.setString(2,password);
        ResultSet resultSet=pstm.executeQuery();
        User u=null;
        while (resultSet.next()){
            u=new User();
            u.setId(resultSet.getInt(1));
            u.setFname(resultSet.getString(2));
            u.setLname(resultSet.getString(3));
            u.setUsername(resultSet.getString(4));
            u.setPassword(resultSet.getString(5));
            u.setDp(resultSet.getString(6));
            u.setPhoneNo(resultSet.getLong(7));
            u.setEmail(resultSet.getString(8));
            u.setAddress(resultSet.getString(9));
            u.setDateJoined(resultSet.getTimestamp(10).toString());
            u.setToken(resultSet.getString(11));
            u.setTotalPost(resultSet.getInt(12));

        }
        if(u!=null) {
            FileWriter fw = new FileWriter(Params.authFolder + "token.txt");
            fw.write(u.getToken());
            fw.close();
            fw = new FileWriter(Params.authFolder + "username.txt");
            fw.write(u.getUsername());
            fw.close();
            fw = new FileWriter(Params.authFolder + "userid.txt");
            fw.write(""+u.getId());
            fw.close();
        }
        return u;
    }


    public User getUser() throws SQLException {
        PreparedStatement pstm=con.prepareStatement("select * from user where username=? and token=?");
        pstm.setString(1,Params.username);
        pstm.setString(2,Params.token);
        ResultSet resultSet=pstm.executeQuery();
        User u=null;
        while (resultSet.next()){
            u=new User();
            u.setId(resultSet.getInt(1));
            u.setFname(resultSet.getString(2));
            u.setLname(resultSet.getString(3));
            u.setUsername(resultSet.getString(4));
            u.setPassword(resultSet.getString(5));
            u.setDp(resultSet.getString(6));
            u.setPhoneNo(resultSet.getLong(7));
            u.setEmail(resultSet.getString(8));
            u.setAddress(resultSet.getString(9));
            u.setDateJoined(resultSet.getTimestamp(10).toString());
            u.setToken(resultSet.getString(11));
            u.setTotalPost(resultSet.getInt(12));

        }
        return u;
    }
    public ArrayList<Category> getCategories() throws SQLException {
        PreparedStatement pstm=con.prepareStatement("select * from category");
        ResultSet r=pstm.executeQuery();
        ArrayList<Category>categories=new ArrayList<>();
        while (r.next()){
            Category c=new Category();
            c.setCategoryId(r.getInt(1));
            c.setCategoryName(r.getString(2));
            c.setCategoryAdded(r.getTimestamp(3).toString());
            c.setTotalItems(r.getInt(4));
            categories.add(c);
        }
        return categories;
    }

    public int addCat(String cat) throws SQLException {
        PreparedStatement pstm=con.prepareStatement("insert into category(categoryName) values(?)");
        pstm.setString(1,cat);
        int r=pstm.executeUpdate();
        return r;
    }

    public int addAnItem(Item item) throws SQLException {
        int id;
        PreparedStatement pstm=con.prepareStatement("insert into item(itemName,itemPrice,itemDescription,itemType,postedBy,itemPic)values(?,?,?,?,?,?)");
        pstm.setString(1,item.getItemName());
        pstm.setInt(2,item.getPrice());
        pstm.setString(3,item.getItemDescription());
        pstm.setInt(4,item.getItemType());
        pstm.setInt(5,item.getPostedBy());
        pstm.setString(6,item.getItemPic());
        int r=pstm.executeUpdate();
        return r;
    }

    public ArrayList<Item> getAllPosts() throws SQLException {
        PreparedStatement pstm=con.prepareStatement("select * from item;");
        ResultSet r=pstm.executeQuery();
        ArrayList<Item> items=new ArrayList<>();
        while (r.next()){
            Item item=new Item();
            item.setId(r.getInt(1));
            item.setItemName(r.getString(2));
            item.setPrice(r.getInt(3));
            item.setItemType(r.getInt(4));
            item.setDatePosted(r.getTimestamp(5).toString());
            item.setIsSold(r.getInt(6));
            item.setSoldTo(r.getInt(7));
            item.setSoldPrice(r.getInt(8));
            item.setItemDescription(r.getString(9));
            item.setPostedBy(r.getInt(10));
            item.setItemPic(r.getString(11));
            if(item.getIsSold()==0){
                items.add(item);
            }
        }
        return items;
    }
}
