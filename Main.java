import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUIStarter());
    }
    static class GUIStarter implements Runnable {
        @Override
        public void run() {
            HotelGUI gui = new HotelGUI();
            gui.setVisible(true);
        }
    }
}
