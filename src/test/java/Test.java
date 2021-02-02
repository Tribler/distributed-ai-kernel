import org.junit.jupiter.api.AfterEach;
import utils.LinearRegression;
import utils.Pair;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Test {

    /**
     * List of machines.
     */
    ArrayList<Machine> list = new ArrayList<>();

    /**
     * The number of interations (cycles).
     */
    public static int N = 5000;

    /**
     * The confidence that we want
     */
    public static double theta = 0.99;

    /**
     * The ABSOLUTE MINIMUM confidence that we are able to tolerate
     */
    public  static double theta_min = 0.95;






    @AfterEach
    public void after(){
        list.clear();
        assertEquals(0, list.size());
    }



    @org.junit.jupiter.api.Test
    public void test() {

        double[] x = {1,3,2,6};
        double[] y = {1,2,3,5};

        perform(list);
       pupulate(x,y);

       double min_err = 100;


       perform(list);

       Pair<Double> average = average(list);


            System.out.println("OUR_AVERAGE SLOPE: " + average.X() + "  OUR AVERAGE INTERCEPT: " + average.Y());

            System.out.println("BEST FIT LINE error:" + LSQRS(x,y,new LinearRegression(x,y).slope(),new LinearRegression(x,y).intercept()));

            System.out.println("OUR LINE error:" + LSQRS(x,y,average.X(),average.Y()));

            System.out.println("RELATIVE ERROR: " + LSQRS(x,y,new LinearRegression(x,y).slope(),new LinearRegression(x,y).intercept()) / LSQRS(x,y,average.X(),average.Y()));



            double relative_error = LSQRS(x,y,new LinearRegression(x,y).slope(),new LinearRegression(x,y).intercept()) / LSQRS(x,y,average.X(),average.Y());
            assertTrue(relative_error > theta);

            if(relative_error < min_err)
                min_err = relative_error;

        System.out.println("SMALLEST ERROR: " + min_err);
        assertTrue(min_err > theta_min);
    }




    private double LSQRS(double[]x, double[] y, double slope, double intercept) {

        double RESULT = 0;

        for(int i=0; i<x.length;i++) {
            RESULT += (y[i] - (x[i]*slope + intercept))*(y[i] - (x[i]*slope + intercept));
        }

        return RESULT;
    }






   private void perform(ArrayList<Machine> list) {

       int it = N;

       while (it > 0 ){
           int rand1 = (int)(Math.random() * (list.size()));
           for(int i =0; i<list.size(); i++) {
               int rand2 = (int) (Math.random() * (list.size()));

               list.get(rand2).updateUM(list.get(i));
           }

           it--;
       }
   }





    private Pair average(ArrayList<Machine> list) {

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