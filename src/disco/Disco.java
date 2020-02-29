package tcp.vitrola.disco;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Disco {

    private List<DadoDeDisco> dados;

    public Disco() {
        dados = new ArrayList<>();
    }

    public void add(DadoDeDisco dado) {
        dados.add(dado);
    }

    public List<DadoDeDisco> getAll() {
        return dados;
    }

    public boolean isEmpty() {
        return dados.isEmpty();
    }

    public DadoDeDisco getLast() {
        int i = 1;

        while(dados.get(dados.size()-i) instanceof Print) {
            i++;
        }

        System.out.println("GetLast: "+dados.get(dados.size()-i).toString());

        return dados.get(dados.size()-i);
    }

    public boolean lastDadoIsNote() {
        if(isEmpty()) {
            return false;
        }

        if (getLast() instanceof Note) {
            return true;
        }

        return false;
    }

    public void reinsertLastOne() {
        Note lastNote = (Note) getLast();
        add(new Note(lastNote.getMidi(), lastNote.getVolume()));
    }

    public int getLastInstrument() {
        /*
        for(DadoDeDisco d : dados) {
            if(d instanceof InstrumentChanger)
                return ((InstrumentChanger) d).getMidi();
        }
        */

        ListIterator<DadoDeDisco> listIter = dados.listIterator(dados.size());
        while (listIter.hasPrevious()) {
            DadoDeDisco d = listIter.previous();

            if(d instanceof InstrumentChanger)
                return ((InstrumentChanger) d).getMidi();
        }

        return 0;
    }

}
