using System;

namespace Shapes
{
    abstract class ShapeBase : IShape
    {
        protected int x;
        protected int y;

        protected ShapeBase(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public abstract string getType();
        public abstract int getArea();
        public int getX()
        {
            return x;
        }
        public int getY()
        {
            return y;
        }
    }

    class Square : ShapeBase
    {
        private int length;

        public Square(int x, int y, int a) : base(x, y)
        {
            length = a;
        }

        public override int getArea()
        {
            return length * length;
        }

        public override string getType()
        {
            return "Square";
        }
    }

    class Circle : ShapeBase
    {
        private int r;

        public Circle(int x, int y, int radius) : base(x, y)
        {
            this.r = radius;
        }

        public override int getArea()
        {
            return (int)(3.14 * r * r);
        }

        public override string getType()
        {
            return "Circle";
        }
    }

    class TextArea : Controls.Textbox, IShape
    {
        public TextArea(int x, int y, int m, int sz) : base(x, y, m, sz)
        { }
        public int getX()
        {
            return GetX();
        }

        public int getY()
        {
            return GetY();
        }

        public int getArea()
        {
            return GetWidth() * GetHeight();
        }

        public string getType()
        {
            return "TextArea";
        }
        
    }



}

