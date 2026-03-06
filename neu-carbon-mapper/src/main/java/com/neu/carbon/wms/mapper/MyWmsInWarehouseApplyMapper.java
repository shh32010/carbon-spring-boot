package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.MyWmsInWarehouseApply;

/**
 * 入库申请审核Mapper接口
 * 
 * @author neuedu
 * @date 2025-04-05
 */
public interface MyWmsInWarehouseApplyMapper 
{
    /**
     * 查询入库申请审核
     * 
     * @param id 入库申请审核ID
     * @return 入库申请审核
     */
    public MyWmsInWarehouseApply selectMyWmsInWarehouseApplyById(Long id);

    /**
     * 查询入库申请审核列表
     * 
     * @param myWmsInWarehouseApply 入库申请审核
     * @return 入库申请审核集合
     */
    public List<MyWmsInWarehouseApply> selectMyWmsInWarehouseApplyList(MyWmsInWarehouseApply myWmsInWarehouseApply);

    /**
     * 新增入库申请审核
     * 
     * @param myWmsInWarehouseApply 入库申请审核
     * @return 结果
     */
    public int insertMyWmsInWarehouseApply(MyWmsInWarehouseApply myWmsInWarehouseApply);

    /**
     * 修改入库申请审核
     * 
     * @param myWmsInWarehouseApply 入库申请审核
     * @return 结果
     */
    public int updateMyWmsInWarehouseApply(MyWmsInWarehouseApply myWmsInWarehouseApply);

    /**
     * 删除入库申请审核
     * 
     * @param id 入库申请审核ID
     * @return 结果
     */
    public int deleteMyWmsInWarehouseApplyById(Long id);

    /**
     * 批量删除入库申请审核
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMyWmsInWarehouseApplyByIds(Long[] ids);
}
