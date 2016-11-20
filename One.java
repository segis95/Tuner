package pack;

import javax.sound.sampled.*;
import java.io.*;
//import java.util.*;
//import java.math.*;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;


public class One {
	public boolean done;
	private AudioFormat format;
	TargetDataLine line;
	DataLine.Info info;
	ByteArrayOutputStream out;
	int numBytesRead;
	int bufSize;
	byte[] data;
	double[] data2;
	DoubleFFT_1D FFT;
	double[] data3;
	
	One(float freq, double count) 
	{
		//setting audio format
		format = new AudioFormat(freq, 16, 1, true, true);
		
		//gather information about the line
		info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info)) 
		{
			System.out.println("Audio format is not supported");
		}
		else
			try {
				//get a line
				line = (TargetDataLine) AudioSystem.getLine(info);
				
				//open the line
				line.open(format);
				
				out = new ByteArrayOutputStream();
				bufSize = line.getBufferSize();
				data = new byte[2 * (int) (bufSize / count)];
				data2 = new double[(int) (bufSize / count)];
				FFT = new DoubleFFT_1D((int) (bufSize / count));
				data3 = new double[(int) (bufSize / count / 2) + 1];
			}
			catch (LineUnavailableException ex) 
			{
				System.out.println("Error on audioline");
				System.exit(0);
			}
	}
	
	public void exec()
	{
		done = false;
		
		//getting data from data flux
		numBytesRead = line.read(data, 0, data.length);
		
		//transform to 16-bit
		for (int k = 0; k < numBytesRead; k += 2)
			data2[k / 2] = data[k + 1] + 256 * data[k];
		
		//transformation Fourier
		FFT.realForward(data2);
		
		//amplitudes
		for (int k = 0; k < numBytesRead / 2 - 1; k += 2)
			data3[k / 2] = Math.sqrt(data2[k] * data2[k] + data2[k + 1] * data2[k + 1]);
		done = true;
	}
	
	public void start_line()
	{
		//start audio catch
		line.start();
	}
	
	public void stop_line()
	{
		//stop audio catch
		line.stop();
	}
	
	public void del()
	{
		//stop of work
		line.stop();
		line.close();
	}

}
