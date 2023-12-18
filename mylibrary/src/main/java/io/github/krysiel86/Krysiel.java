package io.github.krysiel86;

import android.util.Log;

public class Krysiel {

    String testA = "A";
    String testB = "";
    String testC = "";
    String testD = "";

    TestLog testLog = new TestLog();

    int myField;
 
    public void krysiel(String name) {
        Log.e("tpmn", "krysiel Class : " + name);
    }

    public void abc(String testA) {
        Log.e("tpmn", "abc Class : " + testA);

    }

    public void krysielTest() {
        Log.e("tpmn", "testA : " + testA);
        testB = "B";

        Log.e("tpmn", "fkk : " + testB);
        abc(testA);
        myField = testLog.myField;

    }

}
