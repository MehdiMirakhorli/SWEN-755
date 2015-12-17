import org.junit.*;
import junit.framework.JUnit4TestAdapter;
/**
 * Session Expire Time Architecture Breaker Test
 */
public class SessionTest{
  Session session;
  Session expiredSession;
  @Before
  public void setUp(){
    SessionManager s = new SessionManager();
    User user = new User("Hue", "1234", "admin");
    String token = s.authenticate("Hue", "1234");
    session = new Session(user, token);
    expiredSession = new Session(user, token, -1);
  }

  @Test
  public void testSessionIsNotExpired(){
    Assert.assertFalse(session.isExpired());
  }

  @Test
  public void testSessionIsExpired(){
    Assert.assertTrue(expiredSession.isExpired());
  }

  @After
  public void tearDown(){
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(SessionTest.class);
  }
}
