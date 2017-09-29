package com.mobin.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mobin on 2017/9/14.
 */
public interface InsertToPg {
    public static final AtomicInteger count = new AtomicInteger();
    public  void insertTopostgres();
}
