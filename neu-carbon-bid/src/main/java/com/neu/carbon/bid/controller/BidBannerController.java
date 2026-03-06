package com.neu.carbon.bid.controller;

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
import com.neu.carbon.bid.domain.BidBanner;
import com.neu.carbon.bid.service.IBidBannerService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 招标轮播图Controller
 * 
 * @author neuedu
 * @date 2023-12-07
 */
@Api(tags = {"招标轮播图"})
@RestController
@RequestMapping("/bid/banner")
public class BidBannerController extends BaseController
{
    @Autowired
    private IBidBannerService bidBannerService;

    /**
     * 查询招标轮播图列表
     */
    @GetMapping("/list")
    @ApiOperation("查询招标轮播图列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = BidBanner.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(BidBanner bidBanner)
    {
        startPage();
        List<BidBanner> list = bidBannerService.selectBidBannerList(bidBanner);
        return getDataTable(list);
    }

    /**
     * 导出招标轮播图列表
     */
    @ApiOperation("导出招标轮播图列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('bid:banner:export')")
    @Log(title = "招标轮播图", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BidBanner bidBanner)
    {
        List<BidBanner> list = bidBannerService.selectBidBannerList(bidBanner);
        ExcelUtil<BidBanner> util = new ExcelUtil<BidBanner>(BidBanner.class);
        return util.exportExcel(list, "banner");
    }

    /**
     * 获取招标轮播图详细信息
     */
    @ApiOperation("获取招标轮播图详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = BidBanner.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bidBannerService.selectBidBannerById(id));
    }

    /**
     * 新增招标轮播图
     */
    @ApiOperation("新增招标轮播图")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('bid:banner:add')")
    @Log(title = "招标轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BidBanner bidBanner)
    {
        return toAjax(bidBannerService.insertBidBanner(bidBanner));
    }

    /**
     * 修改招标轮播图
     */
    @ApiOperation("修改招标轮播图")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('bid:banner:edit')")
    @Log(title = "招标轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BidBanner bidBanner)
    {
        return toAjax(bidBannerService.updateBidBanner(bidBanner));
    }

    /**
     * 删除招标轮播图
     */
    @ApiOperation("删除招标轮播图")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('bid:banner:remove')")
    @Log(title = "招标轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bidBannerService.deleteBidBannerByIds(ids));
    }
}
