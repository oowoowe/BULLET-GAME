import java.util.List;
import java.util.ArrayList;
import java.util.Random;
public class Boss{
  public int HP,SCC,Time,x,y,view,ex,ey,len,con,S;
  public boolean show;
  public String spell;
  public List<enemy> enemys;
  public List<enemy_bullet> enemy_bullets;
  Random rand;
  public Boss(){
    SCC=0;HP=500;Time=7200;
    x=800;y=300;show=false;spell=" ";
    enemys = new ArrayList<enemy>();
    enemy_bullets  = new ArrayList<enemy_bullet>();
    rand = new Random();S=0;
  }
  public boolean bossAct(int px,int py){
    view-=1;
    if(HP<0||Time==0){
      SCC+=1;
      HP=500;
      show=true;
      Time=7200;
      enemys.clear();
      enemy_bullets.clear();S=1;
    }
    for(int i=enemys.size()-1;i>=0;i-=1){
      if(enemys.get(i).hp<0&&enemys.get(i).type!=-1){
        HP-=10;
        enemys.get(i).type=-1;
      } 
    }
    for(int i=enemy_bullets.size()-1;i>=0;i-=1){
      enemy_bullets.get(i).move();
      if(enemy_bullets.get(i).isOutOfRange()) enemy_bullets.remove(i);
    }
    for(int i=enemy_bullets.size()-1;i>=0;i-=1){
      enemy_bullets.get(i).life-=1;
      if(enemy_bullets.get(i).life<=0)  enemy_bullets.remove(i);
    }
    if(SCC==0){
     if(Time%60==0){
      ex=rand.nextInt(400)+600;
      ey=rand.nextInt(100)+100;
      double W=Math.sqrt((x-px)*(x-px)+(y-py)*(y-py));
      int wx=(int)(((double)(px-x))*15/W),wy=(int)(((double)(py-y))*15/W);
      enemy_bullets.add(new enemy_bullet(x,y,wx,wy));
    }
    double W=Math.sqrt((x-ex)*(x-ex)+(y-ey)*(y-ey));
    if(W>5){
      x-=(int)(((double)(x-ex))*5/W);
      y-=(int)(((double)(y-ey))*5/W);
    }
    else{
      x=ex;y=ey;
    }
      if(Time%600==0){
        enemys.clear();
        for(int i=0;i!=8;i++)
        enemys.add(new enemy(x,y,15));
      }
      for(int i=enemys.size()-1;i>=0;i-=1){
        enemys.get(i).setPos((int)(x+Math.cos((Time+15.7*i)/20)*150),(int)(y+Math.sin((Time+15.7*i)/20)*150));
        if(Time%3==0&&enemys.get(i).type==0){
          W=Math.sqrt((enemys.get(i).x-px)*(enemys.get(i).x-px)+(enemys.get(i).y-py)*(enemys.get(i).y-py));
          int wx=(int)(((double)(enemys.get(i).x-x))*15/W),wy=(int)(((double)(enemys.get(i).y-y))*15/W);
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,wx,wy));
        }
      }
    }
    if(SCC==1){
      x=800;y=300;
      if(show){
        spell="Awaken (Divi::sion)";
        view=120;
        show=false;
      }
      if(Time%180==0){
        enemys.add(new enemy(550,0,20,1));
        enemys.add(new enemy(650,0,10));
        enemys.add(new enemy(750,0,10));
        enemys.add(new enemy(850,0,10));
        enemys.add(new enemy(950,0,10));
        enemys.add(new enemy(1050,0,20,1));
      }
      if(Time%60==0){
        for(int i=enemys.size()-1;i>=0;i-=1){
          double W=Math.sqrt((enemys.get(i).x-px)*(enemys.get(i).x-px)+(enemys.get(i).y-py)*(enemys.get(i).y-py));
          int wx=-(int)(((double)(enemys.get(i).x-px))*15/W),wy=-(int)(((double)(enemys.get(i).y-py))*15/W);
          if(enemys.get(i).type==0)
            enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,wx,wy));
        }
      }
      if(Time%60==30){
        for(int i=enemys.size()-1;i>=0;i-=1){
          if(enemys.get(i).type!=-1){
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,0,4,50));
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,0,-4,50));
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,4,0,50));
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,-4,0,50));
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,2,2,50));
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,-2,2,50));
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,2,-2,50));
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,-2,-2,50));
          }
        }
      }
      for(int i=enemys.size()-1;i>=0;i-=1){
        enemys.get(i).setPos(enemys.get(i).x,enemys.get(i).y+1);
        enemys.removeIf(enemys -> enemys.isOutOfRange());
      }
    }
    if(SCC==2){
      if(show){
        spell="Atrahasis->instantiate";
        view=120;
        show=false;
      }
      if(Time%2400==0){
        enemys.clear();
        for(int i=0;i!=4;i++)
          enemys.add(new enemy(x,y,10));
        for(int i=0;i!=24;i++)
          enemys.add(new enemy(x,y,30,1));
        len=50;
        con=0;
      }
      if(Time%2400==1200)con=1;
      if(Time%12==0&&con==0)len+=1;
      else if(Time%12==0&&con==1)len-=1;
      for(int i=0;i!=4;i++){
        enemys.get(i).setPos((int)(x+Math.cos((Time+31.4*(i+1))/20)*150),(int)(y+Math.sin((Time+31.4*(i+1))/20)*150));
        if(Time%30>20){
          double W=Math.sqrt((enemys.get(i).x-x)*(enemys.get(i).x-x)+(enemys.get(i).y-y)*(enemys.get(i).y-y));
          int wx=-(int)(((double)(enemys.get(i).x-x))*5/W),wy=-(int)(((double)(enemys.get(i).y-y))*5/W);
          enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,wx,wy));
        }
        for(int k=0;k!=6;k++){
          int time=2400-Time;
          enemys.get(i*6+k+4).setPos((int)(enemys.get(i).x+Math.cos((time+20.93*k)/20)*len),(int)(enemys.get(i).y+Math.sin((time+20.93*k)/20)*len));
          if(Time%30>25){
            double W=Math.sqrt((enemys.get(i*6+k+4).x-enemys.get(i).x)*(enemys.get(i*6+k+4).x-enemys.get(i).x)+(enemys.get(i*6+k+4).y-enemys.get(i).y)*(enemys.get(i*6+k+4).y-enemys.get(i).y));
            int wx=-(int)(((double)(enemys.get(i*6+k+4).x-enemys.get(i).x))*5/W),wy=-(int)(((double)(enemys.get(i*6+k+4).y-enemys.get(i).y))*5/W);
            enemy_bullets.add(new enemy_bullet(enemys.get(i*6+k+4).x,enemys.get(i*6+k+4).y,wx,wy));
          }
        }
      }
    }
    if(SCC==3){
      if(show){
        spell="Divi::sion.FierceOffensive()";
        view=120;
        show=false;
        enemys.clear();
        enemys.add(new enemy(700,150,2000));
        enemys.add(new enemy(900,150,2000));
        for(int i=0;i!=12;i+=1)
        enemys.add(new enemy(0,0,20));
      }
      if(Time%1200==0){
        enemys.clear();
        enemys.add(new enemy(650,150,2000));
        enemys.add(new enemy(950,150,2000));
        enemys.add(new enemy(500,300,2000));
        enemys.add(new enemy(1100,300,2000));
        for(int i=0;i!=12;i+=1)
          enemys.add(new enemy(0,0,20,1));
      }
     for(int i=0;i!=16;i+=1){
       if(enemys.get(i).type==1)enemys.get(i).setPos((int)(px+Math.cos((Time+15.7*i)/20)*150),(int)(py+Math.sin((Time+15.7*i)/20)*150));
       if(Time%180==0&&enemys.get(i).type==1){
         enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,1,0));
         enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,0,1));
         enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,-1,0));
         enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,0,-1));
       }
       if(enemys.get(i).type==0){
         if(i==0||i==2){
           enemys.get(i).setPos(enemys.get(i).x-3,enemys.get(i).y);
           if(enemys.get(i).x<500)enemys.get(i).x=1100;
         }
         if(i==1||i==3){
           enemys.get(i).setPos(enemys.get(i).x+3,enemys.get(i).y);
           if(enemys.get(i).x>1100)enemys.get(i).x=500;
         }
         if(Time%50>45){
           double W=Math.sqrt((enemys.get(i).x-px)*(enemys.get(i).x-px)+(enemys.get(i).y-py)*(enemys.get(i).y-py));
           int wx=-(int)(((double)(enemys.get(i).x-px))*5/W),wy=-(int)(((double)(enemys.get(i).y-py))*5/W);
           enemy_bullets.add(new enemy_bullet(enemys.get(i).x,enemys.get(i).y,wx,wy));
         }
       }
     }
      
    }
    Time-=1;
    if(SCC==4) return true;
    return false;
  }
}
class enemy{
  public int x,y,hp,range,type,maxhp;
  public enemy(int nx,int ny,int h){
     x=nx;y=ny;hp=h; maxhp=h;
     range=105;type=0;
  }
  public enemy(int nx,int ny,int h,int t){
     x=nx;y=ny;hp=h; maxhp=h;
     range=105;type=t;
  }
  public void setPos(int nx,int ny){
    x=nx;y=ny;
  }
  public boolean isOutOfRange(){
    if(x<500||x>1100||y>900||y<0)return true;
    return false;
  }
  public boolean isTouch(int nx,int ny){
    double r = Math.sqrt((nx-x)*(nx-x)+(ny-y)*(ny-y));
    if(r<range) return true;
    return false;
  }
}
class enemy_bullet{
  public int x,y,range,sx,sy,life;
  public enemy_bullet(int nx,int ny,int nsx,int nsy,int l){
    x=nx;y=ny;range=7;sx=nsx;sy=nsy;life=l;
  }
  public enemy_bullet(int nx,int ny,int nsx,int nsy){
    x=nx;y=ny;range=7;sx=nsx;sy=nsy;life=3600;
  }
  public boolean isOutOfRange(){
    if(x<500||x>1100||y>900||y<0)return true;
    return false;
  }
  public boolean isTouch(int nx,int ny){
    double r = Math.sqrt((nx-x)*(nx-x)+(ny-y)*(ny-y));
    if(r<range) return true;
    return false;
  }
  public void move(){
    x+=sx;y+=sy;
  }
}
