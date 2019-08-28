class Battleship {
    /*
    TODO: Set max limits, and coordinate names
     */
  final boolean TEST = true;
  final int LENGTH = 10;
  final int HEIGHT = 10;
  public Grid grid;
  public static void main(String[] args) {

    Battleship bs = new Battleship();
    bs.intitialize();

    }


    public void intitialize() {
        if(TEST) {
            grid = new Grid(LENGTH,HEIGHT);
            grid.initTest();
            System.out.println("FIRING AT 0,0");

        }

    }
}




class Grid{



    final boolean TEST = true;
    final boolean DEBUG = false;
  final int length, height; //  SHOld refactor to BOARD_LENGTH and BOARD_HEIGHT
  Coordinate[][] grid;
  //  Player
  Grid(int length, int height) {
    this.length = length;
    this.height = height;
    generateGrid();
  }

  public void initTest() {
      print();
      addShip(0,0,'S',"Carrier");
      addShip(1,0,'S',"Destroyer");
      addShip(2,0,'N',"Frigate");
      print();
      fire(0,0);
      print();
      fire(0,2);
      print();
      fire(0,3);
      fire(0,4);
      fire(0,5);
      print();
      fire(0,1);
  }


  public boolean addShip(int x, int y, char orientation, String ship_class) {

      int len = 0;
      switch(ship_class) {
          case "Carrier":
          len = 4;
          break;
          case "Destroyer":
          len = 3;
          break;
          case "Frigate":
          len = 2;
          break;
      }

      if(x < 0 || x >= length || y < 0 || y >= height) return false;
      Coordinate origin = grid[x][y];
      if(DEBUG) {
          System.out.println("origin x: " + x);
          System.out.println("Origin y: " + y);
      }
      if(isClear(orientation, origin, len)) {
          if(TEST) {
              System.out.println("SPAWNING SHIP");
              Ship s = null;
              switch(ship_class) {
                  case "Carrier":
                  s = new Carrier(orientation,origin,len);
                  break;
                  case "Destroyer":
                   s = new Destroyer(orientation,origin,len);
                    break;
                  case "Frigate":
                   s = new Frigate(orientation,origin,len);
                    break;
              }
              if(s == null ) return false;
              s.setGrid(grid);


          }
      }

      return false;
  }


  public boolean isClear(int orientation,Coordinate origin, int len) {
      int start_x = origin.x; //    can't be bothered to refactor the entire thing
      int start_y = origin.y;   //  Technically correct though
      switch(orientation) {

          case 'N':
            for(int i = 0; i < len;i++) {
                if(!inside(start_x, start_y - i)) {
                    return false;
                }
                //  coordinate y,x
                if(grid[start_y - i][start_x].ship != null) {
                    System.out.println("Coordinate taken.");
                    return false;
                }
            }
          break;
          case 'E':
            for(int i = 0; i < len;i++) {
                if(!inside(start_x - i, start_y)) {
                    return false;
                }
                if(grid[start_y][start_x + i].ship != null) {
                    System.out.println("Coordinate taken.");
                    return false;
                }
            }
          break;
          case 'S':
          for(int i = 0; i < len;i++) {
              if(!inside(start_x, start_y + i)) {
                  return false;
              }
              if(grid[start_y + i][start_x].ship != null) {
                  System.out.println("Coordinate taken.");
                  return false;
              }
          }
          break;
          case 'W':
          for(int i = 0; i < len;i++) {
              if(!inside(start_x + i, start_y)) {
                  return false;
              }
              if(grid[start_y][start_x - i].ship != null) {
                  System.out.println("Coordinate taken.");
                  return false;
              }
          }
          break;
      }

      return true;

  }
  /**
   * Checks if a coordinate is inside the specified grid
   * @param  x x-value
   * @param  y y-value
   * @return   True if inside.
   */
  public boolean inside(int x, int y) {
      if(x < 0 || x >= length) {
          System.out.println("X: " + x);
          System.out.println("ERROR OUTSIDE BOARD");
          return false;
      }
      if(y < 0 || y >= height) {
          System.out.println("Y: " + y);
          System.out.println("ERROR OUTSIDE BOARD");
          return false;
      }
      return true;

  }
  void generateGrid() {
    grid = new Coordinate[height][length];
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < length; j++) {
        grid[i][j] = new Coordinate(i,j);
      }
    }
  }

  /*
    Gets the objective representation of the board.

   */
  String getRepresentation() {
      Coordinate cord;
      String rep = " "; // space to get the corner
      for(int i = 0; i < length;i++) {
          rep+=(char) ('A' + i);
      }
      rep += '\n';
      for(int i = 0; i < height; i++) {
          rep+= i;
        for(int j = 0; j < length; j++) {
            cord = grid[i][j];
            if(DEBUG) {
                System.out.println(i + ", " + j);
            }
            rep += cord.getRepresentation();
        }
        rep+= "\n";
      }

      return rep;
  }

  void print(){
    if(TEST) {

        String s = getRepresentation();
        System.out.print(s);
    } else {
        Coordinate cord;
        for(int i = 0; i < height; i++) {
          for(int j = 0; j < length; j++) {
            cord = grid[i][j];
            System.out.print(cord.getRepresentation());
          }
          System.out.print("\n");
        }
    }

  }

  public boolean fire(int x, int y) {
      if(inside(x,y)) {
          return grid[y][x].fire();

      } else {
          return false;
      }
  }
}


abstract class Ship {

  protected Coordinate[] cords;
  Coordinate[][] grid;
  Ship(char orientation, Coordinate origin, int length) {
    this.orientation = orientation;
    this.origin = origin;
    this.length = length;
    cords = new Coordinate[length];
  }


  protected char orientation; //  N, E, S, W
  protected Coordinate origin;
  protected int length;
  protected int hits = 0;

  public void setGrid(Coordinate[][] grid) {
      this.grid = grid;
      addToBoard();
  }

  public boolean hit() {
     System.out.println("KABOOOOM");
     ++hits;
     if(hits >= length) {
         System.out.println("BLUBLUB");
     }
     return true;
  }

  public void addToBoard() {
      int start_x = origin.x;
      int start_y = origin.y;
      switch(orientation) {

          case 'N':
            for(int i = 0; i < length;i++) {
                Coordinate coordinate = grid[start_y - i][start_x ];
                coordinate.setShip(this);
                cords[i] = coordinate;
            }
          break;
          case 'E':
            for(int i = 0; i < length;i++) {
                Coordinate coordinate = grid[start_y ][start_x + i];
                coordinate.setShip(this);
                cords[i] = coordinate;
            }
          break;
          case 'S':
          for(int i = 0; i < length;i++) {
              Coordinate coordinate = grid[start_y + i][start_x ];
              coordinate.setShip(this);
              cords[i] = coordinate;
          }
          break;
          case 'W':
            for(int i = 0; i < length;i++) {
                Coordinate coordinate = grid[start_y ][start_x - i];
                coordinate.setShip(this);
                cords[i] = coordinate;
            }
          break;
      }
  }

  public char getOrientation() {
    return orientation;
  }

  public Coordinate getOrigin() {
    return origin;
  }

  public int getLength() {
    return length;
  }



}



class Carrier extends Ship{

  Carrier(char orientation, Coordinate origin,int length) {
    super(orientation, origin, length);
  }
}

class Destroyer extends Ship{
  int length = 3;
  Destroyer(char orientation, Coordinate origin,int length) {
    super(orientation, origin, length);
  }
}

class Frigate extends Ship{
  int length = 2;
  Frigate(char orientation, Coordinate origin, int length) {
    super(orientation, origin, length);
  }
}

class Coordinate {
  int x;
  int y;
  Ship ship;
  boolean taken_fire = false;

  Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }


  char getRepresentation() {
    if( ship == null) {
        if(taken_fire) {
          return '*';
        } else {
          return '.';
        }
    } else {
      if(taken_fire) {
        return 'x';
      } else {
        return '-';
      }
    }
    //x ship_not struck - . *
  }
  Ship getShip() {
    return ship;
  }
  Boolean fire() {
      System.out.printf("%d, %d taking fire!!\n",x,y);
      if(taken_fire == false) {
          Ship target = hit();
          System.out.println("TARGET: " + target);
          if( target == null) {
              System.out.println("SPLISH!");
              return false;
          }
          System.out.println("TAKING FIRE!!");
         target.hit();
         return true;
      }

      return false;
  }
  Ship hit() {
    taken_fire = true;
    System.out.println(ship);
    return ship;
      //  Should you sink a ship here?

}


  boolean setShip(Ship ship) {
      this.ship = ship;
      return true;//placeholder
  }

}
