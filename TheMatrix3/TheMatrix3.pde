// the matrix animation
// by jackaon servheen
// CC-BY-NC-SA 2.0

// Size of each cell in the grid, ratio of window size to video size
int videoScale = 13;
int cols, rows;

String text = "00001111";String text2 = text + "`````````````````````        ";
//String text = "1234567890!@#$%^&*()qwertyuioplkjhgfdsazxcvbnm,.<>/?;\"':{[}]_-+=\\|~";String text2 = text + "`````         ";
//String text = "|\\/[{]}()!1iIl";String text2 = text + "       ";


PFont f;

char buffer[][];
int colors[][];
float zoom[][];

int colora = color(0,255,0);
int colorb = color(255,255,255);

void setup() {
  size(displayWidth,displayHeight,P3D);  
  f = loadFont("Courier-Bold-13.vlw");
  // Set up columns and rows
  cols = width/videoScale;
  rows = height/videoScale * 2;
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
  //font(f);
}

boolean sketchFullScreen() {return true;}
int a = 0;

void draw() {
  a+=9;
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
      if (buffer[i][rows - 2] == ' ' && random(1) > 0.3) {
        buffer[i][rows-1] = ' ';
      } else {
        buffer[i][rows-1] = text2.charAt((int)random(text2.length()));
      }
      if (colors[i][rows - 2] == colorb && random(1) > 0.6) {
        colors[i][rows-1] = colorb;
      } else {
        colors[i][rows-1] = (random(1) < 0.9 ? colora : colorb);
      }
      if (buffer[i][rows - 2] == ' ') {
        zoom[i][rows-1] = random(-600,0);
      } else {
        zoom[i][rows-1] = zoom[i][rows-2];
      }
    }
  }
  //3d rotate
  rotateX(radians(60));
  
  scale(1.5);
  translate(-width/4,-height/4);
  //draw it all
  background(0);
  for (int x = 0; x < cols; x++) {
    for (int y = 0; y < rows; y++) {
      char c = buffer[x][y];
      if (c != ' ') {
        translate(0,0,zoom[x][y]);
        fill(colors[x][y]);
        if (c == '`') c = text.charAt((int)random(text.length()));
        text(c,x*videoScale,height - (y*videoScale-a));
        translate(0,0,-zoom[x][y]);
      }
    }
  }
}
