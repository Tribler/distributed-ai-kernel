import org.jfree.data.xy.XYSeries;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import utils.Key;
import utils.LinearRegression;

import java.util.*;


public class Entity2 {


    private ArrayList<ArrayList<String>> songHistory;
    private ArrayList<String> allSongs;


    /**
     * List of machines.
     */
    ArrayList<Machine2> list = new ArrayList<>();

    /**
     * The number of interations (cycles).
     */
    public static int N = 1000;

    /**
     * The confidence that we want
     */
    public static double theta = 0.99;

    /**
     * The ABSOLUTE MINIMUM confidence that we are able to tolerate
     */
    public  static double theta_min = 0.95;


    /**
     * used for graphing
     */
    XYSeries series = new XYSeries("Convergence over time");






    public Entity2(ArrayList<ArrayList<String>> songHistory, ArrayList<String> totalSongs) {
        this.songHistory = songHistory;
        this.allSongs = totalSongs;
        pupulate(songHistory);
    }


    public HashMap<Key, Float> run() {

        perform();

        HashMap<Key, Float> model3 = averageModel();


        return model3;
    }







    private double LSQRS(double[]x, double[] y, double slope, double intercept) {

        double RESULT = 0;

        for(int i=0; i<x.length;i++) {
            RESULT += (y[i] - (x[i]*slope + intercept))*(y[i] - (x[i]*slope + intercept));
        }

        return RESULT;
    }




    private void perform() {

        int it = N;

        while (it > 0 ) {
            int rand1 = (int)(Math.random() * (list.size()));
            for (int i =0; i<list.size(); i++) {
                int rand2 = (int) (Math.random() * (list.size()));

                list.get(rand2).updateUM(list.get(i));
            }
            //utils.Pair<Double> average = average();

            if (it % 100 == 0) {
//                System.out.println(LSQRS(x,y,new utils.LinearRegression(x,y).slope(),new utils.LinearRegression(x,y).intercept()) / LSQRS(x,y,average.X(),average.Y()));
                //series.add(N-it,LSQRS(x,y,new LinearRegression(x,y).slope(),new LinearRegression(x,y).intercept()) / LSQRS(x,y,average.X(),average.Y()));


            }

            System.out.println(it);
            it--;
        }
    }



    private HashMap<Key, Float> averageModel() {

        int nrOfSongs = songHistory.size();
        Machine2 finalMachine = list.get(0);

        for(int i = 1 ; i < list.size() - 1; i++){

            Machine2 m = list.get(i);

            finalMachine.mergeModels(m ,1);

            //finalMap = m.mergeModels(finalMap, m.getModel3());

        }

        finalMachine.mergeModels(list.get(list.size()-1), list.size());

        return finalMachine.getModel3();
    }



    private void pupulate(ArrayList<ArrayList<String>> songHistory){
        for(int i=0; i<songHistory.size();i++)
            list.add(new Machine2(songHistory.get(i).size(),0.5f,songHistory.get(i), allSongs));
    }
    ////?????


    private void print(double[] x, double[] y){
        for(int i=0; i<list.size();i++)
            System.out.println(list.get(i));

        System.out.println("THE REAL VALUE IS:");
        System.out.println("SLOPE: " + new LinearRegression(x,y).slope());
        System.out.println("INTERCEPT: " + new LinearRegression(x,y).intercept());
    }
}
