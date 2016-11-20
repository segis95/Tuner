package pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Scrollbar;

public class ITable implements ActionListener
{	
	float freq = 96000f;
	double count = 2.0;
	int time = 0;
	int mode = 1;
	One ex;
	Analyzer analyzer;
	MinimalStaticChart chart;
	JFrame interfaceFrame;
    Timer timer;
    JLabel tabLabel;
    JLabel noteLabel;
    JLabel freqLabel;
    JLabel sensLabel;
    JComboBox comboBoxFreq;////////////////////
    JButton btnStart;/////////////////
    JButton btnTuner;
    JButton btnRecog;
    JButton btnSpectr;
    JButton btnExit;
    JLabel backGroundLabel;
    JLabel indicateLabel;
    JLabel sensitLabel;
    JScrollBar sensScrollBar;
   
	Location Previous;
	Location Current;
	ImageIcon[] im;
	
	Note note = new Note("G", 2);
	LocArray la  = new LocArray(note);
	String[] stri;
	double[] fr;
	String[] name;
	final int tabLength = 20;
	boolean spectrogram = true;
	
	public ITable()
	{
		try
    	{
	    	JFrame.setDefaultLookAndFeelDecorated(true);
	    	
	    	interfaceFrame = new JFrame("Main GUI");
	    	interfaceFrame.setResizable(false);
	    	interfaceFrame.setBounds(100, 10, 1100, 730);
	    	interfaceFrame.getContentPane().setLayout(null);
    	
	    	//window for tabs
	    	tabLabel = new JLabel("");
	    	tabLabel.setBounds(226, 478, 10 * tabLength, 186);
	    	tabLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    	tabLabel.setIcon(null);
	    	tabLabel.setVerticalAlignment(SwingConstants.TOP);
	    	tabLabel.setForeground(Color.ORANGE);
	    	tabLabel.setFont(new Font("Arial", Font.PLAIN, 18));
	    	tabLabel.setVisible(false);
	    	interfaceFrame.getContentPane().add(tabLabel);
	    	
	    	//window for note
	    	noteLabel = new JLabel("");
	    	noteLabel.setBounds(338, 419, 100, 55);
	    	noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    	noteLabel.setIcon(null);
	    	noteLabel.setVerticalAlignment(SwingConstants.CENTER);
	    	noteLabel.setForeground(Color.ORANGE);
	    	noteLabel.setFont(new Font("Arial", Font.PLAIN, 30));
	    	interfaceFrame.getContentPane().add(noteLabel);
	    	
	    	freqLabel = new JLabel("frequence");
	    	freqLabel.setBounds(10, 10, 90, 30);
	    	freqLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    	freqLabel.setIcon(null);
	    	freqLabel.setOpaque(true);
	    	freqLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	    	freqLabel.setBackground(Color.lightGray);
	    	interfaceFrame.getContentPane().add(freqLabel);
	    	
	    	sensitLabel = new JLabel("sensitivity");
	    	sensitLabel.setBounds(110, 10, 100, 30);
	    	sensitLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    	sensitLabel.setIcon(null);
	    	sensitLabel.setOpaque(true);
	    	sensitLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	    	sensitLabel.setBackground(Color.lightGray);
	    	//sensitLabel.setVisible(false);
	    	interfaceFrame.getContentPane().add(sensitLabel);
	    	
	    	//tuner regime
	    	btnTuner = new JButton("");
	    	btnTuner.setBounds(702, 216, 51, 44);
	    	btnTuner.setIcon(new ImageIcon("images\\23.jpg"));
	    	interfaceFrame.getContentPane().add(btnTuner);
	    	btnTuner.addActionListener(this);
    	
	    	//recognition regime
	    	btnRecog = new JButton("");
	    	btnRecog.setBounds(848, 337, 81, 36);
	    	btnRecog.setIcon(new ImageIcon("images\\31.jpg"));
	    	interfaceFrame.getContentPane().add(btnRecog);
	    	btnRecog.addActionListener(this);
	    	
	    	//open/close spectrum
	    	btnSpectr = new JButton("Spectrum");
	    	btnSpectr.setFont(new Font("Arial", Font.PLAIN, 14));
	    	btnSpectr.setBounds(925, 27, 107, 40);
	    	interfaceFrame.getContentPane().add(btnSpectr);
	    	btnSpectr.addActionListener(this);
	    	
	    	//input button
	    	btnExit = new JButton("");
	    	btnExit.setBounds(957, 635, 75, 44);
	    	btnExit.setIcon(new ImageIcon("images\\41.jpg"));
	    	interfaceFrame.getContentPane().add(btnExit);
	    	btnExit.addActionListener(this);

	    	String[] items = {"96000", "44100", "16000", "8000"};
	    	//frequency of discretisation
	    	comboBoxFreq = new JComboBox(items);
	    	comboBoxFreq.setBounds(10, 40, 90, 40);
	    	interfaceFrame.getContentPane().add(comboBoxFreq);
	    	comboBoxFreq.addActionListener(this);
	    	
	    	//button of start/finish
	    	btnStart = new JButton("Start");
	    	btnStart.setBounds(174, 419, 115, 52);
	    	interfaceFrame.getContentPane().add(btnStart);
	    	btnStart.addActionListener(this);
    	
	    	im = new ImageIcon[11];
	    	for (int i = 1; i <= 5; i++)
	    		im[5 - i] = new ImageIcon("images\\" + Integer.toString(i) + "-.jpg");
	    	im[5] = new ImageIcon("images\\0.jpg");
	    	for (int i = 1; i <= 5; i++)
	    		im[5 + i] = new ImageIcon("images\\" + Integer.toString(i) + "+.jpg");
    	
	    	//indicator
	    	indicateLabel = new JLabel("");
	    	indicateLabel.setBounds(156, 485, 365, 165);
	    	indicateLabel.setIcon(im[5]);
	    	interfaceFrame.getContentPane().add(indicateLabel);
	
	    	//choice of sensitivity
	    	sensScrollBar = new JScrollBar(Scrollbar.HORIZONTAL, 5, 1, 1, 10);
	    	sensScrollBar.setBounds(110, 49, 100, 22);
	    	//sensScrollBar.setVisible(false);
	    	interfaceFrame.getContentPane().add(sensScrollBar);
	    	
	    	//background
	    	backGroundLabel = new JLabel("");
	    	backGroundLabel.setBounds(0, 0, 1097, 702);
	    	backGroundLabel.setIcon(new ImageIcon("images\\musical-notes.jpg"));
	    	interfaceFrame.getContentPane().add(backGroundLabel);
    	
			Previous = new Location(0,0);
			stri = new String[6];
			stri[0] = "";
			for (int i = 0; i < tabLength; i++)
				stri[0] = stri[0] + "-";
			for (int i = 1; i < 6; i++){
				stri[i] = stri[0];
			}
			
			//names of notes
			name = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
			//frequencies of notes
	    	fr = new double[]{65.406391, 69.295658, 73.416192, 77.781746, 82.406889, 87.307058, 92.498606, 97.998859, 103.826174, 110.000000, 116.540940, 123.470825};
	    	Previous = new Location(0,0);
	    	
	    	//timer
			timer = new Timer((int)(1000 / count), this);
			
			ex = new One(freq, count);
			//graph
			chart = new MinimalStaticChart();
			//analizer
			analyzer = new Analyzer(fr);
			
			interfaceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			interfaceFrame.setVisible(true);
    	}
    	catch (Exception ex)
    	{
    		System.out.println("Error");
    		System.exit(0);
    	}
	}
	
	
	
	public void actionPerformed(ActionEvent a) 
	{
		if(a.getSource() == btnStart)
		{
			if (timer.isRunning()) //pause
			{
				ex.stop_line();
				timer.stop();
				btnStart.setText("Start");
			}
			else //start
			{
				ex.start_line();
				timer.start();
				btnStart.setText("Pause");
			}
		}
		
		if(a.getSource() == btnExit)
		{
			ex.del();
			if (spectrogram)
				chart.del();
			System.exit(0);
		}
		
		if(a.getSource() == btnTuner)
		{
			mode = 1;
			tabLabel.setVisible(false);
			noteLabel.setVisible(true);
			indicateLabel.setVisible(true);
			//sensScrollBar.setVisible(false);
			//sensitLabel.setVisible(false);
		}
		
		if(a.getSource() == btnRecog)
		{
			mode = 2;
			tabLabel.setVisible(true);
			noteLabel.setVisible(false);
			indicateLabel.setVisible(false);
			//sensScrollBar.setVisible(true);
			//sensitLabel.setVisible(true);
		}
		
		if(a.getSource() == btnSpectr)
		{
			spectrogram = !spectrogram;
			if (!spectrogram)
			{
				chart.del();
			}
			else
			{
				chart = new MinimalStaticChart();
			}
				
		}
		
		if(a.getSource() == comboBoxFreq)
		{
			String str = (String) comboBoxFreq.getSelectedItem();
			if (str == "96000") freq = 96000.0f;
			if (str == "44100") freq = 44100.0f;
			if (str == "16000") freq = 16000.0f;
			if (str == "8000") freq = 8000.0f;
			boolean run = timer.isRunning();
			if (run)
			{
				ex.stop_line();
				timer.stop();
			}
			ex.del();
			ex = new One(freq, count);
			if (run)
			{
				ex.start_line();
				timer.start();
			}
		}

		if(a.getSource() == timer)
		{
			ex.exec();
			if (spectrogram)
				chart.draw(ex.data3, count, 2000);
			double sens = (double) sensScrollBar.getValue();
			if (mode == 1)
  			{
	  			NoteStruct1 res = analyzer.analyze(ex.data3, count, 4000, sens);
	  			if (res.note == -1)
	  			{
	  				noteLabel.setText("");
	  				indicateLabel.setIcon(im[5]);
	  				indicateLabel.repaint();
	  			}
	  			else
	  			{
		  			noteLabel.setText(name[res.note] + Integer.toString(res.oct));
		  			indicateLabel.setIcon(im[5 + res.n]);
		  			indicateLabel.repaint();
	  			}
  			}
			if (mode == 2)
			{
				note = new Note("G", 2);
			    la  = new LocArray(note);
	  			NoteStruct2[] sym = analyzer.recognize(ex.data3, count, 3000, sens, true) ;/////буква анализатора
	  			
	  			if (sym[0].note != -1)
	  			{
		  			note = new Note(name[sym[0].note], sym[0].oct);//тут нужно будет менять октаву!!!!
		  			if (time == 0){
		  				Current = la.Closest(Previous, note, true);
		  				for (int i = 0; i <= 5; i++){
		  					if (i + 1 == Current.L[0]){
		  						stri[i] = stri[i] + "--" + Integer.toString(Current.L[1]);
		  					}
		  					else{
		  						stri[i] = stri[i] + "---" ;
		  					}
		  				}
		  			
		  			}
		  			else
		  			{
		  				Current = la.Closest(Previous, note, false);
		  				for (int i = 0; i <= 5; i++){
		  					if (i + 1 == Current.L[0]){
		  						stri[i] = stri[i] + "--" + Integer.toString(Current.L[1]);
		  					}
		  					else{
		  						stri[i] = stri[i] + "---" ;
		  					}
		  				}
		  				 
		  			}
		  			time++;
		  			Previous = Current;
	  			}
	  			else
	  			{
	  				for (int i = 0; i <= 5; i++)
	  					stri[i] = stri[i] + "---" ;
	  			}
	  			String s = "";
	  			for (int i = 0; i <= 4; i++)
	  			{
	  				s = s + stri[i].substring(stri[i].length() - tabLength, stri[i].length()) + "\n";
	  			}
	  			s = s + stri[5].substring(stri[5].length() - tabLength, stri[5].length());
	  			tabLabel.setText("<html><pre>" + s + "</pre></html>");
	  			if (stri[0].length() > 50 * tabLength)
	  				for (int i = 0; i < 6; i++)
	  					stri[i] = stri[i].substring(tabLength);
			}
		}
	}
	
	public static void main(String[] args)
	{
		new ITable();
		
	}
}
