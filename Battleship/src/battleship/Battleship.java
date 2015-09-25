package battleship;
import java.util.Scanner;

public class Battleship 
{
    public static int size = 8;
    public int[][] boardA = new int[size][size];
    public int[][] shipsA = new int[size][size];
    public int[][] boardB = new int[size][size];
    public int[][] shipsB = new int[size][size];
    
    Battleship()
    {
        int[][] boardA = this.boardA;
        int[][] shipsA = this.shipsA;
        int[][] boardB = this.boardB;
        int[][] shipsB = this.shipsB;
    }

    public static void main(String[] args)
    {
        int[] shootP = new int[2];
        int[] shootAI = new int[2];
        
        
        Battleship game1 = new Battleship();
        
        
        setupBoard(game1.boardA, game1.boardB);
        showBoards(game1.boardA, game1.boardB);
    }
    
    public static void setupShipsAI(int[][] shipsB)
    {
        
    }
    
    public static void setupShipsPlayer(int[][] shipsA)
    {
        System.out.println("Choose");
    }
    
    public static void shootAI(int[] shootAI)
    {
        
    }
    
    public static void shootPlayer(int[] shootP)
    {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Row: ");
        shootP[0] = input.nextInt();
        shootP[0]--;
        
        System.out.print("Column: ");
        shootP[1] = input.nextInt();
        shootP[1]--;
        
        
        
    }
    
    public static int hitPlayer(int[] shootPlayer, int[][] shipsB)
    {
        int result = -1;
        
        for(int ship = 0 ; ship < shipsB.length ; ship++)
        {
            if( shootPlayer[0] == shipsB[ship][0] && shootPlayer[1] == shipsB[ship][1])
            {
                System.out.printf("You hit a ship located in (%d,%d)\n", shootPlayer[0] + 1, shootPlayer[1] + 1);
                //return true;
            }
        }
        
        return result;
    }
    
    public static int hitAI()
    {
        int result = -1;
        return result;
    }
    
    public static void showBoards(int[][] boardA, int[][] boardB)
    {
        //System.out.println("");
        
        System.out.println("\t1 \t2 \t3 \t4 \t5 \t6 \t7 \t8");
        System.out.println();
        
        for(int row=0 ; row < size ; row++ )
        {
            System.out.print((row+1)+"");
            for(int column=0 ; column < size ; column++ )
            {
                if(boardB[row][column]==-1)
                {
                    System.out.print("\t"+"~");
                }
                else if(boardB[row][column]==0)
                {
                    System.out.print("\t"+"*");
                }
                else if(boardB[row][column]==1)
                {
                    System.out.print("\t"+"X");
                }
                
            }
            System.out.println();
            
        }
        
        System.out.println();
        System.out.println("\t1 \t2 \t3 \t4 \t5 \t6 \t7 \t8");
        System.out.println();
        
        for(int row=0 ; row < size ; row++ )
        {
            System.out.print((row+1)+"");
            for(int column=0 ; column < size ; column++ )
            {
                if(boardA[row][column]==-1)
                {
                    System.out.print("\t"+"~");
                }
                else if(boardA[row][column]==0)
                {
                    System.out.print("\t"+"*");
                }
                else if(boardA[row][column]==1)
                {
                    System.out.print("\t"+"X");
                }
                
            }
            System.out.println();
        }
        
    }
    
    public static void setupBoard(int[][] boardA, int[][] boardB)
    {
        for(int row = 0; row < size; row++)
        {
            for(int column = 0; column < size; column++)
            {
                boardA[row][column] = -1;
                boardB[row][column] = -1;
            }
        }
    }
    
}
