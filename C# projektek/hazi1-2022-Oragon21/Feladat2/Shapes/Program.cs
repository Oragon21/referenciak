using System;

namespace Shapes
{
    class Program
    {
        static void Main(string[] args)
        {
            ShapeTarolo shapeTarolo = new ShapeTarolo();
            shapeTarolo.add(new Circle(6, 10, 4));
            shapeTarolo.add(new Square(3, 15, 5));
            shapeTarolo.add(new TextArea(2, 4, 3, 9));

            shapeTarolo.add(new Circle(10, 20, 30));
            shapeTarolo.add(new Square(6, 30, 9));
            shapeTarolo.add(new TextArea(9, 9, 9, 9));

            shapeTarolo.listElements();
            Console.ReadKey();
        }
    }
}
