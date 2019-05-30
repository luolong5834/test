package mysql.test.service.impl;

import mysql.test.mapper.AccountWorkflowMapper;
import mysql.test.pojo.AccountWorkflow;
import mysql.test.service.AccountWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/5/30
 */
@Service
public class AccountWorkflowServiceImpl implements AccountWorkflowService {
    @Autowired
    AccountWorkflowMapper accountWorkflowMapper;

    @Override
    public AccountWorkflow findWorkFlowByYouxin_id(String youxinId) {
        AccountWorkflow accountWorkflow = new AccountWorkflow();
        accountWorkflow.setYouxinId(Integer.parseInt(youxinId));
        List<AccountWorkflow> accountWorkflows = accountWorkflowMapper.selectBySelective(accountWorkflow);
        AccountWorkflow first = accountWorkflows.get(0);
        //
        recusion(accountWorkflows, first);

        return first;
    }

    public AccountWorkflow recusion(List<AccountWorkflow> data, AccountWorkflow first) {
        for (AccountWorkflow accountWorkflow1 : data) {
            if (first.getOutId() == accountWorkflow1.getInId()) {
                first.getNext().add(accountWorkflow1);
            }
        }
        for (AccountWorkflow accountWorkflow : first.getNext()) {
              recusion(data,accountWorkflow);
        }
        return first;
    }
}
