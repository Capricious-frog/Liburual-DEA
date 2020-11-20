import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Liburua {
    private final HashMap<String, Hitza> liburua;
    int hitzKopurua;

    /**
     * Liburua klasearen eraikitzailea: liburu hutsa sortzen du.
     */
    public Liburua() {
        liburua = new HashMap<>();
        hitzKopurua = 0;
    }


    /**
     * Liburua klasearen eraikitzailea.
     * Testu-fitxategiko hitzak irakurri eta hitz horiekin Liburua egitura sortzen du.
     *
     * @param fitxIzena Kanpoko testu-fitxategiaren izena.
     * @throws IOException sarrera-irteerako salbuespen bat gertatzen bada.
     */
    public Liburua(String fitxIzena) throws java.io.IOException {
        String hitza;
        BufferedReader irakurlea;
        String lerroa;
        String[] hitzak;
        liburua = new HashMap<>();
        hitzKopurua = 0;

        irakurlea = new BufferedReader(new FileReader(new File(fitxIzena)));
        lerroa = irakurlea.readLine();

        while (lerroa != null) {
            hitzak = lerroa.trim().split("[\\s,;:.\"]+");
            // lerroko hitzen tratamendua
            for (int i = 0; i < hitzak.length; i++) {
                hitza = hitzak[i].toLowerCase();
                if (!hitza.equals("")) {
                    hitzKopurua++;

                    // Hitza hash mapean dagoen begiratu
                    if (liburua.containsKey(hitza)) {
                        liburua.get(hitza).gehituHitzaAgerpenKop();
                        try {
                            liburua.get(hitza).hurrengoHitzaGehitu(hitzak[i + 1].toLowerCase()); // Hitzaren hurrengoa gehitu
                        } catch (ArrayIndexOutOfBoundsException e) {
                            // Ez da beharrezkoa ezer gehitzea ez dagoelako hurrengo hitzik.
                        }
                    } else {
                        try {
                            liburua.put(hitza, new Hitza(hitzak[i].toLowerCase(), hitzak[i + 1].toLowerCase())); // Hitz berria eta hurrengoa sartu
                        } catch (ArrayIndexOutOfBoundsException e) {
                            liburua.put(hitza, new Hitza(hitza)); // Hitza lerroaren azkena da.
                        }
                    }
                }
            }

            lerroa = irakurlea.readLine();
        }

        // Hitzen tf-a kalkulatuko dugu
        for (Map.Entry<String, Hitza> entry : liburua.entrySet()) {
            entry.getValue().setTf(tf(entry.getKey()));
        }

        irakurlea.close();
    }

    /**
     * Hitz bat liburuan badagoen ala ez itzultzen du.
     *
     * @param hitza String motako hitza, liburuan bilatu nahi dena
     * @return true, hitza liburuan badago; false, bestela
     */
    public boolean baDago(String hitza) {
        return liburua.containsKey(hitza);
    }

    /**
     * Itzultzen du zein den h hitzaren ondoren (hurrengo posizioan)
     * liburuan maizen agertzen den hitza.
     *
     * @param h String motako hitza
     * @return String motako hitza, liburuan h-ren ondoren gehien agertzen dena.
     * h ez badago liburuan, null itzultzen du.
     */
    public String ondorengoHitzUsuena(String h) {
        return liburua.containsKey(h) ? liburua.get(h).ondorengoHitzUsuena() : null;
    }

    /**
     * Liburuan terminoa hitzak duen TF balioa itzultzen du.
     *
     * @param terminoa String motakoa.
     * @return TF balioa, double motakoa.
     */
    public double tf(String terminoa) {
        return (double) liburua.get(terminoa).getAgerpenKop() / this.hitzKopurua;
    }

    /**
     * Hitza liburuan zenbat aldiz agertzen den bueltatzen du.
     *
     * @param terminoa Hitza String
     * @return Hitza liburuan agerpen kopurua
     */
    public int hitzAgerpenKop(String terminoa) {
        return baDago(terminoa) ? liburua.get(terminoa).getAgerpenKop() : 0;
    }

    /**
     * Liburuko hitz guztiak
     *
     * @return HashMap bat hitz guztiekin
     */
    public HashMap<String, Hitza> getHitzak() {
        return liburua;
    }

    public int getHitzKopurua() {
        return this.hitzKopurua;
    }
}
