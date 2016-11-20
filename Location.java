package pack;

public class Location {
	int[] L;//string/fret
	
	Location(int st, int fr)
	{
		L = new int[] {st, fr};
	}
	
	/*public int Distance(Location one, Location two)
	{
		return Math.abs(one.L[0] - two.L[0]) + Math.abs(one.L[1] - two.L[1]);
	}*/
}
