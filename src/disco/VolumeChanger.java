package tcp.vitrola.disco;

/**
 * Created by izemrc on 27/11/2016.
 */
public class VolumeChanger implements DadoDeDisco {

    double volume;

    public VolumeChanger(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

}
