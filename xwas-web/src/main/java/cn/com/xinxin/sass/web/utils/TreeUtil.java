package cn.com.xinxin.sass.web.utils;

import cn.com.xinxin.sass.web.vo.TreeVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by dengyunhui on 2018/5/4
 **/
public class TreeUtil {
    public static List<TreeVO> build(List<TreeVO> nodes){
        if (nodes == null){
            return null;
        }

        List<TreeVO> root = new ArrayList<>();

        Iterator<TreeVO> iterator = nodes.iterator();

        while (iterator.hasNext()){
            TreeVO node = iterator.next();
            if (Objects.equals("0",node.getParentId()) || Objects.equals(node.getParentId(),node.getId())){
                node.setExpanded(true);
                root.add(node);
                iterator.remove();
            }
        }

        buildChildren(root,nodes);

        return root;
    }

    public static List<TreeVO> build2(List<TreeVO> nodes){
        if (nodes == null){
            return null;
        }

        List<TreeVO> root = new ArrayList<>();

        Iterator<TreeVO> iterator = nodes.iterator();

        while (iterator.hasNext()){
            TreeVO node = iterator.next();
            if (Objects.equals("0",node.getParentId()) || Objects.equals(node.getParentId(),node.getId())){
                node.setExpanded(false);
                root.add(node);
                iterator.remove();
            }
        }

        buildChildren2(root,nodes);

        return root;
    }

    private static void buildChildren(List<TreeVO> parents, List<TreeVO> nodes){
        for (TreeVO parent : parents){
            String pid = parent.getId();

            for (TreeVO node : nodes){
                if (Objects.equals(pid,node.getParentId())){
                    node.setExpanded(false);
                    //parent.getChildren().add(node);
                    parent.getItems().add(node);
                }
            }
        }

        for (TreeVO parent : parents){
            List<TreeVO> children = parent.getItems();
            buildChildren(children,nodes);
        }
    }

    private static void buildChildren2(List<TreeVO> parents, List<TreeVO> nodes){
        for (TreeVO parent : parents){
            String pid = parent.getId();

            for (TreeVO node : nodes){
                if (Objects.equals(pid,node.getParentId())){
                    node.setExpanded(true);
                    parent.getChildren().add(node);
                    //parent.getItems().add(node);
                }
            }
        }

        for (TreeVO parent : parents){
            List<TreeVO> children = parent.getChildren();
            buildChildren2(children,nodes);
        }
    }
}
