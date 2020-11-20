import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Bezeroa {
    public static void main(String[] args) {
        File lib = new File("liburuak");
        LiburuSorta liburuSorta = new LiburuSorta();

        // "liburuak" karpetan dauden fitxategi guztiak irakurri
        for (File fitx : Objects.requireNonNull(lib.listFiles())) {
            try {
                liburuSorta.gehituLiburua(new Liburua(fitx.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        liburuSorta.liburuSortaHitzKopurua();
        System.out.println("\nLiburu kopurua: " + liburuSorta.liburuKopuruaLortu());
        System.out.println("\nHITZ ESANGURATSUENEN PROBA");
        liburuSorta.esanguratsuenakInprimatu(10);
        System.out.println();
        liburuSorta.hurrengoUsuena("HALA");
        System.out.println();
        liburuSorta.hurrengoUsuena("GAUR");
        System.out.println();
        liburuSorta.hurrengoUsuena("ARGI");
        System.out.println();
        liburuSorta.hurrengoUsuena("IBILI");
        System.out.println();
        liburuSorta.hurrengoUsuena("ILARGIAN");
    }
}
