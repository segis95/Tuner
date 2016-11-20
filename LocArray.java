package pack;

public class LocArray {
	Location[] arr;
	
	LocArray(Note note){
		if (note.n == 4){
			
			arr = new Location[] {new Location(1,0)};
			
			if (note.s == "C"){
				arr = new Location[] {new Location(2,1), new Location(3,5),new Location(4,10),new Location(5,15),new Location(6,20) };
			}
			if (note.s == "C#"){
				arr = new Location[] {new Location(2,2), new Location(3,6),new Location(4,11),new Location(5,16) };
			}
			if (note.s == "D"){
				arr = new Location[] {new Location(2,3), new Location(3,7),new Location(4,12),new Location(5,17) };
			}
			if (note.s == "D#"){
				arr = new Location[] {new Location(2,4), new Location(3,8),new Location(4,13),new Location(5,18) };
			}
			if (note.s == "E"){
				arr = new Location[] {new Location(1,0), new Location(2,5),new Location(3,9),new Location(4,14),new Location(5,19) };
			}
			if (note.s == "F"){
				arr = new Location[] {new Location(1,1), new Location(2,6),new Location(3,10),new Location(4,15),new Location(5,20) };
			}
			if (note.s == "F#"){
				arr = new Location[] {new Location(1,2), new Location(2,7),new Location(3,11),new Location(4,16) };
			}
			if (note.s == "G"){
				arr = new Location[] {new Location(1,3), new Location(2,8),new Location(3,12),new Location(4,18) };
			}
			if (note.s == "G#"){
				arr = new Location[] {new Location(1,4), new Location(2,9),new Location(3,13),new Location(4,18), };
			}
			if (note.s == "A"){
				arr = new Location[] {new Location(1,5), new Location(2,10),new Location(3,14),new Location(4,19) };
			}
			if (note.s == "A#"){
				arr = new Location[] {new Location(1,6), new Location(2,11),new Location(3,15),new Location(4,20) };
			}
			if (note.s == "B"){
				arr = new Location[] {new Location(1,7), new Location(2,12),new Location(3,16) };
			}
		}
		
		if (note.n == 3){
			arr = new Location[] {new Location(1,0)};
			
			if (note.s.equals("C")){
				arr = new Location[] {new Location(5,3), new Location(6,8)};
			}
			if (note.s.equals("C#")){
				arr = new Location[] {new Location(5,4), new Location(6,9) };
			}
			if (note.s.equals("D")){
				arr = new Location[] {new Location(4,0), new Location(5,5),new Location(6,10) };
			}
			if (note.s.equals("D#")){
				arr = new Location[] {new Location(4,1), new Location(5,6),new Location(6,11) };
			}
			if (note.s == "E"){
				arr = new Location[] {new Location(4,2), new Location(5,7),new Location(6,12) };
			}
			if (note.s == "F"){
				arr = new Location[] {new Location(4,3), new Location(5,8),new Location(6,13) };
			}
			if (note.s == "F#"){
				arr = new Location[] {new Location(4,4), new Location(5,9),new Location(6,14)};
			}
			if (note.s == "G"){
				arr = new Location[] {new Location(3,0), new Location(4,5),new Location(5,10),new Location(6,15) };
			}
			if (note.s == "G#"){
				arr = new Location[] {new Location(3,1), new Location(4,6),new Location(5,11),new Location(6,16) };
			}
			if (note.s == "A"){
				arr = new Location[] {new Location(3,2), new Location(4,7),new Location(5,12),new Location(6,17) };
			}
			if (note.s == "A#"){
				arr = new Location[] {new Location(3,3), new Location(4,8),new Location(5,13),new Location(6,18) };
			}
			if (note.s == "B"){
				arr = new Location[] {new Location(2,0), new Location(4,9),new Location(5,14),new Location(6,19) };
			}
		}
		
		if (note.n == 2){

			arr = new Location[] {new Location(1,0)};
			
			if (note.s == "E"){
				arr = new Location[] {new Location(6,0)};
			}
			if (note.s == "F"){
				arr = new Location[] {new Location(6,1) };
			}
			if (note.s == "F#"){
				arr = new Location[] {new Location(6,2)};
			}
			if (note.s == "G"){
				arr = new Location[] {new Location(6,3) };
			}
			if (note.s == "G#"){
				arr = new Location[] {new Location(6,4) };
			}
			if (note.s == "A"){
				arr = new Location[] {new Location(5,0), new Location(6,5) };
			}
			if (note.s == "A#"){
				arr = new Location[] {new Location(5,1), new Location(6,6) };
			}
			if (note.s == "B"){
				arr = new Location[] {new Location(5,2), new Location(6,7) };
			}
		}
		
		if (note.n == 5){
			arr = new Location[] {new Location(1,0)};
			
			if (note.s == "C"){
				arr = new Location[] {new Location(1,8), new Location(2,13),new Location(3,17), new Location(1,20)};
			}
			if (note.s == "C#"){
				arr = new Location[] {new Location(1,9), new Location(2,14),new Location(3,18) };
			}
			if (note.s == "D"){
				arr = new Location[] {new Location(1,10), new Location(2,15),new Location(3,19) };
			}
			if (note.s == "D#"){
				arr = new Location[] {new Location(1,11), new Location(2,16),new Location(3,20) };
			}
			if (note.s == "E"){
				arr = new Location[] {new Location(1,12), new Location(2,17)};
			}
			if (note.s == "F"){
				arr = new Location[] {new Location(1,13), new Location(2,18) };
			}
			if (note.s == "F#"){
				arr = new Location[] {new Location(1,14), new Location(2,19)};
			}
			if (note.s == "G"){
				arr = new Location[] {new Location(1,15), new Location(2,20) };
			}
			if (note.s == "G#"){
				arr = new Location[] {new Location(1,16) };
			}
			if (note.s == "A"){
				arr = new Location[] {new Location(1,17) };
			}
			if (note.s == "A#"){
				arr = new Location[] {new Location(1,18) };
			}
			if (note.s == "B"){
				arr = new Location[] {new Location(1,19) };
			}
		}
		
	
	}
	
	public int Distance(Location one, Location two)
	{
		return Math.abs(one.L[0] - two.L[0]) + Math.abs(one.L[1] - two.L[1]);
	}

	
	
	
	public Location Closest(Location loc, Note note, boolean isfirsttime){
		
		LocArray la = new LocArray(note);
		if (isfirsttime == true) {
			return la.arr[0];
		}
		else{
			int min = 1000;
			int min_index = 0;
			for ( int i = 0; i < la.arr.length; i++){
				if (la.arr[0].L[1] == 0){
					return la.arr[i];
				}
				else{
					if (Distance(la.arr[i], loc) < min){
						min = Distance(la.arr[i], loc);
						min_index = i;
					}
				}
			}
			return la.arr[min_index];
		}
		
		
	}
	
}
