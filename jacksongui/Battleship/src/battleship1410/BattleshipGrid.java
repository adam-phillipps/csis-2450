package battleship1410;

	import java.awt.Color;
	import java.awt.Component;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.Image;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.util.HashMap;
	import java.util.Map;
	import javax.swing.BorderFactory;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.SwingConstants;


	public class BattleshipGrid extends JPanel{
		private static final long serialVersionUID = 1L;
		
		
		public static final String MOUSE_PRESSED = "Mouse Pressed";
		private String selectedCell = "";
		private JLabel selectedLabel = null;
		private Image img = null;
		private static final int rowNum = 11;
		private static final int columnNum = 11;
	
/* HashMap is a Hash table based implementation of the Map interface. Discovered during memory game, 
and while researching how to build battleship*/
		private Map<String, JLabel> labelMap = new HashMap<String, JLabel>();
		
		public BattleshipGrid(Image img,int cellSize){
			this.img = img;
				
			MouseAdapter myMouseAdapter = new MyMouseAdapter();
		    addMouseListener(myMouseAdapter);
			
			for (int row = 0; row < rowNum; row++) {
		         for (int column = 0; column < columnNum; column++) {
		            String rowStr = String.valueOf((char) ('A' + row - 1));
		            String colStr = String.valueOf(column);
		            String name = ""; // name for component
		            String text = ""; // text to display
		 
		            // create JLabel to add to grid
		            JLabel label = new JLabel("", SwingConstants.CENTER);
		 
		            // text to display if label is a row header at col 0
		            if (column == 0 && row != 0) {
		               text = "" + rowStr;
		            } else
		 
		            // text to display if label is a col header at row 0
		            if (row == 0 && column != 0) {
		               text = "" + colStr;
		            } else
		 
		            // name to give to label if label is on the grid and not a
		            // row or col header
		            if (row != 0 && column != 0) {
		               name = rowStr + colStr;
		               labelMap.put(name, label);
		            }
		    	           
		            label.setText(text);
		            label.setName(name);
		            label.setPreferredSize(new Dimension(cellSize, cellSize));
		            label.setBorder(BorderFactory.createLineBorder(Color.black));
		            add(label);
		            
		         }
		        
			}		
		}
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			 if (img != null) {
		         int w0 = getWidth();
		         int h0 = getHeight();
		         int x = w0 / 11;
		         int y = h0 / 11;
		 
		         int iW = img.getWidth(null);
		         int iH = img.getHeight(null);
		 
		         g.drawImage(img, x, y, w0, h0, 0, 0, iW, iH, null);
			 }
		}	
		 private class MyMouseAdapter extends MouseAdapter {
		      @Override
		      public void mousePressed(MouseEvent mEvt) {
		         if (mEvt.getButton() == MouseEvent.BUTTON1) {
		            Component comp = getComponentAt(mEvt.getPoint());
		            if (comp instanceof JLabel) {
		               String name = comp.getName();
		               if (!name.trim().isEmpty()) {
		                  String oldValue = selectedCell;
		                  String newValue = name;
		                  selectedCell = name;
		                  selectedLabel = (JLabel) comp;
		                  firePropertyChange(MOUSE_PRESSED, oldValue, newValue);
		               }
		            }
		         }
		      }
		   }
		public String getSelectedCell() {
			return selectedCell;
		}
		public JLabel getSelectedLabel() {
			return selectedLabel;
		}
		public void resetSelections(){
			selectedCell = "";
			selectedLabel = null;
		}
		public void reset(){
			resetSelections();
			for (String key: labelMap.keySet()){
				labelMap.get(key).setIcon(null);
			}
		}
	}
