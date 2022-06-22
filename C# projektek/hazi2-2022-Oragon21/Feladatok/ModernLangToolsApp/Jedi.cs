using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Xml.Serialization;

namespace ModernLangToolsApp
{

    [XmlRoot("Jedi")]
    public class Jedi
    {
        //nev
        [XmlAttribute("Nev")]
        public string Name { get; set; }

        //privat attributom amit lehet allitani
        private int _midiChlorianCount;
        [XmlAttribute("MidiChlorianSzam")]
        public int MidiChlorianCount
        { //itt kérdezzük le a privát attribútumot
            get { return _midiChlorianCount; }
            //ne lehessen 30 vagy annal kisebb ertek, kivételt dob ha 30 vagy az alattira allitodna.
            set
            {
                if (value <= 30) throw new ArgumentException("You are not a true jedi!");
                else _midiChlorianCount = value;
            }
        }

        public Jedi(string name, int midiChlorianCount)
        {
            Name = name;
            MidiChlorianCount = midiChlorianCount;
        }
        

        [Description("Feladat2")]
        public Jedi Clone()
        {
            // kiírjuk az objektumunkat
            XmlSerializer serializer = new XmlSerializer(typeof(Jedi));
            FileStream fileStream = new FileStream("jedi.txt", FileMode.Create);
            serializer.Serialize(fileStream, this);
            fileStream.Close();

            XmlSerializer ser = new XmlSerializer(typeof(Jedi));
            FileStream fs = new FileStream("jedi.txt", FileMode.Open);
            Jedi clone = (Jedi)ser.Deserialize(fs);
            fs.Close();

            return clone;
        }
        
    }
}