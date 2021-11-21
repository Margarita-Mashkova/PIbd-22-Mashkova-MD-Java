import java.awt.*;

public class Autobus {
    private int _startPosX; /// Левая координата отрисовки автобуса
    private int _startPosY;/// Правая кооридната отрисовки автобуса
    private int _pictureWidth;/// Ширина окна отрисовки
    private int _pictureHeight;/// Высота окна отрисовки
    private final int carWidth = 440;/// Ширина отрисовки автобуса
    private final int carHeight = 65;/// Высота отрисовки автобуса
    public int MaxSpeed; /// Максимальная скорость
    public float Weight; /// Вес автобуса
    public Color MainColor;/// Основной цвет кузова
    public Color DopColor;/// Дополнительный цвет
    public boolean FirstVagon;/// Признак наличия первого вагона
    public boolean SecondVagon; /// Признак наличия второго вагона
    public boolean Garmoshka;/// Признак наличия гармошки
    private DoorDraw doorDraw;
    //Методы get и set
    private void setMaxSpeed(int maxSpeed){ this.MaxSpeed = maxSpeed; }
    public int getMaxSpeed(){ return MaxSpeed; }
    private void setWeight(float weight){ this.Weight = weight; }
    public float getWeight(){ return Weight; }
    private void setMainColor(Color mainColor){ this.MainColor = mainColor; }
    public Color getMainColor(){ return MainColor; }
    private void setDopColor(Color dopColor){ this.DopColor = dopColor; }
    public Color getDopColor(){ return DopColor; }
    private void setFirstVagon(boolean firstVagon){ this.FirstVagon = firstVagon; }
    public boolean getFirstVagon(){ return FirstVagon; }
    private void setSecondVagon(boolean secondVagon){ this.SecondVagon = secondVagon; }
    public boolean getSecondVagon(){ return SecondVagon; }
    private void setGarmoshka(boolean garmoshka){ this.Garmoshka = garmoshka; }
    public boolean getGarmoshka(){ return Garmoshka; }
    public void Init(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean firstVagon, boolean secondVagon, boolean garmoshka, int doorCount)
    {
        MaxSpeed = maxSpeed;
        Weight = weight;
        MainColor = mainColor;
        DopColor = dopColor;
        FirstVagon = firstVagon;
        SecondVagon = secondVagon;
        Garmoshka = garmoshka;
        doorDraw = new DoorDraw();
        doorDraw.SetDoorCount(doorCount);
    }
    //Установка позиции автомобиля
    public void SetPosition(int x, int y, int width, int height)
    {
        _startPosX = x;
        _startPosY = y;
        _pictureWidth = width;
        _pictureHeight = height;
    }
    //Изменение направления пермещения
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
    public void DrawTransport(Graphics gr)
    {
        Graphics2D g = (Graphics2D)gr;
        g.setStroke(new BasicStroke(2));
        if (FirstVagon)
        {
            //Корпус1
            g.setColor(MainColor);
            g.drawRect(_startPosX, _startPosY, 190, 60);
            //Дверь
            g.fillRect(_startPosX + 73, _startPosY + 25, 20, 35);
            //Окна
            g.drawOval(_startPosX + 5, _startPosY + 10, 18, 28); //первое
            g.drawOval(_startPosX + 12 + 17, _startPosY + 10, 18, 28); //второе
            for (int n = 5; n < (5 + 18) * 4; n += 5 + 18)
            {
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
        int lengthG = 30; //длина гармошки
        if (Garmoshka)
        {
            //Гармошка
            g.setColor(DopColor);
            g.drawRect(_startPosX + 190, _startPosY + 3, lengthG, 60 - 6);
            for (int n = 5; n < lengthG; n += 5)
            {
                g.drawLine(_startPosX + 190 + n, _startPosY + 3, _startPosX + 190 + n, _startPosY + 3 + 60 - 6);
            }
        }
        if (SecondVagon)
        {
            //Корпус2  //Смещаем первый вагон со всеми его элментами по Х на carWidth + lengthG
            g.drawRect(_startPosX + 190 + lengthG, _startPosY, 190+30, 60);
            //Дверь
            g.fillRect(_startPosX + 190 + lengthG + 73, _startPosY + 25, 20, 35);
            //Окна
            g.drawOval(_startPosX + 5 + 190 + lengthG, _startPosY + 10, 18, 28); //первое
            g.drawOval(_startPosX + 12 + 17 + 190 + lengthG, _startPosY + 10, 18, 28); //второе
            for (int n = 5; n < (5 + 18) * 4; n += 5 + 18)
            {
                g.drawOval(_startPosX + 5 + 18 + 17 + 50 + n + 190 + lengthG, _startPosY + 10, 18, 28);
            }
            //Колёса

            g.setColor(DopColor);
            g.fillOval(_startPosX + 20 + 190 + lengthG, _startPosY + 60 - 10, 25, 25); //заливка
            g.setColor(DopColor);
            g.drawOval(_startPosX + 20 + 190 + lengthG, _startPosY + 60 - 10, 25, 25); //заднее
            g.setColor(DopColor);
            g.fillOval(_startPosX + 190 - 20 - 25 + 190 + lengthG, _startPosY + 60 - 10, 25, 25);//заливка
            g.setColor(DopColor);
            g.drawOval(_startPosX + 190 - 20 - 25 + 190 + lengthG, _startPosY + 60 - 10, 25, 25);//переднее
        }
        doorDraw.DrawDoor(g, DopColor, _startPosX,_startPosY);
    }
}
