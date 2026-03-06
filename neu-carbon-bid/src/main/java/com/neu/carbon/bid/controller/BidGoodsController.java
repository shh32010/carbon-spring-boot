package com.neu.carbon.bid.controller;

import cn.hutool.json.JSONUtil;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.bid.domain.BidGoods;
import com.neu.carbon.bid.service.IBidGoodsService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品信息Controller
 *
 * @author neuedu
 * @date 2023-05-10
 */
@Api(tags = {"招投标（ 采购管理）-商品信息"})
@RestController
@RequestMapping("/bid/goods")
public class BidGoodsController extends BaseController {
    @Autowired
    private IBidGoodsService bidGoodsService;

    /**
     * 查询商品信息列表
     */
    @GetMapping("/list")
    @ApiOperation("查询商品信息列表")
    @DynamicResponseParameters(name = "BidGoodsList", properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = BidGoods.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(BidGoods bidGoods) {
        startPage();
        List<BidGoods> list = bidGoodsService.selectBidGoodsList(bidGoods);
        return getDataTable(list);
    }

    @GetMapping("/front/list")
    @ApiOperation("用户端查询商品信息列表")
    @DynamicResponseParameters(name = "BidGoodsList", properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = BidGoods.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo frontList(BidGoods bidGoods) {
        startPage();
        List<BidGoods> list = bidGoodsService.selectBidGoodsList(bidGoods);
        return getDataTable(list);
    }

    /**
     * 导出商品信息列表
     */
    @ApiOperation("导出商品信息列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('bid:goods:export')")
    @Log(title = "商品信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BidGoods bidGoods) {
        List<BidGoods> list = bidGoodsService.selectBidGoodsList(bidGoods);
        ExcelUtil<BidGoods> util = new ExcelUtil<BidGoods>(BidGoods.class);
        return util.exportExcel(list, "goods");
    }

    /**
     * 获取商品信息详细信息
     */
    @ApiOperation("获取商品信息详细信息")
    @DynamicResponseParameters(name = "BidGoodsGet", properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = BidGoods.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") Long goodsId) {
        return AjaxResult.success(bidGoodsService.selectBidGoodsById(goodsId));
    }

    @ApiOperation("用户端获取商品信息详细信息")
    @DynamicResponseParameters(name = "BidGoodsGet", properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = BidGoods.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/front/{goodsId}")
    public AjaxResult frontGetInfo(@PathVariable("goodsId") Long goodsId) {
        return AjaxResult.success(bidGoodsService.selectBidGoodsById(goodsId));
    }

    /**
     * 新增商品信息
     */
    @ApiOperation("新增商品信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('bid:goods:add')")
    @Log(title = "商品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BidGoods bidGoods) {
        return toAjax(bidGoodsService.insertBidGoods(bidGoods));
    }

    /**
     * 修改商品信息
     */
    @ApiOperation("修改商品信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('bid:goods:edit')")
    @Log(title = "商品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BidGoods bidGoods) {
        if(bidGoods.getGoodsAttrs() != null) {
            bidGoods.setPrivilege( JSONUtil.toJsonStr(bidGoods.getGoodsAttrs()));
        }
        return toAjax(bidGoodsService.updateBidGoods(bidGoods));
    }

    /**
     * 删除商品信息
     */
    @ApiOperation("删除商品信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('bid:goods:remove')")
    @Log(title = "商品信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{goodsIds}")
    public AjaxResult remove(@PathVariable Long[] goodsIds) {
        return toAjax(bidGoodsService.deleteBidGoodsByIds(goodsIds));
    }
}
