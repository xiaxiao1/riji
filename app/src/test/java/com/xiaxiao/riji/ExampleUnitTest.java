package com.xiaxiao.riji;

import com.xiaxiao.riji.util.XiaoUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        String s=XiaoUtil.getFormatCurrentTime("yyyy.MM.dd");
        XiaoUtil.print(s);
        String ss[] = s.split("\\.");
        XiaoUtil.print(ss[2]);
        XiaoUtil.print(ss[0]+"年"+ss[1]+"日");
    }
}