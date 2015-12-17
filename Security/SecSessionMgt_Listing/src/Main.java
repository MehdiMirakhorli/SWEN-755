
public class Main {
    public static void main(String[] args) throws Exception {

        new User("Hue", "1234", "admin");
        new User("Hector", "1234", "user");
        new User("Carlos", "1234", "user");
        new User("Rahul", "1234", "user");

        Server.run();
    }
}
