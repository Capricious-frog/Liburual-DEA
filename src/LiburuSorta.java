import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LiburuSorta {
    ArrayList<Liburua> liburuSorta;

    /**
     * LiburuSorta klasearen eraikitzailea: liburu-sorta hutsa sortzen du.
     */
    public LiburuSorta() {
        liburuSorta = new ArrayList<>();
    }

    /**
     * Liburu bat gehitzen du sortara.
     *
     * @param lib liburua.
     */
    public void gehituLiburua(Liburua lib) {
        liburuSorta.add(lib);
    }

    /**
     * Liburu-sortaren liburu-kopurua itzultzen du.
     *
     * @return liburu-sortaren liburu-kopurua.
     */
    public int liburuKopuruaLortu() {
        return liburuSorta.size();
    }

    /**
     * Liburu-sortaren liburu bakoitzaren hitz esanguratsuenak inprimatzen ditu
     * esangura handienetik txikienera. * Hitz esanguratsuenak dira TF-IDF baliorik altuena dutenak.
     *
     * @param esanKop hitz esanguratsuenen kopurua.
     *                Ohiko balio gisa 10 erabiliko da.
     *                Hortaz, liburu bakoitzetik 10 hitz esanguratsuenak
     *                inprimatuko ditu,modu ordenatuan.
     */
    public void esanguratsuenakInprimatu(int esanKop) {
        HashMap<String, Hitza> hitzak;
        HashMap<Hitza, Double> tfidf_hitza = new HashMap<>();

        // Komentatutako kode zatiak ez zuen ondo funzionatzen. Bi hitzek tf-idf berdina baldinbazuten, bietako bat bakarrik inprimatzen zen.
        /* HashMap<Double, Hitza> tfidf_hitza = new HashMap<>();

        for (int i = 0, liburuSortaSize = liburuSorta.size(); i < liburuSortaSize; i++) {
            Liburua lib = liburuSorta.get(i);
            hitzak = lib.getHitzak();
            tfidf_hitza.clear();

            // Hitz bakoitzaren tf-idf-a kalkulatuko dugu
            for (Map.Entry<String, Hitza> entry : hitzak.entrySet()) {
                tfidf_hitza.put(tfIdf(lib, entry.getKey()), entry.getValue());
            }

            // Hitzak tf-idf-aren arabera ordenatu
            Object[] keys = tfidf_hitza.keySet().toArray(); // Hash maparen gakoak array batera pasa
            Arrays.sort(keys, Collections.reverseOrder()); // tf-idf-ak ordenatu handitik txikira

            System.out.println("-------------------------------------------------------------");
            System.out.println(i + " Liburua:");
            for (int j = 0; j < esanKop; j++) {
                System.out.println(j + ". " + tfidf_hitza.get((Double) keys[j]).toString());
            }

        }*/
        
        for (Liburua lib : liburuSorta) {
            hitzak = lib.getHitzak();
            tfidf_hitza.clear();
            Hitza tf_idf_max;

            // Hitz bakoitzaren Tf-Idf-a kalkulatuko dugu
            hitzak.forEach((key, value) -> tfidf_hitza.put(value, tfIdf(lib, key))); // Lambda funtzio bat erabiltzen dut

            System.out.println("----------------------------------------------------------------------------");

            // Tf-Idf handiena duen hitza artu eta inprimatu ondoren ezabatu egiten dut
            for (int j = 0; j < esanKop; j++) {
                tf_idf_max = Collections.max(tfidf_hitza.entrySet(), Map.Entry.comparingByValue()).getKey();
                System.out.println(tf_idf_max + ", Tf-Idf: " + tfidf_hitza.remove(tf_idf_max));
            }
        }

    }

    /**
     * Hitz bat emanda liburu bakoitzeko hurrengo usuena bueltatzen du
     *
     * @param hitza String motakoa
     */
    public void hurrengoUsuena(String hitza) {
        for (int i = 0, liburuSortaSize = liburuSorta.size(); i < liburuSortaSize; i++) {
            Liburua lib = liburuSorta.get(i);
            System.out.println(i + " liburuan " + hitza + " hitzaren ondorengo hitzik usuena: " + lib.ondorengoHitzUsuena(hitza.toLowerCase()));
        }
    }

    /**
     * Liburu bateko hitz kopurua bueltatzen du
     *
     * @param liburua Hitzak kontatu behar den liburua
     * @return Hitz kopurua
     */
    public int liburuHitzKopurua(Liburua liburua) {
        int kop = 0;

        for (Map.Entry<String, Hitza> entry : liburua.getHitzak().entrySet()) {
            Hitza value = entry.getValue();
            kop += value.getAgerpenKop();
        }

        return kop;
    }

    /**
     * Liburu sortan dauden liburuen hitz kopurua inprimatzen du
     */
    public void liburuSortaHitzKopurua() {
        // Liburu bakoitzean dauden hitz kopurua inprimatzen du
        Liburua lib;
        for (int i = 0, liburuSortaSize = liburuSorta.size(); i < liburuSortaSize; i++) {
            lib = liburuSorta.get(i);
            System.out.println(i + " liburuaren barne-egiturak duen hitz kopurua: " + liburuHitzKopurua(lib));
        }
    }

    /**
     * Liburu-sortan terminoa hitzak duen IDF balioa itzultzen du.
     *
     * @param terminoa ,String motakoa.
     * @return IDF balioa, double motakoa.
     */
    public double idf(String terminoa) {
        int termAgerpenKop = 0;
        for (Liburua lib : liburuSorta) {
            if (lib.baDago(terminoa)) {
                termAgerpenKop++;
            }
        }

        return Math.log(liburuKopuruaLortu() / (0.5 + termAgerpenKop));
    }

    /**
     * Liburu-sortako lib liburuan terminoa hitzak duen TFIDF balioa itzultzen du.
     *
     * @param lib      Liburua motakoa.
     * @param terminoa String motakoa.
     * @return TFIDF balioa, double motakoa.
     */
    public double tfIdf(@NotNull Liburua lib, String terminoa) {
        return lib.tf(terminoa) * idf(terminoa);
    }
}
