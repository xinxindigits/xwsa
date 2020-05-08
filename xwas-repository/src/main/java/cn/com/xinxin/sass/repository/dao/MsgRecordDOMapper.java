package cn.com.xinxin.sass.repository.dao;

import cn.com.xinxin.sass.repository.model.MsgRecordDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgRecordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table msg_record
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table msg_record
     *
     * @mbg.generated
     */
    int insert(MsgRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table msg_record
     *
     * @mbg.generated
     */
    int insertSelective(MsgRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table msg_record
     *
     * @mbg.generated
     */
    MsgRecordDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table msg_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MsgRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table msg_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MsgRecordDO record);

    /**
     * 批量插入记录
     * @param msgRecordDOS 消息记录
     * @return 插入记录条数
     */
    int insertBatch(@Param(value = "msgRecordDOS") List<MsgRecordDO> msgRecordDOS);

    /**
     * 通过机构id，userid，消息发送时间范围查询记录
     * @param userId 用户id
     * @param startTime 消息发送时间范围之起始时间
     * @param endTime 消息发送时间范围之终止时间
     * @param index 消息记录起始
     * @param pageSize 页大小
     * @param tenantId 机构id
     * @return 消息记录
     */
    List<MsgRecordDO> selectPageByOrgIdAndUserIdAndTime(@Param(value = "userId") String userId,
                                                    @Param(value = "startTime") String startTime,
                                                    @Param(value = "endTime") String endTime,
                                                    @Param(value = "index") Long index,
                                                    @Param(value = "pageSize") Integer pageSize,
                                                    @Param(value = "tenantId") String tenantId);

    /**
     * 通过机构id，userid，消息发送时间范围查询记录总数
     * @param userId 用户id
     * @param startTime 消息发送时间范围之起始时间
     * @param endTime 消息发送时间范围之终止时间
     * @param tenantId 机构id
     * @return 消息记录总数
     */
    Long selectCountByOrgIdAndUserIdAndTime(@Param(value = "userId") String userId,
                                                    @Param(value = "startTime") String startTime,
                                                    @Param(value = "endTime") String endTime,
                                                    @Param(value = "tenantId") String tenantId);

    /**
     * 查询两个用户之间的会话记录
     * @param tenantId
     * @param userIdOne
     * @param userIdTwo
     * @return
     */
    List<MsgRecordDO> selectMsgRecordBetweenPersons(@Param("tenantId") String tenantId, @Param("userIdOne") String userIdOne, @Param("userIdTwo") String userIdTwo);

    /**
     * 查询群聊会话记录
     * @param tenantId
     * @param roomId
     * @return
     */
    List<MsgRecordDO> selectRoomMsgRecord(@Param("tenantId") String tenantId,@Param("roomId") String roomId);

    /**
     * 通过租户id和成员userid查询会话记录
     * @param tenantId 租户id
     * @param userId 成员userid
     * @param keyWord 关键字
     * @param startTime 消息发送时间范围之起始时间
     * @param endTime 消息发送时间范围之终止时间
     * @return 会话记录
     */
    List<MsgRecordDO> selectByMemberUserIdAndKeyWordAndTime(@Param("tenantId") String tenantId,
                                             @Param(value = "userId") String userId,
                                           @Param(value = "keyWord") String keyWord,
                                           @Param(value = "startTime") String startTime,
                                           @Param(value = "endTime") String endTime);
}