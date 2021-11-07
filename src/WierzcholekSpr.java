
public class WierzcholekSpr {
	
	Ukladanka ukl;

	public WierzcholekSpr(Ukladanka arg)
	{
		this.ukl = arg;
	}
	
	//przeciazenie przyda sie do wyszukiwania wartosci na stosie.
	//Porownuje "liczba" z wartosciami przechowywanymi w klasie "Wierzcholek"
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Wierzcholek){
			if (ukl.equals(((Wierzcholek) obj).wartosc))
			    return true;
			else
			    return false;
			
		}
		else return false;

	}
}
