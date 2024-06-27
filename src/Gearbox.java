public class Gearbox {
    private int currentGear;

    public Gearbox() {
        this.currentGear = 0;
    }

    public int getCurrentGear() {
        return currentGear;
    }

    public void setGear(int gear) {
        if (gear >= 0 && gear <= 5) {
            this.currentGear = gear;
        }
    }
}
