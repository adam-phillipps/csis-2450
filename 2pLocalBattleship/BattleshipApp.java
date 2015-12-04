package battleship;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BattleshipApp extends JFrame
{
	private static final long serialVersionUID = 5486142986342119175L;
	private JPanel contentPane;
	private Logger logger;

	// Game state variables:
	private Boolean isSetup = true;
	private Boolean gameOver = false;
	private int activePlayer = 1;
	private ArrayList<Ship> p1Navy = new ArrayList<>();
	private ArrayList<Ship> p2Navy = new ArrayList<>();
	private ArrayList<Shot> p1Shots = new ArrayList<>();
	private ArrayList<Shot> p2Shots = new ArrayList<>();
	
	private JPanel gameArea;
	private JPanel topPanel;
	private JLabel lblPlayer;

	private Coordinate shipPlacementStart;
	private ShipType shipBeingPlaced;
	
	//sound effects
	private Clip ShotClip;
	private Clip musicClip;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try
				{
					BattleshipApp frame = new BattleshipApp();
					frame.setVisible(true);
					frame.setTitle("Battleship");
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BattleshipApp()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 900, 600);
		createContentPane();
		
		//Play looped game music
		playMusic();
		ShotClip = initAttackSound();
		
		// setup logging to file
		logger = setupLogger();
		logger.info("Game starting...");

		createTopPanel();
		createLblPlayer(1);
		createLblPlayer(2);

		//Create Game Area and add score boards to the game area
		createGameArea();
		drawGameArea();
	}
	
	private void playMusic()
	{
	    musicClip = null;
		try(AudioInputStream sound = AudioSystem.getAudioInputStream(this.getClass().getResource("sound/BattleHymnoftheRepublic.wav"));)
		{
			//Load the sound into memory (a Clip)
		    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
		    musicClip = (Clip) AudioSystem.getLine(info);
		    musicClip.open(sound);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
			System.out.println("Unsupported Audio Type Format.");
		}
	    //Loop the audio file
	    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
	    FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
	        double gain = .25D; // number between 0 and 1 (loudest)
	        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
	        gainControl.setValue(dB);
	}
	
	private Clip initAttackSound()
	{
	    Clip clip = null;
		try(AudioInputStream sound = AudioSystem.getAudioInputStream(this.getClass().getResource("sound/ShotFired.wav"));)
		{
			//Load the sound into memory (a Clip)
		    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(sound);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
			System.out.println("Unsupported Audio Type Format.");
		}
		
	    //Play the clip
	    return clip;
	}
	
	private void drawGameArea() {
		
		Component[] comps = gameArea.getComponents();
		for(Component c : comps){
			gameArea.remove(c);
		}
		gameArea.add(setupScoreboard(1));
		gameArea.add(setupScoreboard(2));
		gameArea.add(setupGameBoard(1));
		gameArea.add(setupGameBoard(2));
		
		gameArea.validate();
		gameArea.repaint();
		
		if (getNavy(activePlayer).size() == 0 && isSetup && shipBeingPlaced == null)
			JOptionPane.showMessageDialog(null, "Player" + activePlayer + ": Set up your ships!");
	}

	private void createContentPane()
	{
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private JPanel createTopPanel()
	{
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0, 2, 0, 0));
		contentPane.add(topPanel, BorderLayout.NORTH);
		return topPanel;
	}

	private void createGameArea()
	{
		gameArea = new JPanel();
		gameArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		gameArea.setLayout(new GridLayout(0, 2, 0, 0));
		contentPane.add(gameArea, BorderLayout.CENTER);
	}

	private void createLblPlayer(int p)
	{
		lblPlayer = new JLabel("Player " + p);
		lblPlayer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		topPanel.add(lblPlayer);
	}

	private ArrayList<Ship> getNavy(int player)
	{
		return (player == 1) ? p1Navy : p2Navy;
	}

	private ArrayList<Shot> getShots(int player)
	{
		return (player == 1) ? p1Shots : p2Shots;
	}

	private int getOpposingPlayer(){
		return activePlayer == 1 ? 2 : 1;
	}
	
	private void switchPlayers()
	{
		activePlayer = getOpposingPlayer();
	}

	private JPanel setupGameBoard(int player)
	{
		JPanel board = new JPanel();
		board.setBorder(new LineBorder(new Color(0, 0, 0)));
		board.setLayout(new GridLayout(0, 10, 0, 0));
		
		for (int x = 0; x < 10; x++)
		{
			for (int y = 0; y < 10; y++)
			{
				Coordinate btnLocation = new Coordinate(x, y);
				JButton gridButton = new JButton("");
				gridButton.addActionListener(dispatchClick);
				gridButton.setActionCommand(player + "|" + x + "|" + y);
				if (isSetup && activePlayer == player){
					for(Ship s : getNavy(player)){
						if(s.checkPoint(btnLocation)){
							gridButton.setBackground(Color.GREEN);
						}
					}
					if(shipPlacementStart != null && shipPlacementStart.equals(btnLocation))
						gridButton.setBackground(Color.GREEN);
				}
				if (!isSetup)
				{
					for(Shot shot : getShots(player)){
						if(shot.getShotLocation().equals(btnLocation)){
							if(shot.getClass() == Hit.class || shot.getClass() == Sink.class){
								gridButton.setBackground(Color.RED);
							}
							if(shot.getClass() == Miss.class){
								gridButton.setBackground(Color.BLUE);
							}
							gridButton.removeActionListener(dispatchClick);
						}
					}
				}
				board.add(gridButton);
			}
		}
		return board;
	}

	// dispatch function for clicks on the game board
	private ActionListener dispatchClick = new ActionListener() {
		public void actionPerformed(ActionEvent e)
		{
			if(gameOver) return;
			String[] location = e.getActionCommand().split("|");
			if (Integer.parseInt(location[0]) != activePlayer)
			{
				JOptionPane.showMessageDialog(null, "It is not your turn!");
				return;
			}
			Coordinate point = new Coordinate(Integer.parseInt(location[2]),
					Integer.parseInt(location[4]));
			// if performing setup
			if (isSetup)
			{
				doShipPlacement(point);
			}
			// else we are in play mode
			else
			{
				fire(point);
			}
			drawGameArea();
		}

		private void doShipPlacement(Coordinate point)
		{
			if (shipBeingPlaced == null)
				return;
			if (shipPlacementStart == null)
			{
				shipPlacementStart = point;
				return;
			}
			// second click falls through to here.
			
			if (shipPlacementStart.equals(point))
				return;
			if (shipPlacementStart.x != point.x
					&& shipPlacementStart.y != point.y)
				return;

			List<Coordinate> points = new ArrayList<>();
			int xDirection = shipPlacementStart.x > point.x ? -1 : 1;
			int yDirection = shipPlacementStart.y > point.y ? -1 : 1;
			int x = shipPlacementStart.x;
			int y = shipPlacementStart.y;
			while (x != point.x || y != point.y)
			{
				points.add(new Coordinate(x, y));
				if (x != point.x)
					x += xDirection;
				if (y != point.y)
					y += yDirection;
			}
			points.add(point); //add final point
			if (points.size() != shipBeingPlaced.getSize())
				return;
			//delete any ships that already exist because they are being replaced
			for(Ship s : getNavy(activePlayer)){
				if(s.getType() == shipBeingPlaced){
					getNavy(activePlayer).remove(s);
					break;
				}
			}

			for(Ship s : getNavy(activePlayer))//check ship placement collision
				for(Coordinate p : points)
					if(s.checkPoint(p)) return;
			
			getNavy(activePlayer).add(new Ship(points, shipBeingPlaced));
			shipPlacementStart = null;
			shipBeingPlaced = null;
			if (getNavy(activePlayer).size() == 5){
				switchPlayers();
			}
			if (p1Navy.size() == 5 && p2Navy.size() == 5){
				isSetup = false;
			}
		}

		private void fire(Coordinate point)
		{
			ShotClip.setMicrosecondPosition(0);
			ShotClip.start();
			Shot type = null;
			for(Ship s : getNavy(getOpposingPlayer())){
				type = s.receiveShot(point);
				if(type != null) {
					//hitClip.start();
					break;
				}
			}
			if(type==null) type = new Miss(point);
			logger.info("Player " + activePlayer + " fires at " + point.toString() + ".  Result is " + type.getClass().getSimpleName());
			if(type.getClass() == Sink.class){
				JOptionPane.showMessageDialog(null, "You sunk your opponents " + type.getShipAffected().getType().getName());
				logger.info("Player " + activePlayer + " has sunk the opponent's " + type.getShipAffected().getType().getName());
				int liveShips = 0;
				for(Ship s :getNavy(getOpposingPlayer())){
					if(s.checkAlive()) liveShips++;
				}
				if(liveShips == 0){
					JOptionPane.showMessageDialog(null, "CONGRATULATIONS player " + activePlayer + "\nYou have defeated your enemy's navy!");
					logger.info("Player " + activePlayer + " has won the game");
					gameOver = true;
				}
			}
			getShots(activePlayer).add(type);
			switchPlayers();
		}
	};

	// Setup Score Board to show killed ships
	private JPanel setupScoreboard(int player)
	{
		JPanel scoreBoard = new JPanel();
		scoreBoard.setBorder(new LineBorder(new Color(0, 0, 0)));
		scoreBoard.setLayout(new GridLayout(5, 0, 0, 0));

		if(player == activePlayer && isSetup){
			for (ShipType st : ShipType.values()){
				JButton ship = new JButton("");
				ship.setBackground(Color.WHITE);
				ship.setOpaque(true);
				ship.setIcon(st.getIcon());
				
				for(Ship s : getNavy(activePlayer))
					if(s.getType() == st)
						ship.setBackground(Color.GREEN);
				if(st == shipBeingPlaced && activePlayer == player){
					ship.setBackground(Color.YELLOW);
				}
				
				ship.setActionCommand(player +"");
				ship.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						shipBeingPlaced = st;
						shipPlacementStart = null;
						drawGameArea();
					}
				});
				scoreBoard.add(ship);
			}
		}
		else { //during play, use labels rather than buttons
			for(Ship s : getNavy(player)){
				JLabel ship = new JLabel("");
				ship.setBackground(s.checkAlive()?Color.GREEN:Color.RED);
				ship.setHorizontalAlignment(SwingConstants.CENTER);
				ship.setOpaque(true);
				ship.setIcon(s.getType().getIcon());
				scoreBoard.add(ship);
			}
		}
		return scoreBoard;
	}

	// file logging setup
	private Logger setupLogger()
	{
		final Logger logger = Logger.getLogger(Logger.class.getName());
		FileHandler handler;
		logger.setLevel(Level.INFO);
		try
		{
			// log file will be local to the .jar file location
			handler = new FileHandler("gamelog.txt", 8096, 1, true);
			logger.addHandler(handler);
			handler.setFormatter(new GameLogFormatter());
		} catch (SecurityException | IOException e)
		{
			System.out.println("File does not exist in this location, check the gamelog path and try again.");
		}
		return logger;
	}
}