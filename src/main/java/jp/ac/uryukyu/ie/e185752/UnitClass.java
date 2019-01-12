package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] UnitClass
 * (予定)ステータスデータを埋め込む。基盤。
 * (現在)なんかCardVarietyとまとめて作っちゃってもいいんじゃないかと思い始めたんだが...
 *
 */
public class UnitClass {
    String name;
    int maxHp;
    int hp;
    int attack;
    int armor;
    boolean defenceFrag = false;


    public void decreaseHp(int damage){
        //防御していた場合
        if(defenceFrag){
            System.out.println("しかし" + name + "は防御していたためダメージを受けない!");
            return;
        }
        //ダメージが負の数またはノーダメージの場合。基本的に起動しない想定
        if(damage <= 0){
            System.out.println("想定されてないエラーが発生:decreaseHp()");
            return;
        }
        //防御していなかった場合
        hp -= damage;
        if (hp > 0) {
            System.out.println(name + "の体力は" + hp + "に減ってしまった");
        }else{
            System.out.println(name + "は死んでしまった へ(^A^)9");
        }

    }
    public void increaseHp(int heal){
        //回復点が負の数またはノーダメージの場合。基本的に起動しない想定
        //でもTRPGだと回復で死ぬのは稀によくあるからエラーでもない気はする。だがここではエラーだ。私が法律だ。
        if(heal <= 0){
            System.out.println("想定されてないエラーが発生:increaseHp()");
            return;
        }
        //体力が最大体力と等値又は上回る場合。heal連打した製作者がいるんですよ...
        //これやるとmaxHpを削ることにならないか不安なのです！(つまりシャローコピーによるhpがmaxHpと同じ中身になる問題
        if(hp+heal >= maxHp){
            hp = maxHp;
            System.out.println(name + "の体力は満タンになった!");
            return;
        }
        hp += heal;
        System.out.print(name + "は" + heal + "だけ回復した!");
        System.out.println("今の体力は" + hp + "だ!");
    }
    public boolean canMove(){//問題がなければ行動できる。問題があるならfalseだ。
        if(hp<=0){//死んだ奴は普通動けない
            System.out.println("しかし" + name + "は死んでいる!!");
            return false;
        }
        else{ return true; }
    }

    public void attack(UnitClass opponent){//攻撃してダメージを算出するだけ。HP損傷処理は別で行う
        if(canMove()){
            int damage = (attack - opponent.armor);
            if(damage <= 0){
                System.out.println(name + "の攻撃！しかし" + opponent.name + "にダメージを与えられない! ");
                return;//これ以降の処理を禁ずる
            }else{
                System.out.print(name + "の攻撃！" + damage + "のダメージを与えた! ");
                opponent.decreaseHp(damage);
            }
        }
    }
    public void guard(){
        if(canMove()){
            defenceFrag = true;
            System.out.println(name + "は防御した!");
        }
    }
    public void heal(){//最大体力の１割だけ回復(仮)
        if(canMove()) {
            int healPoint = maxHp / 10;
            increaseHp(healPoint);
        }
    }

}
