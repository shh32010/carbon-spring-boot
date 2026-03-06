package com.neu.carbon.bid.mapper;

import java.util.List;

import com.github.pagehelper.Page;
import com.neu.carbon.bid.domain.BidBidding;

/**
 * 招投标Mapper接口
 *
 * @author neuedu
 * @date 2023-04-21
 */
public interface BidBiddingMapper
{
    /**
     * 查询招投标
     *
     * @param id 招投标ID
     * @return 招投标
     */
    public BidBidding selectBidBiddingById(Long id);

    public BidBidding selectNewestBidBidding();

    /**
     * 查询招投标列表
     *
     * @param bidBidding 招投标
     * @return 招投标集合
     */
    public List<BidBidding> selectBidBiddingList(BidBidding bidBidding);

    Page<BidBidding> selectBidBiddingListByEnterpriseIdNege(Long enterpriseId);

    Page<BidBidding> selectBidBiddingListByEnterpriseId(Long enterpriseId);

    /**
     * 新增招投标
     *
     * @param bidBidding 招投标
     * @return 结果
     */
    public int insertBidBidding(BidBidding bidBidding);

    /**
     * 修改招投标
     *
     * @param bidBidding 招投标
     * @return 结果
     */
    public int updateBidBidding(BidBidding bidBidding);

    int view(Long id);

    /**
     * 删除招投标
     *
     * @param id 招投标ID
     * @return 结果
     */
    public int deleteBidBiddingById(Long id);

    /**
     * 批量删除招投标
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBidBiddingByIds(Long[] ids);
}
