// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        HashTable ht = new HashTable();
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/neetiadhia/IdeaProjects/Assignment_8_9/alice_in_wonderland.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (String word : line.split("\\s+")) {
                    word = word.replaceAll("[^A-Za-z]", "").toLowerCase();
                    ht.insert(word, 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ht.createHistogram();
    }
}

class HashTable {
    private int capacity;
    private int currentLength;
    private LinkedList<Node>[] buckets;
    private static final int CAP_IN = 300;

    public HashTable() {
        this.capacity = CAP_IN;
        this.currentLength = 0;
        this.buckets = new LinkedList[this.capacity];
        for (int i = 0; i < this.capacity; i++) {
            this.buckets[i] = new LinkedList<>();
        }
    }

    private int hash(String key) {
        int keyLength = key.length();
        int hashFunctionVal = 0;
        for (int i = 0; i < keyLength; i++) {
            hashFunctionVal += (i + keyLength) * (int) key.charAt(i);
        }
        return hashFunctionVal % this.capacity;
    }

    public void insert(String key, int value) {
        this.currentLength++;
        int index = this.hash(key);
        LinkedList<Node> bucket = this.buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value += value;
                return;
            }
        }
        bucket.add(new Node(key, value));
    }

    public void list() {
        for (LinkedList<Node> bucket : this.buckets) {
            for (Node node : bucket) {
                System.out.println(node.key + " " + node.value);
            }
        }
    }

    public Integer find(String key) {
        int index = this.hash(key);
        LinkedList<Node> bucket = this.buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    public Integer remove(String key) {
        int index = this.hash(key);
        LinkedList<Node> bucket = this.buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                this.currentLength--;
                int result = node.value;
                bucket.remove(node);
                return result;
            }
        }
        return null;
    }

    public void createHistogram() {
        ArrayList<Integer> collisionListLengths = new ArrayList<>();

        for (LinkedList<Node> bucket : this.buckets) {
            int collisionListLength = bucket.size();
            collisionListLengths.add(collisionListLength);
        }

        // Display the histogram
        displayHistogram(collisionListLengths);
    }

    // Helper method to display the histogram
    private void displayHistogram(ArrayList<Integer> lengths) {
        ArrayList<Integer> temp = new ArrayList<>();
        System.out.println("Collision List Lengths Histogram:");
        for (int i = 0; i < lengths.size(); i++) {
            System.out.println("Bucket " + i + ": " + "Length " + lengths.get(i));
            temp.add(lengths.get(i));
        }

        System.out.println(temp);
    }
}

class Node {
    String key;
    int value;

    public Node(String key, int value) {
        this.key = key;
        this.value = value;
    }
}