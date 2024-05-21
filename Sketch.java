import processing.core.PApplet;

/**
 * 5.11 processing task
 * @author EdricLai 
*/
public class Sketch extends PApplet {
  /*
   * global variables
   * and data structures
  */
  // snow
  int intNumSnow = (int) random(5, 10);
  boolean[] arrSnowHide = new boolean[intNumSnow];
  float[] arrSnowX = new float[intNumSnow];
  float[] arrSnowY = new float[intNumSnow];
  float[] arrSnowDia = new float[intNumSnow];
  float[] arrSnowSpeed = new float[intNumSnow];
  // player
  float fltPosX;
  float fltPosY;
  float fltDia;
  float fltSpeed;
  // inputs
  boolean boolW;
  boolean boolS;
  boolean boolD;
  boolean boolA;
  boolean boolUp;
  boolean boolDown;
  boolean boolMouseClick;
  // general
  int intLives;
  float fltTime;
  float fltScale;
  float fltStrokeWeight;

  /**
   * settings function
   * called once
   */
  public void settings() {
    size(850, 650);
  }

  /**
   * setup function
   * called once
   */
  public void setup() {
    // general
    background(0, 0, 0);
    // init general
    intLives = 3;
    fltTime = 1;
    fltScale = width * height;
    fltStrokeWeight = fltScale / 200000;
    // init player
    fltPosX = width / 2;
    fltPosY = height / 2;
    fltDia = fltScale / 20000;
    fltSpeed = fltScale / 40000;
    // init arrays
    for (int i = 0; i < intNumSnow; i++) {
      initSnow(i);
    }
  }

  /**
   * draw function
   * called repeatedly
   */
  public void draw() {
    // game running
    if (intLives > 0) {
      disintegrate();
      noStroke();
      snow();
      player();
      lives();
    }
    // game over
    else {
      background(255, 255, 255);
      fill(0, 0, 0);
      textSize(fltScale / 10000);
      text("Game Over", (width / 2) - (textWidth("Game Over") / 2), height / 2);
    }
  }

  /**
   * disintegration effect
   * called on command
  */
  private void disintegrate() {
    // customize lines
    stroke(0, 0, 0);
    strokeWeight(fltStrokeWeight);
    // random lines
    for (int i = 0; i <= 30; i++) {
      line(random(width), random(height), random(width), random(height));
    }
  }

  /**
   * snow code
   * called on command
  */
  private void snow() {
    for (int i = 0; i < intNumSnow; i++) {
      if (arrSnowHide[i] == false) {
        // visual
        fill(255, 255, 255);
        ellipse(arrSnowX[i], arrSnowY[i], arrSnowDia[i], arrSnowDia[i]);
        // player collision
        if (dist(fltPosX, fltPosY, arrSnowX[i], arrSnowY[i]) < arrSnowDia[i] + fltDia) {
          explosion(i);
          intLives -= 1;
        }
        // mouse collision
        if (boolMouseClick) {
          if (dist(mouseX, mouseY, arrSnowX[i], arrSnowY[i]) < arrSnowDia[i] + fltDia) {
            explosion(i);
          }
        }
      }
      // movement
      arrSnowY[i] += arrSnowSpeed[i] * fltTime;
      if (arrSnowY[i] > height) {
        initSnow(i);
      }
    }
  }
  
  /**
   * snow explosion
   * called on command
   * @param index array index
  */
  private void explosion(int index) {
    // visual
    fill(255, 0, 0);
    ellipse(arrSnowX[index], arrSnowY[index], arrSnowDia[index] * 5, arrSnowDia[index] * 5);
    // hide snow
    arrSnowHide[index] = true;
  }

  /**
   * initialize snow
   * called on command
   * @param index array index
  */
  private void initSnow(int index) {
    arrSnowHide[index] = false;
    arrSnowX[index] = random(width);
    arrSnowY[index] = 0;
    arrSnowDia[index] = random(fltDia / 4, fltDia);
    arrSnowSpeed[index] = random(fltSpeed / 4, fltSpeed);
  }

  /**
   * player code
   * called on command
  */
  private void player() {
    // visual
    fill(0, 255, 255);
    ellipse(fltPosX, fltPosY, fltDia, fltDia);
    // inputs
    if (keyPressed) {
      // control time
      if (boolUp && fltTime > 0) {
        fltTime -= 0.01;
      }
      if (boolDown && fltTime < 2) {
        fltTime += 0.01;
      }
      // movement
      if (boolW) {
        fltPosY -= fltSpeed;
      }
      if (boolS) {
        fltPosY += fltSpeed;
      }
      if (boolD) {
        fltPosX += fltSpeed;
      }
      if (boolA) {
        fltPosX -= fltSpeed;
      }
    }
    // reset time
    if (!boolUp && !boolDown) {
      fltTime = 1;
    }
  }
  
  /**
   * lives code
   * called on command
  */
  private void lives() {
    // init variables
    float guiLifeX = fltScale / 10000;
    float guiLifeY = fltScale / 10000;
    // display lives
    fill(255, 0, 0);
    for (int i = 0; i < intLives; i++) {
      rect(guiLifeX + (i * fltDia * 2), guiLifeY, fltDia, fltDia);
    }
  }

  /**
   * keyPressed function
   * called on keyboard pressed
  */
  public void keyPressed() {
    // arrow keys
    if (keyCode == UP) {
      boolUp = true;
    }
    else if (keyCode == DOWN) {
      boolDown = true;
    }
    // wasd
    else if (key == 'w') {
      boolW = true;
    }
    else if (key == 's') {
      boolS = true;
    }
    else if (key == 'd') {
      boolD = true;
    }
    else if (key == 'a') {
      boolA = true;
    }
  }

  /**
   * keyReleased function
   * called on keyboard released
  */
  public void keyReleased() {
    // arrow keys
    if (keyCode == UP) {
      boolUp = false;
    }
    else if (keyCode == DOWN) {
      boolDown = false;
    }
    // wasd
    else if (key == 'w') {
      boolW = false;
    }
    else if (key == 's') {
      boolS = false;
    }
    else if (key == 'd') {
      boolD = false;
    }
    else if (key == 'a') {
      boolA = false;
    }
  }

  /**
   * mousePressed function
   * called on mouse pressed
  */
  public void mousePressed() {
    boolMouseClick = true;
  }

  /**
   * mouseReleased function
   * called on mouse released
  */
  public void mouseReleased() {
    boolMouseClick = false;
  }
}