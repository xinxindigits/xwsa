import axios from "@/libs/api.request";

export const getOrganizationList = data => {
    return axios.request({
        url: "organization/list",
        data,
        method: "post"
    });
};
export const delOrganization = ({ codes }) => {
    let data = { codes };
    return axios.request({
        url: "organization/delete",
        data,
        method: "post",
    });
};
export const addOrganization = ({ code, name, corpId,state,privateKey,addressListSecret,customerContactSecret,remark,isLeaf,parentId,orgType }) => {
    let data = { code, name, corpId,state,privateKey,addressListSecret,customerContactSecret,remark,isLeaf,parentId,orgType };
    return axios.request({
        url: "organization/create",
        data,
        method: "post"
    });
};
export const updateOrganization = ({ code, extension, name, roleType }) => {
    let data = { code, extension, name, roleType };
    return axios.request({
        url: "organization/update",
        data,
        method: "post"
    });
};