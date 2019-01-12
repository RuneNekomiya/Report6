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
        int selectEnemy = enemyDictionary();
        enemy = new EnemyDictionary(level, selectEnemy);

        System.out.println(enemy.name + "レベル" + level + "が出没！殲滅せよ" + hero.name + "!!");
    }

    public int BattleDooome(){//戦闘中の流れ『バットルドォーム！！』
        while(enemy.hp > 0 && hero.hp > 0){
            round++;
            System.out.println(round + "ターン目!");
            //勇者のターン
            boolean finishedAction = false;
            while(!finishedAction) {
                System.out.println("入力して:勇者の行動");
                String str = scan.next();
                finishedAction = hero.receiveAction(str, enemy);
            }
            //敵のターン
            enemy.attack(hero);

            //ガード判定を消していく
            hero.defenceFrag = false;
            enemy.defenceFrag = false;
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


    public int enemyDictionary(){
        int firstEnemy = 0;
        int secondEnemy = 0;
        int decidedEnemy;

        while(firstEnemy == secondEnemy) {
            /*       一時的に停止するよ！     */
            //if(0<=level && level< 10){//0以上10未満
            firstEnemy = (int) (Math.random() * 2) + 1;
            secondEnemy = (int) (Math.random() * 2) + 1;
            //}
            /*       一時的に停止したよ！     */
        }
        decidedEnemy = enemyPrint(firstEnemy, secondEnemy);
        return decidedEnemy;
    }
    public int enemyPrint(int firstEnemy, int secondEnemy){
        int[] list = new int[2];
        list[0] = firstEnemy;
        list[1] = secondEnemy;

        for(int i = 0; i<list.length; i++){
            switch(list[i]){
                case 0: System.out.println(i+1 + "番, おとおと"); break;
                case 1: System.out.println(i+1 + "番, ぷにたん"); break;
                case 2: System.out.println(i+1 + "番, スライム"); break;
                case 3: System.out.println(i+1 + "番, 野犬"); break;
                case 4: System.out.println(i+1 + "番, おとおと"); break;
                default: break;
            }
        }
        System.out.println("入力して:敵の選択");
        String str = scan.next();
        int charaSheet = Integer.parseInt(str)-1;
        return list[charaSheet];
    }


}

