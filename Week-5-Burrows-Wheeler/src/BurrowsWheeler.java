import java.util.Arrays;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	// if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
    	if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
    }
	
	// apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
    	String s = BinaryStdIn.readString();
    	CircularSuffixArray csa = new CircularSuffixArray(s);
    	
    	// write original string index
    	for (int i = 0; i < csa.length(); i++) {
    		if (csa.index(i) == 0) {
    			BinaryStdOut.write(i);
    			break;
    		}
    	}
    	
    	// write letters in last column of sorted list
    	for (int i = 0; i < s.length(); i++) {
    		int pos = (csa.index(i) + csa.length() - 1) % csa.length();
    		BinaryStdOut.write(s.charAt(pos));
    	}
    	 
    	BinaryStdOut.close();
    }
    
    public static void decode() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] t = s.toCharArray();
        char[] firstCol = new char[t.length];

        for (int i = 0; i < t.length; i++)
            firstCol[i] = t[i];
        
        Arrays.sort(firstCol);
        
        int[] lookFromPos = new int[R];
        int[] next = new int[t.length];

        // First, construct the next array...
        for (int i = 0; i < t.length; i++) {
            next[i] = getNextForChar(firstCol[i], t, lookFromPos);
        }

    	// Reconstruct string
        int i, ptr;
        for (i = 0, ptr = first; i < next.length; i++, ptr = next[ptr]) {
            BinaryStdOut.write(firstCol[ptr]); 
        }
            
        BinaryStdOut.close();
        
    }
    
    // Instance Variables
    private static final int R = 256;
    
    // Private Methods
    private static int getNextForChar(char c, char[] t, int[] lookFromPos)
    {
        for (int i = lookFromPos[c]; i < t.length; i++) {
            if (t[i] == c) {
                lookFromPos[c] = i + 1; // during next search look don't look at chars until i
                return i;
            }
        }

        throw new java.lang.IllegalArgumentException();
    }
}
