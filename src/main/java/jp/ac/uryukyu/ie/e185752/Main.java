package jp.ac.uryukyu.ie.e185752;
import java.util.Scanner;

/**
 * - [ ] Main
 */
public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("入力:Main()");
        String str = scan.next();
        /*
        System.out.println(str);
        //heroの作成、これでチートが出来上がったり出来上がらなかったり。
        System.out.println("汝、力を求むか...?");
        String str = scan.next();
        */

        HeroClass hero = new HeroClass();

        int level = 1;
        while(level != -1){
            BattleSystem battle = new BattleSystem(hero, level);
            level = battle.BattleDooome();
        }
    }//finished method to main()
}
