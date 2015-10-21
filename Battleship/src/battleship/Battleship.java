package battleship;
import java.util.Scanner;
import java.util.Random;

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
        Scanner input = null;
        int result = 0;

        try
        {
            input = new Scanner(System.in);
            Battleship game1 = new Battleship();
            setupBoard(game1.boardA, game1.boardB);
            setupShipsAI(game1.boardB);
            setupShipsPlayer(game1.boardA, input);
            
            while(true)
            {
                showBoards(game1.boardA, game1.boardB);
                shootPlayer(shootP, input);
                result = hitPlayer(shootP, game1.boardB, result);
                if(result >= 17)
                {
                    System.out.println("You've sunk all the enemie's battleships!");
                    System.exit(0);
                }
            }
        }
        catch (Exception ex)
        {
            System.err.println(ex);
            ex.printStackTrace();
        }
        finally
        {
            if (input != null)
                input.close();
        }
        
        
    }
    
    public static void setupShipsAI(int[][] boardB)
    {
        for(int i = 0; i < 5; i++)
        {
            boardB[0][i] = 0;
        }
        
        for(int i = 0; i < 4; i++)
        {
            boardB[i + 1][1] = 0;
        }
        
        for(int i = 0; i < 3; i++)
        {
            boardB[5][i + 1] = 0;
        }
        
        for(int i = 0; i < 3; i++)
        {
            boardB[i + 3][5] = 0;
        }
        
        for(int i = 0; i < 2; i++)
        {
            boardB[7][i + 6] = 0;
        }
        
    }
    
    public static void setupShipsPlayer(int[][] shipsA, Scanner input)
    {
        /*
        int rowChoice = 0;
        int colChoice = 0;
        Scanner input = new Scanner(System.in);
        
        System.out.println("Choose where to place your ships");
        System.out.println("Choose where to place the Carrier (5x1 ship). Row: ");
        rowChoice = input.nextInt();
        System.out.println("Column: ");
        colChoice = input.nextInt();
        System.out.println("Which direction to place the ship");
        
        input.close();*/
        
        for(int i = 0; i < 5; i++)
        {
            shipsA[0][i] = 0;
        }
        
        for(int i = 0; i < 4; i++)
        {
            shipsA[i + 1][1] = 0;
        }
        
        for(int i = 0; i < 3; i++)
        {
            shipsA[5][i + 1] = 0;
        }
        
        for(int i = 0; i < 3; i++)
        {
            shipsA[i + 3][5] = 0;
        }
        
        for(int i = 0; i < 2; i++)
        {
            shipsA[7][i + 6] = 0;
        }
    }
    
    public static void shootAI(int[] shootAI)
    {
        
    }
    
    public static void shootPlayer(int[] shootP, Scanner input)
    {
        
        try
        {
            

            System.out.println("Choose a spot to fire");
            System.out.print("Row: ");
            shootP[0] = input.nextInt();
            shootP[0]--;

            System.out.print("Column: ");
            shootP[1] = input.nextInt();
            shootP[1]--;
        }
        catch (Exception ex)
        {
            System.err.println(ex);
            ex.printStackTrace();
        }
        
        
    }
    
    public static int hitPlayer(int[] shootP, int[][] boardB, int result)
    {
        if(boardB[shootP[0]][shootP[1]] == 0)
        {
            boardB[shootP[0]][shootP[1]] = 1;
            System.out.printf("You hit a ship located in (%d,%d)\n", shootP[0] + 1, shootP[1] + 1);
        }
        else if(boardB[shootP[0]][shootP[1]] == -1)
        {
            boardB[shootP[0]][shootP[1]] = 1;
            System.out.printf("You hit an empty square located at (%d,%d)\n", shootP[0] + 1, shootP[1] + 1);
            result += 1;
        }
        else if(boardB[shootP[0]][shootP[1]] == 1)
        {
            System.out.printf("You have already shot at (%d,%d)!\n", shootP[0] + 1, shootP[1] + 1);
        }
        
        
        for(int ship = 0 ; ship < boardB.length ; ship++)
        {
            if( shootP[0] == boardB[ship][0] && shootP[1] == boardB[ship][1])
            {
                
                System.out.printf("You hit a ship located in (%d,%d)\n", shootP[0] + 1, shootP[1] + 1);
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
                    System.out.print("\t"+"~");
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
