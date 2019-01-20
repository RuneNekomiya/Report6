package jp.ac.uryukyu.ie.e185752;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitClassTest {
    UnitClass testHuman;

    /**
     * turnEnd()に望まれる処理の一つに「スキル選択の初期化」があります. それが真に機能してるか確かめてください.
     */
    @Test
    void turnEnd(){
        testHuman = new UnitClass();
        testHuman.setUnit("てすとくん", 1000, 50, 10);
        int count = 0;
        while(count < 100){
            count++;
            testHuman.selectSkillMethod("heal", testHuman);
            testHuman.playSkill();
            testHuman.turnEnd();
        }
        assertNull(testHuman.selectSkill);
    }

    /**
     * increaseHp()に望まれる条件処理の一つに「過剰回復の中止」があります. それが真に機能してるか確かめてください.
     */
    @Test
    void increaseHp(){
        testHuman = new UnitClass();
        testHuman.setUnit("てすとくん", 1000, 50, 10);
        int count = 0;
        testHuman.decreaseHp(999);//瀕死になってください
        while(count < 100){
            count++;
            testHuman.increaseHp(100);
        }
        assertEquals(testHuman.getHp(), testHuman.getMaxHp());
    }
}