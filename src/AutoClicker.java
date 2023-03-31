import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AutoClicker implements KeyListener {
    private boolean isRunning;
    private boolean hasStarted;
    private int waitTimeLimit = 10; // in seconds
    private JFrame frame;
    private Color offColor = new Color(220, 47, 2);
    private Color onColor = new Color(153, 217, 140);

    public AutoClicker(){
        isRunning = false;
        hasStarted = false;
        frame = new JFrame("AutoClicker");
        frame.setSize(300, 200);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        // Labels
        JLabel autoClickerLabel = new JLabel("Autoclicker is off", JLabel.CENTER);
        autoClickerLabel.setForeground(Color.BLACK);
        autoClickerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel hotKeyTipLabel = new JLabel("Press Shift + Alt + 8 to toggle the autoclicker.", JLabel.CENTER);
        hotKeyTipLabel.setForeground(Color.BLACK);
        hotKeyTipLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // JLabel countdownLabel = new JLabel("", JLabel.CENTER);
        // countdownLabel.setForeground(Color.BLACK);
        // countdownLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add to panel / frame
        panel.add(autoClickerLabel, BorderLayout.CENTER);
        panel.add(hotKeyTipLabel, BorderLayout.SOUTH);
        // panel.add(countdownLabel, BorderLayout.NORTH);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        
        frame.addKeyListener(this);
        frame.getContentPane().getComponent(0).setBackground(offColor);  // Default color on startup
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the location of the JFrame to the center of the screen on startup
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws Exception {
       new AutoClicker().run();
    }

    public void run(){
        try{
            Robot robot = new Robot();
            int button = InputEvent.BUTTON1_DOWN_MASK;

            while(true){
                if(isRunning){
                    // Used for startup so robot doesn't instantly start clicking
                    if(hasStarted){
                        clickerWait(waitTimeLimit);
                        hasStarted = !hasStarted;
                    }
                    robot.mousePress(button);
                    Thread.sleep(100);
                    robot.mouseRelease(button); 
                    Thread.sleep(1000);
                }else{
                    clickerWait(1);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void clickerWait(int timeLimit){
        try{
            for(int i = 0; i < timeLimit; i++){
                Thread.sleep(1000);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Toggles the autoclicker on/off
        if (e.getKeyCode() == KeyEvent.VK_8 && e.isShiftDown() && e.isAltDown()) {
            isRunning = !isRunning;
            hasStarted = !hasStarted;

            // Background color changes
            frame.getContentPane().getComponent(0).setBackground(isRunning ? onColor : offColor);
    
            // Label changes
            JPanel panel = (JPanel) frame.getContentPane().getComponent(0); // Get the panel from the content pane
            JLabel autoClickerLabel = (JLabel) panel.getComponent(0); // Get the first component (the autoclicker toggle lable) from the content pane
            autoClickerLabel.setText(isRunning ? "Autoclicker is ON" : "Autoclicker is OFF");
        }
    }
        
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
