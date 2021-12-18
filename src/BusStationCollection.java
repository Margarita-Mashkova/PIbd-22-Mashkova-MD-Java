import com.sun.media.sound.InvalidDataException;

import java.io.*;
import java.security.KeyException;
import java.util.*;

public class BusStationCollection {
    private final Map<String, BusStation<ITransport, DoorInterface>> busStationStages; /// Словарь (хранилище) с автовокзалами
    private final int pictureWidth;  /// Ширина окна отрисовки
    private final int pictureHeight; /// Высота окна отрисовки
    private final String separator = ":"; /// Разделитель для записи информации в файл

    /// Конструктор
    public BusStationCollection(int pictureWidth, int pictureHeight) {
        busStationStages = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    /// Возвращение списка названий автовокзалов
    public Set<String> keys() {
        return busStationStages.keySet();
    }

    /// Добавление автовокзала
    public void AddBusStation(String name) {
        if (!busStationStages.containsKey(name)) {
            busStationStages.put(name, new BusStation<ITransport, DoorInterface>(pictureWidth, pictureHeight));
        }
    }

    /// Удаление автовокзала
    public void DelBusStation(String name) {
        if (busStationStages.containsKey(name)) {
            busStationStages.remove(name);
        }
    }

    /// Доступ к автовокзалу (индексатор)
    public BusStation<ITransport, DoorInterface> get(String ind) {
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

    /// Сохранение информации по автомбусам на автовокзалах в файл
    public void SaveData(String filename) throws IOException{
        if (!filename.contains(".txt")) {
            filename += ".txt";
        }
        try (FileWriter fileWriter = new FileWriter(filename, false)) {
            fileWriter.write("BusStationCollection\n");
            for (Map.Entry<String, BusStation<ITransport, DoorInterface>> level : busStationStages.entrySet()) {
                //Начинаем парковку
                fileWriter.write("BusStation" + separator + level.getKey() + '\n');
                ITransport bus;
                for (int i = 0; (bus = level.getValue().get(i)) != null; i++) {
                    //Записываем тип машины
                    if (bus.getClass().getSimpleName().equals("Autobus")) {
                        fileWriter.write("Autobus" + separator);
                    } else if (bus.getClass().getSimpleName().equals("AutobusModern")) {
                        fileWriter.write("AutobusModern" + separator);
                    }
                    //Записываемые параметры
                    fileWriter.write(bus.toString() + '\n');
                }
            }
        }
    }

    /// Загрузка нформации по автобусам на автовокзалах из файла
    public void LoadData(String filename) throws IOException, BusStationOverflowException{
        if (!(new File(filename).exists())) {
            throw new FileNotFoundException("Файл " + filename + " не найден");
        }
        try (FileReader fileReader = new FileReader(filename)) {
            Scanner sc = new Scanner(fileReader);
            if (sc.nextLine().contains("BusStationCollection")) {
                //очищаем записи
                busStationStages.clear();
            } else {
                //если нет такой записи, то это не те данные
                throw new InvalidPropertiesFormatException("Неверный формат файла");
            }
            ITransport bus = null;
            String key = "";
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                //идем по считанным записям
                if (line.contains("BusStation")) {//начинаем новую парковку
                    key = line.split(separator)[1];
                    busStationStages.put(key, new BusStation<ITransport, DoorInterface>(pictureWidth, pictureHeight));
                }
                else if (line.contains(separator)) {
                    if (line.contains("Autobus:"))
                        bus = new Autobus(line.split(separator)[1]);
                    else if (line.contains("AutobusModern")) {
                        bus = new AutobusModern(line.split(separator)[1]);
                    }
                    if (busStationStages.get(key).add(bus) == -1) {
                        throw new InvalidDataException(); /////////////////!!!!!!!!!!!!!!!!!!
                    }
                }
            }
        }
    }
    // Сохранение отдельного автовокзала
    public void saveBusStation(String filename, String key) throws IOException, KeyException{
        if (!filename.contains(".txt")) {
            filename += ".txt";
        }
        if (!busStationStages.containsKey(key)) {
            throw new KeyException();
        }
        try (FileWriter fileWriter = new FileWriter(filename, false)) {
            if (busStationStages.containsKey(key))
                fileWriter.write("BusStation" + separator + key + '\n');
            ITransport bus;
            for (int i = 0; (bus = busStationStages.get(key).get(i)) != null; i++) {
                if (bus.getClass().getSimpleName().equals("Autobus")) {
                    fileWriter.write("Autobus" + separator);
                } else if (bus.getClass().getSimpleName().equals("AutobusModern")) {
                    fileWriter.write("AutobusModern" + separator);
                }
                fileWriter.write(bus.toString() + '\n');
            }
        }
    }
    // Загрузка отдельного автовокзала
    public void loadBusStation(String filename) throws IOException, BusStationOverflowException{
        try (FileReader fileReader = new FileReader(filename)) {
            Scanner scanner = new Scanner(fileReader);
            String key;
            String line;
            line = scanner.nextLine();
            if (line.contains("BusStation")) {
                key = line.split(separator)[1];
                if (busStationStages.containsKey(key)) {
                    busStationStages.get(key).clear();
                } else {
                    busStationStages.put(key, new BusStation<ITransport, DoorInterface>(pictureWidth, pictureHeight));
                }
            } else {
                throw new InvalidPropertiesFormatException("Неверный формат файла");
            }
            ITransport bus = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(separator)) {
                    if (line.contains("Autobus:")) {
                        bus = new Autobus(line.split(separator)[1]);
                    } else if (line.contains("AutobusModern")) {
                        bus = new AutobusModern(line.split(separator)[1]);
                    }
                    if (busStationStages.get(key).add(bus) == -1) {
                        throw new InvalidDataException(); /////////////////!!!!!!!!!!!!!!!!!!
                    }
                }
            }
        }
    }
}