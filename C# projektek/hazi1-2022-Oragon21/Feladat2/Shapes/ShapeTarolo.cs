using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace Shapes
{
    class ShapeTarolo
    {
        private List<IShape> shapeLista;

        public ShapeTarolo()
        {
            shapeLista = new List<IShape>();
        }

        public void add(IShape sb)
        {
            shapeLista.Add(sb);
        }

        public void listElements()
        {
            foreach (IShape sb in shapeLista)
            {
                Console.WriteLine("Tipusa: {0}, koordinátái: ({1},{2}), területe: {3}", sb.getType(), sb.getX(), sb.getY(), sb.getArea());

            }
        }
    }
}

