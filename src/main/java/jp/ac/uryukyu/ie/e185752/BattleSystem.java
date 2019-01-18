package jp.ac.uryukyu.ie.e185752;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 戦闘におけるシステム系を全部つっこみました。キャラ毎のものに関しては他のクラスから参照しています。
 */
public class BattleSystem {
    Scanner scan = new Scanner(System.in);
    int round;
    int level;
    HeroClass hero;
    EnemyDictionary enemy;
    ArrayList<UnitClass> characters = new ArrayList<>();

    /**
     * キャラの生成。外から持ってきたHeroと間に合わせで作ったEnemyをBattleSystemの場に整えます。
     * @param hero
     * @param level
     */
    BattleSystem(HeroClass hero , int level){
        this.level = level;
        round = 0;
        //ヒーローの登録
        this.hero = hero;
        characters.add(hero);
        //エネミーの登録
        int selectEnemy = enemyDictionary();
        enemy = new EnemyDictionary(level, selectEnemy);
        characters.add(enemy);

        System.out.print(enemy.name + "Lv." + level + "が出没！殲滅せよ" + hero.name + "!!\n");
        hero.showStatuses(enemy);
    }

    /**
     * 『バットルドォーム！！』--ボールを相手のゴールにシュート!!!!!
     * 戦闘の流れを格納しています。ターン毎に敵と味方のターンが一個ずつ発生するいかにも代表的なものですね。
     */
    public int battleDooome(){
        while(enemy.hp > 0 && hero.hp > 0){
            round++;
            System.out.print(round + "ターン目!\n");
            //勇者の行動選択
            boolean canGetAction = false;
            while(!canGetAction) {
                System.out.print(hero.name + "はどうする? :\n");
                String str = scan.next();
                canGetAction = hero.receiveAction(str, enemy);
            }
            //敵の行動選択
            enemy.selectEnemyAI(hero);
            //両者ともに行動開始
            whichHeadStart(characters);
        }
        //戦闘後の処理
        return result();
    }

    /**
     * 戦闘結果だ! BattleDooome()に格納してもいい気はしたがごちゃごちゃしてたので隔離しました。
     * @return やったねたえちゃん(敵の)レベルが上がるよ! (自分が強くなるとは言ってない)
     */
    public int result(){
        if(enemy.hp <=0){
            System.out.print("戦闘終了! " + hero.name + "の勝利!\n");
            hero.battleEnd();
            return level+1 ;
        }else if(hero.hp <= 0){
            System.out.print("戦闘終了! " + hero.name + "は負けてしまった!\n");
            return -1;
        }else{
            System.out.print("想定されてないエラーが発生:result()\n");
            return -1;
        }
    }

    /**
     * キャラクターたちの行動順序を選択して行動させるためのメソッド. おそらく一番難産だったもの
     * @param characters 行動するキャラクターたちみんな集まれ^~!
     */
    public void whichHeadStart(ArrayList<UnitClass> characters){
        int playCharacter;
        ArrayList<UnitClass> standByCharacters = new ArrayList<>();
        ArrayList<Integer> priorityNumberList = new ArrayList<>();
        for(int i=0; i<characters.size(); i++){
            standByCharacters.add(characters.get(i));
            int x = characters.get(i).skillPriority;
            priorityNumberList.add(x);
        }
        for(int i=1; i > (-1); i--){
            try {
                do {
                    playCharacter = priorityNumberList.indexOf(i);
                    if (playCharacter != -1) {
                        standByCharacters.get(playCharacter).playSkill();
                        standByCharacters = finishedPlayer(standByCharacters, playCharacter);
                        priorityNumberList.remove(playCharacter);
                    }
                } while (playCharacter != -1);
            } catch (IndexOutOfBoundsException e){//IndexOutOfBoundsException = リストが空 = 行動待ちキャラが居ない
                System.out.print("ターン終了. \n");
                return;
            }
        }
    }

    /**
     * 行動終わったキャラクターはい"ね"が"ぁ"ぁ"〜〜!!!(なまはげ風ボイス)
     * @param characters キャラクターたちの格納されているリスト. whichHeadStart()参照
     * @param finishCharacterNumber 行動の終わったキャラクター(のリストでの格納されている場所)
     * @return 行動の終わったキャラクターを除いたcharacters
     */
    public ArrayList finishedPlayer(ArrayList<UnitClass> characters, int finishCharacterNumber){
        ArrayList<UnitClass> resultCharacters = new ArrayList<>();
        for(int i=0; i<characters.size(); i++){
            if(i != finishCharacterNumber){
                resultCharacters.add(characters.get(i));
            }
        }
        return resultCharacters;
    }

    /**
     * Which do you like this enemy or this enemy?
     * 二体敵を紹介させて、どちらがお好みか聞いた後に生成します.
     * 但し、内部では図鑑ナンバーでやり取りしています. 詳しく知りたいならEnemyDictionary.selectStatus()へ
     * @return 「けつばん召喚したい」 -> 参照する:ポケモンNo.152
     */
    public int enemyDictionary(){
        int firstEnemy = 0;
        int secondEnemy = 0;
        int decidedEnemy;
        if(level%10 == 0){//ボスからは逃げられない!!
            System.out.print("ボス部屋に着いた!\n");
            decidedEnemy = 11;
            return decidedEnemy;
        }else {
            System.out.print("敵と衝突しそうだ!\n");
            while (firstEnemy == secondEnemy) {
                if(1 <= level && level < 10){//序盤は優しく
                    firstEnemy = (int) (Math.random() * 2) + 1;
                    secondEnemy = (int) (Math.random() * 2) + 1;
                }else{
                    firstEnemy = (int) (Math.random() * 3) + 1;
                    secondEnemy = (int) (Math.random() * 3) + 1;
                }
            }
        }
        decidedEnemy = enemyPrint(firstEnemy, secondEnemy);
        return decidedEnemy;
    }

    /**
     * 二体敵を紹介させて、どちらがお好みかを伺います。
     * @param firstEnemy enemyDictionary()で生成された1体目のエネミー
     * @param secondEnemy enemyDictionary()で生成された2体目のエネミー
     * @return firstEnemy or secondEnemy
     */
    public int enemyPrint(int firstEnemy, int secondEnemy){
        int[] list = new int[2];
        list[0] = firstEnemy;
        list[1] = secondEnemy;
        int charaSheet = -1;//どっちの番号を選んだかの保存先

        for(int i = 0; i<list.length; i++){
            switch(list[i]){
                case 1: System.out.print(i+1 + "番, ぷにぷにしたスライム\n"); break;
                case 2: System.out.print(i+1 + "番, ベトベトしたスライム\n"); break;
                case 3: System.out.print(i+1 + "番, スレイヤーされるエネミー\n"); break;
                default: break;
            }
        }
        do{
            System.out.print("どっちの敵の方に向かう? :\n");
            String str = scan.next();
            try{
                charaSheet = Integer.parseInt(str)-1;
            }
            catch(NumberFormatException e){
                System.out.print("sorry, one more please?\n");
            }
        }while(charaSheet != 0 && charaSheet != 1);

        return list[charaSheet];
    }
}
