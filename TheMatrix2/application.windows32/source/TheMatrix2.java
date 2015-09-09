import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TheMatrix2 extends PApplet {

// the matrix animation
// by jackaon servheen
// CC-BY-NC-SA 2.0

// Size of each cell in the grid, ratio of window size to video size
int videoScale = 13;
int cols, rows;

String text = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM,./;'[]\\=-!@#$%^&*()_+{}|:\"<>?``````````";
String text2 = text + "                                           ";

PFont f;

char buffer[][];
int colors[][];
float zoom[][];

int colora = color(0,255,0);
int colorb = color(255,255,255);

float rotx,roty,rotz;
float xnoise,ynoise,znoise;
float nspeed = 0.0001f;

public void setup() {
  size(displayWidth,displayHeight,P3D);  
  f = loadFont("Courier-Bold-13.vlw");
  // Set up columns and rows
  cols = width/videoScale;
  rows = height/videoScale + 2;
  buffer = new char[cols][rows];
  colors = new int[cols][rows];
  zoom = new float[cols][rows];
  for (int x = 0; x < cols; x++) {
    for (int y = 0; y < rows; y++) {
      buffer[x][y] = ' ';
      colors[x][y] = colora;
    }
  }
  noCursor();
  //textFont(f);
}

public boolean sketchFullScreen() {return true;}
int a = 0;

public void draw() {
  a+=6;
  if (a > videoScale) {
    a = 0;
    //shift array up
    for (int i = 0; i < cols; i++) {
      for (int j = 1; j < rows; j++) {
        buffer[i][j-1] = buffer[i][j];
        colors[i][j-1] = colors[i][j];
        zoom[i][j-1] = zoom[i][j];
      }
    }
    //add new line
    for (int i = 0; i < cols; i++) {
      if (buffer[i][rows - 2] == ' ' && random(1) > 0.3f) {
        buffer[i][rows-1] = ' ';
      } else {
        buffer[i][rows-1] = text2.charAt((int)random(text2.length()));
      }
      if (colors[i][rows - 2] == colorb && random(1) > 0.6f) {
        colors[i][rows-1] = colorb;
      } else {
        colors[i][rows-1] = (random(1) < 0.9f ? colora : colorb);
      }
      if (buffer[i][rows - 2] == ' ') {
        zoom[i][rows-1] = random(-125,125);
      } else {
        zoom[i][rows-1] = zoom[i][rows-2];
      }
    }
    //random character changes
    colors[(int)random(cols)][(int)random(rows)] = color(255,0,0);
    colors[(int)random(cols)][(int)random(rows)] = color(0,255,0);
    colors[(int)random(cols)][(int)random(rows)] = color(255,255,255);
    buffer[(int)random(cols)][(int)random(rows)] = text2.charAt((int)random(text2.length()));
  }
  //3d rotate
  /*xnoise += nspeed;
  ynoise += nspeed;
  znoise += nspeed;
  rotx += (noise(xnoise)-0.5) * 0.001;
  roty += (noise(ynoise)-0.5) * 0.001;
  rotz += (noise(znoise)-0.5) * 0.0001;*/
  
  xnoise += 0.0032f;
  ynoise += 0.0015f;
  znoise += 0.0005f;
  rotx = sin(xnoise) * 0.06f;
  roty = sin(ynoise) * 0.09f;
  rotz = sin(znoise) * 0.01f;
  
  rotateX(rotx);
  rotateY(roty);
  rotateZ(rotz);
  scale(1.5f);
  translate(-width/4,-height/4);
  //draw it all
  background(0);
  for (int x = 0; x < cols; x++) {
    for (int y = 0; y < rows; y++) {
      char c = buffer[x][y];
      if (c != ' ') {
        translate(0,0,zoom[x][y]);
        fill(colors[x][y]);
        //if (c == '`') c = text.charAt((int)random(text.length()));
        text(c,x*videoScale,height - (y*videoScale-a));
        translate(0,0,-zoom[x][y]);
      }
    }
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#000000", "--hide-stop", "TheMatrix2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
