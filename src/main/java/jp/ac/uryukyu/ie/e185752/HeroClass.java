package jp.ac.uryukyu.ie.e185752;

/**
 * - [ ] HeroClass
 */
public class HeroClass extends UnitClass {
    HeroClass(){
        super.name = "斎藤";
        super.maxHp = 30;
        super.hp = 30;
        super.attack = 10;
        super.armor = 3;
    }
    public boolean receiveAction(String action, UnitClass enemy){
        boolean finishedAction = false;
        switch(action){
            case "attack": case "1": attack(enemy); finishedAction = true; break;
            case "guard": case "2": guard(); finishedAction = true; break;
            case "heal": case "3": heal(); finishedAction = true; break;
            case "help": case "?": attack(enemy); finishedAction = true; break;//後で作りますのん
            default: break;
        }
        return finishedAction;
    }
}
