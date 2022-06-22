using System;
using System.Collections.Generic;
using System.IO;

namespace MusicApp
{
    class Program
    {
        static void Main(string[] args)
        {
            // Ebben tároljuk a dal objektumokat
            List<Song> songs = new List<Song>();
           
            // Fájl beolvasása soronként, songs lista feltöltése
            StreamReader sr = null;
            try
            {
                // A @ jelentése a string konstans előtt: kikapcsolja
                // a string escape-elést, így nem kell a '\' helyett '\\'-t írni.
                sr = new StreamReader(@"c:\temp\music.txt");
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    // A line változóban benne van az egész sor,
                    // a Split-tel a ;-k mentén feldaraboljuk
                    string[] lineItems = line.Split(';');

                    // Ha üres volt a sor
                    if (lineItems.Length == 0)
                        continue;
                    // Első elem, amiben az szerző nevét várjuk
                    // A Trim eltávolítja a vezető és záró whitespace karaktereket
                    string artist = lineItems[0].Trim();
                    // Menjünk végig a dalokon, és vegyük fel a listába
                    for (int i = 1; i < lineItems.Length; i++)
                    {
                        Song song = new Song(artist, lineItems[i].Trim());
                        songs.Add(song);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("A fájl feldolgozása sikertelen.");
                // Az e.Message csak a kivétel szövegét tartalmazza.
                // Ha minden kivétel információt ki szeretnénk írni (pl. stack trace),
                // akkor az e.ToString()-et írjuk ki.
                Console.WriteLine(e.Message);
            }
            finally
            {
                // Lényeges, hogy finally blokkban zárjuk le a fájlt,
                // hogy egy esetleges kivétel esetén se maradjon mögöttünk
                // lezáratlan állomány.
                // try-finally helyett használhattunk volna using blokkot,
                // azt egyelőre nem kell tudni (a félév derekán tanuljuk).
                if (sr != null)
                    sr.Close();
            }
            // A songs lista elemeinek kiírása a konzolra
            foreach (Song song in songs)
                Console.WriteLine(song.ToString());
        }

    }
}

