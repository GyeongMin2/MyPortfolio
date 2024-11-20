public class Member{
    private String userId;
    private String userName;
    boolean loginStatus = false;
    Member(){
    };

    public void updateStatus(){
        System.out.println(userId);
        System.out.println(userName);
        loginStatus = true;
    }
    public void logout(){
        this.loginStatus = false;
        this.userId = null;
        this.userName = null;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
