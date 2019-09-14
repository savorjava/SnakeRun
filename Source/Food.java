//package SnakeRun;

import java.awt.*;
import java.util.*;


public class Food {


   public Food(String function) {
      state = "fresh";
      niche = function;
   }


   public String getNiche() {
      return niche;
   }


   public int getLife() {
      return life;
   }


   public void setLife(int foodLife) {
      life = foodLife;
   }


   public int getOverTime() {
      return overTime;
   }


   public void setOverTime(int time) {
      overTime = time;
   }


   public Point getPosition() {
      return where;
   }


   public void setPosition(Point position) {
      where = position;
   }


   public String getFoodState() {
      return state;
   }


   public void setFoodState(String foodState) {
      state = foodState;
   }

   /** attributes */
   private Point where;
   private String niche;               //should be "longer", "shorter", "none", and so on
   private int life = 7;              //will disappear after 7+ seconds
   private int overTime;               //when will it disappear
   private String state;               //could be "flesh", "eaten" and "gone"
}