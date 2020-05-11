import axios from "@/libs/api.request";
export const queryMsgList = ({
  userId,
  startTime,
  endTime,
  pageIndex,
  pageSize
}) => {
  let data = {
    tenantId: "xinxin",
    userId,
    startTime,
    endTime,
    pageIndex,
    pageSize
  };

  return axios.request({
    url: "wechat/message/query",
    method: "post",
    data
  });
};
export const getMsgByMsgId = ({ id }) => {
  return axios.request({
    url: `wechat/message/detail/${id}`,
    method: "get"
  });
};
//获取指定参与人的会话对象列表
export const getUserInMsgList = ({ userId, keyWord, startTime, endTime }) => {
  let data = { tenantId: "xinxin", userId, keyWord, startTime, endTime };
  return axios.request({
    url: "wechat/message/query/userList",
    method: "post",
    data
  });
};
//获取两用户间会话记录
export const getMsgDetailByUserId = ({
  userId,
  userIdTwo,
  pageIndex,
  pageSize,
  keyWord,
  startTime,
  endTime
}) => {
  let data = {
    tenantId: "xinxin",
    userId,
    userIdTwo,
    pageIndex,
    pageSize,
    keyWord,
    startTime,
    endTime
  };
  return axios.request({
    url: "wechat/message/query/user",
    method: "post",
    data
  });
};
//查询群聊会话记录
export const getMsgDetailByRoomId = ({
  roomId,
  pageIndex,
  pageSize,
  keyWord,
  startTime,
  endTime
}) => {
  let data = {
    tenantId: "xinxin",
    roomId,
    pageIndex,
    pageSize,
    keyWord,
    startTime,
    endTime
  };
  return axios.request({
    url: "wechat/message/query/room",
    method: "post",
    data
  });
};
//查询指定消息的页码
export const getPageIndex = ({ id, roomId, userId, userITwo, pageSize }) => {
  let data = {
    tenantId: "xinxin",
    id,
    roomId,
    userId,
    userITwo,
    pageSize
  };
  return axios.request({
    url: "wechat/message/queryPageIndex",
    method: "post",
    data
  });
};
