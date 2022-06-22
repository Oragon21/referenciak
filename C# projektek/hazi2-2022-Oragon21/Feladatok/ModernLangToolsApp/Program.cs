using System;
using System.ComponentModel;
using System.Collections.Generic;

namespace ModernLangToolsApp
{
    class Program
    {
        static void MessageReceived(string message)
        {
            //kiírjuk a kapott sort
            Console.WriteLine(message);
        }

        public static void fill(JediCouncil jc)
        {
            //adjuk hozzá a jediket a council-hoz
            jc.Add(new Jedi("Anakin", 4200000));
            jc.Add(new Jedi("Obi-wan", 69000));
            jc.Add(new Jedi("Rheee", 4000));
            jc.Add(new Jedi("Sanyi", 500));
            jc.Add(new Jedi("Peti", 900));
        }

        static void Main(string[] args)
        {
            //hozzunk létre egy tanácsot
            JediCouncil council = new JediCouncil();

            //tesztek
            TestFeladat3();
            TestGetUnwortyJedis(council);
            TestGetKindOfUnwortyJedis(council);

          
            Console.ReadKey();
        }

        //teszt 3-ashoz
        [Description("Feladat3")]
        public static void TestFeladat3()
        {
            //hozzunk létre egy tanácsot
            JediCouncil council2 = new JediCouncil();
            //iratkozzunk fel a függvénnyel2
            council2.CouncilChanged += MessageReceived;

            //tanácsfeltöltés
            fill(council2);

            //eltávolítjuk a jediket
            council2.Remove();
            council2.Remove();
            council2.Remove();

        }


        //teszt 4-eshez
        [Description("Feladat4")]
        public static void TestGetUnwortyJedis(JediCouncil council)
        {
            System.Console.WriteLine("Feladat4");
            foreach (var jedi in council.GetUnwortyJedis_Delegate())
            {
                System.Console.WriteLine(jedi.Name);
            }
            System.Console.WriteLine();
        }

        //teszt 5-öshöz
        [Description("Feladat5")]
        public static void TestGetKindOfUnwortyJedis(JediCouncil council)
        {
            System.Console.WriteLine("Feladat5");
            foreach (var jedi in council.GetKindOfUnwortyJedis_Lambda())
            {
                System.Console.WriteLine(jedi.Name);
            }
            System.Console.WriteLine();
        }

        

    }
}