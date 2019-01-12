package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] EnemyClass
 * */
public class EnemyDictionary extends UnitClass {
    int level;
    int charaSheetNumber;

    /**
     * 階層を入手、そこから対応する敵キャラクターをなんかランダムで出して返す。
     * @param level
     */
    EnemyDictionary(int level, int selectEnemy){//ここ全部仮だよ！！！！
        this.level = level;
        if(selectEnemy == 1) {
            super.name = "ぷにぷに";
            super.maxHp = 3 + level * 2;
            super.hp = 3 + level * 2;
            super.attack = 1 + level / 2;
            super.armor = 1;
        }else if(selectEnemy == 2){
            super.name = "スライム";
            super.maxHp = 10 + level * 2;
            super.hp = 10 + level * 2;
            super.attack = 2 + level / 2;
            super.armor = 3 + level / 2;
        }else{
            System.out.println("想定されてないエラーが発生:EnemyDictionary()");
        }
    }
    public void selectEnemyAI(){

    }

}
