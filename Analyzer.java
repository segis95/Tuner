package pack;

public class Analyzer 
{
	int num;
	int extraNum;
	double[] fr;
	double[] extraFr;
	double[] lastW;
	final int addit = 5;
	final int freqLimit = 1200;
	final int lowLimit = 4;
	
	Analyzer(double[] frArr)
	{
		try
		{
			//filling array of frequencies
			num = frArr.length;
			fr = frArr.clone();
			extraNum = num * addit;
			extraFr = new double[extraNum];
			lastW = new double[num];
			for (int i = 0; i < num - 1; i++)
			{
				for (int j = 0; j < addit; j++)
				{
					extraFr[i * addit + j] = (fr[i + 1] * j + fr[i] * (addit - j)) / addit;
				}
			}
			for (int j = 0; j < addit; j++)
			{
				extraFr[(num - 1) * addit + j] = (2 * fr[0] * j + fr[num - 1] * (addit - j)) / addit;
			}
			for (int i = 0; i < num; i++)
			{
				lastW[i] = 0;
			}
		}
		catch (Exception ex)
		{
			System.exit(0);
		}
	}

	public NoteStruct1 analyze(double[] array, double count, int maxFreq, double sensit) //режим тюнера
	{
		try
		{
			int m = (int) (40 / count);
			//looking for average
			double av = 0;
			for (int i = (int) (maxFreq / count); i >= m; i--)
			{
				av += array[i];
			}
			av /= (maxFreq / count - m);
			av *= lowLimit;
			
			double avg = 0;
			int s = 0;
			for (int i = (int) (maxFreq / count); i >= m; i--)
			{
				if (array[i] <= av)
				{
					avg += array[i];
					s++;
				}
			}
			avg /= s;
			avg *= lowLimit;
			
			//looking for wights of frequencies
			double[] w = new double[extraNum];
			for (int i = 0; i < extraNum; i++)
			{
				w[i] = 0;
				for (int j = 1; extraFr[i] * j <= maxFreq; j++)
				{
					if (array[(int) (extraFr[i] * j / count)] >= avg)
					{
						w[i] += array[(int) (extraFr[i] * j / count)];
						w[i] += 0.5 * array[(int) (extraFr[i] * j / count) - 1];
						w[i] += 0.5 * array[(int) (extraFr[i] * j / count) + 1];
					}
				}
			}
			
			//frequency with maximum wheight
			int maxNote = 0;
			for (int i = 0; i < extraNum; i++)
				if (w[i] > w[maxNote]) maxNote = i;
			
			double freq = extraFr[maxNote];
			for (int i = 2; extraFr[maxNote] * i < freqLimit; i++)
			{
				double wNew = 0;
				for (int j = 1; extraFr[maxNote] * i * j <= maxFreq; j++)
				{
					if (array[(int) (extraFr[maxNote] * i * j / count)] >= avg)
					{
						wNew += array[(int) (extraFr[maxNote] * i * j / count)];
						wNew += 0.5 * array[(int) (extraFr[maxNote] * i * j / count) - 1];
						wNew += 0.5 * array[(int) (extraFr[maxNote] * i * j / count) + 1];
					}
				}
				if (5 * wNew > 4 * w[maxNote])
				{
					freq = extraFr[maxNote] * i;
				}
			}
			
			NoteStruct1 res = new NoteStruct1();
			
			double choseW = 0;
			for (int j = 1; freq * j <= maxFreq; j++)
			{
				if (array[(int) (freq * j / count)] >= avg)
				{
					choseW += array[(int) (freq * j / count)];
					choseW += 0.5 * array[(int) (freq * j / count) - 1];
					choseW += 0.5 * array[(int) (freq * j / count) + 1];
				}
			}
			
			if (choseW < 150 / sensit * avg)
			{
				res.note = -1;
				return res;
			}
			
			//determing the octave
			int oct = 2;
			while (freq >= 2 * fr[0])
			{
				oct++;
				freq *= 0.5;
			}
			
			//determing the note
			int choseNote = num - 1;
			while (choseNote > 0 && freq < fr[choseNote])
			{
				choseNote--;
			}
			
			
			int g;
			if (choseNote < num - 1)
				if (freq - fr[choseNote] < fr[choseNote + 1] - freq)
				{
					g = choseNote;
					res.n = (int) (10.0 * (freq - fr[g]) / (fr[g + 1] - fr[g]));
				}
				else
				{
					g = choseNote + 1;
					res.n = (int) (10.0 * (freq - fr[g]) / (fr[g] - fr[g - 1]));
				}
			else
				if (freq - fr[choseNote] < 2 * fr[0] - freq)
				{
					g = choseNote;
					res.n = (int) (10.0 * (freq - fr[g]) / (2 * fr[0] - fr[g]));
				}
				else
				{
					g = 0;
					res.n = (int) (10.0 * (freq - 2 * fr[0]) / (2 * fr[0] - fr[choseNote]));
					oct++;
				}
			res.note = g;
			res.oct = oct;
			return res;
		}
		catch (Exception ex)
		{
			System.exit(0);;
		}
		return null;
	}
	

	public NoteStruct2[] recognize(double[] array, double count, int maxFreq, double sensit, boolean useLast)  //режим распознавания
	{
		try
		{
			int m = (int) (40 / count);
			//average
			double av = 0;
			for (int i = (int) (maxFreq / count); i >= m; i--)
			{
				av += array[i];
			}
			av /= (maxFreq / count - m);
			av *= lowLimit;
			
			double avg = 0;
			int s = 0;
			for (int i = (int) (maxFreq / count); i >= m; i--)
			{
				if (array[i] <= av)
				{
					avg += array[i];
					s++;
				}
			}
			avg /= s;
			avg *= lowLimit;
		
			//weights of frequencies
			double[] w = new double[extraNum];
			for (int i = 0; i < extraNum; i++)
			{
				w[i] = 0;
				for (int j = 1; extraFr[i] * j <= maxFreq; j++)
				{
					if (array[(int) (extraFr[i] * j / count)] >= avg)
					{
						w[i] += array[(int) (extraFr[i] * j / count)];
						w[i] += 0.5 * array[(int) (extraFr[i] * j / count) - 1];
						w[i] += 0.5 * array[(int) (extraFr[i] * j / count) + 1];
					}
				}
			}
			
			NoteStruct2[] res = new NoteStruct2[num + 1];
			
			for (int i = 0; i <= num; i++)
			{
				res[i] = new NoteStruct2(-1, 0);
			}
			int cnt = 0;
			boolean[] was = new boolean[num];
			for (int i = 0; i < num; i++)
			{
				was[i] = false;
			}
			
			//frequency with maximum weight
			int maxNote = 0;
			for (int i = 0; i < extraNum; i++)
				if (w[i] > w[maxNote]) 
					maxNote = i;
			
			while (w[maxNote] > 4 * avg)
			{
				double freq = extraFr[maxNote];
				for (int i = 2; extraFr[maxNote] * i < freqLimit; i++)
				{
					double wNew = 0;
					for (int j = 1; extraFr[maxNote] * i * j <= maxFreq; j++)
					{
						if (array[(int) (extraFr[maxNote] * i * j / count)] >= avg)
						{
							wNew += array[(int) (extraFr[maxNote] * i * j / count)];
							wNew += 0.5 * array[(int) (extraFr[maxNote] * i * j / count) - 1];
							wNew += 0.5 * array[(int) (extraFr[maxNote] * i * j / count) + 1];
						}
					}
					if (5 * wNew > 4 * w[maxNote])
					{
						freq = extraFr[maxNote] * i;
					}
				}
				
				w[maxNote] = 0;
				double choseW = 0;
				for (int j = 1; freq * j <= maxFreq; j++)
				{
					if (array[(int) (freq * j / count)] >= avg)
					{
						choseW += array[(int) (freq * j / count)];
						choseW += 0.5 * array[(int) (freq * j / count) - 1];
						choseW += 0.5 * array[(int) (freq * j / count) + 1];
					}
				}
				
				int oct = 2;
				while (freq >= 2 * fr[0])
				{
					oct++;
					freq *= 0.5;
				}
				
				int choseNote = num - 1;
				while (choseNote > 0 && freq < fr[choseNote])
				{
					choseNote--;
				}
				
				int g;
				if (choseNote < num - 1)
					if (freq - fr[choseNote] < fr[choseNote + 1] - freq)
					{
						g = choseNote;
					}
					else
					{
						g = choseNote + 1;
					}
				else
					if (freq - fr[choseNote] < 2 * fr[0] - freq)
					{
						g = choseNote;
					}
					else
					{
						g = 0;
						oct++;
					}
				
				if ((!was[g]) && (2 * choseW > 3 * lastW[g]) && (choseW > 150 / sensit * avg))
				{
						res[cnt].note = g;
						res[cnt].oct = oct;
						cnt++;
				}
				if (!was[g])
				{
					lastW[g] = choseW;
					was[g] = true;
				}
				for (int i = 0; i < extraNum; i++)
					if (w[i] > w[maxNote])
						maxNote = i;
				
			}
			for (int i = 0; i < num; i++)
				if (!was[i])
				{
					lastW[i] = 0;
				}
			return res;
		}
		catch (Exception ex)
		{
			System.exit(0);;
		}
		return null;
	}
}
