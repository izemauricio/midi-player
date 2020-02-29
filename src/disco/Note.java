package tcp.vitrola.disco;

/**
 * Created by mauricio on 14/09/16.
 */
public class Note implements DadoDeDisco {

    private int midi;
    private int volume;

    public Note(int numero_midi, int volume) {
        this.midi = numero_midi;
        this.volume = volume;
    }

    public int getMidi() {
        return midi;
    }

    public void setMidi(int midi) {
        this.midi = midi;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

}
