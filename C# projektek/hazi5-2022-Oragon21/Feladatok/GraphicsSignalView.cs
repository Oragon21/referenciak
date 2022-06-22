using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Signals
{
    public partial class GraphicsSignalView : UserControl, IView
    {
        
        private float pixelPerSec = 0.000005f;

        private float pixelPerValue = 0.2f;

        private int blob = 3;

        private float zoom = 1f;

        private float zoomParam = 1.2f;


        /// A view sorszáma
        private int viewNumber;

        /// A view sorszáma
        public int ViewNumber
        {
            get { return viewNumber; }
            set { viewNumber = value; }
        }

        private SignalDocument document;


        public GraphicsSignalView(SignalDocument document)
        {
            InitializeComponent();
            this.document = document;
        }
        public GraphicsSignalView()
        {
            InitializeComponent();
        }

        /// A View interfész Update implementációja.
        public void Update()
        {
            Invalidate();
        }

        public Document GetDocument()
        {
            return document;
        }

        /// A UserControl.Paint felüldefiniálása, ebben rajzolunk.
        protected override void OnPaint(PaintEventArgs e)
        {
            ///Csúcsok (idő-Value értékek)
            List<PointF> points = new();
            ///Pen, melyet fel is paraméterezünk.
            Pen pen = new(Color.Red, 2);
            pen.DashStyle = DashStyle.Dot;
            pen.EndCap = LineCap.ArrowAnchor;

            ///Vízszintes tengely (idő)
            e.Graphics.DrawLine(pen, 0, ClientSize.Height / 2, ClientSize.Width, ClientSize.Height / 2);

            ///Függőleges tengely (Value)
            e.Graphics.DrawLine(pen, 2, ClientSize.Height, 2, 0);

            ///Legkissebb idő
            DateTime TimeZero = document.Signals[0].TimeStamp;

            ///Minden tárolt jelre:
            foreach (SignalValue signal in document.Signals)
            {
                TimeSpan Span = signal.TimeStamp - TimeZero;
                ///Átváltás Tick-be 
                float deltaT = Span.Ticks;

                ///X, azaz idő koordináta: 
                float X = deltaT * pixelPerSec * zoom;
                ///Y, azaz Value koordináta: 
                float Y = (float)signal.Value * pixelPerValue * zoom;

                e.Graphics.FillRectangle(
                    new SolidBrush(Color.Blue),
                    X,
                    ClientSize.Height / 2 - Y,
                    blob,
                    blob
                );
                ///listába eltároljuk a szakaszrajzoláshoz
                points.Add(new(X, ClientSize.Height / 2 - Y));
            }

            ///Szakaszok kirajzolása.
            for (int i = 0; i < document.Signals.Count - 1; i++)
            {
                e.Graphics.DrawLine(new Pen(Color.Red), points[i], points[i + 1]
                );
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ///Zoom-olunk
            zoom *= zoomParam;
            blob = (int)Math.Ceiling(blob * zoomParam);
            Invalidate();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            zoom /= zoomParam;
            blob = Math.Min(3, (int)Math.Ceiling(blob / zoomParam));
            Invalidate();
        }
    }
}

