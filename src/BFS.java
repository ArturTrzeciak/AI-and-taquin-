
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BFS {
    String[] args;
    int[] rozmiarPlanszy;
    int plansza[][];

	long przetworzone = 0;

    public BFS(String[] args, int[] rozmiarPlanszy, int[][] plansza) {
        this.args = args;
        this.rozmiarPlanszy = rozmiarPlanszy;
        this.plansza = plansza;
    }

	public String[] uruchom()
	{
	    String[] wynik = {"", "", ""};

		List<String> porzadekPrzesuwania = null;

        String[] tmpPorzadekTab = new String[4];
        //w dfs porzadek odwrocony!

        for (int i = 0; i < 4; i++)
        {
            tmpPorzadekTab[i] = args[1].substring(i, i + 1);
            porzadekPrzesuwania = Arrays.asList(tmpPorzadekTab);
        }
			
		Ukladanka ukl = new Ukladanka(plansza);
		Ukladanka tmpUkl;
		Wierzcholek rozpatrywany = new Wierzcholek(null, ukl, 0);
		
		Queue<Wierzcholek> doSprawdzenia = new LinkedList<Wierzcholek>();
		Stack<Wierzcholek> sprawdzone = new Stack<Wierzcholek>();

		int poziom_rekurencji = 0, max = 0;

		BigInteger czasPoczatek = BigInteger.valueOf(System.nanoTime());

		while (!ukl.sprCzyRozwia()) //poki nie ma rozwiazania
		{
			poziom_rekurencji++;

			//w dfs jesli porzadek jest narzucony, musi byc odwrocony, gdzies wyrzej to realizujemy
				for (String kolejnosc : porzadekPrzesuwania) 				{
					if (kolejnosc.equals("U"))
						{
							tmpUkl = new Ukladanka(ukl.pobierzRozmieszczenie());

							//sprawdza czy mozna przesunac do gory i od razu czy nowe ulozenie nie bylo juz wczesniej rozpatrywane
							if (tmpUkl.doGory())
								if (sprawdzone.search(new WierzcholekSpr(tmpUkl)) < 0)
								{
									przetworzone++;
									doSprawdzenia.add(new Wierzcholek(ukl, tmpUkl, poziom_rekurencji));
								}
						}
					else if (kolejnosc.equals("D"))
						{
							tmpUkl = new Ukladanka(ukl.pobierzRozmieszczenie());

							if (tmpUkl.doDolu())
								if (sprawdzone.search(new WierzcholekSpr(tmpUkl)) < 0)
								{
									przetworzone++;
									doSprawdzenia.add(new Wierzcholek(ukl, tmpUkl, poziom_rekurencji));
								}

						}
					else if (kolejnosc.equals("L"))
						{
							tmpUkl=new Ukladanka(ukl.pobierzRozmieszczenie());

							if (tmpUkl.wLewo())
								if (sprawdzone.search(new WierzcholekSpr(tmpUkl)) < 0)
								{
									przetworzone++;
									doSprawdzenia.add(new Wierzcholek(ukl, tmpUkl, poziom_rekurencji));
								}
						}
					else if (kolejnosc.equals("R"))
						{
							tmpUkl=new Ukladanka(ukl.pobierzRozmieszczenie());

							if (tmpUkl.wPrawo())
								if (sprawdzone.search(new WierzcholekSpr(tmpUkl)) < 0)
								{
									przetworzone++;
									doSprawdzenia.add(new Wierzcholek(ukl, tmpUkl, poziom_rekurencji));
								}
						}

				}

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

		String odwroconyWynik = "";

		for (int i=liczbaKrokow-1; i>=0; i--)
			odwroconyWynik+=wyjscie.substring(i, i+1);

		wynik[0] = Integer.toString(liczbaKrokow);
        wynik[1] = odwroconyWynik;
        wynik[2] = wynik[0] + "\r\n" + wynik[2];

		return wynik;
	}
	
}
