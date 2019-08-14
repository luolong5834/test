/*aa
package com.luolong.opensource.java.test.DB;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

*/
/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/4/8
 * <p>
 * 批处理测试
 * @author long.luo
 * @date 2019/4/8
 * @param []
 * @return void
 * <p>
 * 批处理测试
 * @author long.luo
 * @date 2019/4/8
 * @param []
 * @return void
 *//*

@SpringBootTest
@RunWith(SpringRunner.class)
public class JDBCTest {
    */
/**
 * 批处理测试
 * @author long.luo
 * @date 2019/4/8
 * @param []
 * @return void
 *//*

    @Test
    public void test(){
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        int[] _ids1 = null;
        int[] _ids2 = null;
        try {
            conn.setAutoCommit(false);
            int discountNum=Integer.parseInt(_maps.get("discountNum"));
            ps1 = conn.prepareStatement(SqlStaticActivity.sql1, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2 = conn.prepareStatement(SqlStaticActivity.sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String _discountCode=null;

            ps1.setBigDecimal(2, new BigDecimal(_maps.get("discount")));
            ps1.setString(3, _maps.get("startTime"));
            ps1.setString(4, _maps.get("endTime"));
            ps1.setString(5,_maps.get("createrId"));
            ps1.setString(6, _maps.get("state"));
            ps1.setString(7, _maps.get("discount_name"));
            ps1.setString(8, _maps.get("discount_describe"));
            ps1.setString(9, _maps.get("oneself"));
            ps1.setString(10, _maps.get("mustUnicode"));
            ps1.setString(11, _maps.get("usableDays"));
            ps1.setString(12, _maps.get("ac_id"));
            ps1.setString(13, _maps.get("createTime"));
            ps1.setString(14, _maps.get("updateTime"));
            ps1.setString(15, _maps.get("life_cycle_status"));

            ps2.setString(2,_maps.get("sapCode"));
            ps2.setString(4, _maps.get("createTime"));
            ps2.setString(5,_maps.get("updateTime"));
            ps2.setString(6,_maps.get("life_cycle_status"));

            for (int i=0 ;i<discountNum;i++) {
                _discountCode= RedeemCodeUtils.createBigStrOrNumberRadom(8);
                ps1.setString(1, _discountCode);
                ps1.addBatch();

                ps2.setString(1,RedeemCodeUtils.createBigStrOrNumberRadom(20));
                ps2.setString(3,_discountCode);
                ps2.addBatch();
            }
            _ids1 = ps1.executeBatch();
            _ids2 = ps2.executeBatch();

            if(_ids1.length!=_ids2.length){
                conn.rollback();
            }else{
                conn.commit();
            }
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();

        } finally {
            try {
                if (ps1 != null) ps1.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }



        return _ids1 == null ? 0 : _ids1.length;
    }
}
*/
