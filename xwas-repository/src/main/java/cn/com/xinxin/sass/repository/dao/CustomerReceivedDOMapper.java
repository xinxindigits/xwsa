package cn.com.xinxin.sass.repository.dao;

import cn.com.xinxin.sass.repository.model.CustomerReceivedDO;

public interface CustomerReceivedDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_received
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_received
     *
     * @mbg.generated
     */
    int insert(CustomerReceivedDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_received
     *
     * @mbg.generated
     */
    int insertSelective(CustomerReceivedDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_received
     *
     * @mbg.generated
     */
    CustomerReceivedDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_received
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CustomerReceivedDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_received
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CustomerReceivedDO record);
}