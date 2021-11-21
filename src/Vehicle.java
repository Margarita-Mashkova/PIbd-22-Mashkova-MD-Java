import java.awt.*;
public abstract class Vehicle implements ITransport{
    //Левая координата отрисовки автомобиля
    protected int _startPosX;
    //Правая кооридната отрисовки автомобиля
    protected int _startPosY;
    //Ширина окна отрисовки
    protected int _pictureWidth;
    //Высота окна отрисовки
    protected int _pictureHeight;
    //Максимальная скорость
    public int MaxSpeed;
    //Вес автомобиля
    public float Weight;
    //Основной цвет кузова
    public Color MainColor;
    public void SetPosition(int x, int y, int width, int height)
    {
        _startPosX = x;
        _startPosY = y;
        _pictureWidth = width;
        _pictureHeight = height;
    }
    public abstract void DrawTransport(Graphics g);
    public abstract void MoveTransport(Directions direction);
    private void setMaxSpeed(int maxSpeed){ this.MaxSpeed = maxSpeed; }
    public int getMaxSpeed(){ return MaxSpeed; }
    private void setWeight(float weight){ this.Weight = weight; }
    public float getWeight(){ return Weight; }
    private void setMainColor(Color mainColor){ this.MainColor = mainColor; }
    public Color getMainColor(){ return MainColor; }
}
