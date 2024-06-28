import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JFrame {
    private JPanel panelWelcome;
    private JProgressBar progressBar1;
    private int width = 400, height = 300;

    public Welcome() {
        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setContentPane(panelWelcome);
        setVisible(true);

        Timer timer = new Timer(100, new ActionListener() {
            int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar1.setValue(counter);
                if (counter >= 100) {
                    ((Timer) e.getSource()).stop();
                    dispose();
                    openCarSimulator();
                }
                counter += 5;
            }
        });

        timer.start();
    }

    private void openCarSimulator() {
            CarSimulatorGUI carSimulatorGUI = new CarSimulatorGUI();
            carSimulatorGUI.setVisible(true);
    }
}
