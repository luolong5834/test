package mysql.test.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Setter
@Getter
public class AccountWorkflow {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Integer inId;

    /**
     *
     */
    private Integer outId;

    /**
     *
     */
    private Date applyTime;

    /**
     *
     */
    private Integer youxinId;

    private List<AccountWorkflow> next = new ArrayList<>(3);

}