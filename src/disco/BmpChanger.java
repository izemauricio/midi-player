package tcp.vitrola.disco;

/**
 * Created by izemrc on 27/11/2016.
 */
public class BmpChanger implements DadoDeDisco {

    private double bpm;

    public BmpChanger() {
        this.bpm = 60;
    }

    public BmpChanger(double bpm) {
        this.bpm = bpm;
    }

    public double setBPM() {
        return bpm;
    }

    public double getBPM() {
        return bpm;
    }

}
