package cn.com.xinxin.sass.repository.dao;

import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;
import org.apache.ibatis.annotations.Param;

public interface TenantDataSyncConfigDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org_data_sync_config
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org_data_sync_config
     *
     * @mbg.generated
     */
    int insert(TenantDataSyncConfigDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org_data_sync_config
     *
     * @mbg.generated
     */
    int insertSelective(TenantDataSyncConfigDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org_data_sync_config
     *
     * @mbg.generated
     */
    TenantDataSyncConfigDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org_data_sync_config
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TenantDataSyncConfigDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org_data_sync_config
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TenantDataSyncConfigDO record);

    /**
     * 通过机构id和任务类型查询记录
     * @param tenantId 机构id
     * @param taskType 任务类型
     * @return 机构同步配置信息
     */
    TenantDataSyncConfigDO selectByOrgIdAndTaskType(@Param(value = "tenantId") String tenantId,
                                                    @Param(value = "taskType")String taskType);
}