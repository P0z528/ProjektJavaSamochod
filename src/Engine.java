public class Engine {
    private boolean on;
    private boolean seized;
    private int rpm;

    private int fuel;

    private int speed;
    private int oil;

    public Engine() {
        this.on = false;
        this.seized = false;
        this.rpm = 0;
        this.speed = 0;
        this.fuel = 100;
        this.oil = 100;
    }

    public boolean isOn() {
        return on;
    }



    public boolean isSeized() {
        return seized;
    }

    public int getRpm() {
        return rpm;
    }

    public void start() {
        if (fuel != 0 && !seized) {
            on = true;
            rpm = 1000;
        }

    }

    public void stop() {
        on = false;
        speed = 0;
    }

    public void seize() {
        seized = true;
        on = false;
        rpm = 0;
    }

    public void setRpm(int rpm) {
            this.rpm = rpm;
    }

    public void isOff(){
        on = false;
    }
}
