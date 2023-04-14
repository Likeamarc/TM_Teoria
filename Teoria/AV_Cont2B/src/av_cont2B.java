import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;

/**
 * This class encodes a message using the Huffman coding algorithm.
 */
public class av_cont2B {

    /**
     * This inner class represents a node in the Huffman coding tree.
     */
    private static class Node implements Comparable<Node> {
        char ch;
        int freq;
        Node left, right;
        
        /**
         * Constructs a node with the specified character, frequency, left child, and right child.
         * 
         * @param ch    the character represented by the node
         * @param freq  the frequency of the character
         * @param left  the left child of the node
         * @param right the right child of the node
         */
        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        
        /**
         * Determines whether the node is a leaf node.
         * 
         * @return true if the node is a leaf node, false otherwise
         */
        boolean isLeaf() {
            return (left == null && right == null);
        }
        
        /**
         * Compares this node to another node based on frequency.
         * 
         * @param other the node to compare to
         * @return a negative integer, zero, or a positive integer as this node is less than, equal to,
         *         or greater than the specified node
         */
        public int compareTo(Node other) {
            return freq - other.freq;
        }
    }
    
    /**
     * Encodes a message using the given symbol probabilities.
     * 
     * @param prob the symbol probabilities
     * @return a map from each symbol to its corresponding Huffman code
     */
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
    
    /**
     * Recursively builds the Huffman codes for each symbol in the tree rooted at the given node.
     * 
     * @param node  the root node of the tree
     * @param code  the Huffman code for the node
     * @param codes the map of codes to populate
     */
    private static void buildCodes(Node node, String code, Map<Character, String> codes) {
        if (node.isLeaf()) {
            codes.put(node.ch, code);
            return;
        }
        
        buildCodes(node.left, code + "0", codes);
        buildCodes(node.right, code + "1", codes);
    }
    /**

    Encodes a given message using the provided symbol-to-code map.

    @param message the message to be encoded

    @param symbolToCodeMap a map containing the code for each symbol in the message

    @return the encoded message as a string

    @throws RuntimeException if an invalid input symbol is encountered
    */
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

    /**

    Calculates the entropy of a source given a map of symbol frequencies.

    @param prob a map containing the frequency of each symbol in the source

    @return the entropy of the source as a double
    */
    public static double getEntropy(Map<Character, Double> prob) {
	    double entropy = 0.0;
	    int totalFreq = 0;
	
	    // Calculate the total frequency of all symbols
	    for (Double freq : prob.values()) {
	    	totalFreq += (10*freq);
	    }
	
	    // Calculate the entropy of the source
	    for (Double freq: prob.values()) {
		    double probability = (double) (10*freq) / totalFreq;
		    entropy -= probability * (Math.log(probability) / Math.log(2));
	    }
	
	    return entropy;
    }

    /**

    Calculates the average bit length of a Huffman code given maps of symbol

    frequencies and symbol-to-code mappings.

    @param prob a map containing the frequency of each symbol in the source

    @param codes a map containing the code for each symbol in the source

    @return the average bit length of the Huffman code as a double
    */
    public static double getBitLength(Map<Character, Double> prob,
    Map<Character, String> codes) {
    	double averageBitLength = 0.0;
    	int totalFreq = 0;

    	// Calculate the total frequency of all symbols
    	for (Double freq : prob.values()) {
    		totalFreq+= (10*freq);
    	}

    	// Calculate the average bit length of the Huffman code
    	for (Map.Entry<Character, Double> entry : prob.entrySet()) {
    		char symbol = entry.getKey();
    		Double freq = entry.getValue();
    		String code = codes.get(symbol);
    		double probability = (double) (10*freq) / totalFreq;
    		averageBitLength += probability * code.length();
    	}

    	return averageBitLength;
    }
    
    /**
     * Main method to demonstrate the use of the Huffman encoding algorithm.
     * Reads the probabilities from a CSV file and stores them in a HashMap.
     * Uses the Huffman encoding algorithm to encode a randomly generated sequence of symbols.
     * Prints the original and encoded sequences, as well as the entropy and average bit length of the source.
     * @param args command line arguments (not used in this implementation)
     */
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
                char key = data[0].charAt(0); //Agafem el primer element, per tant, Diamant pasa a D
                							  //I el 10 pasa a 1.
                Double value = Double.parseDouble(data[1]);
                // Do something with the first and second elements
                prob.put(key, value);
                System.out.println(key + " " + value);
            }
            System.out.println("\n");
            
            Map<Character, String> codesTest = encode(prob);
            for (char ch : codesTest.keySet()) {
                System.out.println(ch + ": " + codesTest.get(ch));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n");
      //Creo un nou Map, i vaig afegint Math.random perque siguin
        //aleatoris.

        Map<Character, String> codes = av_cont2B.encode(prob);
        StringBuilder sequence = new StringBuilder();
        int sequenceLength = 1000;
        for (int i = 0; i < sequenceLength; i++) {
            double randomNumber = Math.random();
            char symbol = ' ';
            double sum = 0.0;
            for (char ch : prob.keySet()) {
                sum += prob.get(ch);
                if (randomNumber < sum) {
                    symbol = ch;
                    break;
                }
            }
            sequence.append(symbol);
        }

        //Utilitzo el .encode, el .toString per a conseguir les diferents sequencies.

        String encodedSequence2 = av_cont2B.encode(sequence.toString(), codes);
        System.out.println("Secuencia original: " + sequence.toString() + "\n");
        System.out.println("Secuencia codificada: " + encodedSequence2 + "\n");
        System.out.println("Entropia: " + getEntropy(prob) + "\n");
        System.out.println("N bits: " + getBitLength(prob, codes) + "\n");
    }
}