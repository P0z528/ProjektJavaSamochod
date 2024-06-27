public class OilTank {
    private double oilLevel;

    public OilTank() {
        this.oilLevel = 100;
    }

    public double getOilLevel() {
        return oilLevel;
    }

    public void consumeOil(double amount) {
        oilLevel = Math.max(0, oilLevel - amount);
    }

}
