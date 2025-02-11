import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncode {
    private String orgStr, encodedStr, table;
    public HashMap< Character, Integer> hmapWC;     //  FOR OCCURRENCE COUNT
    public HashMap< Character, String> hmapCode;    //  FOR CODE( CHARACTER/CODE)
    public HashMap< String, Character> hmapCodeR;   //  FOR CODE( CODE/CHARACTER)
    private PriorityQueue<node> pq;                 //  FOR MINHEAP
    private int counter;                            //  UNIQUE ID ASSIGNED TO EACH NODE
    private int treeSize;                           //  # OF TOTAL NODES IN THE TREE
    private node root;

    //  INNER CLASS
    private class node {
        int uid, weight;
        char ch;
        node left, right;

        //  CONSTRUCTOR FOR CLASS NODE
        private node( Character ch, Integer weight, node left, node right) {
            uid = ++counter;
            this.ch = ch;
            this.weight = weight;
            this.left = left;
            this.right = right;

        }
    }

    public HuffmanEncode( String fname) {
        this.orgStr = readFile( fname);     //  STEP 0: READ INPUT FILE
        this.counter = 0;
        this.treeSize = 0;
        hmapWC = new HashMap< Character, Integer>();
        hmapCode = new HashMap< Character, String>();
        hmapCodeR = new HashMap< String, Character>();
        pq = new PriorityQueue< node>(1, new Comparator<node>() {
            @Override
            public int compare(node n1, node n2) {
                if( n1.weight < n2.weight)
                    return -1;
                else if( n1.weight > n2.weight)
                    return 1;
                return 0;
            }
        });
        countWord();        //  STEP 1: COUNT FREQUENCY OF WORD
        buildTree();        //  STEP 2: BUILD HUFFMAN TREE
        buildCodeTable();   //  STEP 3: BUILD HUFFMAN CODE TABLE
        getCodeTable();     //  STEP 4: GET CODE TABLE
        encode();           //  STEP 5: GENERATE ENCODED DATA
        writeFile( fname);  //  STEP 6: WRITE THE COMPRESSED DATA INTO A FILE


    }

    public static String readFile( String fname) {
        StringBuilder sb = new StringBuilder();
        File filename = new File( fname);
        try {
            BufferedReader in = new BufferedReader( new FileReader( filename));
            String line = in.readLine();
            while( line != null) {
                sb.append( line + "\n");
                line = in.readLine();
            }
            in.close();
        } catch ( IOException e) {
            System.out.println( e);
        }
        return sb.toString();
    }

    private void countWord() {
        Character ch;
        Integer weight;
        for( int i = 0; i < orgStr.length(); ++i) {
            ch = new Character( orgStr.charAt( i));
            if( hmapWC.containsKey( ch) == false)
                weight = new Integer( 1);
            else
                weight = hmapWC.get( ch) + 1;
            hmapWC.put( ch, weight);
        }
    }

    private void buildTree() {
        buildMinHeap(); //  SET ALL LEAF NODES INTO MINHEAP
        node left, right;
        while( ! pq.isEmpty()) {
            left = pq.poll();
            treeSize++;
            if( pq.peek() != null) {
                right = pq.poll();
                treeSize++;
                root = new node( '\0', left.weight + right.weight, left, right);
            } else {    //  ONLY LEFT CHILD. RIGHT = null
                root = new node( '\0', left.weight, left, null);
            }

            if( pq.peek() != null) {
                pq.offer( root);
            } else {    //  = TOP ROOT. FINISHED BUILDING THE TREE
                treeSize++;
                break;
            }
        }
    }

    private void buildMinHeap() {
        for( Map.Entry< Character, Integer> entry : hmapWC.entrySet()) {
            Character ch = entry.getKey();
            Integer weight = entry.getValue();
            node n = new node( ch, weight, null, null);
            pq.offer( n);
        }
    }

    private void buildCodeTable() {
        String code = "";
        node n = root;
        buildCodeRecursion( n, code);   //  RECURSION
    }

    private void buildCodeRecursion( node n, String code) {
        if( n != null) {
            if( ! isLeaf( n)) {     //  n = INTERNAL NODE
                buildCodeRecursion( n.left, code + '0');
                buildCodeRecursion( n.right, code + '1');
            } else {    //  n = LEAF NODE
                hmapCode.put( n.ch, code);
                hmapCodeR.put( code, n.ch);
            }
        }
    }

    private boolean isLeaf( node n) {
        return ( n.left == null) && ( n.right == null);
    }

    private void getCodeTable() {
        StringBuilder sb = new StringBuilder();
        sb.append( hmapCodeR.size() + "\n");
        for( Map.Entry< String, Character> entry : hmapCodeR.entrySet()) {
            String code = entry.getKey();
            int ch = entry.getValue();
            sb.append(code + "~" + ch + "\n");
        }
        table = sb.toString();
    }

    private void encode() {
        StringBuilder sb = new StringBuilder();
        Character ch;
        for( int i = 0; i < orgStr.length(); ++i) {
            ch = orgStr.charAt( i);
            sb.append( hmapCode.get( ch));
        }
        encodedStr = sb.toString();
    }

    private void writeFile( String fname) {
        StringBuilder sb = new StringBuilder();
        File fileIn = new File( fname);
        String Iname = fileIn.getName();
        String Oname = fileIn.getPath().replace( "txt", "8z");
        File fileOut = new File( Oname);

        try {
            BufferedWriter bw = new BufferedWriter( new FileWriter( fileOut));
            bw.write( table + encodedStr);
            bw.close();
        } catch ( IOException e) {
            System.out.println( e);
        }
    }

    public double rate() {
        double os = orgSize();
        double es = encSize();
        return ( os / es);
    }

    public long orgSize() {
        return  orgStr.length() * 8;
    }

    public long encSize() {
        return encodedStr.length();
    }

}
