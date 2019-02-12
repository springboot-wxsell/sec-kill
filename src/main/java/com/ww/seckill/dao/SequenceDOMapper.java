package com.ww.seckill.dao;

import com.ww.seckill.dataobject.SequenceDO;

public interface SequenceDOMapper {
    int deleteByPrimaryKey(String name);

    int insert(SequenceDO record);

    int insertSelective(SequenceDO record);

    SequenceDO selectByPrimaryKey(String name);

    SequenceDO selectByName(String name);

    int updateByPrimaryKeySelective(SequenceDO record);

    int updateByPrimaryKey(SequenceDO record);
}