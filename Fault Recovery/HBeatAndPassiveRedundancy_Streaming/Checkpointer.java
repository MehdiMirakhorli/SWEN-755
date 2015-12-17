import java.util.Map;
import java.util.HashMap;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class Checkpointer {
    Map<String, Integer> clientsStates;

    public Checkpointer(){
        try{
            read();
        }catch(Exception e){
            System.err.println(e);
            clientsStates = new HashMap<String, Integer>();
        }
    }

    public void addClient(String clientStringIdentifier){
        if(!clientsStates.containsKey(clientStringIdentifier))
            updateState(clientStringIdentifier, 0);
    }

    public void updateState(String clientStringIdentifier, int currentStreamData){
        try{
            clientsStates.put(clientStringIdentifier, currentStreamData);
        }catch(Exception e){
            System.err.println(e);
        }
    }

    public int getClientLastState(String clientStringIdentifier){
      return clientsStates.get(clientStringIdentifier);
    }

    public void showValues(){
        for (Map.Entry<String,Integer> entry : clientsStates.entrySet()) {
          String key = entry.getKey();
          int value = entry.getValue();
          System.out.println("[" + key + ", " + value + "]");
        }
    }

    public void persist() throws Exception {
        FileOutputStream fos = new FileOutputStream("clients_states.data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(clientsStates);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    public void read() throws Exception {
        FileInputStream fis = new FileInputStream("clients_states.data");
        ObjectInputStream ois = new ObjectInputStream(fis);
        clientsStates = (Map<String, Integer>) ois.readObject();
        ois.close();
    }
}
