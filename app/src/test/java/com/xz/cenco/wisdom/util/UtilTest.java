package com.xz.cenco.wisdom.util;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.log.LogUtils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/5/4.
 */
public class UtilTest {
    @Test
    public void getColor() throws Exception {
//        assertEquals(4, 2 + 2);
        Date upDate = DateUtil.createHMSDate(23,59,0);
        Date downDate = DateUtil.createHMSDate(6,0,0);
        boolean valid = DateUtil.isInPeriodDate(new Date(), downDate, upDate, DateUtil.FORMAT_HMS);
        System.out.println("时间段检查："+valid);
    }

}