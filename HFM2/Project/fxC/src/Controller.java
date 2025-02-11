import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


public class Controller implements Initializable {

    @FXML
    private Label lbl_01;

    @FXML
    private Label lbl_33;

    @FXML
    private Label lbl_11;

    @FXML
    private Label lbl_22;

    @FXML
    private Label lbl_02;

    @FXML
    private Label lbl_03;

    @FXML
    private TextField txt_fld;

    @FXML
    private Label lbl_04;


    private FileChooser fc;

    @Override
    public void initialize(URL location, ResourceBundle resource){
        purge();
        fc = new FileChooser();
    }

    @FXML
    void slctFileDcd(ActionEvent event) {
        purge();

        FileChooser.ExtensionFilter fileExtention =
                new FileChooser.ExtensionFilter(
                        "8Z files (*.8z)", "*.8z");

        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add( fileExtention);

        File file = fc.showOpenDialog( Main.stage);


        if( file != null) {
            String pass = txt_fld.getText();
            if( pass.length() > 16) {
                lbl_04.setText("Password Max Length Is 16 Chars");
                return;
            }

            pass = Crypto.keyGen( pass);

            File ff = new File( file.getAbsolutePath().replace( ".8z", ".8ze"));

            try {
                Crypto.decrypt( pass, file, ff);
            } catch ( CryptoException ex) {
                lbl_04.setText( "Invalid Password");
                System.out.println( ex.getMessage());
                return;
            }

            String fname = ff.getAbsolutePath();

            HuffmanDecode h = new HuffmanDecode( fname);

            ff.delete();

            fc.setInitialDirectory( file.getParentFile());

            lbl_01.setText("Decoded Successfully");
        }

    }

    @FXML
    void slctFileEncd(ActionEvent event) {
        purge();

        FileChooser.ExtensionFilter fileExtention =
                new FileChooser.ExtensionFilter(
                        "TEXT files (*.txt)", "*.txt");

        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add( fileExtention);


        File file = fc.showOpenDialog( Main.stage);

        if( file != null) {
            String pass = txt_fld.getText();
            if( pass.length() > 16) {
                lbl_04.setText("Password Max Length Is 16 Chars");
                return;
            }
            String fname = file.getAbsolutePath();
            HuffmanEncode h = new HuffmanEncode( fname);

            pass = Crypto.keyGen( pass);

            String f = file.getPath().replace( ".txt", ".8z");
            File ff = new File( f);
            try {
                Crypto.encrypt( pass, ff, ff);
            } catch ( CryptoException ex) {
                System.out.println( ex.getMessage());
            }

            fc.setInitialDirectory( file.getParentFile());

            lbl_01.setText( "Original Size");
            lbl_11.setText( Long.toString( h.orgSize()) + " bits");
            lbl_02.setText( "Compressed Size");
            lbl_22.setText( Long.toString( h.encSize()) + " bits");
            lbl_03.setText( "Compression Rate");
            lbl_33.setText( Double.toString( h.rate()));
        }

    }

    void purge() {
        lbl_01.setText( null);
        lbl_11.setText( null);
        lbl_02.setText( null);
        lbl_22.setText( null);
        lbl_03.setText( null);
        lbl_33.setText( null);
        lbl_04.setText( null);
    }
}
