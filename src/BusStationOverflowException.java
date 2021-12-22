public class BusStationOverflowException extends Exception{
    public BusStationOverflowException(){
        super("На автовокзале нет свободных мест");
    }
}
