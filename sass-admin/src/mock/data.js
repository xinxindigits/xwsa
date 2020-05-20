const wrapper = data => {
  return { code: "SUCCESS", data, message: "ok" };
};

export const chatuserlist = () => {
  return wrapper([
    {
      type: 0,
      roomId: null,
      roomName: null,
      userId: "wmD5WOCgAAityiWOL4BK5b-inogaIE5A",
      userName: "测试",
      avatar:
        "https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"
    },
    {
      type: 0,
      roomId: null,
      roomName: null,
      userId: "YuWei",
      userName: "于炜",
      avatar:
        "https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"
    }
  ]);
};
