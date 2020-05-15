# 统一的WEB Portal入口

## 统一的业务管理界面入口, 接入操作日志层

## 定时调度任务使用XXL-JOB工具以及框架等

    1. 在安装配置定时调度时候安装， https://www.xuxueli.com/xxl-job/安装文档即可。将地址增加到对应的配置文件目录下即可
    

## 产品需求
    2.提供给其他系统调用方方便的API
    3.测试服务器地址：******  weblogic/Xinxinpass1
    

## 配置文件说明

    1.local----默认对应开发环境
    2.dev-----周版本测试环境
    3.test-----项目测试环境
    4.pre------生产验证环境
    5.prod-----对应生产环境配置文件
    
    

## 权限值编码规则:
   
   1.基本名称:SASS+功能名称(如资源权限操作:RESOURCE)+分类(如菜单:MENU)+操作(CREATE/REMOVE/UPDATE)
   具体实例比如说对于菜单菜单管理功能:
   
   2.查询以及列表功能: SASS_RESOURCE_MENU_QUERY_LIST
   3.权限值码表:
     3.1 资源权限类编码 
       菜单查询以及列表功能: SASS_RESOURCE_MENU_QUERY_LIST
       资源权限添加: SASS_RESOURCE_MENU_FUNCTION_ADD
       资源权限删除: SASS_RESOURCE_MENU_FUNCTION_REMOVE
       资源权限修改: SASS_RESOURCE_MENU_FUNCTION_UPDATE
   
