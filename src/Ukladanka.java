
public class Ukladanka {
	private int[][] plansza;
	
	public Ukladanka(int[][] tab)
	{
		this.plansza = tab;
	}

	public int[][] pobierzRozmieszczenie() {
		int[][] wynik = new int[plansza.length][plansza[0].length];
		for (int i = 0; i < plansza.length; i++)
			for (int j = 0; j < plansza[i].length; j++)
				wynik[i][j] = plansza[i][j];
		
		return wynik;
	}
	
	public int pobierzRozmiarWiersza()
	{
		return plansza.length;
	}
	
	public int pobierzRozmiarKolumny()
	{
		return plansza[0].length;
	}

	public Boolean doGory()
	{
		int wiersz = -1;
		int kolumna = -1;
		
		//szukanie zera i sprawdzenie od razu czy mozna przesunac
		for (int w = 0; w < plansza.length & wiersz == -1; w++)
			for (int k = 0; k < plansza[w].length & kolumna == -1; k++)
			{
				if (plansza[w][k] == 0)
				{
					if (w == 0) return false;
					wiersz = w;
					kolumna = k;
				}
			}

		int tmp = plansza[wiersz][kolumna];
		plansza[wiersz][kolumna]= plansza[wiersz - 1][kolumna];
		plansza[wiersz - 1][kolumna] = tmp;
		
		return true;
	}
	
	public Boolean doDolu()
	{
		int wiersz = -1;
		int kolumna = -1;
		
		//szukanie zera i sprawdzenie od razu czy mozna przesunac
		for (int w = 0; w < plansza.length & wiersz == -1; w++)
			for (int k = 0; k < plansza[w].length & kolumna == -1; k++)
			{
				if (plansza[w][k] == 0)
				{
					if (w == plansza.length - 1) return false;
					wiersz = w;
					kolumna = k;
				}
			}

		int tmp = plansza[wiersz][kolumna];
		plansza[wiersz][kolumna]= plansza[wiersz + 1][kolumna];
		plansza[wiersz + 1][kolumna] = tmp;
		
		return true;
	}

	public Boolean wLewo()
	{
		int wiersz = -1;
		int kolumna = -1;
		
		//szukanie zera i sprawdzenie od razu czy mozna przesunac
		for (int w = 0; w < plansza.length & wiersz == -1; w++)
			for (int k = 0; k < plansza[w].length & kolumna == -1; k++)
			{
				if (plansza[w][k] == 0)
				{
					if (k == 0) return false;
					wiersz = w;
					kolumna = k;
				}
			}

		int tmp = plansza[wiersz][kolumna];
		plansza[wiersz][kolumna]= plansza[wiersz][kolumna - 1];
		plansza[wiersz][kolumna - 1] = tmp;
		
		return true;
	}
	
	public Boolean wPrawo()
	{
		int wiersz =- 1;
		int kolumna =- 1;
		
		//szukanie zera i sprawdzenie od razu czy mozna przesunac
		for (int w = 0; w < plansza.length & wiersz == -1; w++)
			for (int k = 0; k < plansza[w].length & kolumna == -1; k++)
			{
				if (plansza[w][k] == 0)
				{
					if (k == plansza[w].length - 1) return false;
					wiersz = w;
					kolumna = k;
				}
			}
		
		int tmp = plansza[wiersz][kolumna];
		plansza[wiersz][kolumna] = plansza[wiersz][kolumna + 1];
		plansza[wiersz][kolumna+1] = tmp;

		return true;
		
	}

	public Boolean sprCzyRozwia()
	{
		int iloscLiczb = plansza.length * plansza[0].length;
		int licznik = 1;
		
		for (int w = 0; w < plansza.length; w++)
			for (int k = 0; k < plansza[w].length; k++)
			{
				if (plansza[w][k] != licznik % iloscLiczb)
				    return false;
				licznik++;
			}
		
		return true;
	}
	
	public int metrykaHamminga()
	{
		//wartosc funkcji jest obliczana na podstawie dobrze ulozonych liczb (uwzglednia tez te oddalone o 1 krok)
		int iloscLiczb = plansza.length * plansza[0].length;
		int licznik = 1;
		int wartoscFunkcji = iloscLiczb * 2;
		
		for (int w = 0; w < plansza.length; w++)
			for (int k = 0; k < plansza[w].length; k++)
			{
				if (plansza[w][k] == licznik % iloscLiczb)
				    wartoscFunkcji -= 2;
				else if (w > 0)
				{
					if (plansza[w - 1][k] == licznik % iloscLiczb)
					    wartoscFunkcji--;
				}
				else if (w < plansza.length)
				{
					if (plansza[w + 1][k] == licznik % iloscLiczb)
					    wartoscFunkcji--;
				}
				else if (k > 0)
				{
					if (plansza[w][k - 1] == licznik % iloscLiczb)
					    wartoscFunkcji--;
				}
				else if (k < plansza[w].length)
				{
					if (plansza[w][k+1] == licznik%iloscLiczb)
					    wartoscFunkcji--;
				}
				licznik++;
			}
				
		return wartoscFunkcji;
	}
	
	public int metrykaManhattan()
	{
		//wartosc funkcji jest obliczana na podstawie odleglosci
		int iloscLiczb = plansza.length * plansza[0].length;
		int licznik = 1;
		int wartoscFunkcji = 0;

        //tworzymy tablice ktora ma wartosc kolumny i wiersza dla kazdej liczby.. pozniej odejmujemy poprawne wartosci, i na koncu sumujemy bezwzgledne wartosci
		int[][] tab = new int[iloscLiczb][2];

		for (int w = 0; w < plansza.length; w++)
			for (int k = 0; k < plansza[w].length; k++)
			{
				tab[plansza[w][k]][0] += w;
				tab[plansza[w][k]][1] += k;
				
				//w liczniku mamy wartosc, jaka powinna znajdowac sie na obecnej pozycji
				tab[licznik][0] -= w;
				tab[licznik][1] -= k;
				
				licznik++;
				licznik %= iloscLiczb;
			}

		for (int i = 0; i < iloscLiczb; i++)
			for (int j = 0; j < 2; j++)
				wartoscFunkcji += Math.abs(tab[i][j]);
		
		return wartoscFunkcji;
	}
	
	public static String porownanie(Ukladanka poprzednik, Ukladanka nastepnik)
	{
		int w1 = -1, k1 = -1, w2 = -1, k2 = -1;
		int[][] rozPoprz = poprzednik.pobierzRozmieszczenie();
		int[][] rozNast = nastepnik.pobierzRozmieszczenie();
		
		for (int w = 0; w < poprzednik.pobierzRozmiarWiersza(); w++)
			for (int k = 0; k < poprzednik.pobierzRozmiarKolumny(); k++)
			{
				if (rozPoprz[w][k] == 0)
				{
					w1 = w;
					k1 = k;
				}
			}
		
		for (int w = 0; w < nastepnik.pobierzRozmiarWiersza(); w++)
			for (int k = 0; k < nastepnik.pobierzRozmiarKolumny(); k++)
			{
				if (rozNast[w][k] == 0)
				{
					w2=w;
					k2=k;
				}
			}

		//jesli ktoras z wartosci nie zostala zainicjowana
		if ((w1 == -1 | w2 == -1) | (k1 == -1 | k2 == -1))
		    return "E";
		
		//porownujemy i wyswieltamy wynik.. jesli odleglosc bedzie wieksza niz 1 krok, zwraca znak E, ktory oznacza blad
		if (w1 == w2 & k1 + 1== k2)
		    return "R";
		else if (w1 == w2 & k1 - 1 == k2)
		    return "L";
		else if (w1 + 1 == w2 & k1 == k2)
		    return "D";
		else if (w1 - 1 == w2 & k1 == k2)
		    return "U";
		else return "E";
		
	}

	@Override
	public String toString() {
		String wynik = "";
		
		for (int w = 0; w < plansza.length; w++)
		{
			for (int k = 0; k < plansza[w].length; k++)
			{
				wynik += "" + plansza[w][k];
				if (k< plansza[w].length-1) wynik+="\t";
			}
			if (w < plansza.length-1) wynik += "\n";
		}
		return wynik;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ukladanka)
		{
			Ukladanka other = (Ukladanka) obj;
			int[][] tab1, tab2;
			tab1 = this.pobierzRozmieszczenie();
			tab2 = other.pobierzRozmieszczenie();
			
			if (tab2.length != tab1.length)
			    return false;
			
			if (tab2[0].length != tab1[0].length)
			    return false;
			
			for (int w = 0; w<tab1.length; w++)
				for (int k = 0; k < tab1[0].length; k++)
					if (tab2[w][k] != tab1[w][k])
					    return false;
			return true;
		}
		else return false;
	}
	
}
