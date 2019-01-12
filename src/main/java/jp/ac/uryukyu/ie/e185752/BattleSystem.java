package jp.ac.uryukyu.ie.e185752;
import java.util.Scanner;

/**
 * 一度の戦闘における諸ステータスを保存する
 */
public class BattleSystem {
    Scanner scan = new Scanner(System.in);
    HeroClass hero;
    EnemyDictionary enemy;
    int round;
    int level;
    boolean doWin;

    BattleSystem(HeroClass hero , int level){//キャラの生成
        this.level = level;
        round = 0;
        this.hero = hero;
        enemy = new EnemyDictionary(level);
        System.out.println(enemy.name + "レベル" + level + "が出没！殲滅せよ" + hero.name + "!!");
    }

    public int BattleDooome(){//戦闘中の流れ『バットルドォーム！！』
        while(enemy.hp > 0 && hero.hp > 0){
            round++;
            System.out.println(round + "ターン目!");
            System.out.println("入力して:BattleDome()");
            String str = scan.next();       //後で直せ
            System.out.println(str);        //後で直せ
            hero.attack(enemy);//勇者のターン
            enemy.attack(hero);//敵のターン
        }

        //勝ったか否かの処理
        if(enemy.hp <=0 ){
            System.out.println("勇者" + hero.name + "の勝利!!");
            return result(true);
        }
        if(hero.hp <= 0){
            System.out.println("勇者" + hero.name + "は負けてしまった!!");
            return result(false);
        }else{
            System.out.println("想定されてないエラーが発生:体力残存判定");
            return result(false);
        }
    }//finished method to BattleDome()

    public int result(boolean doWin){//戦闘終了後の流れ
        if(doWin){ return level+1 ; }
        else     { return -1; }
    }


}

