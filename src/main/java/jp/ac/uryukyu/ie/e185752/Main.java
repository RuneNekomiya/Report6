package jp.ac.uryukyu.ie.e185752;
import java.util.Scanner;

/**
 * - [ ] Main
 */
public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("入力:Main()");//未実装
        String str = scan.next();

        HeroClass hero = new HeroClass();

        int level = 1;
        while(level != -1){
            BattleSystem battle = new BattleSystem(hero, level);
            level = battle.BattleDooome();
        }
        System.out.println(hero.name + "の冒険はここで終わってしまったよ〜(^^)b");
    }//finished method to main()
}
