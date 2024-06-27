import java.util.Timer;
import java.util.TimerTask;

public class Car {
    private Engine engine;
    private Gearbox gearbox;
    private FuelTank fuelTank;
    private OilTank oilTank;
    private int speed;
    private int rpm;
    private Dashboard dashboard;
    private Timer timer;
    private Timer speedAdjustmentTimer;
    private Timer decelerationTimer;

    public Car(Dashboard dashboard) {
        this.engine = new Engine();
        this.gearbox = new Gearbox();
        this.fuelTank = new FuelTank();
        this.oilTank = new OilTank();
        this.speed = 0;
        this.dashboard = dashboard;
        this.timer = new Timer(true);
        this.speedAdjustmentTimer = new Timer(true);
        this.decelerationTimer = new Timer(true);
        startConsumption();
    }

    public Engine getEngine() {
        return engine;
    }

    public Gearbox getGearbox() {
        return gearbox;
    }

    public FuelTank getFuelTank() {
        return fuelTank;
    }

    public OilTank getOilTank() {
        return oilTank;
    }

    public int getSpeed() {
        return speed;
    }

    public void increaseGear() {
        if (engine.isOn() && fuelTank.getFuelLevel() > 0 && !engine.isSeized()) {
            changeGear(gearbox.getCurrentGear() + 1);
        }
    }

    public void decreaseGear() {
        changeGear(gearbox.getCurrentGear() - 1);
    }

    public void accelerate(int increase) {
        if (engine.isOn() && fuelTank.getFuelLevel() > 0 && !engine.isSeized()) {
            int newSpeed = speed + increase;
            int maxSpeed = getMaxSpeedForCurrentGear();
            if (newSpeed <= maxSpeed) {
                speed = newSpeed;
                int rpm = calculateRpmForSpeed(speed);
                engine.setRpm(rpm);
                updateDashboard();
            }
        }
    }

    public void brake(int decrease) {
        speed = Math.max(0, speed - decrease);
        int rpm = calculateRpmForSpeed(speed);
        engine.setRpm(rpm);
        updateDashboard();
    }

    public void changeGear(int gear) {
        int previousGear = gearbox.getCurrentGear();
        gearbox.setGear(gear);
        if (gear < previousGear && speed > getMaxSpeedForCurrentGear()) {
            adjustSpeedForGear();
        }
        updateDashboard();
    }

    public void startEngine(){
        if (fuelTank.getFuelLevel() > 0 && !engine.isSeized()) {
            engine.start();
        }

    }
    public void stopEngine() {
        engine.stop();
        startDeceleration();
        updateDashboard();
    }

    public void updateDashboard() {
        dashboard.update(this);
    }

    private int getMaxSpeedForCurrentGear() {
        switch (gearbox.getCurrentGear()) {
            case 1:
                return 40;
            case 2:
                return 70;
            case 3:
                return 100;
            case 4:
                return 150;
            case 5:
                return 190;
            default:
                return 0;
        }
    }

    private void adjustSpeedForGear() {
        // Cancel any existing speed adjustment task
        if (speedAdjustmentTimer != null) {
            speedAdjustmentTimer.cancel();
            speedAdjustmentTimer = new Timer(true);
        }

        // Schedule a new speed adjustment task
        speedAdjustmentTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int maxSpeed = getMaxSpeedForCurrentGear();
                if (speed > maxSpeed) {
                    speed -= 1; // Gradually decrease the speed
                    int rpm = calculateRpmForSpeed(speed);
                    engine.setRpm(rpm);
                    updateDashboard();
                } else {
                    cancel(); // Stop the timer when the speed is adjusted
                }
            }
        }, 0, 100); // Update every 0.1 seconds
    }

    public void startDeceleration() {
        // Cancel any existing deceleration task
        if (decelerationTimer != null) {
            decelerationTimer.cancel();
            decelerationTimer = new Timer(true);
        }

        // Schedule a new deceleration task
        decelerationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (speed > 0) {
                    speed -= 1; // Gradually decrease the speed
                    if (gearbox.getCurrentGear() >= 0 && speed < getMaxSpeedForCurrentGear()) {
                        decreaseGear();
                        rpm = calculateRpmForSpeed(speed);
                        engine.setRpm(rpm);
                    }
                    updateDashboard();
                } else {
                    rpm = 0;
                    updateDashboard();
                    cancel(); // Stop the timer when the speed reaches 0
                }
            }
        }, 0, 100); // Update every 0.1 seconds
    }

    private int calculateRpmForSpeed(int speed) {
        int maxSpeed = getMaxSpeedForCurrentGear();
        if (gearbox.getCurrentGear() == 0) {
            return 0 + (6000 * speed / 40);
        }
        return 1000 + (6000 * speed / maxSpeed);
    }
    private double getFuelConsumptionRate() {
        int rpm = engine.getRpm();
        if (rpm > 4000) {
            return 2 + (rpm - 4000) / 1000.0 * 0.2;
        }
        else if(rpm == 1000){
            return 0.5;
        }
        else {
            return 1;
        }
    }

    private double getOilConsumptionRate() {
        int rpm = engine.getRpm();
        if (rpm > 4000) {
            return 0.8 + (rpm - 4000) / 1000.0 * 0.2;
        } else {
            return 0.1;
        }
    }
    private void startConsumption() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (engine.isOn() && !engine.isSeized()) {

                    double fuelConsumptionRate = getFuelConsumptionRate();
                    fuelTank.consumeFuel(fuelConsumptionRate);

                    double oilConsumptionRate = getOilConsumptionRate();
                    oilTank.consumeOil(oilConsumptionRate);

                    if (oilTank.getOilLevel() <= 0) {
                        engine.seize();
                        engine.setRpm(0);
                        startDeceleration();
                    }

                    if(fuelTank.getFuelLevel() <= 0) {
                        engine.setRpm(0);
                        engine.isOff();
                        startDeceleration();
                    }

                    updateDashboard();
                }
            }
        }, 0, 1000); // Update every 1 second
    }
}
