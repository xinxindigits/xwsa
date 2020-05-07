import axios from "@/libs/api.request";
export const queryMsgList = ({
  tenantId,
  userId,
  startTime,
  endTime,
  pageIndex,
  pageSize
}) => {
  let data = { tenantId, userId, startTime, endTime, pageIndex, pageSize };

  return axios.request({
    url: "wechat/message/query",
    method: "post",
    data
  });
};
export const getMsgByMsgId = ({ msgId }) => {
  return axios.request({
    url: `wechat/message/detail/${msgId}`,
    method: "get"
  });
};
export default { queryMsgList };
