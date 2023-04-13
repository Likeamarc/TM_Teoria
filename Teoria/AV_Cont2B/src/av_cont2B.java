import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;
import com.beust.jcommander.JCommander;

public class av_cont2B {
	
	private static class Node implements Comparable<Node> {
        char ch;
        int freq;
        Node left, right;
        
        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        
        boolean isLeaf() {
            return (left == null && right == null);
        }
        
        public int compareTo(Node other) {
            return freq - other.freq;
        }
    }
    
    public static Map<Character, String> encode(Map<Character, Double> prob) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        for (char ch : prob.keySet()) {
            double freq = prob.get(ch);
            pq.offer(new Node(ch, (int) (freq * 100), null, null));
        }
        
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.offer(parent);
        }
        
        Map<Character, String> codes = new HashMap<>();
        buildCodes(pq.peek(), "", codes);
        return codes;
    }
    
    private static void buildCodes(Node node, String code, Map<Character, String> codes) {
        if (node.isLeaf()) {
            codes.put(node.ch, code);
            return;
        }
        
        buildCodes(node.left, code + "0", codes);
        buildCodes(node.right, code + "1", codes);
    }
    
    public static String encode(String message, Map<Character, String> symbolToCodeMap) {
        StringBuilder encoded = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char currentSymbol = message.charAt(i);
            String currentCode = symbolToCodeMap.get(currentSymbol);
            if (currentCode == null) {
                throw new RuntimeException("Invalid input symbol: " + currentSymbol);
            }
            encoded.append(currentCode);
        }

        return encoded.toString();
    }

	public static void main(String[] args) {
		
		String csvFile = "HuffmanProbs.csv";
        String line = "";
        String delimiter = ",";
        String[] data = new String[2];
        Map<Character, Double> prob = new HashMap<>();
        //ArgParser parser = new ArgParser();
		//JCommander jComm = null;
		

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
        	
        	//jComm = new JCommander(parser, args);
			//csvFile = parser.getInput();
			
            while ((line = br.readLine()) != null) {

                // use comma as separator
                data = line.split(delimiter);
                char key = data[0].charAt(0);
                Double value = Double.parseDouble(data[1]);
                // Do something with the first and second elements
                prob.put(key, value);
                System.out.println(key + " " + value);
            }
            System.out.println("\n");
            
            Map<Character, String> codes = encode(prob);
            for (char ch : codes.keySet()) {
                System.out.println(ch + ": " + codes.get(ch));
            }
            
            String encodedMessage = encode("DKQK19D", codes);
            System.out.println("Encoded message: " + encodedMessage);
            
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
