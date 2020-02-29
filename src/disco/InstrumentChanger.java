package tcp.vitrola.disco;

/**
 * Created by izemrc on 27/11/2016.
 */
public class InstrumentChanger implements DadoDeDisco {

    private int instrument_midi_number = 0;

    public InstrumentChanger(int midi) {
        this.instrument_midi_number = midi;
    }

    public int getMidi() {
        return instrument_midi_number;
    }
}
