package battleship;

import java.util.Scanner;


public class Player 
{
    
    public static void setupShipsPlayer(int[][] boardA)
    {
        int rowChoice = 0;
        int colChoice = 0;
        Scanner input = null;
        
        try
        {
            input = new Scanner(System.in);
        
        System.out.println("Choose where to place your ships");
        System.out.println("Choose where to place the Carrier (5x1 ship). Row: ");
        rowChoice = input.nextInt();
        System.out.println("Column: ");
        colChoice = input.nextInt();
        System.out.println("Which direction to place the ship");
        }
        catch (Exception ex)
        {
            System.err.println(ex);
            ex.printStackTrace();
        }
               
                
        input.close();
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
}
