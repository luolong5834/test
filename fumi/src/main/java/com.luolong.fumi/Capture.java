/*
package com.luolong.fumi;

import org.springframework.util.StopWatch;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Capture {
    void test() {
        List<ExtendOrder> list = new ArrayList();


        for (int i = 0; i < 400000; i++) {
            final ExtendOrder extendOrder = new ExtendOrder();
            extendOrder.setStrikeQty(new BigDecimal(1));
            extendOrder.setFinAccountId("11");
            list.add(extendOrder);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("aa");
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setStrikeQty(new BigDecimal(3));
        }
        stopWatch.stop();
        stopWatch.start("bb");
        list.stream().map(o ->{
            o.setStrikeQty(new BigDecimal(3));
            return o;
        }).collect(Collectors.toList());
        stopWatch.stop();
        System.out.println(stopWatch);


    }

    public static void main(String[] args) {

    }
}
*/
