package sample.models;

public class User {
    String fname;
    String lname;
    String username;
    String password;
    String address;
    String dp;
    String email;
    String dateJoined;
    String token;
    int id,totalPost;
    long phoneNo;

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(int totalPost) {
        this.totalPost = totalPost;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public User(String fname, String lname, String username, String password, String address, String dp, String email, int id, int totalPost, long phoneNo) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = password;
        this.address = address;
        this.dp = dp;
        this.email = email;
        this.id = id;
        this.totalPost = totalPost;
        this.phoneNo = phoneNo;
    }

    public User(String fname, String lname, String username, String password, String address, String dp, String email, long phoneNo) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = password;
        this.address = address;
        this.dp = dp;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public User() {

    }
}
