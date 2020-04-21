package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.api.enums.ResourceTypeEnum;
import cn.com.xinxin.sass.biz.service.RoleResourceService;
import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.biz.util.PasswordUtils;
import cn.com.xinxin.sass.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.sass.biz.vo.UserPwdVO;

import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.UserDOMapper;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dengyunhui on 2018/4/24
 **/
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleResourceService roleResourceService;


    @Autowired
    private UserAclTokenRepository userAclTokenRepository;

    @Override
    public int createUser(UserDO userDO) {
        UserPwdVO userPwdVO = PasswordUtils.encryptPassword(userDO.getAccount(), userDO.getPassword());
        userDO.setSalt(userPwdVO.getSalt());
        userDO.setPassword(userPwdVO.getPassword());
        userDO.setStatus((byte)0);
        userDO.setExpireDate(DateUtils.addMonths(new Date(),3));
        return userDOMapper.insertSelective(userDO);
    }

    @Override
    public void resetPassword(Long userId, String newPassword,String updater) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        if (userDO != null){
            userDO.setPassword(newPassword);
            UserPwdVO userPwdVO = PasswordUtils.encryptPassword(userDO.getAccount(),userDO.getPassword());

            UserDO update = new UserDO();
            update.setId(userDO.getId());
            update.setSalt(userPwdVO.getSalt());
            update.setPassword(userPwdVO.getPassword());
            update.setGmtUpdater(updater);
            userDOMapper.updateByPrimaryKeySelective(update);
        }

    }

    @Override
    public void modifyPassword(Long userId, String originPassword, String newPassword, String updater) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        if (userDO == null){
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"根据用户id找不到对应的用户");
        }

        String originEncryptedPassword = userDO.getPassword();
        String inputOriginPassword = PasswordUtils.encryptPassword(userDO.getAccount(), userDO.getSalt(), originPassword);
        if (!Objects.equals(originEncryptedPassword,inputOriginPassword)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"旧密码错误,修改密码失败");
        }

        UserPwdVO userPwdVO = PasswordUtils.encryptPassword(userDO.getAccount(),newPassword);

        UserDO update = new UserDO();
        update.setId(userDO.getId());
        update.setSalt(userPwdVO.getSalt());
        update.setPassword(userPwdVO.getPassword());
        update.setGmtUpdater(updater);
        userDOMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public UserDO findByUserAccount(String account) {
        return userDOMapper.selectByAccount(account);
    }

    @Override
    public List<RoleDO> findRolesByAccount(String account) {
        UserDO userDO = findByUserAccount(account);

        if (userDO != null){
            return userRoleService.findRoleByUserAccount(userDO.getAccount());
        }

        return null;
    }

    @Override
    public List<ResourceDO> findResourcesByAccount(String account) {

        List<RoleDO> roleDOS = userRoleService.findRoleByUserAccount(account);

        if (!CollectionUtils.isEmpty(roleDOS)){
            List<String> roleCodes = roleDOS.stream().map(RoleDO::getCode).collect(Collectors.toList());

            return roleResourceService.findResources(roleCodes);
        }

        return null;
    }


    @Override
    public List<ResourceDO> findPermissionsByAccount(String account) {
        List<ResourceDO> resourceDOS = findResourcesByAccount(account);

        if (!CollectionUtils.isEmpty(resourceDOS)){
            return resourceDOS.stream().filter(resourceDO -> resourceDO.getResourceType().equals(ResourceTypeEnum.FUNCTION.name())).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<ResourceDO> findRootMenusByAccount(String userNo) {
        List<ResourceDO> resourceDOS = findResourcesByAccount(userNo);

        if (!CollectionUtils.isEmpty(resourceDOS)){
            return resourceDOS.stream().filter(ResourceDO::getRoot).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<ResourceDO> findMenusByAccount(String account) {

        List<ResourceDO> resourceDOS = findResourcesByAccount(account);
        if (CollectionUtils.isEmpty(resourceDOS)){
           return null;
        }

        List<ResourceDO> resourceDOList = resourceDOS.stream().distinct()
                    .filter(resourceDO -> resourceDO.getResourceType().equals(ResourceTypeEnum.MENU.getCode()))
                    .collect(Collectors.toList());
        return resourceDOList;

    }

    @Override
    public List<ResourceDO> findFunctionsByAccount(String account) {
        List<ResourceDO> resourceDOS = findResourcesByAccount(account);
        if (CollectionUtils.isEmpty(resourceDOS)){
            return null;
        }

        List<ResourceDO> resourceDOList = resourceDOS.stream().distinct()
                .filter(resourceDO -> resourceDO.getResourceType().equals(ResourceTypeEnum.FUNCTION.getCode()))
                .collect(Collectors.toList());
        return resourceDOList;
    }

    @Override
    public PageResultVO<UserDO> findByConditionPage(PageResultVO page, QueryUserConditionVO queryUserConditionVO) {
        UserDO userDO = new UserDO();
        userDO.setAccount(queryUserConditionVO.getNo());
        userDO.setName(queryUserConditionVO.getName());

        com.github.pagehelper.Page doPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        // 后面在实现
        //List<UserDO> userDOS = userDOMapper.findByCondition(userDO);
        List<UserDO> userDOS = Lists.newArrayList();

        PageResultVO<UserDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotal(doPage.getTotal());
        result.setItems(userDOS);
        return result;
    }

    @Override
    public boolean updateUser(UserDO userDO) {
        int n = userDOMapper.updateByPrimaryKeySelective(userDO);
        return n == 1;
    }

    @Override
    public Boolean delete(Long id) {
        int deleteResult = userDOMapper.deleteByPrimaryKey(id);
        return deleteResult>0?true:false;
    }

    @Override
    public UserDO findById(Long id) {
        return userDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserDO getLoginUser(String sessionId) {

        return null;
    }

    @Override
    public Boolean hasPermission(String sessionId,String url) {

        UserDO userDO = getLoginUser(sessionId);
        // 判断user是否存在
        if(null == userDO){
            return false;
        }
        // 判断资源是否存在
        List<ResourceDO> resourceDOS = findResourcesByAccount(userDO.getAccount());
        if (CollectionUtils.isEmpty(resourceDOS)) {
            return false;
        }

        // 判断资源地址是否存在
        List<String> urls = resourceDOS.stream().map(ResourceDO::getUrl).collect(Collectors.toList());
        if (urls.contains(url)){
            return true;
        }else {
            return false;
        }

    }

}
