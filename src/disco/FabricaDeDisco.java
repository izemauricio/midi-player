package tcp.vitrola.disco;

/**
 * Created by mauricio on 14/09/16.
 */
public class FabricaDeDisco {

    public static final int NOTA_C3 = 60;
    public static final int NOTA_D3 = 62;
    public static final int NOTA_E3 = 64;
    public static final int NOTA_F3 = 65;
    public static final int NOTA_G3 = 67;
    public static final int NOTA_A3 = 69;
    public static final int NOTA_B3 = 71;

    private int oitava = 0; // 0 to 127
    private int intensidade = 100; // 0 to 127

    private void setOitavaUp() {
        oitava += 12;

        // No maximo 4 oitavas
        if(oitava > 48) {
            oitava = 48;
        }

        if(oitava < -48) {
            oitava = -48;
        }
    }

    private void setOitavaDown() {
        oitava -= 12;

        // No maximo 4 oitavas
        if(oitava > 48) {
            oitava = 48;
        }

        if(oitava < -48) {
            oitava = -48;
        }
    }

    private void setOitavaUpOrReset() {
        oitava += 12;

        // No maximo 4 oitavas
        if(oitava > 48) {
            oitava = 0;
        }

        if(oitava < -48) {
            oitava = 0;
        }
    }

    private int getOitava() {
        return oitava;
    }

    private boolean isCharInsideString(char c, String s) {
        return (s.indexOf(c) != -1);
    }

    private void setIntensidade(double i) {
        intensidade = (int) (intensidade * i);

        if(intensidade>127)
            intensidade = 127;

        if(intensidade < 0)
            intensidade = 0;
    }

    public Disco criar(String cifras) throws Exception {

        if(cifras.isEmpty())
            throw new Exception("Não existe cifra para ler.");

        Disco disco = new Disco();

        for (char cifra : cifras.toCharArray()) {

            switch (cifra) {

                case 'C':
                    disco.add(new Note(NOTA_C3 + oitava, intensidade));
                    disco.add(new Print("Note C"));
                    break;

                case 'D':
                    disco.add(new Note(NOTA_D3 + oitava, intensidade));
                    disco.add(new Print("Note D"));
                    break;

                case 'E':
                    disco.add(new Note(NOTA_E3 + oitava, intensidade));
                    disco.add(new Print("Note E"));
                    break;

                case 'F':
                    disco.add(new Note(NOTA_F3 + oitava, intensidade));
                    disco.add(new Print("Note F"));
                    break;

                case 'G':
                    disco.add(new Note(NOTA_G3 + oitava, intensidade));
                    disco.add(new Print("Note G"));
                    break;

                case 'A':
                    disco.add(new Note(NOTA_A3 + oitava, intensidade));
                    disco.add(new Print("Note A"));
                    break;

                case 'B':
                    disco.add(new Note(NOTA_B3 + oitava, intensidade));
                    disco.add(new Print("Note B"));
                    break;

                // PAUSE
                case 'p':
                    disco.add(new Pause());
                    disco.add(new Print("Pause"));
                    break;

                case '-':
                    disco.add(new BmpChanger(0.5));
                    disco.add(new Print("BMP diminuido 2x"));
                    break;

                case '+':
                    disco.add(new BmpChanger(2.0));
                    disco.add(new Print("BMP aumentado 2x"));
                    break;

                case 'V':
                    disco.add(new VolumeChanger(2));
                    disco.add(new Print("Volume aumentado 2x"));
                    break;

                case 'I':
                    setIntensidade(1.2);
                    break;

                case 'i':
                    setIntensidade(0.8);
                    break;

                case 'v':
                    disco.add(new VolumeChanger(0.5));
                    disco.add(new Print("Volume diminuido 2x"));
                    break;

                case '>':
                    setOitavaUp();
                    disco.add(new Print("Oitava alterada para " + getOitava()));
                    break;

                case '<':
                    setOitavaDown();
                    disco.add(new Print("Oitava alterada para " + getOitava()));
                    break;

                case '?':
                    setOitavaUpOrReset();
                    disco.add(new Print("Oitava alterada para " + getOitava()));
                    break;

                case '.':
                    setOitavaUpOrReset();
                    disco.add(new Print("Oitava alterada para " + getOitava()));
                    break;

                case ' ':
                    disco.add(new VolumeChanger(2));
                    disco.add(new Print("Volume aumentado 2x"));
                    break;

                case '!':
                    disco.add(new InstrumentChanger(7));
                    disco.add(new Print("Instrumento alterado para " + 7));
                    break;

                case '\n':
                    disco.add(new InstrumentChanger(15));
                    disco.add(new Print("Instrumento alterado para " + 15));
                    break;

                case ';':
                    disco.add(new InstrumentChanger(76));
                    disco.add(new Print("Instrumento alterado para " + 76));
                    break;

                case ',':
                    disco.add(new InstrumentChanger(20));
                    disco.add(new Print("Instrumento alterado para " + 20));
                    break;

                // Usando indexof e regex
                default:

                    // Repetir se anterior for nota
                    if(isCharInsideString(cifra, "abcdefg")) {
                        if(disco.lastDadoIsNote()) {
                            disco.reinsertLastOne();
                            disco.add(new Print("Repetir ultima note"));
                        } else {
                            disco.add(new Pause());
                            disco.add(new Print("Pause"));
                        }
                    }

                    else if(isCharInsideString(cifra, "OoIiUu")) {
                        disco.add(new VolumeChanger(1.1));
                        disco.add(new Print("Volume aumentado 10%"));
                    }

                    else if(isCharInsideString(cifra, "BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz")) {

                        if(disco.lastDadoIsNote()) {
                            disco.reinsertLastOne();
                            disco.add(new Print("Repetir ultima note"));
                        } else {
                            disco.add(new Pause());
                            disco.add(new Print("Pause"));
                        }

                    }

                    else if(isCharInsideString(cifra, "0123456789")) {
                        int digito = new Integer(cifra-48);
                        int antigo_instrumento = disco.getLastInstrument();
                        int novo_instrumento = antigo_instrumento + digito;

                        disco.add(new InstrumentChanger(novo_instrumento));
                        disco.add(new Print("Instrumento alterado para " + novo_instrumento + "(antigo: "+antigo_instrumento+")"));
                    }

                    else {

                        if(disco.lastDadoIsNote()) {
                            disco.reinsertLastOne();
                            disco.add(new Print("Repetir ultima note"));
                        } else {
                            disco.add(new Pause());
                            disco.add(new Print("Pause"));
                        }

                    }

                    break;

                    //throw new Exception("Nao foi possivel reconhecer a cifra");
                    // Sem break, pois nunca chegará nesta linha
            }

        }

        return disco;
    }

}
