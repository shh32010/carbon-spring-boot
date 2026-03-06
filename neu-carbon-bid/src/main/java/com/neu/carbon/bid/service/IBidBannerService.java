package com.neu.carbon.bid.service;

import java.util.List;
import com.neu.carbon.bid.domain.BidBanner;

/**
 * 招标轮播图Service接口
 * 
 * @author neuedu
 * @date 2023-12-07
 */
public interface IBidBannerService 
{
    /**
     * 查询招标轮播图
     * 
     * @param id 招标轮播图ID
     * @return 招标轮播图
     */
    public BidBanner selectBidBannerById(Long id);

    /**
     * 查询招标轮播图列表
     * 
     * @param bidBanner 招标轮播图
     * @return 招标轮播图集合
     */
    public List<BidBanner> selectBidBannerList(BidBanner bidBanner);

    /**
     * 新增招标轮播图
     * 
     * @param bidBanner 招标轮播图
     * @return 结果
     */
    public int insertBidBanner(BidBanner bidBanner);

    /**
     * 修改招标轮播图
     * 
     * @param bidBanner 招标轮播图
     * @return 结果
     */
    public int updateBidBanner(BidBanner bidBanner);

    /**
     * 批量删除招标轮播图
     * 
     * @param ids 需要删除的招标轮播图ID
     * @return 结果
     */
    public int deleteBidBannerByIds(Long[] ids);

    /**
     * 删除招标轮播图信息
     * 
     * @param id 招标轮播图ID
     * @return 结果
     */
    public int deleteBidBannerById(Long id);
}
