package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] UnitClass
 * メソッドごとが所有する処理にも一応種類を持たせている。
 * 1, 『ユニットの行動』--メソッド呼び出しが基本
 * 2, 『行動によって変動するステータスやフラグの管理』
 * 3, 『行動の許可やBattleSystemなどに関連する類』
 */
public class UnitClass {
    String name;
    int maxHp, hp; int notAbleHealFrag = 0;
    int attack;
    int armor; int defenceFrag = 0;
    String selectSkill= null;
    UnitClass target = null;
    int skillPriority = 0;
    /* ここからコマンドリスト. 必要な理由だって? 視認性の問題ですよ. つまり意味はほとんどありません */
    String s_Attack="attack", s_Guard="guard", s_Heal="heal", s_Stop="stop";


    /**
     * どのアクションを選択するのかい?
     * 勇者のスキル選択の際のコマンドもこちらに書き込んでしまった...慚愧に堪えぬ(一応仕様という事にしておきますが)
     * @param action どのアクションを起こしたいか. BattleSystem.BattleDooome()参照
     */
    public void selectSkillMethod(String action, UnitClass target){
        switch(action){
            case "attack": case "1": selectSkill = s_Attack; this.target = target; skillPriority = 0; break;
            case "heal"  : case "3": selectSkill = s_Heal  ; this.target = target; skillPriority = 0; break;
            case "guard" : case "2": selectSkill = s_Guard ; skillPriority = 1; break;
            case "stop"  :           selectSkill = s_Stop  ; skillPriority = 0; break;
            default: break;
        }
    }
    public void playSkill(){
        switch(selectSkill){
            case "attack": attack(target); turnEnd(); break;
            case "heal"  : heal(target)  ; turnEnd(); break;
            case "guard" : guard()       ; turnEnd(); break;
            case "stop"  : stop()        ; turnEnd(); break;
            default: break;
        }
    }
    /**
     * --『行動の許可やBattleSystemなどに関連する類』
     * 死んだ奴は普通動けないよなぁ??
     * 動けるかの判定を行います. 行動の殆どにはこれを挟もう.
     * @return 動けるかの判定. trueなら動ける. 問題があるならfalseだ.
     */
    public boolean canMove(){
        if(hp<=0){
            System.out.print(name + "は行動をしようとしていた! しかし" + name + "は死んでいる!!");
            return false;
        }
        else{ return true; }
    }
    /**
     * --『行動の許可やBattleSystemなどに関連する類』
     * 状態変化などの変更(または初期化)に用いるメソッドにturnStart()とturnEnd()の二種類があります.
     * こちらは「行動を開始したら変更・解除される類のもの(Example:防御)」を想定して書き込んでます.
     */
    public void turnStart(){
        if(defenceFrag > 0){ defenceFrag -= 1;}
        if(notAbleHealFrag > 0){ notAbleHealFrag -= 1; }
    }

    /**
     * --『行動の許可やBattleSystemなどに関連する類』
     * 状態変化などの変更(または初期化)に用いるメソッドにturnStart()とturnEnd()の二種類があります.
     * こちらは「行動を終了したら変更・解除される類のもの(Example:スキル選択)」を想定して書き込んでます.
     */
    public void turnEnd(){
        selectSkill = null;
        skillPriority = 0;
        if(maxHp < hp){ hp = maxHp; System.out.println("想定されてないエラーが発生:turnEnd().回復判定"); }
        System.out.print("\n");
    }

    /**
     * --『行動によって変動するステータスやフラグの管理』
     * ダメージ量を受け取り、その分だけhpを減少させprintが起こるだけのメソッド.
     * 但しhpが減らない例外もいくつかあります. このメソッドの存在する理由の一つである.
     * @param damage 減少するダメージ
     */
    public void decreaseHp(int damage){
        //防御していた場合
        if(defenceFrag != 0){
            System.out.print("しかし" + name + "は防御していたためダメージを受けない!");
            return;
        }
        //ダメージが負の数またはノーダメージの場合。基本的に起動しない想定
        if(damage <= 0){
            System.out.print("想定されてないエラーが発生:decreaseHp()");
            return;
        }
        //オーバーキルした場合
        if(hp<=0){
            System.out.print("オーバーキルだ! ヒャッハー!");
        }
        //防御していなかった場合
        hp -= damage;
        if (hp > 0) {
            System.out.print(name + "の体力は" + hp + "に減ってしまった!");
        }else{
            System.out.print(name + "は死んでしまった へ(^A^)9");
        }

    }

    /**
     * --『行動によって変動するステータスやフラグの管理』
     * 回復点を受け取り、その分だけhpを回復させprintが起こるだけのメソッド.
     * 但しhpが回復しない例外もいくつかあります. このメソッドの存在する理由の一つである.
     * @param heal
     */
    public void increaseHp(int heal){
        //回復点が負の数またはノーダメージの場合。基本的に起動しない想定
        if(heal <= 0){
            System.out.print("想定されてないエラーが発生:increaseHp()");
            return;
        }
        //体力が最大体力と等値又は上回る場合。heal連打した製作者がいるんですよ...基本的に起動しない想定
        if(hp+heal >= maxHp){
            hp = maxHp;
            System.out.print(name + "の体力は満タンになった!");
            return;
        }
        hp += heal;
        System.out.print(name + "は" + heal + "だけ回復した!");
        System.out.print("今の体力は" + hp + "だ!");
    }

    /**
     * --『行動によって変動するステータスやフラグの管理』
     * 純粋なダメージポイントを生成します。但し、相手の装甲を考えずに攻撃手の状態から算出するので「装甲点の減算を含めたダメージ」はattack()などにあります.
     * 製作者「ダメージは乱数で作りたいよね(無思考の乱数主義者的発想)」
     * @param magnification ダメージ倍率. 2倍のダメージを与える場合はこちらに2を入れるとよろしい
     * @return ダメージポイント(但し装甲は加味しない)
     */
    public int damagePoint(float magnification){
        //乱数=(0.8~1.1)倍 = 0.8+ [0,0.3]
        double randomNumber = Math.random()*3/10 + 0.8;
        int damage = (int)(attack * magnification * randomNumber);
        return damage;
    }

    /**
     * --『ユニットの行動』
     * 相手を殺せっ! 純粋な殺意を迸らせろっ!
     * 殴るだけ。
     * @param target 標的となる敵方
     */
    public void attack(UnitClass target){
        if(canMove()){
            int damage = damagePoint(1) - target.armor;
            if(damage <= 0){
                System.out.print(name + "の攻撃！しかし" + target.name + "にダメージを与えられない! ");
                return;
            }else{
                System.out.print(name + "の攻撃！" + damage + "のダメージを与えた! ");
                target.decreaseHp(damage);
            }
        }
    }

    /**
     * --『ユニットの行動』
     * 絶対防御って響き、かっこいいですよね。理解できる貴方に速報です。これ、実質絶対防御スキルですよ?
     * defenceFragを立て、その後のdecreaseHp()判定部分に影響してきます。
     */
    public void guard(){
        if(canMove()){
            defenceFrag += 1;
            System.out.print(name + "は防御した!");
        }
    }

    /**
     * --『ユニットの行動』
     * 最大体力の３割だけ回復します(仮)
     * 仕様上のスキルとして残してますので、後々効果や発動条件が変わるかもしれませんね。
     */
    public void heal(UnitClass target){
        if(canMove()) {
            if(notAbleHealFrag == 0) {
                notAbleHealFrag += 3;
                int healPoint = maxHp * 3 / 10;
                target.increaseHp(healPoint);
            }else{
                System.out.print(name + "は回復中毒になっているため回復できない!");
            }
        }
    }

    /**
     * --『ユニットの行動』
     * スライムとかって動きノロそうじゃない? だったらナマケモノな行動してもええじゃん!
     * 何もしません.
     */
    public void stop(){
        System.out.print(name + "はボケーっとしている!");
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
