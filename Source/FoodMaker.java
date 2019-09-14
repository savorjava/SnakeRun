//package SnakeRun;

import java.awt.*;
import java.lang.*;
import java.util.*;

public class FoodMaker extends Thread {


   public FoodMaker(Point canvasMatrix[][], int canvasRaw, int canvasCol) {
      Point position = null;

      matrix = canvasMatrix;
      raw = canvasRaw;
      col = canvasCol;
      for(int i = 0; i < amountofFoods; i++) {
         foods[i] = makeFood();
         position = matrix[rand.nextInt(raw)][rand.nextInt(col)];
         foods[i].setPosition(position);
      }
      start();
   }


   public Food makeFood() {
      Calendar rightNow = Calendar.getInstance();
      int seconds = rightNow.get(Calendar.HOUR) * 60 * 60
                                 + rightNow.get(Calendar.MINUTE) * 60
                                 + rightNow.get(Calendar.SECOND);
      Food food = null;

      switch (rand.nextInt(5)) {
         case 0 : food = new Food("longer");
         case 1 : food = new Food("longer"); break;
         case 2 : food = new Food("shorter");
         case 3 : food = new Food("shorter"); break;
         case 4 : food = new Food("none");
      }
      food.setLife(7 + rand.nextInt(7));
      food.setOverTime(seconds + food.getLife());

      return food;
   }


   public Food[] getFoods() {
      return foods;
   }


   public void foodCheck() {
      Calendar rightNow = Calendar.getInstance();
      int seconds = rightNow.get(Calendar.HOUR) * 60 * 60
                                 + rightNow.get(Calendar.MINUTE) * 60
                                 + rightNow.get(Calendar.SECOND);
      Point position = null;

      for(int i = 0; i < amountofFoods; i++) {
         if(foods[i].getOverTime() < seconds)
            foods[i].setFoodState("gone");
         if(!foods[i].getFoodState().equals("fresh")) {
            foods[i] = makeFood();
            position = matrix[rand.nextInt(raw)][rand.nextInt(col)];
            foods[i].setPosition(position);
         }
      ;}
   }


   public void run() {
      while(true) {
         foodCheck();
         try{
            sleep(100);
         }
         catch(InterruptedException e) {
            System.out.println("Exception Message: " + e);
         }
      }
   }



   /** attributes */
   private int amountofFoods = 10;             //amount of FoodMaker will support the food
   private Food[] foods = new Food[amountofFoods];
   private Point matrix[][] = null;
   private int raw = 0, col = 0;
   private Random rand = new Random();

}