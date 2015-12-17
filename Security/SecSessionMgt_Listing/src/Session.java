import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class Session {
    private static Map<String, Session> sessions = new HashMap<String, Session>();
    private User user;
    private Date expireDate;
    private String token;
    final static long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
    final static int minutes = 1;

    public Session(User user, String token) {
        this(user, token, 1);
    }

    public Session(User user, String token, int expireMinutes) {
        setExpireDate(expireMinutes);
        this.user = user;
        this.token = token;
        Session.sessions.put(token, this);
    }

    public Date getExpireDate(){
        return expireDate;
    }

    public String getToken(){
        return token;
    }

    private void setExpireDate(int minutes){
        this.expireDate = new Date((new Date()).getTime() + (minutes * ONE_MINUTE_IN_MILLIS));
    }

    static public Session find(String token){
        return Session.sessions.get(token);
    }

    boolean isExpired(){
        return (getExpireDate().compareTo(new Date()) < 0);
    }

    boolean hasRole(String role){
        return (getUser().getRole().equals(role));
    }

    public User getUser(){
        return user;
    }

    public String toString(){
        return "user: " + user + " token: " + token + " expire: " + expireDate;
    }
}