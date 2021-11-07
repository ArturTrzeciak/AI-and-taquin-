
public class WierzcholekSprG {
	private int g;

	public WierzcholekSprG(int g) {
		this.g = g;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WierzcholekA){
			if (this.g==((WierzcholekA) obj).g) return true;
			else return false;
			
		}
		else return false;
		
		}
}