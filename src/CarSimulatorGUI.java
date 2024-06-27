import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

public class CarSimulatorGUI extends JFrame {
    private JButton AccelerateButton;
    private JButton BrakeButton;
    private JButton UpGearButton;
    private JButton DownGearButton;
    private JButton StartButton;
    private JButton StopButton;
    private JProgressBar FuelLevelBar;
    private JProgressBar OilLevelBar;
    private JLabel GearLabel;
    private JLabel Status;
    private JLabel SpeedLabel;
    private JLabel RpmLabel;
    private JPanel panel1;
    private JButton AddFuelButton;
    private JButton AddOilButton;
    private JButton EngineResetButton;
    private Car car;
    private CarController carController;
    private Timer timer;

    public CarSimulatorGUI() {
        setTitle("Symulator Samochodu");
        setSize(1000, 400);
        this.setContentPane(this.panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        car = new Car(new Dashboard());
        carController = new CarController(car, this);

        AccelerateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                carController.accelerate(1);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                carController.accelerate(1);
            }
        });

        BrakeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                carController.brake(1);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                carController.brake(1);
            }
        });

        UpGearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carController.increaseGear();
            }
        });

        DownGearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carController.decreaseGear();
            }
        });

        StartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carController.startEngine();
            }
        });

        StopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carController.stopEngine();
            }
        });


        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDashboard();
            }
        });
        timer.start();

        updateDashboard();
    }

    public void updateDashboard() {
        GearLabel.setText("Bieg: " + car.getGearbox().getCurrentGear());
        Status.setText("Status: " + (car.getEngine().isSeized() ? "Zatarty" : (car.getEngine().isOn() ? "Włączony" : "Wyłączony")));
        SpeedLabel.setText("Prędkość: " + car.getSpeed() + " km/h");
        RpmLabel.setText("Obroty: " + car.getEngine().getRpm() / 100 + " x100rpm");
        FuelLevelBar.setValue((int) car.getFuelTank().getFuelLevel());
        OilLevelBar.setValue((int) car.getOilTank().getOilLevel());
    }

}
