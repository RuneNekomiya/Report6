package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] EnemyClass
 * */
public class EnemyDictionary {
    int level = 0;
    String name;
    private int maxHp;
    int hp;
    int attack;
    int armor;
    boolean defenceFrag = false;

    /**
     * 階層を入手、そこから対応する敵キャラクターをなんかランダムで出して返す。
     * @param level
     */
    EnemyDictionary(int level){
        this.level = level;
        name = "スライムちゃん";
        maxHp = 10 + level*2;
        hp = 10 + level*2;
        attack = 3 + level/2;
        armor = 1 + level/10;
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
    public boolean canMove(){
        if(hp>0){return true;}
        else{
            System.out.println("しかし" + name + "は死んでいる!");
            return false;
        }
    }
    public void attack(HeroClass hero){//攻撃してダメージを算出するだけ。HP損傷処理は別で行う
        if(canMove()){
            int damage = (attack - hero.armor);
            System.out.print(name + "の攻撃! " + damage + "のダメージを与えた! ");
            hero.decreaseHp(damage);
        }
    }
}
