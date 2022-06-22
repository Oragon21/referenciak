using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;

namespace ModernLangToolsApp
{
    //delegált típus
    public delegate void CouncilChangedDelegate(string message);

    
    class JediCouncil
    {
        public event CouncilChangedDelegate CouncilChanged;

        //lista a jedikrol
        List<Jedi> members = new List<Jedi>();

        //Add fuggveny
        public void Add(Jedi newJedi)
        {
            members.Add(newJedi);
            CouncilChanged?.Invoke("Új taggal bõvültünk");
        }

        public void Remove()
        {
            // Eltávolítja a lista utolsó elemét
            members.RemoveAt(members.Count - 1);
            if (CouncilChanged != null)
            {
                if (members.Count > 0)
                    CouncilChanged("Zavart érzek az erõben");
                else
                    CouncilChanged("A tanács elesett!");
            }
        }

        //megkeressük az összes fiatalt
        public List<Jedi> GetUnwortyJedis_Delegate()
        {
            return members.FindAll(IsJediUnworty);
        }

        //segédfüggvény ami megkeresi 600 alattiakat
        private bool IsJediUnworty(Jedi jedi)
        {
            return jedi.MidiChlorianCount < 600;
        }

        //lambdás keresés
        public List<Jedi> GetKindOfUnwortyJedis_Lambda()
        {
            return members.FindAll((jedi) => jedi.MidiChlorianCount < 1000);
        }
    }
}