import org.jfree.data.xy.XYSeries;
import utils.LinearRegression;

import java.util.ArrayList;


public class Entity {


    double[] x;

    double[] y;


    /**
     * List of machines.
     */
    ArrayList<Machine> list = new ArrayList<>();

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






    public Entity(double[] x, double[] y) {
        this.x = x;
        this.y = y;
        pupulate(x,y);
    }


    public void run() {

        perform();

        Pair<Double> average = average();

        System.out.println("OUR_AVERAGE SLOPE: " + average.X() + "  OUR AVERAGE INTERCEPT: " + average.Y());

        System.out.println("[BEST_FIT LINE]: SLOPE:" + new LinearRegression(x,y).slope() + " intercept: " +  new LinearRegression(x,y).intercept());

        System.out.println("BEST FIT LINE error:" + LSQRS(x,y,new LinearRegression(x,y).slope(),new LinearRegression(x,y).intercept()));

        System.out.println("OUR LINE error:" + LSQRS(x,y,average.X(),average.Y()));

        System.out.println("RELATIVE ERROR: " + LSQRS(x,y,new LinearRegression(x,y).slope(),new LinearRegression(x,y).intercept()) / LSQRS(x,y,average.X(),average.Y()));

        double relative_error = LSQRS(x,y,new LinearRegression(x,y).slope(),new LinearRegression(x,y).intercept()) / LSQRS(x,y,average.X(),average.Y());
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
            Pair<Double> average = average();

            if (it % 100 == 0) {
//                System.out.println(LSQRS(x,y,new utils.LinearRegression(x,y).slope(),new utils.LinearRegression(x,y).intercept()) / LSQRS(x,y,average.X(),average.Y()));
                series.add(N-it,LSQRS(x,y,new LinearRegression(x,y).slope(),new LinearRegression(x,y).intercept()) / LSQRS(x,y,average.X(),average.Y()));


            }

            it--;
        }
    }



    private Pair average() {

        double sumx=0,sumy=0;

        for(int i=0; i<list.size();i++) {
            sumx += list.get(i).getIntercept();
            sumy += list.get(i).getBias();
        }
        return new Pair(sumx/list.size(), sumy/list.size());
    }



    private void pupulate(double[] x, double []y){
        for(int i=0; i<x.length;i++)
            list.add(new Machine(x[i], y[i]));
    }


    private void print(double[] x, double[] y){
        for(int i=0; i<list.size();i++)
            System.out.println(list.get(i));

        System.out.println("THE REAL VALUE IS:");
        System.out.println("SLOPE: " + new LinearRegression(x,y).slope());
        System.out.println("INTERCEPT: " + new LinearRegression(x,y).intercept());
    }
}
