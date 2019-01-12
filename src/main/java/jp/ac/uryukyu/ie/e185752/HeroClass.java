package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] HeroClass
 */
public class HeroClass {
    String name;
    private int maxHp;
    int hp;
    int attack;
    int armor;
    boolean defenceFrag = false;

    HeroClass(){
        name = "斎藤";
        maxHp = 30;
        hp = 30;
        attack = 10;
        armor = 3;
    }


    public void decreaseHp(int damage){
        if(defenceFrag){//防御していた場合
            System.out.println("しかし" + name + "は防御していたためダメージを受けない!");
            //処理終了してね！
        }
        else if(damage == 0){   //ノーダメージの場合。悲しいね。
            System.out.println(name + "は涼しい顔をして笑っている!");
        }
        else {//防御していなかった場合
            hp -= damage;
            if (hp > 0) {
                System.out.println(name + "の体力は" + hp + "に減ってしまった");
            } else {
                System.out.println(name + "は死んでしまった へ(^A^)9");
            }
        }
    }
    public void increaseHp(int heal){
        hp += heal;
        System.out.println(name + "の体力は" + hp + "に増えた!!");
        System.out.println(name + "は" + heal + "だけ回復した!");
        System.out.println("今の体力は" + hp + "だ!");
    }
    public boolean canMove(){
        if(hp>0){return true;}
        else{
            System.out.println("しかし" + name + "は死んでいる!!");
            return false;
        }
    }


    public void attack(EnemyDictionary enemy){//攻撃してダメージを算出するだけ。HP損傷処理は別で行う
        if(canMove()){
            int damage = (attack - enemy.armor);
            System.out.print(name + "の攻撃！" + damage + "のダメージを与えた! ");
            enemy.decreaseHp(damage);
        }
    }
    public void guard(){
        if(canMove()){
            defenceFrag = true;
            System.out.println(name + "は防御した!");
        }
    }
    public void heal(){
        if(canMove()) {
            int healPoint = maxHp / 10;
            increaseHp(healPoint);
        }
    }
}
