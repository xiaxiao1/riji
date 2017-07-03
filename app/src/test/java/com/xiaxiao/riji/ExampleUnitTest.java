package com.xiaxiao.riji;

import com.xiaxiao.riji.util.XiaoUtil;

import org.junit.Test;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        XiaoUtil.print("===========================================");
        Date d=Calendar.getInstance().getTime();
        Calendar c=Calendar.getInstance();
        Format f= new SimpleDateFormat("yyyy.MM.dd");
        Date dd=(Date)f.parseObject(s);
        c.set(Calendar.DAY_OF_MONTH,34);
        Date d2=c.getTime();
        int i = d2.compareTo(d);
        XiaoUtil.print(d.toString()+"\n"+d2.toString()+i);
        XiaoUtil.print(dd.toString());
    }
}