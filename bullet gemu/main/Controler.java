public class Controler{
  public int PC,BC,SE,BGM,BG,LIFE,bomb;
  public Buttom [] buttom;
  public Controler(){
    PC=0;BC=0;
    SE=3;BGM=3;
    BG=0;LIFE=5;
    bomb=5;
    String [] text ={"Start", "Setting", "Exit", "BGM", "SE"};
    int [][] textPos ={{267,800},{800,800},{1334,800},{400,225},{400,450}};
    buttom = new Buttom[5];
    for(int i=0;i!=5;i+=1){
       buttom[i] = new Buttom(text[i], textPos[i][0], textPos[i][1]);
    }
  }
  public Buttom getButtom(int n){
    return buttom[n];
  }
}
class Buttom{
  public String text;
  public int x,y;
  Buttom(String n,int nx,int ny){
    text=n;x=nx;y=ny;
  }
}
