package battleship;
import battleship.Board;

public class ShowBoard 
{
    public static void showBoards(int[][] boardPlayer, int[][] boardAI)
    {
        //System.out.println("");
        
        System.out.println("\t1 \t2 \t3 \t4 \t5 \t6 \t7 \t8");
        System.out.println();
        
        for(int row = 0; row < Board.getSize() ; row++)
        {
            System.out.print((row + 1) + "");
            for(int column = 0; column < Board.getSize(); column++ )
            {
                if(boardAI[row][column] == -1)
                {
                    System.out.print("\t"+"~");
                }
                else if(boardAI[row][column] == 0)
                {
                    System.out.print("\t"+"~");
                }
                else if(boardAI[row][column] == 1)
                {
                    System.out.print("\t"+"X");
                }
            }
            System.out.println(); 
        }
        
        System.out.println("\t1 \t2 \t3 \t4 \t5 \t6 \t7 \t8");
        System.out.println();
        
        for(int row = 0; row < Board.getSize() ; row++)
        {
            System.out.print((row + 1) + "");
            for(int column = 0; column < Board.getSize(); column++ )
            {
                if(boardPlayer[row][column] == -1)
                {
                    System.out.print("\t"+"~");
                }
                else if(boardPlayer[row][column] == 0)
                {
                    System.out.print("\t"+"*");
                }
                else if(boardPlayer[row][column] == 1)
                {
                    System.out.print("\t"+"X");
                }
            }
            System.out.println(); 
        }
    }
}
