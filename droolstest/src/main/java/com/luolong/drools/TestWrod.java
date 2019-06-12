package com.luolong.drools;

import com.luolong.drools.modul.Person;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/3/12
 */
public class TestWrod {
    /*public static void main(String[] args) {
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks =kc.newKieSession("ksession_luolong");
        int count = ks.fireAllRules();
        int count1 = ks.fireAllRules(0);
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();
    }*/

    public static void main(String[] args) {
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks = kc.newKieSession("ksession_luolong");
        Person person = new Person("Li", 30);
        FactHandle insert = ks.insert(person);
        int count = ks.fireAllRules();
        System.out.println("总执行了" + count + "条规则");
        System.out.println(person);
        ks.dispose();
    }
}
