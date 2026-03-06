package com.neu.carbon.bid.domain;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品信息对象 bid_goods
 *
 * @author neuedu
 * @date 2023-05-10
 */
@ApiModel("商品信息")
public class BidGoods extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    @Excel(name = "商品名称")
    private String goodsName;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @Excel(name = "描述")
    private String description;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 期限：1年2月
     */
    @ApiModelProperty("期限：1年2月")
    @Excel(name = "期限：1年2月")
    private Long endType;

    /**
     * 权益
     */
    @ApiModelProperty("权益")
    @Excel(name = "权益")
    private String privilege;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    @Excel(name = "图标")
    private String pic;

    @ApiModelProperty("商品权益")
    private List<BidGoodsAttribute> goodsAttrs;

    public List<BidGoodsAttribute> getGoodsAttrs() {
        return goodsAttrs;
    }

    public void setGoodsAttrs(List<BidGoodsAttribute> goodsAttrs) {
        this.goodsAttrs = goodsAttrs;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setEndType(Long endType) {
        this.endType = endType;
    }

    public Long getEndType() {
        return endType;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
        if (StrUtil.isNotBlank(privilege)) {
            this.goodsAttrs = JSON.parseArray(privilege, BidGoodsAttribute.class);
        }
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("goodsId", getGoodsId())
                .append("goodsName", getGoodsName())
                .append("description", getDescription())
                .append("price", getPrice())
                .append("endType", getEndType())
                .append("privilege", getPrivilege())
                .append("pic", getPic())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
