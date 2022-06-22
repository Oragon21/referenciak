using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Signals
{
    public class SignalDocument : Document
    {
        private List<SignalValue> signals = new List<SignalValue>();
        public IReadOnlyList<SignalValue> Signals
        {
            get { return signals; }
        }
        private SignalValue[] testValues = new SignalValue[]
            {
                 new SignalValue(110, new DateTime(2022, 1, 1, 0, 0, 0, 50)),
        new SignalValue(130, new DateTime(2022, 1, 1, 0, 0, 1, 250)),
        new SignalValue(170, new DateTime(2022, 1, 1, 0, 0, 3, 150)),
        new SignalValue(150, new DateTime(2022, 1, 1, 0, 0, 8, 200)),
        new SignalValue(125, new DateTime(2022, 1, 1, 0, 0, 6, 100)),
        new SignalValue(190, new DateTime(2022, 1, 1, 0, 0, 7, 300)),
        new SignalValue(250, new DateTime(2022, 1, 1, 0, 0, 9, 270)),
        new SignalValue(420, new DateTime(2022, 1, 1, 0, 0, 11, 0))
            };


        public SignalDocument(string name) : base(name)
        {
            // Kezdetben dolgozzunk úgy, hogy a signals
            // jelérték listát a testValues alapján inicializáljuk.
            signals.AddRange(testValues);
        }

        public override void SaveDocument(string filePath)
        {
            using (StreamWriter sw = new StreamWriter(filePath))
            {
                foreach (SignalValue v in signals)
                {
                    string dt = v.TimeStamp.ToUniversalTime().ToString("o");
                    sw.WriteLine(v.Value + "\t" + dt + '\n');
                }

            }
        }

        public override void LoadDocument(string filePath)
        {
            signals.Clear();
            using (StreamReader sr = new StreamReader(filePath))
            {
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    if (line.Trim() != "") //ha ures kihagyjuk
                    {
                        string[] columns = line.Split('\t');
                        double val = double.Parse(columns[0]);
                        DateTime time = DateTime.Parse(columns[1]);
                        DateTime rtime = time.ToLocalTime();
                        signals.Add(new SignalValue(val, rtime));
                    }

                }
            }
            TraceValues();
            UpdateAllViews();
        }

        void TraceValues()
        {
            foreach (SignalValue signal in signals)
                Trace.WriteLine(signal.ToString());
        }
    }
}