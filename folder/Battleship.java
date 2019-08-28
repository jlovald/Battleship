class Battleship {

  final static boolean TEST = true;
  final int LENGTH = 10;
  final int HEIGHT = 10;

  public static void main(String[] args) {
    if(TEST) {

    }
  }


}

class Grid{

  final int length, height;
  Coordinate[][] grid;
  //  Player
  Grid(int length, int height) {
    this.length = length;
    this.height = height;
  }

  void generateGrid() {
    grid = new Coordinate[height][length];
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < length; j++) {
        grid[i][j] = new Coordinate(i,j);
      }
    }
  }

  void print(){
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < length; j++) {
        //if(grid[i][j])
      }
      System.out.print("\n");
    }
  }
}


abstract class Ship {

  protected Coordinate[] cords;

  Ship(char orientation, Coordinate origin, int length) {
    this.orientation = orientation;
    this.origin = origin;
    this.length = length;
    cords = new Coordinate[length];
  }

  protected char orientation; //  N, E, S, W
  protected Coordinate origin;
  protected int length;

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

  Ship hit() {
    taken_fire = true;
    if(ship != null) {
      return null;
    } else {

      //  Should you sink a ship here?
      return getShip();
    }
  }

  boolean setShip(Ship ship) {
    if(ship != null) {
      return false;
    } else {
      this.ship = ship;
    }
      return true;//placeholder
  }




}
