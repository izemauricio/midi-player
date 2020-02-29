package tcp.vitrola;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tcp.vitrola.disco.FabricaDeDisco;
import tcp.vitrola.disco.Vitrola;
import tcp.vitrola.disco.Disco;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by izemrc on 02/10/2016.
 */
public class FxMainController {

    private Vitrola vitrola;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextArea textlog;

    @FXML
    private ComboBox ComboBoxInstruments;
    ObservableList list2;

    @FXML
    public void initialize() {
        log("Inicializado");

        try {
            vitrola = new Vitrola();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        List<String> list = vitrola.getInstrumentos();

        list2 = FXCollections.observableList(list);

        list2.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                log("MUDEI" + c.toString());

                //list2.add("A");
            }
        });

        //ComboBoxInstruments.getItems().clear();
        ComboBoxInstruments.setItems(list2);
        ComboBoxInstruments.setValue("Instrumentos");
    }

    public void MenuButtonInstrumentOpen() {
        log("Clicked!");
    }

    public void comboboxinstrumentomousereleased() {
        int index = ComboBoxInstruments.getSelectionModel().getSelectedIndex();
        String instrumento = (String) ComboBoxInstruments.getSelectionModel().getSelectedItem();
        log(instrumento+" index: "+index);
        vitrola.setInstrumento(index);
    }

    public synchronized void log(String text) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        textlog.appendText(sdf.format(cal.getTime()) + ": " + text + "\n");
    }

    public void open() {
        System.out.println("Open Clicked");

        JFileChooser fc = new JFileChooser();

        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            // Read the file and put the information on textedit
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));

                try {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {


                        sb.append(line);
                        //sb.append("*");
                        sb.append(System.lineSeparator());

                        line = br.readLine();
                    }

                    String everything = sb.toString();

                    txtDescription.setText(everything);
                } finally {
                    br.close();
                }
            } catch (Exception e) {
            }
        } else {
        }
    }

    public void save() {
        JFileChooser fc = new JFileChooser();

        int returnVal = fc.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String content = txtDescription.getText();

            try {
                PrintWriter out = new PrintWriter(file.getAbsolutePath());
                out.println(content);
                out.close();
            } catch (Exception e) {
            }
        } else {
        }
    }

    public void play() {
        FabricaDeDisco leitor = new FabricaDeDisco();

        Disco disco = new Disco();

        try {
            disco = leitor.criar(txtDescription.getText());
        } catch (Exception e) {
            log("ERROR: " + e.getMessage() + e.toString());
        }

        try {

            vitrola.setDisco(disco);
            vitrola.setVerbose(this);
            vitrola.play();

        } catch (InterruptedException e) {
            log("ERROR: " + e.getMessage());
        }
    }

    public void stop() {
        log("Botao STOP clicked.");
    }

    public void noteC() {
        txtDescription.appendText("C");
    }

    public void noteD() {
        txtDescription.appendText("D");
    }

    public void noteE() {
        txtDescription.appendText("E");
    }

    public void noteF() {
        txtDescription.appendText("F");
    }

    public void noteG() {
        txtDescription.appendText("G");
    }

    public void noteA() {
        txtDescription.appendText("A");
    }

    public void noteB() {
        txtDescription.appendText("B");
    }

    public void noteP() {
        txtDescription.appendText("p");
    }

    public void noteReset() {
        txtDescription.setText("");
    }

    public void aumentarVolume() {
        txtDescription.appendText("V");
    }

    public void diminuirVolume() {
        txtDescription.appendText("v");
    }

    public void aumentarOitava() {
        txtDescription.appendText(">");
    }

    public void diminuirOitava() {
        txtDescription.appendText("<");
    }

    public void oitavaPadrao() {
        txtDescription.appendText("*");
    }

    public void aumentarBPM() {
        txtDescription.appendText("+");
    }

    public void diminuirBPM() {
        txtDescription.appendText("-");
    }

    public void aumentarIntensidade() {
        txtDescription.appendText("I");
    }

    public void diminuirIntensidade() {
        txtDescription.appendText("i");
    }

    public void about() {
        JOptionPane.showMessageDialog(null, "Vitrola de Cifra\nBETA Version\nv = Diminuir volume\nV = Aumentar volume\ni = Diminuir intensidade\nI = Aumentar intensidade\n< = Diminuir oitava\n> = Aumentar oitava");
    }

    public void exit() {
        Stage stage = (Stage) ComboBoxInstruments.getScene().getWindow();
        stage.close();
    }

}
