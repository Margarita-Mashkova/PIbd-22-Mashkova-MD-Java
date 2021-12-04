import javax.swing.*;
import java.awt.*;
public class BusStation<T extends ITransport, V extends DoorInterface> {
    private final T[] _places; /// Массив объектов, которые храним
    private final int pictureWidth;/// Ширина окна отрисовки
    private final int pictureHeight;/// Высота окна отрисовки
    private final int _placeSizeWidth = 210 + 230 + 12;/// Размер парковочного места (ширина)
    private final int _placeSizeHeight = 100;/// Размер парковочного места (высота)

    /// Конструктор
    public BusStation(int picWidth, int picHeight) {
        int width = picWidth / _placeSizeWidth;
        int height = picHeight / _placeSizeHeight;
        _places = (T[]) new ITransport[width * height];
        pictureWidth = picWidth;
        pictureHeight = picHeight;
    }
    /// Логика действия: на парковку добавляется автобус
    public int add(T bus) {
        for (int i = 0; i < _places.length; i++) {
            if (_places[i] == null) {
                _places[i] = bus;
                _places[i].SetPosition(10 + i % 3 * _placeSizeWidth, i / 3 * _placeSizeHeight + 15, pictureWidth, pictureHeight);
                return i;
            }
        }
        return -1;
    }
    /// Логика действия: с парковки забираем автобус
    public T takeAutobus(int index) {
        if ((index < _places.length) && (index >= 0)) {
            if(_places[index] != null){
            T temp = _places[index];
            _places[index] = null;
            return temp;
            }
            else {
                JOptionPane.showMessageDialog(null, "Парковочное место пустое!");
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Такого места на автовокзале не существует!");
            return null;
        }
    }

    // Перегрузка оператора ==
    public boolean equals(BusStation<T,V> busOne, BusStation<T,V> busTwo){
        busOne = new BusStation<>(pictureWidth, pictureHeight);
        busTwo = new BusStation<>(pictureWidth, pictureHeight);
        int busOneCounts = busOne._places.length;
        int busOneEmpty = 0;
        for(int i = 0; i < busOneCounts; i++){
            if(busOne._places[i] == null) {
                busOneEmpty++;
            }
        }
        int busTwoCounts = busTwo._places.length;
        int busTwoEmpty = 0;
        for(int i = 0; i < busTwoCounts; i++){
            if(busTwo._places[i] == null) {
                busTwoEmpty++;
            }
        }
        return busOneEmpty == busTwoEmpty;
    }
    // Перегрузка оператора !=
    public boolean notEquals(BusStation<T,V> busOne, BusStation<T,V> busTwo){
        return !equals(busOne, busTwo);
    }

    /// Метод отрисовки автобусов на парковке
    public void Draw(Graphics g) {
        DrawMarking(g);
        for (int i = 0; i < _places.length; i++) {
            if (_places[i] != null) {
                _places[i].DrawTransport(g);
            }
        }
    }
    /// Метод отрисовки разметки парковочных мест
    private void DrawMarking(Graphics g) {
        g.setColor(Color.black);
        for (int i = 0; i < pictureWidth / _placeSizeWidth; i++) {
            for (int j = 0; j < pictureHeight / _placeSizeHeight + 1; ++j) {//линия рамзетки места
                g.drawLine(5 + i * _placeSizeWidth, 5 + j * _placeSizeHeight, 5+ + i * _placeSizeWidth + _placeSizeWidth / 2, 5 + j * _placeSizeHeight);
            }
            g.drawLine(5 + i * _placeSizeWidth, 5, 5 + i * _placeSizeWidth, 5 + (pictureHeight / _placeSizeHeight) * _placeSizeHeight);
        }
    }
}

