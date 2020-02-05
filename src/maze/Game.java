package maze;

import java.util.ArrayList;

// One Thing which is displayed on the board
abstract class Entity{
    int x;
    int y;
    static String image;

    public Entity(int x , int y) {
        this.x=x;
        this.y=y;
    }
}

// Goal to reach
class Goal extends Entity { Goal(int x , int y) { super(x,y); } }

// Prevents the player from moving onto it
class Block extends Entity{ Block(int x , int y) { super(x,y); } }

// To be moved by the user
class Player extends Entity implements Movable {
    public Player(int x , int y) { super(x,y); }
    
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

interface Movable{
    public void setPosition(int x, int y);
}

// Holds the instances of all the objects
public class Game{
    static Player p;
    static Goal g;
    static ArrayList<Block> blocks = new ArrayList<>();
    static int size;
    
    static boolean won = false;
    
    static int levelNum;
    
    // a 2d representation of each level
    private static final char[][][] LEVELS = {
        // Level one
        {
            {'p','0','0','0','0'},
            {'0','b','b','b','0'},
            {'0','b','0','0','0'},
            {'0','0','0','b','b'},
            {'0','b','0','0','g'}
        },
        // Level two
        {
            {'p','0','0','b','0','b'},
            {'0','b','0','0','0','b'},
            {'0','0','b','b','0','0'},
            {'b','0','b','b','b','b'},
            {'0','0','b','0','0','0'},
            {'b','0','0','0','b','g'}
        },
        // Level three
        {
            {'p','0','0','0','0','b','0'},
            {'0','b','0','b','0','0','0'},
            {'b','0','0','b','0','b','0'},
            {'0','0','b','b','b','b','0'},
            {'0','b','0','0','0','b','0'},
            {'0','b','0','b','0','b','0'},
            {'0','0','0','b','0','0','g'},
        },
    };
    
    static final int NUMBER_OF_LEVELS = LEVELS.length;
    
    public static void start() { loadLevel(0); }
    
    public static void loadNextLevel() {
        if (++levelNum >= LEVELS.length) levelNum = 0;
        
        loadLevel(levelNum);
    }
    
    public static void loadLevel(int i) {
        won = false;
        
        levelNum = i;
        
        char[][] level = LEVELS[levelNum];
        size = level.length;
        blocks.clear();
        
        for (int row = 0; row < level.length; row++)
            for (int col = 0; col < level[row].length; col++)
                switch(level[row][col]) {
                    case 'p': p = new Player(col, row);         break;
                    case 'g': g = new Goal(col, row);           break;
                    case 'b': blocks.add(new Block(col, row));  break;
                }
    }
    
    public static void move(int x, int y) {
        int newX = p.x+x;
        int newY = p.y+y;
        
        if (!isOut(newX, newY) && !isOnBlock(newX, newY)) {
            if (isCollided(g, newX, newY))
                won = true;
            
            p.setPosition(newX, newY);
        }  
    }
    
    private static boolean isOnBlock(int x, int y) {
        for (Block block : blocks) {
            if (isCollided(block, x, y)) return true;
        }
        
        return false;
    }
    
    private static boolean isOut(int x, int y) {
        return x>=size || y>=size || x<0 || y<0;
    }
    
    private static boolean isCollided(Entity entity, int x, int y) {
        return entity.x == x && entity.y == y;
    }
} 
