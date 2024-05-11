import java.util.Random;

public class MyTestingClass {
    private String id;

    public MyTestingClass(String id) {
        this.id = id;
    }

    // Custom hashCode method
    @Override
    public int hashCode() {
        int hash = 7;
        for (int i = 0; i < id.length(); i++) {
            hash = hash * 31 + id.charAt(i);
        }
        return hash;
    }

    public static void main(String[] args) {
        MyHashTable<MyTestingClass, String> table = new MyHashTable<>();

        // Adding random 10000 elements to the hashtable
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            String id = "Element" + i;
            MyTestingClass key = new MyTestingClass(id);
            table.put(key, "Value" + i);

        }
    }
}
