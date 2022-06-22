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
            //Új InputDialog nyitása
            InputDialog dlg = new InputDialog();

           

            //Ok gomb megnyomva
            if (dlg.ShowDialog() == DialogResult.OK)
            {
                //ListView kiürítése
                listView1.Items.Clear();

                //try
                //{
                    //Mappa megnyitása
                    DirectoryInfo di = new DirectoryInfo(dlg.Path);

                    //Mappa fájljainak listázása
                    foreach (FileInfo fi in di.GetFiles())
                    {
                        listView1.Items.Add(new ListViewItem(new String[] { fi.Name, fi.Length.ToString(), fi.FullName }));
                    }

               // }
               /* catch (Exception ex)
                {
                    //Kivétel kiírása
                   // MessageBox.Show(ex.ToString(), "Error");
                }*/
            }
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            //Ha nincs kiválasztva semmi, akkor visszater
            if (listView1.SelectedItems.Count != 1) return;

            //Maga az kivalasztott elem
            ListViewItem selected = listView1.SelectedItems[0];

            //Név
            lName.Text = selected.SubItems[0].Text;

            //Datum
            lCreated.Text = File.GetCreationTime(selected.SubItems[2].Text).ToString();

        }

        //Dupla kattintás
        private void listView1_DoubleClick(object sender, EventArgs e)
        {
            //Hibakezelés: pl. nincs jogunk a fájl olvasására
            //try
           // {
                //Fájl elérésenek utja
                string filePath = listView1.SelectedItems[0].SubItems[2].Text;

                //Fájl tartalma
                tContent.Text = File.ReadAllText(filePath);

                //Fájl adatai
                

                reloadTimer.Start();
                counter = counterInitialValue;
                loadedFile = new FileInfo(filePath);
        
                

            //}
            /*catch (Exception ex)
            {
                //Keletkezett kivétel közlése a felhasználóval
               // MessageBox.Show(ex.ToString(), "Error");
            }*/
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            counter--;
            // Fontos! Ez váltja ki a Paint eseményt és ezzel a
            // a téglalap újrarajzolását
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

            // A téglalap szélessége a téglalap kezdõhosszúságából (adott
            // a feladatkiírásban) számítható,
            // szorozva a számláló aktuális és max értékének arányával 
        }
    }
}