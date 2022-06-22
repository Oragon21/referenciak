namespace MultiThreadedApp
{
    public partial class Form1 : Form
    {
        //ManualResetEvent
        private ManualResetEvent manualWait = new ManualResetEvent(false);
        //AutoResetEvent
        private AutoResetEvent autoWait = new AutoResetEvent(false);
        //lépésszámláló
        private long pixelCount = 0;
        private object sync = new object();
        public Form1()
        {
            InitializeComponent();
        }

        //lépésszám növelése
        void increasePixels(long step)
        {
            lock (sync)
            {
                pixelCount += step;
            }
        }
        //lépésszám lekérése
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
                //a biciklik a pStart panelig fognak elmenni legelsõre

                while (bike.Left < pStart.Left)
                {
                    MoveBike(bike);
                    Thread.Sleep(100);
                    if (bike.Left >= pStart.Left)
                        manualWait.WaitOne();
                }

                //ezután a pRest panelig fognak versenyezni a biciklik
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
                // Lenyeljük, de szigorúan kizárólag a ThreadInterruptedException-t.
                // Ha nem kezelnénk az Interrupt hatására a szállfüggvényünk
                // és az alkalmazásunk is csúnyán "elszállna".

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
            t.IsBackground = true; // Ne blokkolja a szál a processz megszûnését
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
            // Ha még nem indítottuk ez a szálat, ez null.
            if (thread == null)
                return;
            // Megszakítjuk a szál várakozását, ez az adott szálban egy
            // ThreadInterruptedException-t fog kiváltani
            // A függvény leírásáról részleteket az elõadás anyagaiban találsz
            thread.Interrupt();
            // Megvárjuk, amíg a szál leáll
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