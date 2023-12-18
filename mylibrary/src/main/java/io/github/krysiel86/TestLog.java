package io.github.krysiel86;

import android.util.Log;

public class TestLog {

    String testA = "A";
    String testB = "";

    int myField = 0;

    public void TestLog(String name) {
        Log.e("tpmn", "TestLog Class : " + name);
    }

    public void Test() {
        Log.e("tpmn", "testA : " + testA);
        testB = "B";

        Log.e("tpmn", "fkk : " + testB);

    }

}
