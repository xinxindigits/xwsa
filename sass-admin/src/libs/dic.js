/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

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
const customerType = {
  1: "微信用户",
  2: "企业微信用户"
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
const taskType = {
  ["CONTACT_SYNC"]: "通讯录同步",
  ["MESSAGE_SYNC"]: "会话信息同步"
};
const taskState = {
  ["0"]: "启用",
  ["1"]: "禁用"
};
const staffAccountState = {
  [1]: "已激活",
  [2]: "已禁用",
  [4]: "未激活",
  [5]: "退出企业"
};
const memberStatus = {
  INACTIVE: "非活跃",
  ACTIVE: "活跃"
};
const customerStatus = {
  INACTIVE: "非活跃",
  ACTIVE: "活跃"
};
const taskResult = {
  ["INIT"]: "已激活",
  ["RECEIVING"]: "获取数据中",
  ["IMPORTING"]: "数据导入中",
  ["SUCCESS"]: "成功同步",
  ["FAILURE"]: "任务失败"
};
const dic = {
  gender,
  userStatus,
  organizationState,
  msgType,
  msgAction,
  tagType,
  customerType,
  staffAccountState,
  taskType,
  taskState,
  memberStatus,
  taskResult,
  customerStatus
};
const mapDic = function(key, val) {
  if (dic[key]) {
    return dic[key][val] || val;
  } else return "";
};
export {
  mapDic,
  gender,
  userStatus,
  tagType,
  customerType,
  taskType,
  taskResult
};
