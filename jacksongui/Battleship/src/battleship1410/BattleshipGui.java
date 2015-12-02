package battleship1410;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.border.EmptyBorder;

public class BattleshipGui extends JPanel {
	private static final long serialVersionUID = 1L;	
	
	private static final int cellSize = 40;
	private BattleshipGrid playerGrid;
	private BattleshipGrid opponentGrid;
	
	public BattleshipGui() throws IOException{
		
		String waterPicString = "water.jpg";
		BufferedImage waterPic = ImageIO.read(getClass().getResource(waterPicString));
		
		playerGrid = new BattleshipGrid(waterPic, cellSize);
		opponentGrid = new BattleshipGrid(waterPic, cellSize);
		
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setBackground(Color.BLACK);
		setLayout(new BorderLayout(10, 10));
		
		JPanel playerPanel = new JPanel();
		playerPanel.setForeground(Color.BLUE);
		playerPanel.setBackground(new Color(30, 144, 255));
		add(playerPanel, BorderLayout.WEST);
		playerPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblPlayer = new JLabel("Player");
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer.setFont(new Font("Tahoma", Font.BOLD, 12));
		playerPanel.add(lblPlayer, BorderLayout.NORTH);
		
		playerGrid.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerPanel.add(playerGrid, BorderLayout.CENTER);
		playerGrid.setLayout(new GridLayout(11, 11, 0, 0));
		
		JPanel opponentPanel = new JPanel();
		opponentPanel.setBackground(Color.RED);
		add(opponentPanel, BorderLayout.EAST);
		opponentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblOpponent = new JLabel("Opponent");
		lblOpponent.setHorizontalAlignment(SwingConstants.CENTER);
		lblOpponent.setFont(new Font("Tahoma", Font.BOLD, 12));
		opponentPanel.add(lblOpponent, BorderLayout.NORTH);
		
		opponentGrid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		opponentGrid.setBorder(new LineBorder(new Color(0, 0, 0)));
		opponentPanel.add(opponentGrid, BorderLayout.CENTER);
		opponentGrid.setLayout(new GridLayout(11, 11, 0, 0));
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		
		JPanel centerTopPanel = new JPanel();
		centerPanel.add(centerTopPanel);
		centerTopPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Cell Pressed");
		centerTopPanel.add(lblNewLabel, BorderLayout.NORTH);
		
		JTextArea textarea = new JTextArea();
		textarea.setRows(10);
		textarea.setFocusable(false);
		textarea.setColumns(30);
		textarea.setEditable(false);
		centerTopPanel.add(textarea, BorderLayout.CENTER);
		
		playerGrid.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pChngEvt) {
				if (pChngEvt.getPropertyName().equals(BattleshipGrid.MOUSE_PRESSED)) {
					textarea.append("Player: " + pChngEvt.getNewValue() + "\n");
					playerGrid.resetSelections();
				}
			}
		});
		
		opponentGrid.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pChngEvt){
				if (pChngEvt.getPropertyName().equals(
					BattleshipGrid.MOUSE_PRESSED)) {
						textarea.append("Opponent: " + pChngEvt.getNewValue() + "\n");
						opponentGrid.resetSelections();
				}
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(BattleshipGui.class.getResource("/battleship1410/battle1.jpg")));
		add(lblNewLabel_1, BorderLayout.NORTH);
	}
	
}