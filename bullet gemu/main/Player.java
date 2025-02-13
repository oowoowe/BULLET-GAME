import java.util.List;
import java.util.ArrayList;

public class Player {
    public int pos_x,pos_y,S;
    public int speed_x,speed_y,time;
    public List<player_bullet> bullets;
    boolean left,right,up,down,shoot;
    
    public Player(int x,int y){
       pos_x=x;
       pos_y=y;
       left=false;
       right=false;
       up=false;
       down=false;
       shoot=false;
       speed_x=10;
       speed_y=10;
       bullets = new ArrayList<>();
       time=0;
       S=0;
    }
    public void setMoveS(boolean n){
      if(n){speed_x=5;speed_y=5;}
      else {speed_x=10;speed_y=10;}
    }
    public void setMoveL(boolean l){left=l;}
    public void setMoveR(boolean r){right=r;}
    public void setMoveU(boolean u){up=u;}
    public void setMoveD(boolean d){down=d;}
    public void setShoot(boolean s){shoot=s;}
    public void Move(){
      if(left&&pos_x>500)pos_x-=speed_x;
      if(right&&pos_x<1100)pos_x+=speed_x;
      if(down&&pos_y<900)pos_y+=speed_y;
      if(up&&pos_y>0)pos_y-=speed_y;
      time+=1;
      if(shoot){
        if(time>10){
          S=1;
          if(speed_x==5){
            bullets.add(new player_bullet(pos_x+10, pos_y-10,100,0,-30,0));
            bullets.add(new player_bullet(pos_x-10, pos_y-10,100,0,-30,0));
            bullets.add(new player_bullet(pos_x, pos_y-15,100,0,-30,0));
            bullets.add(new player_bullet(pos_x+20, pos_y-5,100,0,-30,0));
            bullets.add(new player_bullet(pos_x-20, pos_y-5,100,0,-30,0));
          }
          else{
          bullets.add(new player_bullet(pos_x+5, pos_y-50,100,-10,-30,0));
          bullets.add(new player_bullet(pos_x-5, pos_y-50,100,10,-30,0));
          bullets.add(new player_bullet(pos_x+10, pos_y-50,100,-20,-30,0));
          bullets.add(new player_bullet(pos_x-10, pos_y-50,100,20,-30,0));
          bullets.add(new player_bullet(pos_x, pos_y-50,100,0,-30,0));
          }
          time=0;
        }
      }
      for(int i=0;i < bullets.size();i+=1){
        bullets.get(i).Move();
        bullets.removeIf(bullet -> bullet.isOutOfRange());
      }
    }
    public int get(int n){
      if(n==0) return pos_x;
      else return pos_y;
    }
}

class player_bullet{
  public int x,y,range,sx,sy,type;
  public player_bullet(int nx,int ny,int r,int nsx,int nsy,int t){
    x=nx;y=ny;range=r;
    sx=nsx;sy=nsy;
    type=t;
  }
  public boolean isTouch(int nx,int ny){
    double r = Math.sqrt((nx-x)*(nx-x)+(ny-y)*(ny-y));
    if(r<range) return true;
    return false;
  }
  public boolean isOutOfRange(){
    if(x<500||x>1100||y>900||y<0)return true;
    return false;
  }
  public void Move(){
    x+=sx;
    y+=sy;
  }
}
