/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageBean;

import java.util.ArrayList;

/**
 *
 * @author ivantactukmercado
 */
public class userDB {
    
    ArrayList<User> users=new ArrayList<>();

    public userDB() {
        loadData();
    }
    
    private void loadData(){
        users.add(new User("ivanC", "123", User.Role.Cashier));
        users.add(new User("ivanAM", "123", User.Role.AccountManager));
        users.add(new User("ivanBM", "123", User.Role.BankManger));        
    }
    
    public User getUser(String username, String password){
        if (username==null || password==null) return null;
        for(User usr:users){
            if (username.equals(usr.getUsername())&& password.equals(usr.getPassword())){
                return usr;
            }
        }        
        return null;
    }
    
}
