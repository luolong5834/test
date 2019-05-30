package mysql.test.service;

import mysql.test.pojo.AccountWorkflow;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/5/30
 */
@Service
public interface AccountWorkflowService {
    AccountWorkflow findWorkFlowByYouxin_id(String youxinId);
}
