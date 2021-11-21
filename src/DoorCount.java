public enum DoorCount {
    Three,
    Four,
    Five;
    public static DoorCount getCount(int count) {
        switch (count) {
            case 3:
                return Three;
            case 4:
                return Four;
            case 5:
                return Five;
        }
        return null;
    }
}
