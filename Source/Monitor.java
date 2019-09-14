//package SnakeRun;

import java.awt.*;
import java.lang.*;
import java.util.*;

public class Monitor extends Thread {


   public Monitor(ArrayList Snakes, Food[] foods, ArrayList users) {
      snakeArrayList = Snakes;
      this.foods = foods;
      this.users = users;

      start();
   }


   public void setStage(int stage) {
      this.stage=stage;
   }


   public int getStage() {
      return stage;
   }


   public void run() {
      while(true) {
         checkTouch();

         try {
            sleep(50);
         }
         catch(InterruptedException e) {
            System.out.println("Monitor Exception Message: " + e);
         }
      }

   }


   public void checkTouch() {
      Point headPosition = null;
      Snake temp = null;
      User user = (User)users.get(0);
      ArrayList bodyPosition = null;

      for(int i = 0; i < snakeArrayList.size(); i++) {
         temp = (Snake)snakeArrayList.get(i);
         headPosition = temp.getHeadPosition();

         /** check if the snakes get the foods */
         synchronized(foods) {
            for(int j = 0; j < foods.length; j++) {
               if(headPosition.equals(foods[j].getPosition())
                  &&foods[j].getFoodState().equals("fresh")) {
                  foods[j].setFoodState("eaten");
                  if((foods[j].getNiche()).equals("longer")) {
                     temp.setSnakeBody(1);
                     user.setScore(user.getScore() + 8);
                  }
                  if((foods[j].getNiche()).equals("shorter"))
                     if(temp.getBodyPosition().size() > 2) {
                        temp.setSnakeBody(-1);
                        user.setScore(user.getScore() + 5);
                     }
                  if((foods[j].getNiche()).equals("none"))
                     user.setScore(user.getScore() + 3);
               }
            }
         }

         /** check if the snakes bite itself */
         synchronized(headPosition) {
            for(int k = 0; k < snakeArrayList.size(); k++) {
               Snake temp1 = (Snake)snakeArrayList.get(k);
               if(!temp1.getState().equals("borning")&&!temp1.getState().equals("temp")) {
                  bodyPosition = temp1.getBodyPosition();
                  /** check from the second body as could not bite the first body */
                  for(int j = 1; j < bodyPosition.size(); j++) {
                     if(headPosition.equals((Point)bodyPosition.get(j))) {
                        temp.setState("temp");
                        break;
                     }
                     if(temp.getState().equals("temp"))
                        break;
                  }
               }
            }
         }
      }
   }


   /** attributes */
   private int stage=1;      //you have go to which stage
   private Food[] foods;            //where is the feed in the frame
   private String mode="single";    //"single" or "network" game
   private ArrayList snakeArrayList;
   private ArrayList users;
}