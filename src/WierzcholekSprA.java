
public class WierzcholekSprA {
	private int g;
	private Ukladanka wartosc;

	public WierzcholekSprA(Ukladanka wartosc, int g) {
		this.g = g;
		this.wartosc=wartosc;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WierzcholekA){
			if ((this.wartosc.equals(((WierzcholekA) obj).wartosc)) &&(this.g<((WierzcholekA) obj).g)) return true;
			else return false;
			
		}
		else return false;
		
		}
}