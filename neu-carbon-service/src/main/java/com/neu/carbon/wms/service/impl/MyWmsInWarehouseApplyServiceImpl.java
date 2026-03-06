package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.MyWmsInWarehouseApplyMapper;
import com.neu.carbon.wms.domain.MyWmsInWarehouseApply;
import com.neu.carbon.wms.service.IMyWmsInWarehouseApplyService;

/**
 * 入库申请审核Service业务层处理
 * 
 * @author neuedu
 * @date 2025-04-05
 */
@Service
public class MyWmsInWarehouseApplyServiceImpl implements IMyWmsInWarehouseApplyService 
{
    @Autowired
    private MyWmsInWarehouseApplyMapper myWmsInWarehouseApplyMapper;

    /**
     * 查询入库申请审核
     * 
     * @param id 入库申请审核ID
     * @return 入库申请审核
     */
    @Override
    public MyWmsInWarehouseApply selectMyWmsInWarehouseApplyById(Long id)
    {
        return myWmsInWarehouseApplyMapper.selectMyWmsInWarehouseApplyById(id);
    }

    /**
     * 查询入库申请审核列表
     * 
     * @param myWmsInWarehouseApply 入库申请审核
     * @return 入库申请审核
     */
    @Override
    public List<MyWmsInWarehouseApply> selectMyWmsInWarehouseApplyList(MyWmsInWarehouseApply myWmsInWarehouseApply)
    {
        return myWmsInWarehouseApplyMapper.selectMyWmsInWarehouseApplyList(myWmsInWarehouseApply);
    }

    /**
     * 新增入库申请审核
     * 
     * @param myWmsInWarehouseApply 入库申请审核
     * @return 结果
     */
    @Override
    public int insertMyWmsInWarehouseApply(MyWmsInWarehouseApply myWmsInWarehouseApply)
    {
        return myWmsInWarehouseApplyMapper.insertMyWmsInWarehouseApply(myWmsInWarehouseApply);
    }

    /**
     * 修改入库申请审核
     * 
     * @param myWmsInWarehouseApply 入库申请审核
     * @return 结果
     */
    @Override
    public int updateMyWmsInWarehouseApply(MyWmsInWarehouseApply myWmsInWarehouseApply)
    {
        return myWmsInWarehouseApplyMapper.updateMyWmsInWarehouseApply(myWmsInWarehouseApply);
    }

    /**
     * 批量删除入库申请审核
     * 
     * @param ids 需要删除的入库申请审核ID
     * @return 结果
     */
    @Override
    public int deleteMyWmsInWarehouseApplyByIds(Long[] ids)
    {
        return myWmsInWarehouseApplyMapper.deleteMyWmsInWarehouseApplyByIds(ids);
    }

    /**
     * 删除入库申请审核信息
     * 
     * @param id 入库申请审核ID
     * @return 结果
     */
    @Override
    public int deleteMyWmsInWarehouseApplyById(Long id)
    {
        return myWmsInWarehouseApplyMapper.deleteMyWmsInWarehouseApplyById(id);
    }
}
