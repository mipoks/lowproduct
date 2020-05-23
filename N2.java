import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.concurrent.Phaser;

public class N2 {
    private static Vector<Pair<String, Long>> products;

    public final static int NUMBER = 30;

    public static void main(String[] args) {
        products = new Vector<>();
        boolean was = false;
        try (BufferedReader bf = new BufferedReader(new FileReader("transactions.csv"))) {
            String str;
            while ((str = bf.readLine()) != null){
                //System.out.println(str);
                String[] line = str.split(";");
                if (was == true) {
                    products.add(new Pair<>(line[0], Long.parseLong(line[1])));
                }
                was = true;
            }
            Collections.sort(products, new Comparator<Pair<String, Long>>() {
                @Override
                public int compare(Pair<String, Long> o1, Pair<String, Long> o2) {
                    if (o1.getValue() > o2.getValue())
                        return -1;
                    else {
                        if (o1.getValue() == o2.getValue())
                            return 0;
                        else
                            return 1;
                    }
                }
            });
            bf.close();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("out.csv"))) {
                for (int i = 0; i < NUMBER; i++) {
                    bw.write(products.get(i).getKey() + ";");
                    bw.write(products.get(i).getValue() + "\n");
                }
                bw.flush();
                bf.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
