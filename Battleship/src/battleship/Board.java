package battleship;

//import static battleship.Battleship.size;


public class Board 
{
    private static int size = 8;
    private int[][] board = new int[size][size];
    //private int[][] boardB = new int[size][size];
    
    public static int getSize()
    {
        return size;
    }
    
    Board()
    {
        int[][] board = this.board;
        setupBoard(board);
    }
    
    public int[][] getBoard()
    {
        return board;
    }
    
    
    public static void setupBoard(int[][] board)
    {
        for(int row = 0; row < size; row++)
        {
            for(int column = 0; column < size; column++)
            {
                board[row][column] = -1;
            }
        }
    }
    //private int[][] shipsA = new int[size][size];
    //private int[][] shipsB = new int[size][size];
}
