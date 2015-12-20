/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ivantactukmercado
 */
@ManagedBean
@RequestScoped
public class MbLogin {
    
    private String user;
    private String password;
    private final HttpServletRequest httpServletRequest ;
    private final FacesContext faceContext;
    private FacesMessage facesMessage;

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Creates a new instance of MbLogin
     */
    public MbLogin() {
        faceContext=FacesContext.getCurrentInstance();        
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
    }
    
    public String login(){
        userDB db=new userDB();
        User usr=db.getUser(user,password);
        if(usr!=null){
            httpServletRequest.getSession().setAttribute("sessionUser", usr.getUsername());
            httpServletRequest.getSession().setAttribute("userRole", usr.getRole());
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO,"ACCESS GRANTED",null);
            faceContext.addMessage(null, facesMessage);
            return "ManageBankAccount";
        }else{
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR,"ACCESS DENIED",null);
            faceContext.addMessage(null, facesMessage);
            return "index";
        }
    }
    
}
