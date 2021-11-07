
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStar {
	String[] args;
	int[] rozmiarPlanszy;
	int plansza[][];

    int idHeurystyki = 1;
    static long przetworzone = 0;

    public AStar(String[] args, int[] rozmiarPlanszy, int[][] plansza) {
        this.args = args;
        this.rozmiarPlanszy = rozmiarPlanszy;
        this.plansza = plansza;
        //this.idHeurystyki = Integer.parseInt(this.args[1]);
        if (this.args[1].compareTo("hamm") == 0)
            this.idHeurystyki = 1;
        else if (this.args[1].compareTo("manh") == 0)
            this.idHeurystyki = 2;
    }
	
	private void dodajWierzcholek(PriorityQueue<WierzcholekA> doSprawdzenia, Stack<WierzcholekA> sprawdzone, Ukladanka klucz, Ukladanka wartosc, int g, int depthLevel)
	{
		przetworzone++;
		
		if (sprawdzone.search(new WierzcholekSpr(wartosc)) < 0)
		{
			switch(idHeurystyki)
			{
				case 1:
				    doSprawdzenia.add(new WierzcholekA(klucz, wartosc, wartosc.metrykaHamminga() + g, g, depthLevel));
				    break;
				case 2:
				    doSprawdzenia.add(new WierzcholekA(klucz, wartosc, wartosc.metrykaManhattan() + g, g, depthLevel));
				    break;
			}
		}
		else
		{
			int poz = sprawdzone.search(new WierzcholekSprA(wartosc, g));
			if (poz < 0)
			{
				switch(idHeurystyki)
				{
					case 1:
					    sprawdzone.add(sprawdzone.size()-poz, new WierzcholekA(klucz, wartosc, wartosc.metrykaHamminga()+g, g, depthLevel));
					    break;
					case 2:
					    sprawdzone.add(sprawdzone.size()-poz, new WierzcholekA(klucz, wartosc, wartosc.metrykaManhattan()+g, g, depthLevel));
					    break;
				}
				sprawdzone.remove(new WierzcholekSprA(wartosc, g));

			}
		}
	}

    public String[] uruchom()
	{
        String[] wynik = {"", "", ""};
		
		Ukladanka ukl = new Ukladanka(plansza);
		Ukladanka tmpUkl;
		WierzcholekA rozpatrywany = new WierzcholekA(null, ukl, ukl.metrykaManhattan(), 0, 0);

		PriorityQueue<WierzcholekA> doSprawdzenia = new PriorityQueue<WierzcholekA>();
		Stack<WierzcholekA> sprawdzone = new Stack<WierzcholekA>();

		dodajWierzcholek(doSprawdzenia, sprawdzone, null, ukl, 0, 0);

		int poziom_rekurencji = 0, max = 0;

        BigInteger czasPoczatek = BigInteger.valueOf(System.nanoTime());

		while (!ukl.sprCzyRozwia()) //poki nie ma rozwiazania
		{
            tmpUkl = new Ukladanka(ukl.pobierzRozmieszczenie());

            poziom_rekurencji++;

            //sprawdza czy mozna przesunac do gory i od razu czy nowe ulozenie nie bylo juz wczesniej rozpatrywane
            if (tmpUkl.doGory())
                if (sprawdzone.search(new WierzcholekSpr(tmpUkl))<0)
                dodajWierzcholek(doSprawdzenia, sprawdzone, ukl, tmpUkl, rozpatrywany.g+1, poziom_rekurencji);

            tmpUkl = new Ukladanka(ukl.pobierzRozmieszczenie());
            if (tmpUkl.doDolu())
                if (sprawdzone.search(new WierzcholekSpr(tmpUkl))<0)
                    dodajWierzcholek(doSprawdzenia, sprawdzone, ukl, tmpUkl, rozpatrywany.g+1, poziom_rekurencji);

            tmpUkl = new Ukladanka(ukl.pobierzRozmieszczenie());
            if (tmpUkl.wLewo())
                if (sprawdzone.search(new WierzcholekSpr(tmpUkl))<0)
                dodajWierzcholek(doSprawdzenia, sprawdzone, ukl, tmpUkl, rozpatrywany.g+1, poziom_rekurencji);

            tmpUkl = new Ukladanka(ukl.pobierzRozmieszczenie());
            if (tmpUkl.wPrawo())
                if (sprawdzone.search(new WierzcholekSpr(tmpUkl))<0)
                dodajWierzcholek(doSprawdzenia, sprawdzone, ukl, tmpUkl, rozpatrywany.g+1, poziom_rekurencji);

			sprawdzone.add(rozpatrywany);
			rozpatrywany = doSprawdzenia.poll();

			if (rozpatrywany != null)
            {
                poziom_rekurencji = rozpatrywany.depthLevel;
                max = (poziom_rekurencji > max) ? poziom_rekurencji : max;
                ukl = rozpatrywany.wartosc;
            }
			else
			{
				BigInteger czasKoniec = BigInteger.valueOf(System.nanoTime());
				wynik[0] = "-1";
                wynik[2] = wynik[0] + "\r\n" + sprawdzone.size() + "\r\n";
                wynik[2] += przetworzone + "\r\n";
                wynik[2] += max + "\r\n";
                wynik[2] += (float)((czasKoniec.subtract(czasPoczatek)).longValue()/1000)/1000.0f +"\r\n";
                return wynik;
			}

		}

		sprawdzone.add(rozpatrywany);
		BigInteger czasKoniec = BigInteger.valueOf(System.nanoTime());

		//System.out.println("Ilosc stanow przetworzonych: " + przetworzone);
		//System.out.println("Ilosc stanow odwiedzonych: " + sprawdzone.size());

        wynik[2] = sprawdzone.size() + "\r\n";
        wynik[2] += przetworzone + "\r\n";
        wynik[2] += max + "\r\n";
        wynik[2] += (float)((czasKoniec.subtract(czasPoczatek)).longValue()/1000)/1000.0f +"\r\n";
		
		//doszlismy do rozwiazania, zdejmujemy wezly ze stosu. Szukamy odp wartosci, jesli znajdziemy, klucz tej wartosci staje sie nowa szukana wartoscia i tak az trafimy na null (stan poczatkowy)
		
		String wyjscie = "";
		int liczbaKrokow = 0;

		Ukladanka szukana = rozpatrywany.wartosc; // wartosc rozwiazanej ukladanki;
		Wierzcholek sprawdzany = sprawdzone.pop();

		while(sprawdzany.klucz != null)
		{
			if (sprawdzany.wartosc.equals(szukana))
			{
				wyjscie += Ukladanka.porownanie(sprawdzany.klucz, sprawdzany.wartosc);
				liczbaKrokow++;
				szukana = sprawdzany.klucz;
			}

			sprawdzany = sprawdzone.pop();
		}

		String odwroconyWynik="";

		for (int i=liczbaKrokow-1; i>=0; i--)
			odwroconyWynik+=wyjscie.substring(i, i+1);

        wynik[0] = Integer.toString(liczbaKrokow);
        wynik[1] = odwroconyWynik;
        wynik[2] = wynik[0] + "\r\n" + wynik[2];

        return wynik;

		
		
	}

}
