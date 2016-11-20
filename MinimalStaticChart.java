package pack;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
 
public class MinimalStaticChart 
{
	JFrame frame;
	Chart2D chart;
	ITrace2D trace;
	
	MinimalStaticChart() 
	{
		super();
		try
		{
		    //создаем график
		    chart = new Chart2D();
		    chart.getAxisY().setPaintScale(false);
		    // Create an ITrace: 
		    trace = new Trace2DSimple(); 
		    // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
		    chart.addTrace(trace);    
		    // Add all points, as it is static
		    //Создаем фрейм
		    frame = new JFrame("Specrtogram");
		    // add the chart to the frame: 
		    frame.getContentPane().add(chart);
		    frame.setSize(700, 300);
		    // Enable the termination button [cross on the upper right edge]: 
		    frame.addWindowListener(
		        new WindowAdapter()
		        {
		          public void windowClosing(WindowEvent e)
		          {
		              System.exit(0);
		          }
		        }
		      );
		    frame.setVisible(true);
		}
		catch (Exception ex)
		{
			System.exit(0);
		}
	}
	  
	public void draw(double[] array, double count, int lenghtX)
	{
		try
		{
			int m = (int) (40 / count);
			double av = 0;
			for (int i = (int) (lenghtX / count); i >= m; i--)
			{
				av += array[i];
			}
			av /= lenghtX;
			av *= 4 * count;
			double avg = 0;
			int k = 0;
			for (int i = (int) (lenghtX / count); i >= m; i--)
			{
				if (array[i] <= av)
				{
					avg += array[i] / 1000;
					k++;
				}
			}
			avg /= k;
			
			trace.removeAllPoints();
			//adding points
			for (int i = (int) (lenghtX / count); i >= m; i--)
			{
				trace.addPoint(count * i, array[i] / 1000);
			}
			trace.addPoint(m * count, 4 * avg);
			trace.addPoint(lenghtX, 4 * avg);
		}
		catch (Exception ex)
		{
			System.exit(0);
		}
	}
	  
	public void del()
	{
		frame.dispose();
		frame = null;
	}
}
