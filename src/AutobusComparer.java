import java.util.Comparator;

public class AutobusComparer implements Comparator<Vehicle> {
    public int compare(Vehicle x, Vehicle y) {
        if ((x instanceof Autobus && !(x instanceof AutobusModern)) && y instanceof AutobusModern) {
            return 1;
        }
        else if (x instanceof AutobusModern && (y instanceof Autobus && !(y instanceof AutobusModern))) {
            return -1;
        }
        else if (x instanceof AutobusModern && y instanceof AutobusModern) {
            return ComparerAutobusModern((AutobusModern) x, (AutobusModern) y);
        }
        else {
            return ComparerAutobus((Autobus) x, (Autobus) y);
        }
    }

    private int ComparerAutobus(Autobus x, Autobus y) {
        if (x.MaxSpeed != y.MaxSpeed) {
            return Integer.compare(x.MaxSpeed, y.MaxSpeed);
        }
        if (x.Weight != y.Weight) {
            return Float.compare(x.Weight, y.Weight);
        }
        if (x.MainColor.getRGB() != y.MainColor.getRGB()) {
            return Integer.compare(x.MainColor.getRGB(), y.MainColor.getRGB());
        }
        return 0;
    }

    private int ComparerAutobusModern(AutobusModern x, AutobusModern y) {
        int res = ComparerAutobus(x, y);
        if (res != 0) {
            return res;
        }
        if (x.DopColor.getRGB() != y.DopColor.getRGB()) {
            return Integer.compare(x.DopColor.getRGB(), y.DopColor.getRGB());
        }
        if (x.SecondVagon != y.SecondVagon) {
            return Boolean.compare(x.SecondVagon, y.SecondVagon);
        }
        if (x.Garmoshka != y.Garmoshka) {
            return Boolean.compare(x.Garmoshka, y.Garmoshka);
        }
        int xReal = 0;
        int yReal = 0;

        if (x.door instanceof DoorRealRect) {
            xReal = 1;
        }
        else if (x.door instanceof DoorRealEllipse) {
            xReal = 2;
        }
        else if (x.door instanceof DoorRealSquare) {
            xReal = 3;
        }

        if (y.door instanceof DoorRealRect) {
            yReal = 1;
        }
        else if (y.door instanceof DoorRealEllipse) {
            yReal = 2;
        }
        else if (y.door instanceof DoorRealSquare) {
            yReal = 3;
        }

        if (xReal != yReal) {
            return Integer.compare(xReal, yReal);
        }

        if(x.DoorNumber != y.DoorNumber){
            return Integer.compare(x.DoorNumber, y.DoorNumber);
        }

        return 0;
    }
}