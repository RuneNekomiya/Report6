package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] HeroClass
 */
public class HeroClass extends UnitClass{
    /**
     * Heroは田所浩二、当たり前だよなぁ?という極左派の人はsuper.nameを弄って, どうぞ.
     */
    HeroClass(){
        super.name = "ひろゆき";
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
        //回復中毒判定などに引っかかってないか?
        if((action=="heal" || action=="3") && notAbleHealFrag != 0){
            System.out.print(name + "は回復中毒になってるから回復したくない!\n");
            return false;
        }
        //行動する
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
     * 状態変化の初期化やHPの自動回復など、戦闘終了したらやって欲しい感じのものを突っ込む.
     */
    public void battleEnd(){
        defenceFrag = 0; notAbleHealFrag = 0;//フラグの初期化
        selectSkill = null; skillPriority = 0;//スキル決定の初期化
        int healPoint = (int)(maxHp * Math.random() * 3 / 10);//回復量0~3割. 若干弱め
        System.out.print(name + "は自然治癒力を酷使した! ");
        increaseHp(healPoint);
        System.out.print("\n");//文章がちょっと不自然なので挿入
        levelUp();
        System.out.print("--------------------\n");//改行表現
    }

    /**
     * 能力が上がるよ! 成長できるって素敵!!!
     * バトル終了後に判定起動. 中身は見て感じろ. TRPGかじった人なら一瞬で理解できるから.
     */
    public void levelUp(){
        int canUpdateHp = (int)(Math.random() * 100);
        int canUpdateAttack = (int)(Math.random() * 100);
        int canUpdateArmor = (int)(Math.random() * 100);

        if(canUpdateHp < 60){//HP成長ダイスロール. 成功判定は60
            maxHp += 1;
            System.out.print(name + "の最大体力が1上がった! 現在の体力は" + hp + "/" + maxHp +  "だ!\n");
        }
        if(canUpdateAttack < 30){//ATK成長ダイスロール. 成功判定は30
            attack += 1;
            System.out.print(name + "の攻撃力が1上がった! 現在の攻撃力は" + attack +  "だ!\n");
        }
        if(canUpdateArmor < 10){//DEF成長ダイスロール. 成功判定は10
            armor += 1;
            System.out.print(name + "の装甲点が1上がった! 現在の装甲点は" + armor +  "だ!\n");
        }
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
        System.out.print(name + "のステータス\t体力:" + hp + "/" + maxHp + ", 攻撃:" + attack + ", 装甲:" + armor + "\n");
        System.out.print(enemy.name + "のステータス\t体力:" + enemy.hp + "/" + enemy.maxHp + ", 攻撃:" + enemy.attack + ", 装甲:" + enemy.armor + "\n");
    }
}
