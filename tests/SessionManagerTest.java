import org.junit.*;
import junit.framework.JUnit4TestAdapter;
/**
 * Authentication Architecture Breaker Test
 */
public class SessionManagerTest{
  @Before
  public void setUp(){
    new User("Hue", "1234", "admin");
  }

  @Test
  public void testAuthenticationWithValidCredentials(){
    SessionManager s = new SessionManager();
    Assert.assertNotNull(s.authenticate("Hue", "1234"));
  }

  @Test
  public void testAuthenticationWithInvalidCredentials(){
    SessionManager s = new SessionManager();
    Assert.assertNull(s.authenticate("Hue", "invalid"));
  }

  @Test
  public void testAuthenticationReturnsValidToken(){
    SessionManager s = new SessionManager();
    String token = s.authenticate("Hue", "1234");
    Assert.assertEquals(token.length(), SessionManager.TOKEN_LENGTH);
  }

  @After
  public void tearDown(){
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(SessionManagerTest.class);
  }
}
