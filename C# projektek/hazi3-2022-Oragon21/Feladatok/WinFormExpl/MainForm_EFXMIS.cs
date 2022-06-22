using System;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace WinFormExpl
{
    public partial class MainForm_EFXMIS : Form
    {

        FileInfo loadedFile = null;
        int counter;
        readonly int counterInitialValue;

        public MainForm_EFXMIS()
        {
            InitializeComponent();
            counterInitialValue = 50; 
        }

       

        private void miExit_Click_1(object sender, EventArgs e)
        {
            //Bezaras
            Close();
        }

        private void miOpen_Click(object sender, EventArgs e)
        {
            //�j InputDialog nyit�sa
            InputDialog dlg = new InputDialog();

           

            //Ok gomb megnyomva
            if (dlg.ShowDialog() == DialogResult.OK)
            {
                //ListView ki�r�t�se
                listView1.Items.Clear();

                //try
                //{
                    //Mappa megnyit�sa
                    DirectoryInfo di = new DirectoryInfo(dlg.Path);

                    //Mappa f�jljainak list�z�sa
                    foreach (FileInfo fi in di.GetFiles())
                    {
                        listView1.Items.Add(new ListViewItem(new String[] { fi.Name, fi.Length.ToString(), fi.FullName }));
                    }

               // }
               /* catch (Exception ex)
                {
                    //Kiv�tel ki�r�sa
                   // MessageBox.Show(ex.ToString(), "Error");
                }*/
            }
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            //Ha nincs kiv�lasztva semmi, akkor visszater
            if (listView1.SelectedItems.Count != 1) return;

            //Maga az kivalasztott elem
            ListViewItem selected = listView1.SelectedItems[0];

            //N�v
            lName.Text = selected.SubItems[0].Text;

            //Datum
            lCreated.Text = File.GetCreationTime(selected.SubItems[2].Text).ToString();

        }

        //Dupla kattint�s
        private void listView1_DoubleClick(object sender, EventArgs e)
        {
            //Hibakezel�s: pl. nincs jogunk a f�jl olvas�s�ra
            //try
           // {
                //F�jl el�r�senek utja
                string filePath = listView1.SelectedItems[0].SubItems[2].Text;

                //F�jl tartalma
                tContent.Text = File.ReadAllText(filePath);

                //F�jl adatai
                

                reloadTimer.Start();
                counter = counterInitialValue;
                loadedFile = new FileInfo(filePath);
        
                

            //}
            /*catch (Exception ex)
            {
                //Keletkezett kiv�tel k�zl�se a felhaszn�l�val
               // MessageBox.Show(ex.ToString(), "Error");
            }*/
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            counter--;
            // Fontos! Ez v�ltja ki a Paint esem�nyt �s ezzel a
            // a t�glalap �jrarajzol�s�t
            detailsPanel.Invalidate();
            if (counter <= 0)
            {
                counter = counterInitialValue;
                tContent.Text = File.ReadAllText(loadedFile.FullName);
            }

        }

        private void detailsPanel_Paint(object sender, PaintEventArgs e)
        {
            if (loadedFile != null)
                e.Graphics.FillRectangle(Brushes.Brown, 0, 0, (counter*(120/(float)counterInitialValue)), 6);

            // A t�glalap sz�less�ge a t�glalap kezd�hossz�s�g�b�l (adott
            // a feladatki�r�sban) sz�m�that�,
            // szorozva a sz�ml�l� aktu�lis �s max �rt�k�nek ar�ny�val 
        }
    }
}