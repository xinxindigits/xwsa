package cn.com.xinxin.sass.repository.dao;

import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;

public interface DepartmentReceivedDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_received
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_received
     *
     * @mbg.generated
     */
    int insert(DepartmentReceivedDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_received
     *
     * @mbg.generated
     */
    int insertSelective(DepartmentReceivedDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_received
     *
     * @mbg.generated
     */
    DepartmentReceivedDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_received
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DepartmentReceivedDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_received
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DepartmentReceivedDO record);
}