package battleship1410;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class BattleshipApp {
	private static void gui(){
		BattleshipGui mainPanel;
			try {
				mainPanel = new BattleshipGui();
				JFrame frame = new JFrame("Battleship");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(mainPanel);
				frame.pack();
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
				} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	public static void main(String[] args) {
		BattleshipGame game = new BattleshipGame();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui();
				//game.setUp();
			}
		});
		game.setUp();
	}
}
