import java.util.Random;

public class ErrorGenerator{
    public static boolean generateError(){
        Random random = new Random();
        int number = random.nextInt(100);
        if(number > 90 && number < 95)
            return false;
        return true;
    }
}
