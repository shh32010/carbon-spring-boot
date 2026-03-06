package com.neu.carbon.bid.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 招标轮播图对象 bid_banner
 * 
 * @author neuedu
 * @date 2023-12-07
 */
@ApiModel("招标轮播图")
public class BidBanner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    private Long id;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String url;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("url", getUrl())
            .toString();
    }
}
