package com.neu.carbon.bid.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单支付记录对象 bid_order_payment_record
 *
 * @author neuedu
 * @date 2023-05-10
 */
@ApiModel("订单支付记录")
public class BidOrderPaymentRecord extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 7589494573871029745L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    /** 订单编号 */
    @ApiModelProperty("订单编号")
    @Excel(name = "订单编号")
    private Long orderId;

    /** 网关流水 */
    @ApiModelProperty("网关流水")
    @Excel(name = "网关流水")
    private String gatewayId;

    /** 价格 */
    @ApiModelProperty("价格")
    @Excel(name = "价格")
    private BigDecimal price;

    /** 支付方式：1微信 2支付宝 */
    @ApiModelProperty("支付方式：1微信 2支付宝")
    @Excel(name = "支付方式：1微信 2支付宝")
    private Long paymentMethod;

    /** 支付状态：1成功0失败 */
    @ApiModelProperty("支付状态：1成功0失败")
    @Excel(name = "支付状态：1成功0失败")
    private Long paymentStatus;

    /** 支付时间 */
    @ApiModelProperty(value="支付时间",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId()
    {
        return orderId;
    }
    public void setGatewayId(String gatewayId)
    {
        this.gatewayId = gatewayId;
    }

    public String getGatewayId()
    {
        return gatewayId;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setPaymentMethod(Long paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentMethod()
    {
        return paymentMethod;
    }
    public void setPaymentStatus(Long paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public Long getPaymentStatus()
    {
        return paymentStatus;
    }
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentTime()
    {
        return paymentTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("gatewayId", getGatewayId())
            .append("price", getPrice())
            .append("paymentMethod", getPaymentMethod())
            .append("paymentStatus", getPaymentStatus())
            .append("paymentTime", getPaymentTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
