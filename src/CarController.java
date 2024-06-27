public class CarController {
    private Car car;
    private CarSimulatorGUI gui;

    public CarController(Car car, CarSimulatorGUI gui) {
        this.car = car;
        this.gui = gui;
    }

    public void accelerate(int increase) {
        car.accelerate(increase);
        gui.updateDashboard();
    }

    public void brake(int decrease) {
        car.brake(decrease);
        gui.updateDashboard();
    }

    public void increaseGear() {
        car.increaseGear();
        gui.updateDashboard();
    }

    public void decreaseGear() {
        car.decreaseGear();
        gui.updateDashboard();
    }

    public void startEngine() {
        car.startEngine();
        gui.updateDashboard();
    }

    public void stopEngine() {
        car.stopEngine();
        gui.updateDashboard();
    }
}
