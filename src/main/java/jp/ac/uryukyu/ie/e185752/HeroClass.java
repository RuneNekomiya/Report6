package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] HeroClass
 */
public class HeroClass extends UnitClass{
    /**
     * コンストラクタ. 中身はただの能力値の書き込み
     */
    HeroClass(){ setUnit("ひろゆき", 30, 10, 3); }


    /**
     * --『行動の許可やBattleSystemなどに関連する類』
     * 「コマンド選択」→「コマンド読み込み」→「使用順序の選定」→「各自行動開始」の順で行動するキャラクター達.
     * その内の「コマンド読み込み」に該当するメソッド.
     * @param action どのアクションを起こしたいか. BattleSystem.battleDooome()参照
     * @param target 攻撃なり回復なりの標的.
     * @return
     */
    public boolean receiveAction(String action, UnitClass target){
        if((action.equals("heal") || action.equals("3")) && getNotAbleHealFrag() != 0){//回復中毒なのに回復しようとしていないか?
            System.out.print(getName() + "は回復中毒になってるから回復したくない!\n");
            return false;
        }
        else {//なんごともなければ行動する
            selectSkillMethod(action, target);
            if (selectSkill != null) {
                return true;
            }else {
                switch (action) {
                    case "help": case "?": commandHelp(); break;
                    case "show": case "0": showStatuses(target); break;
                    default: break;
                }
                return false;
            }
        }
    }

    /**
     * Heroくん、実は能力値を向上させることができるんだって!
     * 状態変化の初期化やHPの自動回復など、戦闘終了したらやって欲しい感じのものを突っ込む.
     */
    public void battleEnd(){
        levelUp();
        refresh();
        System.out.print("--------------------\n");//改行表現
    }

    /**
     * コマンド紹介. 起動時に呼び出されるしコマンドコールでも呼び出される子.
     * System.out.println()しかやらないので返り値はvoidだしメソッド呼び出しも無い.
     * 序盤過ぎればほぼ要らない子だが必ず必要とも言える.
     * (1エンター毎に表記できるようになる工夫が欲しいなぁ(チラッチラッ))
     */
    public void commandHelp(){
        System.out.print("attack、または1で攻撃します。\n");
        System.out.print("defence、または2で防御します。\n");
        System.out.print("heal、または3で回復します。\n");
        System.out.print("show、または0でステータス開示します。\n");
        System.out.print("困った場合はhelp、または?でコマンド解説します。\n");
        System.out.print("--------------------\n");//改行表現
    }

    /**
     * ステータス紹介. 毎勝負ごとに呼び出される子.
     * System.out.print()しかやらないので返り値はvoidだしメソッド呼び出しも無い.
     */
    public void showStatuses(UnitClass enemy){
        System.out.print(getName() + "のステータス\t体力:" + getHp() + "/" + getMaxHp() + ", 攻撃:" + getAttack() + ", 装甲:" + getArmor() + "\n");
        System.out.print(enemy.getName() + "のステータス\t体力:" + enemy.getHp() + "/" + enemy.getMaxHp() + ", 攻撃:" + enemy.getAttack() + ", 装甲:" + enemy.getArmor() + "\n");
    }
}
