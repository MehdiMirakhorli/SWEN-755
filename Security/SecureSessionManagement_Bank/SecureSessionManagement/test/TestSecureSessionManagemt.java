/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ManageBean.MbLogin;
import ManageBean.MbSession;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author ivantactukmercado
 */
public class TestSecureSessionManagemt {
    
    
    FacesContext context;
    public TestSecureSessionManagemt() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() {
        context = ContextMocker.mockFacesContext();
        Map<String, Object> session = new HashMap<String, Object>();
        HttpSession httpSession=new SessionMocker();
        HttpServletRequest httpServletRequest=mock(HttpServletRequest.class);
        ExternalContext ext = mock(ExternalContext.class);
        when(ext.getSessionMap()).thenReturn(session);
        when(ext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(context.getExternalContext()).thenReturn(ext);
    }
    
    @After
    public void tearDown() {
        if (context!=null)
        context.release();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testLoginCorrect() {  
        MbLogin log;
        log=new MbLogin();
        log.setUser("ivanC");
        log.setPassword("123");                  
        assertEquals(log.login(), "ManageBankAccount");
     }
     
     @Test
     public void testLoginIncorrect() {
        MbLogin log;
        log=new MbLogin();
        log.setUser("ivanC");
        log.setPassword("1234");                  
        assertEquals(log.login(), "index");
     }
     
     @Test
     public void testLogOut() {
         MbLogin log;
         log=new MbLogin();
         log.setUser("ivanC");
         log.setPassword("123");
         log.login();
         MbSession ses=new MbSession();
         ses.closeSession();
         assertNull(ses.getRole());
         
     }
     
}
