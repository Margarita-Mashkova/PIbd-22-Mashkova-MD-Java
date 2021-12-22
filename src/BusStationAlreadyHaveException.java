public class BusStationAlreadyHaveException extends Exception{
    public BusStationAlreadyHaveException(){
        super("На автовокзале уже есть такой автобус");
    }
}
