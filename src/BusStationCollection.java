import java.util.HashMap;
import java.util.Map;
import java.util.Map;
import java.util.Set;
public class BusStationCollection {
    private final Map<String, BusStation<ITransport, DoorInterface>> busStationStages; /// Словарь (хранилище) с автовокзалами
    private final int pictureWidth;  /// Ширина окна отрисовки
    private final int pictureHeight; /// Высота окна отрисовки
    /// Конструктор
    public BusStationCollection(int pictureWidth, int pictureHeight)
    {
        busStationStages = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }
    /// Возвращение списка названий автовокзалов
    public Set<String> keys() {
        return busStationStages.keySet();
    }
    /// Добавление автовокзала
    public void AddBusStation(String name)
    {
        if(!busStationStages.containsKey(name))
        {
            busStationStages.put(name, new BusStation<ITransport, DoorInterface>(pictureWidth, pictureHeight));
        }
    }
    /// Удаление автовокзала
    public void DelBusStation(String name)
    {
        if (busStationStages.containsKey(name))
        {
            busStationStages.remove(name);
        }
    }
    /// Доступ к автовокзалу (индексатор)
    public BusStation<ITransport, DoorInterface> get(String ind)
    {
        if (busStationStages.containsKey(ind)) {
            return busStationStages.get(ind);
        }
        return null;
    }
    // Индексатор, возвращающий элемент словаря и элемент параметризованного класса
    public ITransport get(String name, int index) {
        if (busStationStages.containsKey(name)) {
            return busStationStages.get(name).get(index);
        }
        return null;
    }
}
