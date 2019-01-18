package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] HeroClass
 */
public class HeroClass extends UnitClass{
    /**
     * Heroは田所浩二、当たり前だよなぁ?という極左派の人はsuper.nameを弄って, どうぞ.
     */
    HeroClass(){
        super.name = "斎藤";
        super.maxHp = 30;
        super.hp = maxHp;
        super.attack = 10;
        super.armor = 3;
    }

    /**
     * どのアクションを選択するのかい?
     * @param action どのアクションを起こしたいか. BattleSystem.battleDooome()参照
     * @param target 攻撃なり回復なりの標的.
     * @return
     */
    public boolean receiveAction(String action, UnitClass target){
        selectSkillMethod(action, target);
        if(selectSkill != null){
            return true;
        }else{
            switch(action){
                case "help": case "?": commandHelp()      ; break;
                case "show": case "0": showStatuses(target); break;
                default: break;
            }
            return false;
        }
    }

    /**
     * コマンド紹介. 起動時に呼び出されるしコマンドコールでも呼び出される子.
     * System.out.println()しかやらないので返り値はvoidだしメソッド呼び出しも無い.
     * 序盤過ぎればほぼ要らない子だが必ず必要とも言える.
     * (1エンター毎に表記できるようになる工夫が欲しいなぁ(チラッチラッ))
     */
    public void commandHelp(){
        System.out.println("attack、または1で攻撃します。");
        System.out.println("defence、または2で防御します。");
        System.out.println("heal、または3で回復します。");
        System.out.println("show、または0でステータス開示します。");
        System.out.println("困った場合はhelp、または?でコマンド解説します。");
        System.out.println("--------------------");//改行表現
    }

    /**
     * ステータス紹介. 毎勝負ごとに呼び出される子.
     * System.out.println()しかやらないので返り値はvoidだしメソッド呼び出しも無い.
     */
    public void showStatuses(UnitClass enemy){
        System.out.println(name + "のステータス\t体力:" + hp + "/" + maxHp + ", 攻撃:" + attack + ", 装甲:" + armor);
        System.out.println(enemy.name + "のステータス\t体力:" + enemy.hp + "/" + enemy.maxHp + ", 攻撃:" + enemy.attack + ", 装甲:" + enemy.armor);
    }
}
