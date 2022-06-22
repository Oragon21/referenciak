using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;

namespace ModernLangToolsApp
{
    //deleg�lt t�pus
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
            CouncilChanged?.Invoke("�j taggal b�v�lt�nk");
        }

        public void Remove()
        {
            // Elt�vol�tja a lista utols� elem�t
            members.RemoveAt(members.Count - 1);
            if (CouncilChanged != null)
            {
                if (members.Count > 0)
                    CouncilChanged("Zavart �rzek az er�ben");
                else
                    CouncilChanged("A tan�cs elesett!");
            }
        }

        //megkeress�k az �sszes fiatalt
        public List<Jedi> GetUnwortyJedis_Delegate()
        {
            return members.FindAll(IsJediUnworty);
        }

        //seg�df�ggv�ny ami megkeresi 600 alattiakat
        private bool IsJediUnworty(Jedi jedi)
        {
            return jedi.MidiChlorianCount < 600;
        }

        //lambd�s keres�s
        public List<Jedi> GetKindOfUnwortyJedis_Lambda()
        {
            return members.FindAll((jedi) => jedi.MidiChlorianCount < 1000);
        }
    }
}