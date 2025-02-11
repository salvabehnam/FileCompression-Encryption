import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Crypto {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static void encrypt( String key, File input, File output)
        throws CryptoException {
        doCrypto( Cipher.ENCRYPT_MODE, key, input, output);
    }

    public static void decrypt( String key, File input, File output)
        throws CryptoException {
        doCrypto( Cipher.DECRYPT_MODE, key, input, output);
    }

    private static void doCrypto( int cipherMode, String key, File input, File output)
        throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec( key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance( TRANSFORMATION);
            cipher.init( cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream( input);
            byte[] inputBytes = new byte[ ( int) input.length()];
            inputStream.read( inputBytes);

            byte[] outputBytes = cipher.doFinal( inputBytes);

            FileOutputStream outputStream = new FileOutputStream( output);
            outputStream.write( outputBytes);

            inputStream.close();
            outputStream.close();
        } catch ( NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    public static String keyGen( String pass) {
        char[] c = new char[ 16 - pass.length()];
        for( int i = 0; i < c.length; ++i) {
            c[ i] = 'J';
        }
        return pass + new String( c);
    }
}
