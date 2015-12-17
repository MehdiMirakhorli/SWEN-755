import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Formatter;
import java.security.SecureRandom;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SessionManager {

    final static int TOKEN_LENGTH = 8;

    public String authenticate(String name, String password) {
        User a_user = User.find(name);
        if(a_user != null){
            if (a_user.getPassword().equals(SessionManager.encryptPassword(password))) {
                return createToken(a_user);
            }
        }
        return null;
    }

    String createToken(User user) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString().substring(0, TOKEN_LENGTH);
        new Session(user, token);
        return token;
    }

    Session checkToken(String token){
        return Session.find(token);
    }

    Boolean checkRole(User user, String role){
        return user.getRole().equals(role);
    }

    static String encryptPassword(String password){
        String sha1 = "";
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash){
        Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
