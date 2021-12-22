import java.awt.*;
import java.util.Iterator;

public class Autobus extends Vehicle implements Comparable<Autobus>,Iterable<Object>{
    private int carWidth = 190;/// Ширина отрисовки автобуса
    private int carHeight = 70;/// Высота отрисовки автобуса
    protected final String separator = ";"; /// Разделитель для записи информации по объекту в файл
    public Autobus(int maxSpeed, float weight, Color mainColor)
    {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
    }
    protected Autobus(int maxSpeed, float weight, Color mainColor, int carWidth, int carHeight){
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
        this.carWidth = carWidth;
        this.carHeight = carHeight;
    }
    /// Конструктор для загрузки с файла
    public Autobus(String info)
    {
        String[] strs = info.split(separator);
        if (strs.length == 3)
        {
            MaxSpeed = Integer.parseInt(strs[0]);
            Weight = Float.parseFloat(strs[1]);
            MainColor = Color.decode(strs[2]);
        }
    }
    //Изменение направления пермещения
    @Override
    public void MoveTransport(Directions direction)
    {
        float step = MaxSpeed * 100 / Weight;
        switch (direction)
        {
            // вправо
            case Right:
                if (_startPosX + step < _pictureWidth - carWidth)
                {
                    _startPosX += step;
                }
                break;
            //влево
            case Left:
                if (_startPosX - step > 0)
                {
                    _startPosX -= step;
                }
                break;
            //вверх
            case Up:
                if (_startPosY - step > 0)
                {
                    _startPosY -= step;
                }
                break;
            //вниз
            case Down:
                if (_startPosY + step < _pictureHeight - carHeight)
                {
                    _startPosY += step;
                }
                break;
        }
    }
    //Отрисовка автомобиля
    @Override
    public void DrawTransport(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setStroke(new BasicStroke(2));
        //Корпус1
        g.setColor(MainColor);
        g.drawRect(_startPosX, _startPosY, 190, 60);
        //Дверь
        g.fillRect(_startPosX + 73, _startPosY + 25, 20, 35);
        //Окна
        g.drawOval(_startPosX + 5, _startPosY + 10, 18, 28); //первое
        g.drawOval(_startPosX + 12 + 17, _startPosY + 10, 18, 28); //второе
        for (int n = 5; n < (5 + 18) * 4; n += 5 + 18) {
            g.drawOval(_startPosX + 5 + 18 + 17 + 50 + n, _startPosY + 10, 18, 28);
        }
        //Колёса
        g.setColor(MainColor);
        g.fillOval(_startPosX + 20, _startPosY + 50, 25, 25); //заливка
        g.setColor(MainColor);
        g.drawOval(_startPosX + 20, _startPosY + 50, 25, 25); //заднее
        g.setColor(MainColor);
        g.fillOval(_startPosX + 190 - 20 - 25, _startPosY + 50, 25, 25);//заливка
        g.setColor(MainColor);
        g.drawOval(_startPosX + 190 - 20 - 25, _startPosY + 50, 25, 25);//переднее
    }
    @Override
    public String toString()
    {
        return MaxSpeed+separator+Weight+separator+MainColor.getRGB();
    }

    @Override
    public int compareTo(Autobus other) {
        if (other == null) {
            return 1;
        }
        if (MaxSpeed != other.MaxSpeed) {
            return Integer.valueOf(MaxSpeed).compareTo(other.MaxSpeed);
        }
        if (Weight != other.Weight) {
            return Float.valueOf(Weight).compareTo(other.Weight);
        }
        if (MainColor.getRGB() != other.MainColor.getRGB()) {
            return Integer.valueOf(MainColor.getRGB()).compareTo(other.MainColor.getRGB());
        }
        return 0;
    }
    /// Метод интерфейса для класса Autobus
    public boolean equals(Autobus other)
    {
        if (other == null)
        {
            return false;
        }
        if(!getClass().getSimpleName().equals(other.getClass().getSimpleName()))
        {
            return false;
        }
        if (MaxSpeed != other.MaxSpeed)
        {
            return false;
        }
        if (Weight != other.Weight)
        {
            return false;
        }
        if (MainColor.getRGB() != other.MainColor.getRGB())
        {
            return false;
        }
        return true;
    }
    /// Перегрузка метода от object
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Autobus))
        {
            return false;
        }
        else {
            Autobus autobus = (Autobus) obj;
            return equals(autobus);
        }
    }
    @Override
    public Iterator<Object> iterator() {
        Iterator<Object> iterator = new Iterator<Object>() {

            private int current = -1;
            private int allProperties = 2;

            @Override
            public boolean hasNext() {
                current++;
                return current<=allProperties;
            }

            @Override
            public String next() {
                if(current==0){
                    return Integer.toString(MaxSpeed);
                }
                else if(current==1){
                    return Float.toString(Weight);
                }
                return Integer.toString(MainColor.getRGB());
            }
        };
        return iterator;
    }
}
