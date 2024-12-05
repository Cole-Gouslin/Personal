/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.firsttext;

/**
 *
 * @author cgous
 */

import java.util.*;

public class FirstText {

    public static void main(String[] args) {
        
        // initialize objects
        Scanner scan = new Scanner(System.in);
        
        // initialize variables
        String textInput; 
        Player player = new Player(0,0,10,10,20);
        Enemy enemy1 = new Enemy("Goblin",1,1,10,10,10);
        Enemy currentEnemy = null;
        System.out.println("All enemies: " + Enemy.getAllEnemies());
        
            
        // PLAY      
        
        
        while (true) {
            System.out.print("Hi! Welcome to MAZE! ");
            
            
            while (true) {
                System.out.println("\nWhere would you like to move?\n\n- FORWARD\n- BACKWARD\n- LEFT\n- RIGHT");
                textInput = scan.nextLine();

                // movement input if sequence
                if (textInput.equalsIgnoreCase("FORWARD") || textInput.equalsIgnoreCase("UP") || textInput.equalsIgnoreCase("W")){
                    player.setX(player.getX() + 1);
                    System.out.println(player.getCoordinates() + "\n");

                    } else if (textInput.equalsIgnoreCase("BACKWARD") || textInput.equalsIgnoreCase("DOWN") || textInput.equalsIgnoreCase("BACK") || textInput.equalsIgnoreCase("S")){
                        player.setX(player.getX() - 1);
                        System.out.println(player.getCoordinates() + "\n");

                    } else if (textInput.equalsIgnoreCase("LEFT") || textInput.equalsIgnoreCase("A")){
                        player.setY(player.getY() - 1);
                        System.out.println(player.getCoordinates() + "\n");

                    } else if (textInput.equalsIgnoreCase("RIGHT") || textInput.equalsIgnoreCase("D")){
                        player.setY(player.getY() + 1);
                        System.out.println(player.getCoordinates() + "\n");

                    } else {
                        System.out.println("INVALID INPUT\nPLEASE TRY AGAIN");
                        continue;
                }  
                
                // Check for collision with any enemy in the static list
                    currentEnemy = null;
                    for (Enemy enemy : Enemy.getAllEnemies()) {
                        //System.out.println("Checking collision with enemy at: " + enemy.getCoordinates());
                        if (enemy.isAtPosition(player.getX(), player.getY())) {
                            currentEnemy = enemy;
                            break;
                        }
                    }    
                    if (currentEnemy != null) {
                        System.out.println("You encountered an enemy!\n");
                        
                        //display stats

                        System.out.println(player.getStats());
                        System.out.println("");
                        System.out.println(currentEnemy.getStats() + "\n");

                        // enter battle

                        boolean finishBattle = false; //this may cause issues if it remains as set to true, but i dont think so
                        boolean enemyReact = true;
                        do {
                            
                            // detect action
                            
                            System.out.println("What action will you take?\n\n- FIGHT\n- CHECK\n- ITEM\n- RUN");
                            textInput = scan.nextLine();
                            
                            if (textInput.equalsIgnoreCase("FIGHT") || textInput.equalsIgnoreCase("ATK") || textInput.equalsIgnoreCase("ATTACK") || textInput.equalsIgnoreCase("F") || textInput.equalsIgnoreCase("1")){
                                int dmg = player.damageCalc(player.getATK(), currentEnemy.getDEF());
                                currentEnemy.setRHP(currentEnemy.getRHP() - dmg);
                                System.out.println("You dealt " + dmg + " damage to the " + currentEnemy.getName() + "\n");
                                enemyReact = true;
                            } else if (textInput.equalsIgnoreCase("CHECK") || textInput.equalsIgnoreCase("2") ||textInput.equalsIgnoreCase("C")){
                                System.out.println("\n" + player.getStats());
                                System.out.println(currentEnemy.getStats() + "\n");
                                enemyReact = false;
                            } else if (textInput.equalsIgnoreCase("ITEM") || textInput.equalsIgnoreCase("3") || textInput.equalsIgnoreCase("BAG") || textInput.equalsIgnoreCase("I")){
                                System.out.println("NO ITEMS\n");
                                enemyReact = true;
                            } else if (textInput.equalsIgnoreCase("RUN") || textInput.equalsIgnoreCase("4") || textInput.equalsIgnoreCase("R") || textInput.equalsIgnoreCase("FLEE")){
                                System.out.println("CANT RUN");
                                enemyReact = false;
                            } else {
                                System.out.println("INVALID INPUT\nPLEASE TRY AGAIN\n");
                                continue;
                            }
                            
                            // enemy reaction
                            
                            if ((currentEnemy.getRHP() > 0) && (enemyReact = true)){
                                int dmg = currentEnemy.damageCalc(currentEnemy.getATK(), player.getATK());
                                player.setRHP(player.getRHP() - dmg);
                                System.out.println("The " + currentEnemy.getName() + " attacked and dealt " + dmg + " damage");
                                System.out.println(player.getHPTFrac());
                                System.out.println(currentEnemy.getHPTFrac() + "\n");
                            }
                            
                            // check for victory/defeat
                            
                            if (currentEnemy.getRHP() <= 0){
                                System.out.println("You defeated the " + currentEnemy.getName());
                                finishBattle = true;
                            } else if (player.getRHP() <= 0){
                                System.out.println("You died to the " + currentEnemy.getName());
                                System.exit(0);
                            }
                        } while (!finishBattle);
                    } else {
                        //System.out.println("NO ENEMY FOUND");
                }
            }
        }        
    }             
}



// player class //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class Player {
        
    // initialize
    private ArrayList<Integer> coordinates;
    private int atk;
    private int def;
    private int hpt;
    private int rhp;
    
    // constructors
    
     public Player(){
    }
    
    public Player(int inX, int inY){        
        coordinates = new ArrayList<>(Arrays.asList(inX,inY));  
    }
    
    public Player(int inX, int inY , int inATK, int inDEF, int inHPT){        
        coordinates = new ArrayList<>(Arrays.asList(inX,inY));
        atk = inATK;
        def = inDEF;
        hpt = inHPT;
        rhp = inHPT;
    }
    
    public Player(int inX, int inY , int inATK, int inDEF, int inHPT, int inRHP){        
        coordinates = new ArrayList<>(Arrays.asList(inX,inY));
        atk = inATK;
        def = inDEF;
        hpt = inHPT;
        rhp = inRHP;
    } 
    
    // mutators
    
    public void setX (int inX){
        coordinates.set(0, inX);
    }
    
    public void setY (int inY){
        coordinates.set(1, inY);
    }
    
    public void setATK (int inATK){
        atk = inATK;
    }    
    
    public void setDEF (int inDEF){
        def = inDEF;
    }
    
    public void setHPT (int inHPT){
        hpt = inHPT;
    }
    
    public void setRHP (int inRHP){
        rhp = inRHP;
    }    
    
    // accessors
    
    public ArrayList<Integer> getCoordinates(){
        return coordinates;
    }
    
    public int getX(){
        return coordinates.get(0);
    }
    
    public int getY(){
        return coordinates.get(1);
    }

    public int getATK(){
        return atk;
    }
    
    public int getDEF(){
        return def;
    }
    
    public int getHPT(){
        return hpt;
    }
    
    public int getRHP(){
        return rhp;
    }
    
    // hpt fraction printable
    
    public String getHPTFrac(){
        return "Your hpt: " + rhp + "/" + hpt;
    }

    //damage method
    
    public int damageCalc (int inATK,int inDEF){
        return Math.round(5*(inATK/inDEF));
    }
    
    // display stats method
    
    public String getStats(){
        return "You:\nHPT: " + rhp + "/" + hpt + " ATK: " + atk + " DEF: " + def;
    }
    
    //collision detection method
    
    public boolean isAtPosition(int x, int y) {
    return coordinates.get(0) == x && coordinates.get(1) == y;
    }
    
    // toString
    
    @Override
    public String toString() {
    return "Player at (" + coordinates.get(0) + ", " + coordinates.get(1) + ")";
    }
}
// standard enemy class ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class Enemy {
    
    // static list of enemies
    
    private static List<Enemy> allEnemies = new ArrayList<>();
    
    // initialize
    private ArrayList<Integer> coordinates;
    private int atk;
    private int def;
    private int hpt;
    private int rhp;
    private String name;
    
    // constructors
    
    public Enemy(){
        allEnemies.add(this);
    }
    
    public Enemy(String inName){
        name = inName;
        allEnemies.add(this);
    }
    
    public Enemy(int inX, int inY){
        coordinates = new ArrayList<>(Arrays.asList(inX,inY));
        allEnemies.add(this);
    } 
    
    public Enemy(String inName, int inX, int inY , int inATK, int inDEF, int inHPT){        
        name = inName;
        coordinates = new ArrayList<>(Arrays.asList(inX,inY));
        atk = inATK;
        def = inDEF;
        hpt = inHPT;
        rhp = inHPT;
        allEnemies.add(this);
    } 
    
     public Enemy(String inName, int inX, int inY , int inATK, int inDEF, int inHPT, int inRHP){        
        name = inName;
        coordinates = new ArrayList<>(Arrays.asList(inX,inY));
        atk = inATK;
        def = inDEF;
        hpt = inHPT;
        rhp = inRHP;
        allEnemies.add(this);
    } 
    
    // mutators
    
    public void setX (int inX){
        coordinates.set(0, inX);
    }
    
    public void setY (int inY){
        coordinates.set(1, inY);
    }
    
    public void setATK (int inATK){
        atk = inATK;
    }    
    
    public void setDEF (int inDEF){
        def = inDEF;
    }
    
    public void setHPT (int inHPT){
        hpt = inHPT;
    }
    
    public void setRHP (int inRHP){
        rhp = inRHP;
    }
    
    public void setName (String inName){
        name = inName;
    }
    
    // accessors
    
    public ArrayList<Integer> getCoordinates(){
        return coordinates;
    }
    
    public int getX(){
        return coordinates.get(0);
    }
    
    public int getY(){
        return coordinates.get(1);
    }
     
    public int getATK(){
        return atk;
    }
    
    public int getDEF(){
        return def;
    }
    
    public int getHPT(){
        return hpt;
    }
    
    public int getRHP(){
        return rhp;
    }
    
    public String getName(){
        return name;
    }
    
    // hpt fraction printable
    
    public String getHPTFrac(){
        return name + " hpt: " + rhp + "/" + hpt;
    }
    
    // retrieve enemy list
    
    public static List<Enemy> getAllEnemies(){
        return allEnemies;
    }
    
    //damage method
    
    public int damageCalc (int inATK,int inDEF){
        return Math.round(5*(inATK/inDEF));
    }
    
    // display stats method
    
    public String getStats(){
        return name + ":\nHPT: " + rhp + "/" + hpt + " ATK: " + atk + " DEF: " + def;
    }
    
    // collision detection method
    
    public boolean isAtPosition(int x, int y) {
    return coordinates.get(0) == x && coordinates.get(1) == y;
    }
    
    // toString
    
    @Override
    public String toString() {
    return "Enemy at (" + coordinates.get(0) + ", " + coordinates.get(1) + ")";
    }
}

// random enemy spawn / respawns
// enemy distance compass
// levels
// visible map
// bosses
// specific areas