package com.neu.carbon.wms.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.enums.BusinessType;
import com.neu.carbon.wms.domain.MyWmsInWarehouseApply;
import com.neu.carbon.wms.service.IMyWmsInWarehouseApplyService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 入库申请审核Controller
 * 
 * @author neuedu
 * @date 2025-04-05
 */
@Api(tags = {"入库申请审核"})
@RestController
@RequestMapping("/wmsApply/inWarahouseApplyAudit")
public class MyWmsInWarehouseApplyController extends BaseController
{
    @Autowired
    private IMyWmsInWarehouseApplyService myWmsInWarehouseApplyService;

    /**
     * 查询入库申请审核列表
     */
    @GetMapping("/list")
    @ApiOperation("查询入库申请审核列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MyWmsInWarehouseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MyWmsInWarehouseApply myWmsInWarehouseApply)
    {
        startPage();
        List<MyWmsInWarehouseApply> list = myWmsInWarehouseApplyService.selectMyWmsInWarehouseApplyList(myWmsInWarehouseApply);
        return getDataTable(list);
    }

    /**
     * 导出入库申请审核列表
     */
    @ApiOperation("导出入库申请审核列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApplyAudit:export')")
    @Log(title = "入库申请审核", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MyWmsInWarehouseApply myWmsInWarehouseApply)
    {
        List<MyWmsInWarehouseApply> list = myWmsInWarehouseApplyService.selectMyWmsInWarehouseApplyList(myWmsInWarehouseApply);
        ExcelUtil<MyWmsInWarehouseApply> util = new ExcelUtil<MyWmsInWarehouseApply>(MyWmsInWarehouseApply.class);
        return util.exportExcel(list, "inWarahouseApplyAudit");
    }

    /**
     * 获取入库申请审核详细信息
     */
    @ApiOperation("获取入库申请审核详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MyWmsInWarehouseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(myWmsInWarehouseApplyService.selectMyWmsInWarehouseApplyById(id));
    }

    /**
     * 新增入库申请审核
     */
    @ApiOperation("新增入库申请审核")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApplyAudit:add')")
    @Log(title = "入库申请审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MyWmsInWarehouseApply myWmsInWarehouseApply)
    {
        return toAjax(myWmsInWarehouseApplyService.insertMyWmsInWarehouseApply(myWmsInWarehouseApply));
    }

    /**
     * 修改入库申请审核
     */
    @ApiOperation("修改入库申请审核")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApplyAudit:edit')")
    @Log(title = "入库申请审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MyWmsInWarehouseApply myWmsInWarehouseApply)
    {
        return toAjax(myWmsInWarehouseApplyService.updateMyWmsInWarehouseApply(myWmsInWarehouseApply));
    }

    /**
     * 删除入库申请审核
     */
    @ApiOperation("删除入库申请审核")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApplyAudit:remove')")
    @Log(title = "入库申请审核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(myWmsInWarehouseApplyService.deleteMyWmsInWarehouseApplyByIds(ids));
    }
}
