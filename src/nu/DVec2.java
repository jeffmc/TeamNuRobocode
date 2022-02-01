package nu;

// Double Vector with 2 components, referred to as x and y.
// BY jeff mcmillan

public class DVec2 {

	private double[] v = { 0, 0 };
	
	public DVec2() {}
	
	public double x() { return v[0]; } // getter
	public double y() { return v[1]; }
	public void x(double x) { v[0] = x; } // setter
	public void y(double y) { v[1] = y; }
	
	public static DVec2 n(double x, double y) { return new DVec2(x,y); } // new
	
	private DVec2(double[] vs) { // array ref constructor
		if (vs == null) throw new IllegalArgumentException("Array is null!");
		if (vs.length != 2) throw new IllegalArgumentException("Not an array of length 2!");
		v = vs;
	}
	
	public DVec2(double x, double y) { v[0] = x; v[1] = y; }
	
	public static DVec2 c(DVec2 copy) {
		return new DVec2(copy.v.clone());
	}
	
	public static DVec2 r(DVec2 ref) {
		return new DVec2(ref.v);
	}
	
	public DVec2 set(DVec2 o) { v = o.v.clone(); return this; }
	
	public DVec2 ref(DVec2 o) { v = o.v; return this; }
	
	@Override
	public String toString() {
		return String.format("[%1$f, %2$f]", v[0], v[1]);
	}
}
