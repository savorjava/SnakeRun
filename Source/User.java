
/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */


public class User {


   public User(int ID, String name) {
      userID = ID;
      userName = name;
   }


   public void setScore(int score) {
      this.score=score;
   }


   public int getScore() {
      return score;
   }


   public int getUserID() {
      return userID;
   }


   public String getUserName() {
      return userName;
   }


   public int getGameLife() {
      return gameLife;
   }


   public void setGameLife(int life) {
      gameLife = life;
   }


   /** attributes */
   private int userID = 1;               //every user has a ID
   private String userName = "User1";    //every user has a userName
   private int score=0;          //you have got how many score
   private int gameLife = 3;     //every user has 3 times chance


}