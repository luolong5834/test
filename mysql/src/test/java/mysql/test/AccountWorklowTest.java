package mysql.test;

import mysql.test.mapper.AccountWorkflowMapper;
import mysql.test.pojo.AccountWorkflow;
import mysql.test.service.AccountWorkflowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/5/30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountWorklowTest {
    @Autowired
    AccountWorkflowMapper accountWorkflowMapper;

    @Autowired
    AccountWorkflowService accountWorkflowService;

    @Test
    public void test() {
        final AccountWorkflow result = accountWorkflowService.findWorkFlowByYouxin_id("1");
        System.out.println(result);
    }
}
