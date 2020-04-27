package cn.com.xinxin.sass.web.utils;

import cn.com.xinxin.sass.web.vo.DeptTreeVO;
import cn.com.xinxin.sass.web.vo.MenuTreeVO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author: zhouyang
 * @created: 20/04/2020.
 * @updater:
 * @description: 构建树形结果工具类
 */
public class TreeResultUtil {



    public static List<MenuTreeVO> buildCheckedTree(List<MenuTreeVO> nodes,List<String> checkList){

        if (nodes == null){
            return null;
        }

        List<MenuTreeVO> root = new ArrayList<>();

        Iterator<MenuTreeVO> iterator = nodes.iterator();

        while (iterator.hasNext()){

            MenuTreeVO node = iterator.next();

            if(checkList.contains(node.getCode())){
                // 如果已经checked，选中
                node.setChecked(true);
            }else{
                node.setChecked(false);
            }

            if (Objects.equals("0",node.getParentId()) || Objects.equals(node.getParentId(),node.getId())){
                node.setExpanded(true);
                root.add(node);
                iterator.remove();
            }
        }

        buildChildren(root,nodes);

        return root;
    }


    public static List<MenuTreeVO> build(List<MenuTreeVO> nodes){
        if (nodes == null){
            return null;
        }

        List<MenuTreeVO> root = new ArrayList<>();

        Iterator<MenuTreeVO> iterator = nodes.iterator();

        while (iterator.hasNext()){
            MenuTreeVO node = iterator.next();
            if (Objects.equals("0",node.getParentId()) || Objects.equals(node.getParentId(),node.getId())){
                node.setExpanded(true);
                root.add(node);
                iterator.remove();
            }
        }

        buildChildren(root,nodes);

        return root;
    }

    public static List<MenuTreeVO> build2(List<MenuTreeVO> nodes){
        if (nodes == null){
            return null;
        }

        List<MenuTreeVO> root = new ArrayList<>();

        Iterator<MenuTreeVO> iterator = nodes.iterator();

        while (iterator.hasNext()){
            MenuTreeVO node = iterator.next();
            if (Objects.equals("0",node.getParentId()) || Objects.equals(node.getParentId(),node.getId())){
                node.setExpanded(false);
                root.add(node);
                iterator.remove();
            }
        }

        buildChildren2(root,nodes);

        return root;
    }

    private static void buildChildren(List<MenuTreeVO> parents, List<MenuTreeVO> nodes){
        for (MenuTreeVO parent : parents){
            String pid = parent.getId();

            for (MenuTreeVO node : nodes){
                if (Objects.equals(pid,node.getParentId())){
                    node.setExpanded(false);
                    parent.getChildren().add(node);
                    //parent.getItems().add(node);
                }
            }
        }

        for (MenuTreeVO parent : parents){
            List<MenuTreeVO> children = parent.getChildren();
            buildChildren(children,nodes);
        }
    }

    private static void buildChildren2(List<MenuTreeVO> parents, List<MenuTreeVO> nodes){
        for (MenuTreeVO parent : parents){
            String pid = parent.getId();

            for (MenuTreeVO node : nodes){
                if (Objects.equals(pid,node.getParentId())){
                    node.setExpanded(true);
                    parent.getChildren().add(node);
                    //parent.getItems().add(node);
                }
            }
        }

        for (MenuTreeVO parent : parents){
            List<MenuTreeVO> children = parent.getChildren();
            buildChildren2(children,nodes);
        }
    }


    public static List<DeptTreeVO> buildDeptTrees(List<DeptTreeVO> nodes){
        if (nodes == null){
            return null;
        }

        List<DeptTreeVO> root = new ArrayList<>();

        Iterator<DeptTreeVO> iterator = nodes.iterator();

        while (iterator.hasNext()){
            DeptTreeVO node = iterator.next();
            if (Objects.equals("0",node.getParentId()) || Objects.equals(node.getParentId(),node.getDepartmentId())){
                node.setExpanded(true);
                root.add(node);
                iterator.remove();
            }
        }

        buildDeptTreeChildren(root,nodes);

        // 如果不需要构建树形结构，直接返回
        if(CollectionUtils.isEmpty(root)){
            return nodes;
        }

        return root;
    }


    private static void buildDeptTreeChildren(List<DeptTreeVO> parents, List<DeptTreeVO> nodes){



        for (DeptTreeVO parent : parents){
            String pid = parent.getDepartmentId();

            for (DeptTreeVO node : nodes){
                if (Objects.equals(pid,node.getParentId())){
                    node.setExpanded(false);
                    parent.getChildren().add(node);
                }
            }
        }

        for (DeptTreeVO parent : parents){
            List<DeptTreeVO> children = parent.getChildren();
            buildDeptTreeChildren(children,nodes);
        }
    }

}
