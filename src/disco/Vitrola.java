package tcp.vitrola.disco;

import tcp.vitrola.FxMainController;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mauricio on 14/09/16.
 */
public class Vitrola {

    private double bpm;
    private double volume;

    private Disco disco;
    private Synthesizer sintetizador;
    private MidiChannel canais[];

    private FxMainController logger = null;

    public Vitrola() throws MidiUnavailableException {
        sintetizador = MidiSystem.getSynthesizer();

        sintetizador.open();

        canais = sintetizador.getChannels();

        Soundbank bank = sintetizador.getDefaultSoundbank();

        sintetizador.loadAllInstruments(bank);

        resetVitrola();
    }

    private void resetVitrola() {
        setBPM(120);

        setVolume(60);

    }

    public void setVolume(double volume) {

        if (volume > 127)
            volume = 127;

        if (volume < 5)
            volume = 5;

        this.volume = volume;

        canais[0].controlChange(7, (int) volume);
    }

    public double getVolume() {
        return volume;
    }

    public void setBPM(double bpm) {

        if (bpm > 300)
            bpm = 300;

        if (bpm < 30)
            bpm = 30;

        this.bpm = bpm;
    }

    public double getBPM() {
        return this.bpm;
    }

    public void setDisco(Disco disco) {
        this.disco = disco;
    }

    public void setInstrumento(int index) {
        Instrument[] instr = sintetizador.getDefaultSoundbank().getInstruments();
        Patch instrumentInformation = instr[index].getPatch();
        canais[0].programChange(instrumentInformation.getBank(), instrumentInformation.getProgram());
    }

    public List<String> getInstrumentos() {
        Instrument[] orchestra = sintetizador.getAvailableInstruments();
        List<String> list = new ArrayList<String>();
        for (Instrument i : orchestra) {
            list.add(i.toString());
        }
        return list;
    }

    public String getInstrumentos2() {
        Instrument[] orchestra = sintetizador.getAvailableInstruments();

        StringBuilder sb = new StringBuilder();
        String eol = System.getProperty("line.separator");

        for (Instrument i : orchestra) {
            sb.append(i.toString());
            sb.append(eol);
        }

        return sb.toString();
    }

    // Retorna intervalo x em milisegundo para ter y bpm (1 minuto = 60,000 milisegundo)
    private double getMilisegundoFromBMP(double bmp) {
        return 60000 / bpm;
    }

    public void setVerbose(FxMainController i) {
        logger = i;
    }

    public void play() throws InterruptedException {

        resetVitrola();

        new Thread() {

            public void run() {

                for (DadoDeDisco dado : disco.getAll()) {

                    if (dado instanceof Note) {
                        Note note = (Note) dado;

                        canais[0].noteOn(note.getMidi(), note.getVolume());
                        try {
                            Thread.sleep((long) getMilisegundoFromBMP(getBPM()));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        canais[0].noteOff(note.getMidi());
                    } else if (dado instanceof Pause) {
                        try {
                            Thread.sleep((long) getMilisegundoFromBMP(getBPM()));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (dado instanceof InstrumentChanger) {
                        InstrumentChanger novo_instrumento = (InstrumentChanger) dado;

                        setInstrumento(novo_instrumento.getMidi());
                    } else if (dado instanceof BmpChanger) {
                        BmpChanger novo_bpm = (BmpChanger) dado;

                        setBPM(getBPM() * novo_bpm.getBPM());
                    } else if (dado instanceof VolumeChanger) {
                        VolumeChanger novo_volume = (VolumeChanger) dado;

                        setVolume(getVolume() * novo_volume.getVolume());
                    } else if (dado instanceof Print) {
                        Print print = (Print) dado;

                        System.out.println("" + print.getText());

                        if (logger != null) {
                            //logger.log(print.getText());
                        }
                    } else {
                        continue;
                    }

                }

            }
        }.start();

    }

}
