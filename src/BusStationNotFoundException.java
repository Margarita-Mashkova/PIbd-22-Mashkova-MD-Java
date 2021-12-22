public class BusStationNotFoundException extends Exception{
    public BusStationNotFoundException(int i){
        super("Не найден автобус по месту " + i);
    }
}
