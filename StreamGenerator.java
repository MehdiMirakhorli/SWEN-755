public class StreamGenerator {
    int data = 0;

    public StreamGenerator(int initialData){
        this.data = initialData;
    }

    public int nextData(){
        int currentData = data;
        data += 1;
        return currentData;
    }

    public int getCurrentData(){
      return data;
    }
}
