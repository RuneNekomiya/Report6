package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] Main
 * Battleをたくさん引き起こします.
 */
public class Main {
    public static void main(String[] args){
        /* 未実装もいいところなので一旦凍結. いつかつかえるようになるといいな
        Scanner scan = new Scanner(System.in);
        System.out.println("入力:Main()");
        String str = scan.next();
        */
        BattleSystem battle;
        HeroClass hero = new HeroClass();

        int level = 1;
        System.out.print("戦闘中はコマンドを押していきます。\n");
        hero.commandHelp();
        while(level != -1){
            battle = new BattleSystem(hero, level);
            level = battle.battleDooome();
        }
        System.out.print(hero.getName() + "の冒険はここで終わってしまったよ〜(^^)b\n");
    }
}
