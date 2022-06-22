namespace MultiThreadedApp
{
    public partial class Form1 : Form
    {
        //ManualResetEvent
        private ManualResetEvent manualWait = new ManualResetEvent(false);
        //AutoResetEvent
        private AutoResetEvent autoWait = new AutoResetEvent(false);
        //l�p�ssz�ml�l�
        private long pixelCount = 0;
        private object sync = new object();
        public Form1()
        {
            InitializeComponent();
        }

        //l�p�ssz�m n�vel�se
        void increasePixels(long step)
        {
            lock (sync)
            {
                pixelCount += step;
            }
        }
        //l�p�ssz�m lek�r�se
        long getPixels()
        {
            lock (sync)
            {
                return pixelCount;
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        public void BikeThreadFunction(object param)
        {
            try
            {
                Button bike = (Button)param;
                bike.Tag = Thread.CurrentThread;
                //a biciklik a pStart panelig fognak elmenni legels�re

                while (bike.Left < pStart.Left)
                {
                    MoveBike(bike);
                    Thread.Sleep(100);
                    if (bike.Left >= pStart.Left)
                        manualWait.WaitOne();
                }

                //ezut�n a pRest panelig fognak versenyezni a biciklik
                while (bike.Left < pRest.Left)
                {
                    MoveBike(bike);
                    Thread.Sleep(100);
                    if (bike.Left >= pRest.Left)
                        autoWait.WaitOne();
                }

                while (bike.Left < pTarget.Left)
                {
                    MoveBike(bike);
                    Thread.Sleep(100);
                }
            }
            catch (ThreadInterruptedException)
            {
                // Lenyelj�k, de szigor�an kiz�r�lag a ThreadInterruptedException-t.
                // Ha nem kezeln�nk az Interrupt hat�s�ra a sz�llf�ggv�ny�nk
                // �s az alkalmaz�sunk is cs�ny�n "elsz�llna".

            }
        }

        delegate void BikeAction(Button bike);
        Random random = new Random();
        public void MoveBike(Button bike)
        {
            if (InvokeRequired)
            {
                Invoke(new BikeAction(MoveBike), bike);
            }
            else
            {
                int randomStep = random.Next(3, 9);
                bike.Left += randomStep;
                increasePixels(randomStep);
            }

        }

        private void bStart_Click(object sender, EventArgs e)
        {
            startBike(bBike1);
            startBike(bBike2);
            startBike(bBike3);
        }
        private void startBike(Button bBike)
        {
            Thread t = new Thread(BikeThreadFunction);
            bBike.Tag = t;
            t.IsBackground = true; // Ne blokkolja a sz�l a processz megsz�n�s�t
            t.Start(bBike);
        }

        private void bStep1_Click(object sender, EventArgs e)
        {
            manualWait.Set();
            manualWait.Reset();
        }
        private void bike_Click(object sender, EventArgs e)
        {
            Button bike = (Button)sender;
            Thread thread = (Thread)bike.Tag;
            // Ha m�g nem ind�tottuk ez a sz�lat, ez null.
            if (thread == null)
                return;
            // Megszak�tjuk a sz�l v�rakoz�s�t, ez az adott sz�lban egy
            // ThreadInterruptedException-t fog kiv�ltani
            // A f�ggv�ny le�r�s�r�l r�szleteket az el�ad�s anyagaiban tal�lsz
            thread.Interrupt();
            // Megv�rjuk, am�g a sz�l le�ll
            thread.Join();

            manualWait.Reset();
            autoWait.Reset();

            bike.Left = 14;
            startBike(bike);
        }
        private void bStep2_Click(object sender, EventArgs e)
        {
            autoWait.Set();
            autoWait.Reset();
        }

        //lepesszam megjelenitese
        private void bPixelCount_Click(object sender, EventArgs e)
        {
            bPixelCount.Text = getPixels().ToString();
        }
    }

}