import javax.swing.*;
import java.awt.*;

public class SplashScreen {
        public static void Loading()
        {
                JWindow window = new JWindow();
                window.add(new JLabel("", new ImageIcon("gifloading.gif"), SwingConstants.CENTER));
                window.setSize(289, 164);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
                window.setLocation(x, y);
                window.setVisible(true);
                try {
                        Thread.sleep(2000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                window.setVisible(false);
        }
}
