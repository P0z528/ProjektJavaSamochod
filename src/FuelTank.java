public class FuelTank {
    private double fuelLevel;

    public FuelTank() {
        this.fuelLevel = 100;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void consumeFuel(double amount) {
        fuelLevel = Math.max(0, fuelLevel - amount);
    }
}
