import javax.swing.*;

public class Dashboard {
    private JLabel gearLabel;
    private JLabel statusLabel;
    private JLabel speedLabel;
    private JLabel rpmLabel;
    private JProgressBar fuelLevelProgressBar;
    private JProgressBar oilLevelProgressBar;

    public Dashboard() {
        // Initialize labels and progress bars
        gearLabel = new JLabel("Bieg: 0");
        statusLabel = new JLabel("Silnik: Wyłączony");
        speedLabel = new JLabel("Prędkość: 0 km/h");
        rpmLabel = new JLabel("Obroty: 0 x100rpm");
        fuelLevelProgressBar = new JProgressBar(0, 100);
        oilLevelProgressBar = new JProgressBar(0, 100);
    }

    public void update(Car car) {
        gearLabel.setText("Bieg: " + car.getGearbox().getCurrentGear());
        statusLabel.setText("Silnik: " + (car.getEngine().isOn() ? "Włączony" : "Wyłączony"));
        speedLabel.setText("Prędkość: " + car.getSpeed() + " km/h");
        rpmLabel.setText("Obroty: " + car.getEngine().getRpm() / 100 + " x100rpm");
        fuelLevelProgressBar.setValue((int) car.getFuelTank().getFuelLevel());
        oilLevelProgressBar.setValue((int) car.getOilTank().getOilLevel());
    }
}
