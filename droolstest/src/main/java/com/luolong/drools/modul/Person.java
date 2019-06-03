package com.luolong.drools.modul;

import lombok.Data;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/3/12
 */
@Data
public class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
