package jp.ac.uryukyu.ie.e185752;

import java.util.ArrayList;

/**
 * - [ ] EnemyClass
 * */
public class EnemyDictionary extends UnitClass {
    int level;
    int charaSheetNumber;
    ArrayList<String> selectSkillList = new ArrayList<>();

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
     * name, maxHp, hp, attack, armorをレベル依存で作成します.
     */
    public void selectStatus(){
        switch(charaSheetNumber){
            case 1:
                name = "ぷにたん";
                maxHp = 3 + level * 2;
                hp = maxHp;
                attack = 1 + level / 2;
                armor = 1;
                selectSkillList.add(s_Attack);
                selectSkillList.add(s_Stop);
                break;
            case 2:
                name = "スライム";
                maxHp = 10 + level * 2;
                hp = maxHp;
                attack = 2 + level / 2;
                armor = 3 + level / 2;
                selectSkillList.add(s_Attack);
                selectSkillList.add(s_Attack);
                selectSkillList.add(s_Stop);
                selectSkillList.add(s_Guard);
                break;
            case 3:
                name = "ゴブリン";
                maxHp = 20 + level;
                hp = maxHp;
                attack = 5 + level / 2;
                armor = 0;
                selectSkillList.add(s_Attack);
                selectSkillList.add(s_Attack);
                selectSkillList.add(s_Stop);
                selectSkillList.add(s_Heal);
                break;
            case 11:
                name = "どらごん";
                maxHp = 30 + level*2;
                hp = maxHp;
                attack = 10 + level/2;
                armor = 2 + level/10;
                selectSkillList.add(s_Attack);
                selectSkillList.add(s_Stop);
                selectSkillList.add(s_Stop);
                break;
            default:
                System.out.println("想定されてないエラーが発生:selectStatus()");
                break;
        }
    }//47行だからセーフだよ...きっとセーフだよ??セーフだよねお兄ちゃん??????セーフだって言ってよお兄ちゃんッ!!!!!!!!!

    /**
     * 敵の行動を決めるメソッド. selectStatus()に依存するのでめっちゃ簡単.
     */
    public void selectEnemyAI(UnitClass target){
        int x = (int)(Math.random() * selectSkillList.size());
        String select = selectSkillList.get(x);
        selectSkillMethod(select, target);
    }

}
