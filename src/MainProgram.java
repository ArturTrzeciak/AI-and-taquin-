import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;

public class MainProgram {

	public static String[] wybierzAlgorytm(String[] args, int rozmiarPlanszy[], int plansza[][])
	{
	    String [] wynikiStat = new String[2];

		if ("bfs".equals(args[0]))
		{
			BFS bfs = new BFS(args, rozmiarPlanszy, plansza);
            wynikiStat = bfs.uruchom();
		}
		else if ("dfs".equals(args[0]))
		{
			DFS dfs = new DFS(args, rozmiarPlanszy, plansza);
            wynikiStat = dfs.uruchom();
		}

		else if ("astr".equals(args[0]))
		{
			AStar aStar = new AStar(args, rozmiarPlanszy, plansza);
            wynikiStat = aStar.uruchom();
		}

		return wynikiStat;
	}

	
	public static void main(String[] args) {
		String nazwaPlikuWejscia = "dane.txt";
        String nazwaPlikuWyjscia = "wyniki.txt";
        String nazwaPlikuStat = "stat.txt";
		
		if (args.length == 0)
		{
			System.out.println("Nie podano parametrow wejsciowych !");
			return;
		}

		if (args.length != 5)
        {
            System.out.println("Niepoprawna ilosc argumentow !");
            System.out.println("prawidlowa kolejnosc:\n akronim_alg parametr_strategii plik_wej plik_wyj plik_stat");
            return;
        }

        if (args[0].compareTo("bfs") != 0 && args[0].compareTo("dfs") != 0 && args[0].compareTo("astr") != 0)
        {
            System.out.println("Algorytm o wybranym akronimie nie istnieje !");
            return;
        }

        if (args[0].compareTo("astr") == 0 && (args[1].compareTo("hamm") != 0 && args[1].compareTo("manh") != 0))
        {
            System.out.println("Podano bledny parametr dla algorytmu A* !");
            return;
        }

        nazwaPlikuWejscia = args[2];
        nazwaPlikuWyjscia = args[3];
        nazwaPlikuStat = args[4];

        File plikWejsciowy = new File(nazwaPlikuWejscia);

		if (!plikWejsciowy.isFile())
        {
            System.out.println("Plik wejsciowy o podanej nazwie nie istnieje lub jest katalogiem !");
            return;
        }

        int ilosc_wierszy, ilosc_kolumn;
        String bufor;
        String[] noweArg = new String[2];
        int rozmiarPlanszy[] = new int[2];
        int plansza[][] = null;
        BufferedReader odczyt;
        FileWriter zapisWyniku, zapisStat;
        Boolean pierwszyObieg = true;
			
        try	{
                odczyt=new BufferedReader(new FileReader(nazwaPlikuWejscia));

                int i_w = 0;
                while((bufor = odczyt.readLine()) != null)
                {
                    String[] podzielony_ciag;

                    if (pierwszyObieg)
                    {
                        podzielony_ciag = bufor.split(" ");
                        ilosc_wierszy = Integer.parseInt(podzielony_ciag[0]);
                        ilosc_kolumn = Integer.parseInt(podzielony_ciag[1]);
                        //System.out.println(ilosc_wierszy + " - " + ilosc_kolumn);

                        noweArg[0] = args[0];
                        noweArg[1] = args[1];
                        rozmiarPlanszy[0] = ilosc_wierszy;
                        rozmiarPlanszy[1] = ilosc_kolumn;
                        plansza = new int[ilosc_wierszy][ilosc_kolumn];

                        pierwszyObieg = false;
                        continue;
                    }

                    podzielony_ciag = bufor.split(" ");

                    for (int i = 0; i < rozmiarPlanszy[1]; i++)
                    {
                        plansza[i_w][i] = Integer.parseInt(podzielony_ciag[i]);
                    }

                    i_w++;
                }

                odczyt.close();

                /*System.out.println(rozmiarPlanszy[0] + ":" + rozmiarPlanszy[1]);
                for (int i = 0; i < rozmiarPlanszy[0]; i++)
                {
                    for (int j = 0; j < rozmiarPlanszy[1]; j++)
                    {
                        System.out.print(plansza[i][j] + " ");
                    }
                    System.out.println();
                }*/

                //long czasPoczatek = System.nanoTime();

                //wywolanie algorytmu
                String wynikiStat[] = wybierzAlgorytm(noweArg, rozmiarPlanszy, plansza);
                //long czasKoniec = System.nanoTime();

                System.out.println("Wyniki:\n" + wynikiStat[0] + "\n" + wynikiStat[1]);
                System.out.println("\nStatystyki:\n" + wynikiStat[2]);
                //System.out.println("\nCzas operacji: "+ (czasKoniec - czasPoczatek)/1000000.0 +" ms\n\n");

                zapisWyniku = new FileWriter(nazwaPlikuWyjscia, false);
                zapisStat = new FileWriter(nazwaPlikuStat, false);
                zapisWyniku.write(wynikiStat[0] + "\r\n" + wynikiStat[1]);
                zapisStat.write(wynikiStat[2]);
                zapisWyniku.close();
                zapisStat.close();


        }
        catch (Exception e){
            System.out.println(e);
            return;
    }
			

			
		
	}

}
