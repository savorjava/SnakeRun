//package SnakeRun;

import java.awt.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;

public class SnakeRun {


   public static void main(String[] args) {
      GameFrame frame=new GameFrame("SnakeRun");
      frame.setResizable(false);
      frame.setVisible(true);
   }

   public SnakeRun() {
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   private void jbInit() throws Exception {
   }
}

class GameFrame extends JFrame implements Runnable, KeyListener {


   public GameFrame(String title) {
      super(title);
      thread = new Thread(this);

      /** make the frame be in the center of the screen */
      setSize(frameSizeX,frameSizeY);
      setLocation(
         (Toolkit.getDefaultToolkit().getScreenSize().width-frameSizeX)/2,
         (Toolkit.getDefaultToolkit().getScreenSize().height-frameSizeY)/2
      );

      /** set the canvas and graphics */
      container=getContentPane();
      container.setLayout(new BorderLayout());
//      container.add(toolBar, BorderLayout.NORTH);
      container.add(drawCanvas,BorderLayout.CENTER);
//      graphics=drawCanvas.getGraphics();

      /** prepare the buffered image and graphics */
      bufferedImage=new BufferedImage(frameSizeX,frameSizeY,BufferedImage.TYPE_INT_RGB);
      bufferedGraphics=bufferedImage.getGraphics();

      /** this part will help me end the application when I click the X on the windows */
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            dispose();
            System.exit(0);
         }
      } );

      addKeyListener(this);


      /** menu is not a good ideal, they can not paint themselves
       * properly as painting the JPanel
       */
//      menuMaker();
//      setJMenuBar(gameMenuBar);

      /** toolbar will own the same function as menu */
      /** these actions will complete the function for the button of the bar */
      /** toolbar is not a good ideal too
      class SingleAction extends AbstractAction {
         public SingleAction(String name, Icon icon, Component t) {
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, icon);
            putValue(Action.SHORT_DESCRIPTION, name);
            target = t;
         }

         public void actionPerformed(ActionEvent evt) {

         }

         private Component target;
      }

      JButton barButton = new JButton(new ImageIcon("resources/body.gif"));
      barButton.addActionListener(new SingleAction("test", new ImageIcon("resources/body.gif"), drawCanvas));
      barButton.setToolTipText("Single Game");
//      toolBar.add(new SingleAction("test", new ImageIcon("resources/body.gif"), drawCanvas));
      toolBar.add(barButton);
      drawCanvas.requestFocus();
*/


      /** initial the resources */
      redFood = new ImageIcon("resources/redfood.gif");
      greenFood = new ImageIcon("resources/greenfood.gif");
      whiteFood = new ImageIcon("resources/whitefood.gif");
      headnorth = new ImageIcon("resources/head01north.gif");
      headsouth = new ImageIcon("resources/head01south.gif");
      headwest = new ImageIcon("resources/head01west.gif");
      headeast = new ImageIcon("resources/head01east.gif");
      body = new ImageIcon("resources/body01.gif");
      face = new ImageIcon("resources/title.gif");


      /** set the user information */
      user = new User(1, "User1");
      users.add(user);

      /** start the Game */
      thread.start();
   }


   public Snake snakeFactory() {
      Point point=null;             //set default headPosition is null
      String direction="north";     //default diretion
      int stepX=0,stepY=0;          //set the bodyPostion

      amountofSnake++;
      Snake snake = new Snake(amountofSnake);
      snake.setSnakeWidth(6);
      snake.setBodyDiameter(snakeBodyDiameter);
      snake.setState("borning");

      /** initial the direction when snake is borning */
      switch(rand.nextInt(4)) {
         case 0 : direction="north"; stepY=-1; break;
         case 1 : direction="south"; stepY=1;  break;
         case 2 : direction="west";  stepX=-1; break;
         case 3 : direction="east";  stepX=1;
      }
      snake.setDirection(direction);

      /** initial the poiton of head of snake */
      point=matrix[rand.nextInt(canvasRow)][rand.nextInt(canvasCol)];
      snake.setSnakePosition(point);       //set bodyPosition too

      /** set the speed of snake */
      if(monitor != null)
         snake.setSpeed(snake.getSpeed() - (monitor.getStage() - 1) * 10);

      return snake;
   }

/* menu is not a good ideal for this application
   public void menuMaker() {
      gameMenu = new JMenu("Game");
      gameMenu_Single = new JMenuItem("New Single Game", 4);
      gameMenu_Net = new JMenuItem("New Network Game", 4);
      gameMenu_Exit = new JMenuItem("Exit", 2);
      gameMenu.add(gameMenu_Single);
      gameMenu.add(gameMenu_Net);
      gameMenu.add(gameMenu_Exit);

      configMenu = new JMenu("Config");
      configMenu_Env = new JMenuItem("Environment", 1);
      configMenu_Sound = new JMenuItem("Sound", 1);
      configMenu_Lang = new JMenuItem("Language - en", 1);
      configMenu.add(configMenu_Env);
      configMenu.add(configMenu_Sound);
      configMenu.add(configMenu_Lang);

      helpMenu = new JMenu("Help");
      helpMenu_Content = new JMenuItem("Content", 1);
      helpMenu_About = new JMenuItem("About", 1);
      helpMenu.add(helpMenu_Content);
      helpMenu.add(helpMenu_About);

      gameMenuBar.add(gameMenu);
      gameMenuBar.add(configMenu);
      gameMenuBar.add(helpMenu);



   }
*/


   public void run() {
      while(true) {
         try{
            repaint();
            thread.sleep(50);
         }
         catch(InterruptedException e) {
            System.out.println("Exception Message: " + e);
         }
      }
   }


   public void setGameState(String state) {
      gameState = state;
   }


   public void paint(Graphics g) {
      /** when application begin to run */
         oldFont = bufferedGraphics.getFont();
//         Font newFont = new Font("Serif", Font.BOLD|Font.ITALIC, 30);
      if(gameState.equals("title")) {
         graphics=drawCanvas.getGraphics();
         canvasSizeX = drawCanvas.getSize().width;       //reset the canvas size
         canvasSizeY = drawCanvas.getSize().height;      //as canvas has been showed
//         graphics.setFont(newFont);
         bufferedGraphics.drawImage(face.getImage(), 0, 0,
               face.getIconWidth(), face.getIconHeight(), this);
//         graphics.setFont(oldFont);
         if(choice.equals("single"))
            bufferedGraphics.drawImage(redFood.getImage(), 35, 178,
                  redFood.getIconWidth(), redFood.getIconHeight(), this);
         if(choice.equals("environment"))
            bufferedGraphics.drawImage(redFood.getImage(), 35, 209,
                  redFood.getIconWidth(), redFood.getIconHeight(), this);
         if(choice.equals("config"))
            bufferedGraphics.drawImage(redFood.getImage(), 35, 240,
                  redFood.getIconWidth(), redFood.getIconHeight(), this);
         if(choice.equals("exit"))
            bufferedGraphics.drawImage(redFood.getImage(), 35, 271,
                  redFood.getIconWidth(), redFood.getIconHeight(), this);
         graphics.drawImage(bufferedImage, 0, 0, frameSizeX, frameSizeY, this);
      }

      /** will display the passing stage picture */
      if(gameState.equals("stage")) {
         bufferedGraphics.setColor(new Color(72,122,189));
         bufferedGraphics.fillRect(0, 0, canvasSizeX, canvasSizeY);
         bufferedGraphics.setColor(new Color(73,253,251));
         bufferedGraphics.setFont(new Font("Serif", Font.BOLD|Font.ITALIC, 30));
         bufferedGraphics.drawString("OK !  You have passed stage .. " +
               monitor.getStage(), 20, 70);
         bufferedGraphics.setFont(new Font("Serif", Font.BOLD, 20));
         bufferedGraphics.setColor(Color.yellow);
         bufferedGraphics.drawString(user.getUserName() +
               "  . . . . . . . . . . . . . . .  " + user.getScore(), 100, 150);
         bufferedGraphics.setFont(oldFont);
         bufferedGraphics.setColor(Color.green);
         bufferedGraphics.drawString("< - send me a mail if you need the source code - >", 30, 280);
         bufferedGraphics.drawString("< savorjava@yahoo.com   cn.geocities.com/scjdcn/ >", 30, 300);
         bufferedGraphics.drawString("< Special thanks: Shelly & Huihui >", 30, 320);
         bufferedGraphics.setColor(Color.red);
         bufferedGraphics.drawString("Please Press \"Enter\"", 350, 330);
         graphics.drawImage(bufferedImage, 0, 0, frameSizeX, frameSizeY, this);
      }

      /** will display "Game Over" when snake died */
      if(gameState.equals("ending")) {
         bufferedGraphics.setColor(new Color(72,122,189));
         bufferedGraphics.fillRect(0, 0, canvasSizeX, canvasSizeY);
         bufferedGraphics.setColor(new Color(73,253,251));
         bufferedGraphics.setFont(new Font("Serif", Font.BOLD|Font.ITALIC, 30));
         if(monitor.getStage() >= 10)
            bufferedGraphics.drawString("Congratulations !  You win.", 20, 70);
         else
            bufferedGraphics.drawString("Sorry !  You are lost.", 20, 70);
         bufferedGraphics.setFont(new Font("Serif", Font.BOLD, 20));
         bufferedGraphics.setColor(Color.yellow);
         bufferedGraphics.drawString(user.getUserName() +
               "  . . . . . . . . . . . . . . .  " + user.getScore(), 100, 150);
         bufferedGraphics.setFont(oldFont);
         bufferedGraphics.setColor(Color.green);
         bufferedGraphics.drawString("< - send me a mail if you need the source code - >", 30, 280);
         bufferedGraphics.drawString("< savorjava@yahoo.com   cn.geocities.com/scjdcn/ >", 30, 300);
         bufferedGraphics.drawString("< Special thanks: Shelly & Huihui >", 30, 320);
         bufferedGraphics.setColor(Color.red);
         bufferedGraphics.drawString("Please Press \"ESC\"", 350, 330);
         graphics.drawImage(bufferedImage, 0, 0, frameSizeX, frameSizeY, this);

      }

      /** will tell the user that his snake has lost one life */
      if(gameState.equals("over")) {
//         graphics=drawCanvas.getGraphics();
         bufferedGraphics.setColor(new Color(160,224, 239));
         bufferedGraphics.fillRect(0, 0, frameSizeX, frameSizeY);
         drawFood(bufferedGraphics);
         drawSnake(bufferedGraphics);//, (Snake)snakeArrayList.get(0));
         drawScore(bufferedGraphics);
         graphics.drawImage(bufferedImage, 0, 0, frameSizeX, frameSizeY, this);
      }

      /** painting when snake is running */
      if(gameState.equals("running")) {
//         graphics=drawCanvas.getGraphics();
         bufferedGraphics.setColor(new Color(160,224, 239));
         bufferedGraphics.fillRect(0, 0, frameSizeX, frameSizeY);
         drawFood(bufferedGraphics);

         /** check the snake died to temp or ending */
         for(int i = 0; i < snakeArrayList.size(); i++) {
            Snake temp = (Snake)snakeArrayList.get(i);
            /** if snake has lifes more than 0 then set game state to "running"
             * and now, the snake is not moving for state "temp", or the snake
             * will die and game state to be seted "ending"
             */
            if(temp.getState().equals("temp"))
               if(user.getGameLife() < 0) {
                  temp.setState("died");
                  gameState = "ending";
               }
         }

//         bufferedGraphics.setColor(Color.yellow);
         drawSnake(bufferedGraphics);//, (Snake)snakeArrayList.get(0));
         drawScore(bufferedGraphics);
         graphics.drawImage(bufferedImage, 0, 0, frameSizeX, frameSizeY, this);
      }

      /** this state of game is before the "running" state, for inintial snake
       * and canvas
       */
      if(gameState.equals("beginning")) {
         if(monitor == null)
            setMatrix(snakeBodyDiameter);

         /** There will be at least 2 snakes, this one is the first one */
         Snake snake=snakeFactory();
         snakeArrayList.add(snake);

         /** make snake running */
         snake.start();
         setGameState("running");
         bufferedGraphics.setColor(new Color(160,224, 239));
         bufferedGraphics.fillRect(0, 0, frameSizeX, frameSizeY);
//         bufferedGraphics.setColor(new Color(160,224, 239));
//       drawSnake(bufferedGraphics, (Snake)snakeArrayList.get(0));
         graphics.drawImage(bufferedImage, 0, 0, frameSizeX, frameSizeY, this); //you can use g instead of graphics
      }

      /** there are some thing wrong about the menu */
//      gameMenuBar.repaint();
//      gameMenu_Single.repaint();
//      gameMenu_Net.repaint();

   }


   public void drawSnake(Graphics g) {
      for(int i = 0; i < snakeArrayList.size(); i++) {
         Snake temp=(Snake)snakeArrayList.get(i);
         int diameter=temp.getBodyDiameter();
         Point headPosition=temp.getHeadPosition();
         ArrayList snakePosition=temp.getBodyPosition();

         if(temp.getState().equals("borning")) {
            /** make the snake can cross the wall */
            if(headPosition.x < 0)
               temp.setHeadPosition(new Point(matrix[canvasRow-1][canvasCol-1].x, headPosition.y));
            if(headPosition.x > matrix[canvasRow-1][canvasCol-1].x + diameter)
               temp.setHeadPosition(new Point(0,headPosition.y));
            if(headPosition.y < 0)
               temp.setHeadPosition(new Point(headPosition.x, matrix[canvasRow-1][canvasCol-1].y));
            if(headPosition.y > matrix[canvasRow-1][canvasCol-1].y + diameter)
               temp.setHeadPosition(new Point(headPosition.x,0));
         }
         else {
            if(headPosition.x < 0
               ||headPosition.x > matrix[canvasRow-1][canvasCol-1].x + diameter
               ||headPosition.y < 0
               ||headPosition.y > matrix[canvasRow-1][canvasCol-1].y + diameter)
               if(user.getGameLife() > 1)
                  temp.setState("temp");
               else
                  gameState="ending";
         }

         /** draw sanke's head according it's direction */
         g.setColor(Color.yellow);
//         g.fillOval(headPosition.x, headPosition.y, diameter, diameter);
         if(temp.getDirection().equals("north"))
            g.drawImage(headnorth.getImage(), headPosition.x, headPosition.y, this);
         if(temp.getDirection().equals("south"))
            g.drawImage(headsouth.getImage(), headPosition.x, headPosition.y, this);
         if(temp.getDirection().equals("west"))
            g.drawImage(headwest.getImage(), headPosition.x, headPosition.y, this);
         if(temp.getDirection().equals("east"))
            g.drawImage(headeast.getImage(), headPosition.x, headPosition.y, this);

      /** draw snake's body */
         synchronized(snakePosition) {
         for(int j = 0; j < temp.getSnakeWidth(); j++) {
/*            g.fillOval(((Point)snakePosition.get(i)).x,
                       ((Point)snakePosition.get(i)).y,
                       diameter, diameter);*/

            g.drawImage(body.getImage(),
                        ((Point)snakePosition.get(j)).x,
                        ((Point)snakePosition.get(j)).y, this);
         }
         }
         temp=null;
      }
   }

  /** draw foods according to their position and properties */
   public void drawFood(Graphics g) {
      for(int i = 0; i < foods.length; i++) {
         if (foods[i].getNiche().equals("longer")) {
//            g.setColor(Color.green);
            g.drawImage(greenFood.getImage(),foods[i].getPosition().x, foods[i].getPosition().y, null);
         }
         if (foods[i].getNiche().equals("shorter")) {
//            g.setColor(Color.red);
            g.drawImage(redFood.getImage(),foods[i].getPosition().x, foods[i].getPosition().y, null);
         }
         if (foods[i].getNiche().equals("none")) {
            g.setColor(Color.gray);
            g.drawImage(whiteFood.getImage(),foods[i].getPosition().x, foods[i].getPosition().y, null);
         }
      }
   }


   /** you should show user some information at here */
   public void drawScore(Graphics g) {
      g.setColor(Color.blue);
      g.setFont(oldFont);
      int i = 0;
      for(i = 0; i < snakeArrayList.size(); i++) {
         Snake temp = (Snake)(snakeArrayList.get(i));
         g.drawString("<Snake " + (i+1) + ":" + temp.getBodyPosition().size() + ">", 3, 13 + i * 10);
         if(temp.getState().equals("temp"))
            bufferedGraphics.drawString("<Snake " + temp.getSnakeID() + " have killed itself! Press \"ENTER\"...>", 200, 13);
      }
      g.drawString("<" + user.getUserName() + ":" + user.getScore() + ">", 3, 13 + i * 10);
      g.drawString("<Game Life: " + user.getGameLife() + ">", 100, 13);
   }


   /** the canvas will be divided to many little blocks */
   public void setMatrix(int diameter) {
      canvasRow = canvasSizeY / diameter;
      canvasCol = canvasSizeX / diameter;
      matrix = new Point[canvasRow][canvasCol];
      for(int i = 0; i < canvasRow; i++)
         for(int j = 0; j < canvasCol; j++)
            matrix[i][j] = new Point(j * diameter, i * diameter);
//      setSize(canvasCol*diameter,canvasRow*diameter);

      /** make the foods */
      foodMaker = new FoodMaker(matrix, canvasRow, canvasCol);
      foods = foodMaker.getFoods();

      /** make a monitor to watch the snake(s) */
      monitor=new Monitor(snakeArrayList, foods, users);

   }


   public void repaint(Graphics g) {
      paint(g);
   }


   /** Handle the key event from the frame */
   public void keyTyped(KeyEvent e) {
   }


   public void keyPressed(KeyEvent e) {

      /** checking the user's choice of game */
      if(gameState.equals("title"))
         switch (e.getKeyCode()) {
            case KeyEvent.VK_UP : if(choice.equals("single"))
                                     choice = "exit";
                                  else if(choice.equals("environment"))
                                     choice = "single";
                                   else if(choice.equals("config"))
                                     choice = "environment";
                                  else if(choice.equals("exit"))
                                     choice = "config";
                                  break;
            case KeyEvent.VK_DOWN : if(choice.equals("single"))
                                     choice = "environment";
                                  else if(choice.equals("environment"))
                                     choice = "config";
                                   else if(choice.equals("config"))
                                     choice = "exit";
                                  else if(choice.equals("exit"))
                                     choice = "single";
                                  break;
            case KeyEvent.VK_ENTER : if(choice.equals("single"))
                                       gameState = "beginning";
                                    else if(choice.equals("exit")) {
                                       dispose();
                                       System.exit(0);
                                    }

         }

      /** go to next stage when user press "enter" key */
      if(gameState.equals("stage"))
         switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER : if(monitor.getStage() > 9)
                                       gameState = "ending";
                                     else {
                                       gameState = "beginning";
                                       /** stop the thread of snakes */
                                       for( int i = 0; i < snakeArrayList.size(); i++) {
                                          Snake temp = (Snake)snakeArrayList.get(i);
                                          temp.setState("died");
                                       }
                                       snakeArrayList.clear();
                                       monitor.setStage(monitor.getStage() + 1);
                                     }
                                     twoSnakes = false;
                                     break;
         }

      if(gameState.equals("ending"))
         switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE : gameState = "title";
                                       /** stop the thread of snakes */
                                       for( int i = 0; i < snakeArrayList.size(); i++) {
                                          Snake temp = (Snake)snakeArrayList.get(i);
                                          temp.setState("died");
                                       }
                                      snakeArrayList.clear();
                                      monitor.setStage(1);
                                      user.setGameLife(3);
                                      user.setScore(0);
                                      twoSnakes = false;
                                      break;
         }

      if(gameState.equals("running")) {
//      graphics.setColor(Color.white);
//      graphics.drawString("you have pressed " + e.getKeyCode() + "   |   " + e.getModifiers(),50, 20);
          /** be sure if user has 2 snakes, and snake scare nothing at "beginning"
           * with "boring"but the state will change to "living" when user press
           * arrow key, and then, don't touch the wall or other dangerous things
           */
         Snake temp1 = (Snake)snakeArrayList.get(0);
         Snake temp2 = null;
         if(twoSnakes) {
            temp2 = (Snake)snakeArrayList.get(1);
            if(temp2.getState().equals("borning"))
               temp2.setState("living");
         }
         if(temp1.getState().equals("borning"))
            temp1.setState("living");

         switch (e.getKeyCode()) {
            case KeyEvent.VK_UP : /* if(gameState.equals("title"))
                                    choice = "single"; */
                                  if(!temp1.getDirection().equals("south"))
                                     temp1.setDirection("north");
                                   break;
            case KeyEvent.VK_DOWN : if(gameState.equals("title"))
                                    choice = "single";
                                  if(gameState.equals("running")) {
                                    if(!temp1.getDirection().equals("north"))
                                       temp1.setDirection("south");
                                   }
                                    break;
            case KeyEvent.VK_LEFT : if(!temp1.getDirection().equals("east"))
                                       temp1.setDirection("west");
                                    break;
            case KeyEvent.VK_RIGHT : if(!temp1.getDirection().equals("west"))
                                       temp1.setDirection("east");
                                     break;
         }
         if(temp2 != null) {
            switch (e.getKeyCode()) {
               case KeyEvent.VK_E : if(!temp2.getDirection().equals("south"))
                                        temp2.setDirection("north");
                                    break;
               case KeyEvent.VK_D : if(!temp2.getDirection().equals("north"))
                                        temp2.setDirection("south");
                                    break;
               case KeyEvent.VK_S : if(!temp2.getDirection().equals("east"))
                                        temp2.setDirection("west");
                                    break;
               case KeyEvent.VK_F : if(!temp2.getDirection().equals("west"))
                                        temp2.setDirection("east");
                                    break;
            }
         }
/** this part is second goal, the second snake will born! */
//           if(users[0].getScore() > 150)  // for test!!!!!
//               gameState = "stage";

         if((temp1.getBodyPosition().size() >= 12) && (temp2 == null)) {
            temp1.setSnakeBody(-6);

            /** born the second snake */
            temp2 = snakeFactory();
            snakeArrayList.add(temp2);
            temp2.start();
            twoSnakes = true;
         }

         if(twoSnakes)
            if(temp1.getBodyPosition().size() >= 12
               && temp2.getBodyPosition().size() >= 12)
               gameState = "stage";

         temp1 = null;
         temp2 = null;

         switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER :
               for(int i = 0; i < snakeArrayList.size(); i++) {
                  Snake temp = (Snake)snakeArrayList.get(i);
                  if(temp.getState().equals("temp")) {
                     temp.setState("borning");
                  }
               }
            if(user.getGameLife() > 1)
               user.setGameLife(user.getGameLife() - 1);
            else gameState = "ending";
         }
      }
//      graphics.drawString("canvasSizeX: "+canvasSizeX+"  "+"canvasSizeY: "+canvasSizeY,10,200);
//      graphics.drawOval(0,0,snakeBodyDiameter,snakeBodyDiameter);
   }


   public void keyReleased(KeyEvent e) {
   }


   public void actionPerformed(ActionEvent event) {

   }


   private int amountofSnake = 0;   //how many snake(s) have been created
   private Container container;     //canvas will be included in it
   private JPanel drawCanvas = new JPanel();          //Snake(s) will run in it
   private BufferedImage bufferedImage;               //buffered image of drawCanvas
   private Graphics graphics, bufferedGraphics;       //graphics of drawCanvas and graphics of buffered image
   private ArrayList snakeArrayList = new ArrayList(8);        //This game can include most 8 snakes
   private Monitor monitor = null;         //this monitor watch the snake and make the canvas drawing
   private Random rand = new Random();
   private int frameSizeX = 500, frameSizeY = 375;    //initial the size of frame
   private int canvasSizeX, canvasSizeY;              //should be smaller than frame size
   private Thread thread=null;
   private String gameState="title";  //there are "beginning", "running", "stage", "title" and "ending"
   private Point matrix[][];              //canvas comprised by grids that 9*9
   private int snakeBodyDiameter = 11;     //initial the value to 9
   private int canvasRow=0, canvasCol=0;  //canvas will be divided to y rows and x cols
   private FoodMaker foodMaker = null;
   private Food foods[] = null;
   private String choice = "single";      //record the user's choice of game mode
   private boolean twoSnakes = false;     //make sure if there are two snakes are running
   private ImageIcon redFood, greenFood, whiteFood;
   private ImageIcon headnorth, headsouth, headwest, headeast, body;
   private ImageIcon face;
   private ArrayList users = new ArrayList();    //the most user number is 4
   private User user = null;
   private Font oldFont;                     //I hope this variety can help me

 //   private JToolBar toolBar = new JToolBar();
 //  private JMenuBar gameMenuBar = new JMenuBar();
 //  private JMenu gameMenu, configMenu, helpMenu;
 //  private JMenuItem gameMenu_Single, gameMenu_Net, gameMenu_Exit;
 //  private JMenuItem configMenu_Env, configMenu_Sound, configMenu_Lang;
 //  private JMenuItem helpMenu_Content, helpMenu_About;
}
