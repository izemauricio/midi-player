package tcp.vitrola.disco;

/**
 * Created by izemrc on 29/11/2016.
 */
public class Print implements DadoDeDisco {

    private String text;

    public Print(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
