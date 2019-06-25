package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import controler.CalculateWeightChanges;


public class Chart extends JFrame {
    CalculateWeightChanges calculateWeightChanges;

   
    public Chart(final String title, int userID, int courseID) {
        super(title);
        
        calculateWeightChanges = new CalculateWeightChanges(userID, courseID);        
        
        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 540));
        setContentPane(chartPanel);
         
       
    }

    
    private CategoryDataset createDataset() {
        
        // row keys...
        final String seriesWeight = "Weight";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();             
        
        for(int i=0; i<calculateWeightChanges.averageWeightList.size();i++){
            
           
            dataset.addValue(calculateWeightChanges.averageWeightList.get(i), seriesWeight, "Day-"+calculateWeightChanges.dateList.get(i).substring(0, 2));        
                        
        }

        return dataset;
                
    }
    
    
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
            "Weight Changes in Line Chart",         // chart title
            "Type",                                 // domain axis label
            "Value",                                // range axis label
            dataset,                                // data
            PlotOrientation.VERTICAL,               // orientation
            true,                                   // include legend
            true,                                   // tooltips
            false                                   // urls
        );



        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);


        
        // customise the renderer...
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);

        renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            1, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            2, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
    }
    
    
    public void customize( JFreeChart jFreeChart) {
    XYPlot xyPlot = ( XYPlot )jFreeChart.getPlot();

    XYItemRenderer defaultRenderer = new XYLineAndShapeRenderer();
    XYSplineRenderer splineRenderer = new XYSplineRenderer();

    // Make the spline line thick.
    //
    splineRenderer.setSeriesShapesVisible( 0, false );
    splineRenderer.setSeriesStroke(
        0, new BasicStroke(
            4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
            1.0f, null, 0.0f
        )
    );

    splineRenderer.setSeriesPaint( 0, new Color( 255, 140, 0 ) );
    
    xyPlot.setRenderer( 0, splineRenderer );
    xyPlot.setRenderer( 1, defaultRenderer );

    System.out.println( "Renderer 0: " + xyPlot.getRenderer(0));
    System.out.println( "Renderer 1: " + xyPlot.getRenderer(1));
  }
    

}
