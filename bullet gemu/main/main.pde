import ddf.minim.*;
Player player;
Controler c;
Boss b;
Minim m;
AudioPlayer lobby,b_1,b_2,sc,st,bt;
PImage BG_1,BG_2,BG_3,BG_4,BG_5,boss,bullet,Kei,Aris,player_img,side,bullet_2,bullet_3,enemy,enemy2;
boolean mus;
void setup() {
    m = new Minim(this);
    mus=false;
    size(1600,900);
    frameRate(60);
    player = new Player(800,800);
    c=new Controler();
    b=new Boss();
    BG_1=loadImage("image\\kei.jpg");
    BG_2=loadImage("image\\Ark.png");
    BG_3=loadImage("image\\Ark-2.png");
    BG_4=loadImage("image\\division.jpg");
    BG_5=loadImage("image\\GG.png");
    boss=loadImage("image\\boss.png");
    bullet=loadImage("image\\bullet.png");
    bullet_2=loadImage("image\\bullet_2.png");
    bullet_3=loadImage("image\\bullet_3.png");
    enemy=loadImage("image\\enemy.png");
    enemy2=loadImage("image\\enemy2.png");
    player_img=loadImage("image\\player.png");
    Kei=loadImage("image\\Kei.png");
    Aris=loadImage("image\\Aris.png");
    side=loadImage("image\\side.png");
    lobby=m.loadFile("bgm\\lobby.mp3");
    b_1=m.loadFile("bgm\\battle1.mp3");
    b_2=m.loadFile("bgm\\battle2.mp3");
    sc=m.loadFile("SE\\sc.mp3");
    st=m.loadFile("SE\\st.mp3");
    bt=m.loadFile("SE\\bt.mp3");
    textSize(80);
    textAlign(CENTER,CENTER);
    lobby.play();
}
void draw() {
    player.Move();
    background(0,0,0);
    fill(255);
    if(c.PC==0){
      image(BG_1,0,0);
      for(int i=0;i!=3;i+=1){
      Buttom n=c.getButtom(i);
      if(c.BC==i)  text(">"+n.text,n.x,n.y);
      else text(n.text,n.x,n.y);
      }
    }
    else if(c.PC==1){
      image(BG_2,0,0);
      for(int i=3;i!=5;i+=1){
      Buttom n=c.getButtom(i);
      String s="";
      if(i==3)  for(int k=c.BGM;k!=0;k-=1) s=s+"▌";
      if(i==4)  for(int k=c.SE;k!=0;k-=1) s=s+"▌";
      if(c.BC==i-3)  text(">"+n.text+":"+s,n.x,n.y);
      else text(n.text+":"+s,n.x,n.y);
      }
    }
    else if(c.PC==2){
      if(player.S==1){
        st.pause();
        st.play();
        st.rewind();
        player.S=0;
      }
      if(b.S==1){
        sc.pause();
        sc.play();
        sc.rewind();
        b.S=0;
      }
      if(b.SCC!=0&&!mus){
        b_1.pause();
        b_2.play();
        mus=true;
      }
      if(b.SCC<2)image(BG_4,160,0);
      else  image(BG_3,160,0);
      image(side,0,0);
      for(int i=player.bullets.size()-1;i >=0 ;i-=1){
        if(player.bullets.get(i).type==0)
          image(bullet,(player.bullets.get(i).x)-15,(player.bullets.get(i).y)-15);
        if(player.bullets.get(i).isTouch(b.x,b.y)){
          player.bullets.remove(i);
          b.HP-=1;
        }
        else{
          for(int k=b.enemys.size()-1;k >=0 ;k-=1){
            if(player.bullets.get(i).isTouch(b.enemys.get(k).x,b.enemys.get(k).y)&&b.enemys.get(k).type!=-1){
              player.bullets.remove(i);
              b.enemys.get(k).hp-=1;
              break;
            }
          }
        }
      }
     for(int i=0;i < b.enemys.size();i+=1){
       int bx=b.enemys.get(i).x-25,by=b.enemys.get(i).y-50;
        if(b.enemys.get(i).type==0)image(enemy , bx , by);
        if(b.enemys.get(i).type==1)image(enemy2 , bx , by);
        if(b.enemys.get(i).type!=-1){
          int hp=b.enemys.get(i).hp*46/b.enemys.get(i).maxhp;
          fill(178, 69, 150);
          rect(bx,by-5,50,10);
          fill(147, 35, 124);
          rect(bx+2,by-4,hp,8);
        }
      }
      for(int i=0;i < b.enemy_bullets.size();i+=1){
        image(bullet_2 , b.enemy_bullets.get(i).x-15 , b.enemy_bullets.get(i).y-15);
      }
      for(int i=0;i < b.enemy_bullets.size();i+=1){
        if(b.enemy_bullets.get(i).isTouch(player.pos_x,player.pos_y)){
          c.LIFE-=1;
          b.enemy_bullets.clear();
          if(c.LIFE==0)c.PC=4;
          break;
        }
      }
      image(player_img,player.get(0)-52,player.get(1)-71);
      fill(150,150,200);
      if(player.speed_x==5)
      ellipse(player.get(0),player.get(1),15,15);
      
      int hp=b.HP*59/50;
      fill(178, 69, 150);
      rect(500,0,600,20);
      fill(147, 35, 124);
      rect(505,5,hp,10);
      
      textAlign(LEFT,LEFT);
      textSize(75);
      text("Time:"+b.Time/60,50,300);
      String Hp="";
      if(c.LIFE>=0){
        for(int i=0;i!=c.LIFE;i+=1)Hp=Hp+"▌";
        text("HP:"+Hp,50,400);
      }
      String Bomb="";
      for(int i=0;i!=c.bomb;i+=1)Bomb=Bomb+"▌";
      //text("Bomb:\n"+Bomb,50,500);
      textAlign(LEFT,CENTER);
      textSize(25);
      text(b.spell,500,30);
      if(b.bossAct(player.pos_x,player.pos_y))c.PC=3;
      image(boss,b.x-52,b.y-71);
      if(b.view>0){
        textAlign(CENTER,TOP);
        textSize(50);
        fill(147, 35, 124, b.view*255/120); 
        text(b.spell,850,800);
        fill(147, 35, 124,255);
        tint(255, 255, 255, b.view*255/120);
        image(Kei,600,0);
        tint(255, 255, 255, 255);
      }
    }
    else if(c.PC==3){
      background(187, 188, 237);
      image(Aris,500,0);
      textAlign(LEFT,TOP);
      textSize(100);
      text("pan-pankapan!",100,200);
      textSize(200);
      text("you win!",50,450);
    }
    else if(c.PC==4){
      image(BG_5,0,0);
    }
    if(c.PC==3||c.PC==4){
    b_1.pause();
    b_2.pause();
    }  
}
void keyPressed() {
  if(c.PC==0){
     bt.pause();
     bt.play();
     bt.rewind();
    if (keyCode == RIGHT && c.BC < 2)
      c.BC+=1;
    else if(keyCode == LEFT && c.BC > 0)
      c.BC-=1;
    else if(key == 'z'||key == 'Z'){
      if(c.BC==0){
          c.PC=2;
          lobby.pause();
          b_1.play();
      }
      else if(c.BC==1)  {
        c.PC=1;
        c.BC=0;
      }
      else if(c.BC==2)  exit();
    }   
   else if(key == 'x')  c.BC=2;
  }
  else if(c.PC==1){
     bt.pause();
     bt.play();
     bt.rewind();
    if (lobby != null) lobby.setGain((c.BGM - 3) * 5);
    if (b_1 != null) b_1.setGain((c.BGM - 3) * 5);
    if (b_2 != null) b_2.setGain((c.BGM - 3) * 5-10);
    if (bt != null) bt.setGain((c.SE - 3) * 5);
    if (st != null) st.setGain((c.SE - 3) * 5-10);
    if (sc != null) sc.setGain((c.SE - 3) * 5-10);
    if(keyCode ==UP && c.BC > 0)
      c.BC-=1;
    else if(keyCode == DOWN && c.BC < 1)
      c.BC+=1;
    else if(keyCode == RIGHT){
      if(c.BC==0 && c.BGM<5) c.BGM+=1;
      else if(c.BC==1 && c.SE<5)  c.SE+=1;
    }
    else if(keyCode == LEFT){
      if(c.BC==0 && c.BGM>0)  c.BGM-=1;
      else if(c.BC==1 && c.SE>0) c.SE-=1;
    }
    else if(key == 'x'){
      c.BC=0;
      c.PC=0;
    }
  }
  else if(c.PC==2){
    if(key == 'z'||key == 'Z'){
      player.setShoot(true);
      
    }
    if (keyCode == UP)player.setMoveU(true);
    if (keyCode == DOWN)player.setMoveD(true);
    if (keyCode == RIGHT)player.setMoveR(true);
    if (keyCode == LEFT)player.setMoveL(true);
    if(keyCode == SHIFT)player.setMoveS(true);
    if(key == 'p'||key == 'P') c.LIFE=-1;
  }
}
void keyTyped() {
  if(c.PC==3||c.PC==4)exit();
}
void keyReleased() {
  if (c.PC==2) {
    if(key == 'z'||key == 'Z')player.setShoot(false);
    if (keyCode == UP)player.setMoveU(false);
    if (keyCode == DOWN)player.setMoveD(false);
    if (keyCode == RIGHT)player.setMoveR(false);
    if (keyCode == LEFT)player.setMoveL(false);
    if(keyCode == SHIFT)player.setMoveS(false);
  }
}
