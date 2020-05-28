const gender = {
  [0]: "未知",
  [1]: "男",
  [2]: "女"
};
const tagType = {
  ["COMMON"]: "普通标签",
  ["OTHER"]: "其他标签"
};
const msgType = {
  agree: "同意会话聊天内容",
  card: "名片",
  calendar: "日程消息",
  collect: "填表消息",
  CHchatrecord: "会话记录消息",
  disagree: "不同意会话聊天内容",
  docmsg: "在线文档消息",
  emotion: "表情",
  file: "文件",
  image: "图片",
  link: "链接",
  location: "位置",
  markdown: "格式消息",
  mixed: "混合消息",
  meeting: "会议邀请消息",
  news: "图文消息",
  redpacket: "红包消息",
  revoke: "撤回",
  switch: "切换企业",
  todo: "待办消息",
  text: "文本",
  video: "视频",
  voice: "音频",
  vote: "投票",
  weapp: "小程序"
};
const msgAction = {
  send: "发送",
  recall: "撤回",
  switch: "切换企业日志"
};
const userStatus = {
  [0]: "启用",
  [1]: "禁用"
};
const organizationState = {
  ["Y"]: "启用",
  ["N"]: "禁用"
};
const dic = {
  gender,
  userStatus,
  organizationState,
  msgType,
  msgAction
};
const mapDic = function(key, val) {
  if (dic[key]) {
    return dic[key][val] || val;
  } else return "";
};
export { mapDic, gender, tagType };
