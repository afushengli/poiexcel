package com.fsl.poiexcel.mapper;

import com.fsl.poiexcel.bean.Operatelock;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;


public interface OperatelockMapper  extends Mapper<Operatelock> {

    public int  batchUpdatebyid(Map<String,Object> map);

    public int  batchDeletebyid(Map<String,Object> map);
}
