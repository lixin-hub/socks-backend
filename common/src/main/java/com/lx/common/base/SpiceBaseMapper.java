package com.lx.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

//实现批量插入
public interface SpiceBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入
     * {@link com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn}
     *
     * @param entityList 要插入的数据
     * @return 成功插入的数据条数
     */
    int insertBatchSomeColumn(List<T> entityList);
}