package com.lx.userservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.common.base.Result;
import com.lx.userservice.pojo.Area;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class AreaServiceTest {

    @Autowired
    AreaService service;

    @Test
    public void selectTree() {
        String parentId = "1";
        List<Area> tree = service.getTree(parentId, null);
        log.info(Result.builder().ok().data(tree).build().toString());
    }

    //    @Test
    public void select() {
        List<Area> provinces = service.selectProvince();
        for (Area province : provinces) {
            province.setParentsCode("0");
//            province.setTreeName(province.getName());
            service.updateById(province);
            travel(province.getId(), province.getName() + "/", "0," + province.getId());
        }
    }

    public void travel(String parent, String treeName, String parentCode) {
        List<Area> provinces = service.selectList(new QueryWrapper<Area>().eq("parent", parent));
        if (provinces == null || provinces.size() == 0) return;
        for (Area province : provinces) {
//            province.setTreeName(treeName+province.getName());
            province.setParentsCode(parentCode);
            service.updateById(province);
//            System.out.println(treeName+province.getName());
            System.out.println(treeName + province.getParentsCode());
            travel(province.getId(), province.getTreeName() + "/", province.getParentsCode() + "," + province.getId());
        }
    }

    @Test
    public void setRoot() {
        List<Area> selectList = service.selectList(new QueryWrapper<>());
        HashMap<String, Area> hashMap = new HashMap<>(30);
        List<Area> rootList = new ArrayList<>(30);
        for (Area t : selectList) {
            String parent = t.getParent();
            hashMap.put(t.getId(), t);
            if (parent.equals("0")) {
                rootList.add(t);
            }
        }
        for (Area t : selectList) {
            String parent = t.getParent();
            if (hashMap.containsKey(parent)) {
                hashMap.get(parent).addChild(t);
            }
        }
        tr(rootList);
    }


    public void tr(List<Area> roots) {
        for (Area area : roots) {
            List<Area> children = area.getChildren();
            if (children==null||children.size() == 0) {
                log.info(area.getTreeName());
                area.setLeaf(true);
                service.updateById(area);
            } else {
                tr(children);
            }

        }
    }
}