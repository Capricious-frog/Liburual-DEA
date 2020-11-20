import java.util.*;

public class Hitza {
    private final String hitza;
    HashMap<String, Integer> hurrengoa;
    private int agerpenKop;
    private double tf;

    /**
     * Eraikitzailea
     *
     * @param hitza String motako hitza
     */
    public Hitza(String hitza) {
        this.hitza = hitza;
        this.agerpenKop = 1;
        this.hurrengoa = new HashMap<>();
    }

    /**
     * Eraikitzailea
     *
     * @param hitza     String motako hitza
     * @param hurrengoa Hitzaren hurrengoa
     */
    public Hitza(String hitza, String hurrengoa) {
        this.hitza = hitza;
        this.agerpenKop = 1;
        this.hurrengoa = new HashMap<>();

        hurrengoHitzaGehitu(hurrengoa);
    }

    /**
     * Hitza bueltatzen du
     *
     * @return Hitza String motakoa
     */
    public String getHitza() {
        return hitza;
    }

    /**
     * Hitz usuena bueltatzen du, existitzen ez baldin bada "null" bueltatuko du.
     *
     * @return Listako balio handienaren key-a itzultzen du.
     */
    public String ondorengoHitzUsuena() {
        try {
            return Collections.max(hurrengoa.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        } catch (NoSuchElementException e) {
            return "null";
        }
    }

    /**
     * Hurrengo hitza zenbat alditan agertu den gordetzen du.
     *
     * @param hitza hurrengo hitza
     */
    public void hurrengoHitzaGehitu(String hitza) {
        hurrengoa.merge(hitza, 1, Integer::sum);
    }

    /**
     * Hitzaren agerpen kopurua berritzen du.
     *
     * @param agerpenKop hitzaren agerpen kopurua
     */
    private void setAgerpenKop(int agerpenKop) {
        this.agerpenKop = agerpenKop;
    }

    /**
     * Hitzaren agerpen kopurua bueltatzen du.
     *
     * @return Hitzaren agerpen kopurua
     */
    public int getAgerpenKop() {
        return agerpenKop;
    }

    /**
     * Hitzaren agerpen kopuruari + 1 egiten dio.
     */
    public void gehituHitzaAgerpenKop() {
        this.agerpenKop++;
    }

    /**
     * Tf-aren setterra
     *
     * @param tf Hitzaren tf balioa
     */
    public void setTf(double tf) {
        this.tf = tf;
    }

    /**
     * Tf-aren getterra
     *
     * @return Hitzaren tf balioa
     */
    public double getTf() {
        return tf;
    }

    /**
     * Hitzaren datuak inprimazen ditu
     *
     * @return Hitzaren datuak.
     */
    @Override
    public String toString() {
        return "Hitza: \"" + getHitza() + "\", agerpen kopurua: " + getAgerpenKop();
    }

}
