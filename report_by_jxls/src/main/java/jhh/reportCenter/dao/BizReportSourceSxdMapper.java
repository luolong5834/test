package jhh.reportCenter.dao;

import jhh.reportCenter.model.BizReportSourceSxd;
import jhh.reportCenter.model.dto.BizReportSourceSxdDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BizReportSourceSxdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_report_source_sxd
     *
     * @mbg.generated Thu Jan 24 10:28:08 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_report_source_sxd
     *
     * @mbg.generated Thu Jan 24 10:28:08 CST 2019
     */
    int insert(BizReportSourceSxd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_report_source_sxd
     *
     * @mbg.generated Thu Jan 24 10:28:08 CST 2019
     */
    int insertSelective(BizReportSourceSxd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_report_source_sxd
     *
     * @mbg.generated Thu Jan 24 10:28:08 CST 2019
     */
    BizReportSourceSxd selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_report_source_sxd
     *
     * @mbg.generated Thu Jan 24 10:28:08 CST 2019
     */
    int updateByPrimaryKeySelective(BizReportSourceSxd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_report_source_sxd
     *
     * @mbg.generated Thu Jan 24 10:28:08 CST 2019
     */
    int updateByPrimaryKey(BizReportSourceSxd record);

    int getTotalCount(@Param("condition") BizReportSourceSxdDto condition);

    List<BizReportSourceSxd> findList(@Param("condition") BizReportSourceSxdDto condition, @Param("offset") int offset, @Param("limit") int limit);
}