
public class WierzcholekA extends Wierzcholek implements Comparable<WierzcholekA> {
	public int f;
	public int g;
	
	public WierzcholekA(Ukladanka klucz, Ukladanka wartosc, int f, int g, int depthLevel) {
		super(klucz, wartosc, depthLevel);
		this.f = f;
		this.g = g;
	}

	@Override
	public int compareTo(WierzcholekA o) {
		// TODO Auto-generated method stub
		return this.f-o.f;
	}

	
	
}
