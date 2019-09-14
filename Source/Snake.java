//package SnakeRun;

import java.lang.*;
import java.awt.*;
import java.util.*;

public class Snake extends Thread {


   public Snake(int ID) {
      snakeID=ID;
   }


   public void setSnakePosition(Point position) {
      int stepX = 0, stepY = 0;

      headPosition=position;

      if(snakeDirection.equals("north")) stepY = 1;
      if(snakeDirection.equals("south")) stepY = -1;
      if(snakeDirection.equals("west")) stepX = 1;
      if(snakeDirection.equals("east")) stepX = -1;
      makeSnakeBody(stepX, stepY);                 //create body of snake
   }


   public void makeSnakeBody(int stepX, int stepY) {
      for(int i = 0; i<snakeWidth; i++) {
         bodyPosition.add(
            new Point(headPosition.x + stepX * (i+1) * bodyDiameter,
                      headPosition.y + stepY * (i+1) * bodyDiameter));
      }
   }


   public int getSnakeWidth() {
      return snakeWidth;
   }


   public void setSnakeWidth(int width) {
      snakeWidth = width;
   }


   synchronized public void setSnakeBody(int value) {
      if(value > 0) {
         Point position = (Point)bodyPosition.get(snakeWidth - 1);
         for(int i = 0; i < value; i++)
            bodyPosition.add(
               new Point(position.x, position.y));
      }
      if(value < 0)
         for(int i = 1; i <= Math.abs(value); i++)
            bodyPosition.remove(snakeWidth - i);
      snakeWidth += value;
   }


   public Point getHeadPosition() {
      return headPosition;
   }


   public void setHeadPosition(Point head) {
      headPosition = head;
   }


   public ArrayList getBodyPosition() {
      return bodyPosition;
   }


   public int getSnakeID() {
      return snakeID;
   }


   public void setState(String state) {
      snakeState=state;
   }


   public String getState() {
      return snakeState;
   }


   public void setDirection(String direction) {
      snakeDirection=direction;
   }


   public String getDirection() {
      return snakeDirection;
   }


   public int getSpeed() {
      return runSpeed;
   }


   public void setSpeed(int speed) {
      runSpeed = speed;
   }


   public void setBodyDiameter(int diameter) {
      bodyDiameter=diameter;
   }


   public int getBodyDiameter() {
      return bodyDiameter;
   }


   public void run() {
      /** the snake thread live when snake state is not "died" */
      while(snakeState.equals("borning")||snakeState.equals("living")||snakeState.equals("temp")) {
         if(!snakeState.equals("temp")) {
            snakeWidth = bodyPosition.size();
            bodyPosition.remove(snakeWidth-1);
            bodyPosition.add(0,headPosition);
            int stepX = 0, stepY = 0;
            if(snakeDirection.equals("north")) stepY = -1;
            if(snakeDirection.equals("south")) stepY = 1;
            if(snakeDirection.equals("west")) stepX = -1;
            if(snakeDirection.equals("east")) stepX = 1;
//             headPosition = null;
            Point temp=new Point(((Point)(bodyPosition.get(0))).x + stepX * bodyDiameter,
                                 ((Point)(bodyPosition.get(0))).y + stepY * bodyDiameter);
            headPosition = temp;
//            temp = null;
//            headPosition.x += stepX * bodyDiameter;
//            headPosition.y += stepY * bodyDiameter;
         }
            try{
               sleep(runSpeed);
            }
            catch(InterruptedException e) {
               System.out.println("Exception Message: " + e);
            }
      }

   }




// attributes area

   private ArrayList bodyPosition=new ArrayList(25);     //record the position(Point) of every part of snake
   private Point headPosition;      //where is the head of snake
   private int snakeID;             //every snake has an ID, special in net version
   private String snakeState;       //snake could be "borning", "died", "temp" or "living"
   private String snakeDirection = "north";   //snake could run towards "north", "south", "west", "east"
   private int bodyDiameter=11;        //the diameter of circle which compose the body of snake
   private int snakeWidth=0;       //snakeWidth do not include head of snake
   private int runSpeed = 150;      //the speed of snake's running
}