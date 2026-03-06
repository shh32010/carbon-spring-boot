package com.neu.carbon.bid.service.impl;

import cn.hutool.core.util.IdUtil;
import com.neu.carbon.bid.domain.BidGoods;
import com.neu.carbon.bid.mapper.BidGoodsMapper;
import com.neu.carbon.bid.service.IBidGoodsService;
import com.neu.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品信息Service业务层处理
 *
 * @author neuedu
 * @date 2023-05-10
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BidGoodsServiceImpl implements IBidGoodsService
{

    private final BidGoodsMapper bidGoodsMapper;

    /**
     * 查询商品信息
     *提交
     * @param goodsId 商品信息ID
     * @return 商品信息
     */
    @Override
    public BidGoods selectBidGoodsById(Long goodsId)
    {
        return bidGoodsMapper.selectBidGoodsById(goodsId);
    }

    /**
     * 查询商品信息列表
     *
     * @param bidGoods 商品信息
     * @return 商品信息
     */
    @Override
    public List<BidGoods> selectBidGoodsList(BidGoods bidGoods)
    {
        return bidGoodsMapper.selectBidGoodsList(bidGoods);
    }

    /**
     * 新增商品信息
     *
     * @param bidGoods 商品信息
     * @return 结果
     */
    @Override
    public int insertBidGoods(BidGoods bidGoods)
    {
        bidGoods.setCreateTime(DateUtils.getNowDate());
        bidGoods.setGoodsId(IdUtil.getSnowflake(1, 1).nextId());
        return bidGoodsMapper.insertBidGoods(bidGoods);
    }

    /**
     * 修改商品信息
     *
     * @param bidGoods 商品信息
     * @return 结果
     */
    @Override
    public int updateBidGoods(BidGoods bidGoods)
    {
        bidGoods.setUpdateTime(DateUtils.getNowDate());
        return bidGoodsMapper.updateBidGoods(bidGoods);
    }

    /**
     * 批量删除商品信息
     *
     * @param goodsIds 需要删除的商品信息ID
     * @return 结果
     */
    @Override
    public int deleteBidGoodsByIds(Long[] goodsIds)
    {
        return bidGoodsMapper.deleteBidGoodsByIds(goodsIds);
    }

    /**
     * 删除商品信息信息
     *
     * @param goodsId 商品信息ID
     * @return 结果
     */
    @Override
    public int deleteBidGoodsById(Long goodsId)
    {
        return bidGoodsMapper.deleteBidGoodsById(goodsId);
    }
}
