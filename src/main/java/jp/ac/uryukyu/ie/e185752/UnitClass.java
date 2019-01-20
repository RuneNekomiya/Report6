package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] UnitClass
 * メソッドごとが所有する処理にも一応種類を持たせている。
 * 0, getter()
 * 1, 『ユニットの行動』--メソッド呼び出しが基本
 * 2, 『行動によって変動するステータスやフラグの管理』
 * 3, 『行動の許可やBattleSystemなどに関連する類』
 */
public class UnitClass {
    private String name;
    private int maxHp, hp; private int notAbleHealFrag = 0;
    private int attack;
    private int armor; private int defenceFrag = 0;
    String selectSkill= null;
    UnitClass target = null;
    int skillPriority = 0;


    public String getName(){ return name; }
    public int getMaxHp(){ return maxHp; }
    public int getHp(){ return hp; }
    public int getAttack(){ return attack; }
    public int getArmor(){ return armor; }
    public int getNotAbleHealFrag(){ return notAbleHealFrag; }

    /**
     * ユニットの能力値を書き換えます. ただしその条件として, name, maxHp, attack, armor全てが初期値である必要があります.
     * つまり本来ならコンストラクタでやるべき内容であり, 使用上無理だと判断したため条件式の厳しいsetterメソッドもどきになってます.
     * @param name ユニットの名前
     * @param maxHp ユニットの最大体力(現在体力も作成される)
     * @param attack ユニットの攻撃力
     * @param armor ユニットの装甲点
     */
    public void setUnit(String name, int maxHp, int attack, int armor){
        //厳しい条件式の内容 : 名前, 最大体力, 攻撃力, 装甲点, 全てがnullまたは0の場合
        if(this.name==null && this.maxHp==0 && this.attack==0 && this.armor==0) {
            this.name = name;
            this.maxHp = maxHp;
            hp = maxHp;
            this.attack = attack;
            this.armor = armor;
        }
    }

    /**
     * --『行動の許可やBattleSystemなどに関連する類』
     * 「コマンド選択」→「コマンド読み込み」→「使用順序の選定」→「各自行動開始」の順で行動するキャラクター達.
     * その内の「コマンド読み込み」に該当するメソッド.
     * 勇者のスキル選択の際のコマンドもこちらに書き込んでしまった...慚愧に堪えぬ(一応仕様という事にしておきますが)
     * @param action どのアクションを起こしたいか. BattleSystem.BattleDooome()参照
     */
    public void selectSkillMethod(String action, UnitClass target){
        switch(action){
            case "attack": case "1": selectSkill = "attack"; this.target = target; skillPriority = 0; break;
            case "heal"  : case "3": selectSkill = "heal"  ; this.target = target; skillPriority = 0; break;
            case "guard" : case "2": selectSkill = "guard" ; skillPriority = 1; break;
            case "stop"  :           selectSkill = "stop"  ; skillPriority = 0; break;
            default: break;
        }
    }

    /**
     * --『行動の許可やBattleSystemなどに関連する類』
     * 「コマンド選択」→「コマンド読み込み」→「使用順序の選定」→「各自行動開始」の順で行動するキャラクター達.
     *  その内の「各自行動開始」に該当するメソッド. 行動を呼び覚ますだけとも言えるが.
     */
    public void playSkill(){
        if(canMove()){
            switch(selectSkill){
                case "attack": attack(target); turnEnd(); break;
                case "heal"  : heal()        ; turnEnd(); break;
                case "guard" : guard()       ; turnEnd(); break;
                case "stop"  : stop()        ; turnEnd(); break;
                default: System.out.print("想定されてないエラーが発生:playSkill()"); break;
            }
        }
    }

    /**
     * --『行動の許可やBattleSystemなどに関連する類』
     * @return 動けるかの判定. trueなら動ける. 問題があるならfalseだ.
     */
    public boolean canMove(){
        if(hp<=0){ return false; }
        else{ return true; }
    }

    /**
     * --『行動の許可やBattleSystemなどに関連する類』
     * 状態変化などの変更(または初期化)に用いるメソッドにturnStart()とturnEnd()の二種類があります.
     * こちらは「ターン開始時に変更・解除されるべき類のもの(Example:防御)」を想定して書き込んでます.
     */
    public void turnStart(){
        if(defenceFrag > 0){ defenceFrag -= 1;}//防御判定の解除, またはターン経過による効果時間の減少
        if(notAbleHealFrag > 0){ notAbleHealFrag -= 1; }//回復中毒の効果時間の減少
    }

    /**
     * --『行動の許可やBattleSystemなどに関連する類』
     * 状態変化などの変更(または初期化)に用いるメソッドにturnStart()とturnEnd()の二種類があります.
     * こちらは「ユニットが行動を終了したら変更・解除されるべき類のもの(Example:スキル選択)」を想定して書き込んでます.
     */
    public void turnEnd(){
        selectSkill = null; //選択スキルの初期化
        skillPriority = 0;  //スキル優先度の初期化
        if(maxHp < hp){ hp = maxHp; System.out.println("想定されてないエラーが発生:turnEnd().回復判定"); }//体力が最大体力を上回ったらエラーテキスト. でも普通に動くよ.
        System.out.print("\n"); //相手のターンまたはターン終了になるので文章の改行
    }

    /**
     * --『行動の許可やBattleSystemなどに関連する類』
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
     * --『行動によって変動するステータスやフラグの管理』
     * ダメージ量を受け取り、その分だけhpを減少させprintが起こるだけのメソッド.
     * 但しhpが減らない例外もいくつかあります. このメソッドの存在する理由の一つである.
     * @param damage 減少するダメージ
     */
    public void decreaseHp(int damage){
        if(damage <= 0){//ダメージが負の数またはノーダメージの場合。基本的に起動しない想定
            System.out.print("想定されてないエラーが発生:decreaseHp()");
            return;
        }
        if(hp<=0){//オーバーキルした場合
            System.out.print("オーバーキルだ! ヒャッハー!");
            hp -= damage;//オーバーキルでも体力は減らす
            return;
        }
        if(defenceFrag != 0){//防御していた場合
            System.out.print("しかし" + name + "は防御していたためダメージを受けない!");
            return;
        }
        else{//普通に攻撃が通るなら...
            hp -= damage;
            if (hp > 0) {//やったか!?->「残念だったね!」
                System.out.print(name + "の体力は" + hp + "に減ってしまった!");
            }else{       //やったか!?->「やったぞ..やった! 俺はあいつに勝ったんだ! ははっ, ハハハハハッ!!!」
                System.out.print(name + "は死んでしまった へ(^A^)9");
            }
        }
    }

    /**
     * --『行動によって変動するステータスやフラグの管理』
     * 回復点を受け取り、その分だけhpを回復させprintが起こるだけのメソッド.
     * 但しhpが回復しない例外もいくつかあります. このメソッドの存在する理由の一つである.
     * @param heal 回復点
     */
    public void increaseHp(int heal){
        if(hp >= maxHp){//すでにフル体力の場合
            System.out.print("しかしすでに" + name + "の体力は満タンだ!");
            return;
        }
        if(heal <= 0){//回復点が負の数またはノーダメージの場合
            System.out.print("が、" + name +  "の治癒力は働かなかった! ");
            return;
        }
        if(hp+heal >= maxHp){//回復したら体力満タンになる場合
            hp = maxHp;
            System.out.print(name + "の体力は満タンになった!");
            return;
        }
        else{//普通に回復する
            hp += heal;
            System.out.print(name + "は" + heal + "だけ回復した!");
            System.out.print("今の体力は" + hp + "だ!");
        }
    }

    /**
     * --『行動によって変動するステータスやフラグの管理』
     * 純粋なダメージポイントを生成します. 但し, 相手の装甲を考えずに攻撃手の状態から算出するので「装甲点の減算を含めたダメージ」はattack()などにあります.
     * 製作者「ダメージは乱数で作りたいよね(特に何も考えてない乱数主義者みたいな発想)」
     * @param magnification ダメージ倍率. 2倍のダメージを与える場合はこちらに2を入れるとよろしい
     * @return 純粋なダメージポイントを生成します
     */
    public int damagePoint(float magnification){
        //乱数=(0.8~1.2)倍 = 0.8+ [0 ~ 0.4]
        double randomNumber = Math.random()*4/10 + 0.8;
        int damage = (int)(attack * magnification * randomNumber);
        return damage;
    }

    /**
     * --『ユニットの行動』
     * 殴るだけ. ですがそれは恐るべきものです. なぜならほとんどの生物は頭を潰されたら死ぬしかないのですから.
     * @param target 標的となる敵方
     */
    public void attack(UnitClass target){
        int damage = damagePoint(1) - target.armor;
        if(damage <= 0){//もしも装甲点に攻撃を吸収された場合
            System.out.print(name + "の攻撃！しかし" + target.name + "にダメージを与えられない! ");
            return;
        }else{//普通に攻撃が通る場合
            System.out.print(name + "の攻撃！" + damage + "のダメージを与えた! ");
            target.decreaseHp(damage);
        }
    }

    /**
     * --『ユニットの行動』
     * 絶対防御って響き, かっこいいですよね? 理解できる貴方に速報です! これは, 無制限に発動できる絶対防御スキルですよ?
     * defenceFragを立て, その後のdecreaseHp()判定部分に影響してきます.
     */
    public void guard(){
        defenceFrag += 1;
        System.out.print(name + "は防御した!");
    }

    /**
     * --『ユニットの行動』
     * 最大体力の３割だけ回復します.
     * Q. なんで３割なの?
     * A. 特に意味はありません. 強いていうなら今のゲームバランスだと３割がデバッグしやすかった. 悪気はなかった, 反省している.
     */
    public void heal(){
        if(notAbleHealFrag > 0){//回復中毒フラグが立っている場合
            System.out.print(name + "は回復中毒になっているため回復できない!");
        }
        else{//普通に回復できる場合
            notAbleHealFrag += 3;//三ターン回復禁止令を発令
            int healPoint = maxHp * 3 / 10;//最大体力の三割回復
            System.out.print(name + "は回復した! ");
            increaseHp(healPoint);
        }
    }

    /**
     * 回復中毒や防御判定などの一時的な状態変化を解除します. また, 体力をほんの少しだけ回復させます.
     * 現使用上だと, スキルとしての仕様というよりはバトル終了としての意味合いが強いです.
     */
    public void refresh(){
        defenceFrag = 0; notAbleHealFrag = 0;//フラグの初期化
        selectSkill = null; skillPriority = 0;//スキル決定の初期化
        int healPoint = (int)(maxHp * Math.random() * 3 / 10);//回復量0~3割. 若干弱め
        System.out.print(name + "は自然治癒力を酷使した! ");
        increaseHp(healPoint);
        System.out.print("\n");//文章がちょっと不自然なので挿入
    }

    /**
     * --『ユニットの行動』
     * スライムとかって動きノロそうじゃない? だったらナマケモノな行動してもええじゃん!
     * 何もしません.
     */
    public void stop(){
        System.out.print(name + "はボケーっとしている!");
    }

}
