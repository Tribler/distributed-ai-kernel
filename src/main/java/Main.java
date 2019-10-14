import org.jfree.ui.RefineryUtilities;
import utils.XYSeriesDemo;

public class Main {
    public static void main(String []args) {

        double[] x = {1,3,2,6};
        double[] y = {1,2,3,5};

        Entity entity = new Entity(x,y);
        entity.run();


        final XYSeriesDemo demo = new XYSeriesDemo("Convergence over time", entity.series);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
