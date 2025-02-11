import java.io.*;
import java.util.HashMap;

public class HuffmanDecode {
    private String encodedStr, decodedStr;
    private int counter;
    public HashMap< String, Character> hmapCodeR;

    public HuffmanDecode( String fname) {

        hmapCodeR = new HashMap< String, Character>();

        readFile( fname);   //  STEP 0: READ CODE TABLE AND ENCODED DATA
        decode();           //  STEP 1: DECODE ENCODED DATA
        writeFile( fname);  //  STEP 2: WRITE DECODED DATA

    }

    public void readFile( String fname) {
        StringBuilder sb = new StringBuilder();
        File fileName = new File( fname);
        try {
            BufferedReader br = new BufferedReader( new FileReader( fileName));
            counter = Integer.parseInt( br.readLine());
            for( int i = 0; i < counter; ++i) {
                String[] s = br.readLine().split("~");
                String code = s[ 0];
                Character ch = ( char)Integer.parseInt( s[1]);
                hmapCodeR.put( code, ch);
            }
            sb.append( br.readLine());
            br.close();
        } catch (IOException e) {
            System.out.println( e);
        }

        encodedStr = sb.toString();
    }

    public void decode() {
        StringBuilder sb = new StringBuilder();
        String t = "";

        for( int i = 0; i < encodedStr.length(); ++i) {
            t += encodedStr.charAt( i);
            if( hmapCodeR.containsKey( t)) {
                sb.append( hmapCodeR.get( t));
                t = "";
            }
        }
        decodedStr = sb.toString();
    }

    public void writeFile( String fname) {
        StringBuilder sb = new StringBuilder();
        File fileIn = new File( fname);
        String Oname = fileIn.getPath().replace( ".8ze", ".txt");

        File fileOut = new File( Oname);

        try {
            BufferedWriter bw = new BufferedWriter( new FileWriter( fileOut));
            bw.write( decodedStr);
            bw.close();
        } catch ( IOException e) {
            System.out.println( e);
        }

    }

}
