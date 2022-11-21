package com.lx.userservice.service.permission;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.userservice.dao.permission.PermissionDao;
import com.lx.userservice.pojo.permission.Permission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PermissionService extends ServiceImpl<PermissionDao, Permission> {


    public <E extends IPage<Permission>> E tree(E page, Wrapper<Permission> queryWrapper) {
        E page1 = super.page(page, queryWrapper);
        List<Permission> list = super.list();
        Map<String, Permission> all = list.stream().collect(Collectors.toMap(Permission::getId, a -> a, (k1, k2) -> k1));
        for (Permission record : list) {
            String parent = record.getParent();
            if (all.containsKey(parent)) {
                all.get(parent).addChild(record);
            }
        }
        List<Permission> root = new ArrayList<>((int) page1.getSize());
        page1.getRecords().forEach(i -> root.add(all.get(i.getId())));
        page1.setRecords(root);
        return page1;
    }
}
