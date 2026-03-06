package com.neu.carbon.bid.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.bid.domain.BidOrderPaymentRecord;
import com.neu.carbon.bid.service.IBidOrderPaymentRecordService;
import com.neu.carbon.bid.service.IBidOrderService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.framework.client.PaymentClient;
import com.neu.system.domain.PaymentModel;
import com.neu.system.domain.PaymentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单支付记录Controller
 *
 * @author neuedu
 * @date 2023-05-10
 */
@Api(tags = {"招投标（ 采购管理）-订单支付记录"})
@RestController
@RequestMapping("/bid/payment/record")
public class BidOrderPaymentRecordController extends BaseController {
    private final IBidOrderPaymentRecordService bidOrderPaymentRecordService;
    private final IBidOrderService bidOrderService;
    private final IBidOrderPaymentRecordService paymentRecordService;
    @Autowired
    public BidOrderPaymentRecordController(IBidOrderPaymentRecordService bidOrderPaymentRecordService
            , IBidOrderService bidOrderService
            , IBidOrderPaymentRecordService paymentRecordService) {
        this.bidOrderPaymentRecordService = bidOrderPaymentRecordService;
        this.bidOrderService = bidOrderService;
        this.paymentRecordService = paymentRecordService;
    }

    /**
     * 查询订单支付记录列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询订单支付记录列表", hidden = true)
    @DynamicResponseParameters(name = "bidPaymentRecordList", properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = BidOrderPaymentRecord.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(BidOrderPaymentRecord bidOrderPaymentRecord) {
        startPage();
        List<BidOrderPaymentRecord> list = bidOrderPaymentRecordService.selectBidOrderPaymentRecordList(bidOrderPaymentRecord);
        return getDataTable(list);
    }


    /**
     * 导出订单支付记录列表
     */
    @ApiOperation(value = "导出订单支付记录列表", hidden = true)
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('bid:record:export')")
    @Log(title = "订单支付记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BidOrderPaymentRecord bidOrderPaymentRecord) {
        List<BidOrderPaymentRecord> list = bidOrderPaymentRecordService.selectBidOrderPaymentRecordList(bidOrderPaymentRecord);
        ExcelUtil<BidOrderPaymentRecord> util = new ExcelUtil<BidOrderPaymentRecord>(BidOrderPaymentRecord.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 获取订单支付记录详细信息
     */
    @ApiOperation(value = "获取订单支付记录详细信息", hidden = true)
    @DynamicResponseParameters(name = "bidPaymentRecordGet", properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = BidOrderPaymentRecord.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bidOrderPaymentRecordService.selectBidOrderPaymentRecordById(id));
    }

    @ApiOperation("用户端获取支付银行")
    @DynamicResponseParameters(name = "bidPaymentRecordGetBanks", properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = BidOrderPaymentRecord.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/banks")
    public AjaxResult getBanks() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "微信付款");
        map.put("2", "支付宝付款");
        return AjaxResult.success(map);
    }

    @ApiOperation("用户端获取支付二维码")
    @DynamicResponseParameters(name = "bidPaymentQrcode", properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = PaymentResponse.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/qrcode/{payMethod}")
    public AjaxResult getQrcode(@PathVariable(value = "payMethod") Integer payMethod) {
        PaymentModel model = new PaymentModel();
        model.setPaymentMethod(payMethod);
        PaymentResponse response = PaymentClient.preCreate(model);
        return AjaxResult.success(response);
    }

    /**
     * 新增订单支付记录
     */
    @ApiOperation(value = "新增订单支付记录", hidden = true)
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('bid:record:add')")
    @Log(title = "订单支付记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BidOrderPaymentRecord bidOrderPaymentRecord) {
        return toAjax(bidOrderPaymentRecordService.insertBidOrderPaymentRecord(bidOrderPaymentRecord));
    }

    /**
     * 修改订单支付记录
     */
    @ApiOperation(value = "修改订单支付记录", hidden = true)
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('bid:record:edit')")
    @Log(title = "订单支付记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BidOrderPaymentRecord bidOrderPaymentRecord) {
        return toAjax(bidOrderPaymentRecordService.updateBidOrderPaymentRecord(bidOrderPaymentRecord));
    }

    /**
     * 删除订单支付记录
     */
    @ApiOperation(value = "删除订单支付记录", hidden = true)
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('bid:record:remove')")
    @Log(title = "订单支付记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bidOrderPaymentRecordService.deleteBidOrderPaymentRecordByIds(ids));
    }
}
