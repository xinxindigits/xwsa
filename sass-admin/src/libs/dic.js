const gender = {
  [0]: "未知",
  [1]: "男",
  [2]: "女"
};
const userStatus = {
  [0]: "启用",
  [1]: "禁用"
};
const dic = {
  gender,
  userStatus
};
const mapDic = function(key, val) {
  if (dic[key]) {
    return dic[key][val] || "";
  } else return "";
};
export { mapDic, gender };
