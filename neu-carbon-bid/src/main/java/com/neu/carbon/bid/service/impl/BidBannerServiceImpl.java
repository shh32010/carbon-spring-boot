package com.neu.carbon.bid.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.bid.mapper.BidBannerMapper;
import com.neu.carbon.bid.domain.BidBanner;
import com.neu.carbon.bid.service.IBidBannerService;

/**
 * 招标轮播图Service业务层处理
 * 
 * @author neuedu
 * @date 2023-12-07
 */
@Service
public class BidBannerServiceImpl implements IBidBannerService 
{
    @Autowired
    private BidBannerMapper bidBannerMapper;

    /**
     * 查询招标轮播图
     * 
     * @param id 招标轮播图ID
     * @return 招标轮播图
     */
    @Override
    public BidBanner selectBidBannerById(Long id)
    {
        return bidBannerMapper.selectBidBannerById(id);
    }

    /**
     * 查询招标轮播图列表
     * 
     * @param bidBanner 招标轮播图
     * @return 招标轮播图
     */
    @Override
    public List<BidBanner> selectBidBannerList(BidBanner bidBanner)
    {
        return bidBannerMapper.selectBidBannerList(bidBanner);
    }

    /**
     * 新增招标轮播图
     * 
     * @param bidBanner 招标轮播图
     * @return 结果
     */
    @Override
    public int insertBidBanner(BidBanner bidBanner)
    {
        return bidBannerMapper.insertBidBanner(bidBanner);
    }

    /**
     * 修改招标轮播图
     * 
     * @param bidBanner 招标轮播图
     * @return 结果
     */
    @Override
    public int updateBidBanner(BidBanner bidBanner)
    {
        return bidBannerMapper.updateBidBanner(bidBanner);
    }

    /**
     * 批量删除招标轮播图
     * 
     * @param ids 需要删除的招标轮播图ID
     * @return 结果
     */
    @Override
    public int deleteBidBannerByIds(Long[] ids)
    {
        return bidBannerMapper.deleteBidBannerByIds(ids);
    }

    /**
     * 删除招标轮播图信息
     * 
     * @param id 招标轮播图ID
     * @return 结果
     */
    @Override
    public int deleteBidBannerById(Long id)
    {
        return bidBannerMapper.deleteBidBannerById(id);
    }
}
