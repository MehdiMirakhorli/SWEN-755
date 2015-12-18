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
public class User {
    private final String username;
    private final String password;
    private final Role role;
    
    public enum Role{
        BankManger,AccountManager,Cashier
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
    
   
    
    
}
