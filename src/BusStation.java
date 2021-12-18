import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class BusStation<T extends ITransport, V extends DoorInterface> {
    private final List<T> _places; /// Массив объектов, которые храним
    private final int _maxCount; /// Максимальное количество мест на парковке автовокзала
    private final int pictureWidth;/// Ширина окна отрисовки
    private final int pictureHeight;/// Высота окна отрисовки
    private final int _placeSizeWidth = 210 + 230 + 12;/// Размер парковочного места (ширина)
    private final int _placeSizeHeight = 100;/// Размер парковочного места (высота)
    /// Конструктор
    public BusStation(int picWidth, int picHeight) {
        int width = picWidth / _placeSizeWidth;
        int height = picHeight / _placeSizeHeight;
        _maxCount = width * height;
        _places = new ArrayList<>();
        pictureWidth = picWidth;
        pictureHeight = picHeight;
    }
    /// Логика действия: на парковку добавляется автобус
    public int add(T bus) throws BusStationOverflowException {
        if (_places.size() == _maxCount)
        {
            throw new BusStationOverflowException();
        }
        else
        {
            _places.add(bus);
            return _places.size() - 1;
        }
    }
    /// Логика действия: с парковки забираем автобус
    public T takeAutobus(int index) throws BusStationNotFoundException {
        if ((index < _places.size()) && (index >= 0)) {
            T temp = _places.get(index);
            _places.remove(index);
            return temp;
        }
        else {
           throw new BusStationNotFoundException(index);
        }
    }
    // Перегрузка оператора ==
    public boolean equals(BusStation<T,V> busOne, BusStation<T,V> busTwo){
        busOne = new BusStation<>(pictureWidth, pictureHeight);
        busTwo = new BusStation<>(pictureWidth, pictureHeight);
        int busOneCounts = busOne._places.size();
        int busOneEmpty = 0;
        for(int i = 0; i < busOneCounts; i++){
            if(busOne._places.get(i) == null) {
                busOneEmpty++;
            }
        }
        int busTwoCounts = busTwo._places.size();
        int busTwoEmpty = 0;
        for(int i = 0; i < busTwoCounts; i++){
            if(busTwo._places.get(i) == null) {
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
        for (int i = 0; i < _places.size(); i++) {
            _places.get(i).SetPosition(8 + i % 3 * _placeSizeWidth, i / 3 * _placeSizeHeight + 15, pictureWidth, pictureHeight);
            _places.get(i).DrawTransport(g);
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
    /// Индексатор для получения элемента из списка
    public T get(int index) {
        if ((index > -1) && (index < _places.size())) {
            return _places.get(index);
        }
        return null;
    }
    public void clear() {
        _places.clear();
    }
}

