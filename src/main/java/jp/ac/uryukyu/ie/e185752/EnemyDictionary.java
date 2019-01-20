package jp.ac.uryukyu.ie.e185752;

import java.util.ArrayList;

/**
 * - [ ] EnemyClass
 * */
public class EnemyDictionary extends UnitClass {
    private int level;
    private int charaSheetNumber;
    private ArrayList<String> selectSkillList = new ArrayList<>();

    /**
     * 階層を入手、そこから対応する敵キャラクターをなんかランダムで出して返す。
     * @param level 敵のレベル. 高ければ高いほど強い敵が現れるよ.
     */
    EnemyDictionary(int level, int selectEnemy){
        this.level = level;
        charaSheetNumber = selectEnemy;
        selectStatus();
    }

    /**
     * どのキャラクターを選ぶ?
     * name, maxHp, hp, attack, armorをレベル依存で作成します. また, 敵の行動AIを追加.
     */
    public void selectStatus(){
        switch(charaSheetNumber){
            case 1:
                setUnit("ぷにたん", 3 + level*2, 1 + level/2, 1);
                selectSkillList.add("attack");
                selectSkillList.add("stop");
                break;
            case 2:
                setUnit("スライム", 10 + level*2,  2 + level/2, 3 + level/3);
                selectSkillList.add("attack");
                selectSkillList.add("attack");
                selectSkillList.add("stop");
                selectSkillList.add("guard");
                break;
            case 3:
                setUnit("ゴブリン", 20 + level, 5 + level/2, 0);
                selectSkillList.add("attack");
                selectSkillList.add("attack");
                selectSkillList.add("stop");
                selectSkillList.add("heal");
                break;
            case 11:
                setUnit("フォラゴンEX", 30 + level*2, 10 + level/2, 2 + level/10);
                selectSkillList.add("attack");
                selectSkillList.add("attack");
                selectSkillList.add("heal");
                break;
            default:
                System.out.println("想定されてないエラーが発生:selectStatus()");
                break;
        }
    }

    /**
     * 敵の行動を決めるメソッド. selectStatus()に依存するのでめっちゃ簡単.
     */
    public void selectEnemyAI(UnitClass target){
        int x = (int)(Math.random() * selectSkillList.size());
        String select = selectSkillList.get(x);
        selectSkillMethod(select, target);
    }

}
