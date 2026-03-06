package com.neu.carbon.bid.service;


import com.neu.carbon.bid.domain.BidGoods;

import java.util.List;

/**
 * 商品信息Service接口
 *
 * @author neuedu
 * @date 2023-05-10
 */
public interface IBidGoodsService
{
    /**
     * 查询商品信息
     *
     * @param goodsId 商品信息ID
     * @return 商品信息
     */
    public BidGoods selectBidGoodsById(Long goodsId);

    /**
     * 查询商品信息列表
     *
     * @param bidGoods 商品信息
     * @return 商品信息集合
     */
    public List<BidGoods> selectBidGoodsList(BidGoods bidGoods);

    /**
     * 新增商品信息
     *
     * @param bidGoods 商品信息
     * @return 结果
     */
    public int insertBidGoods(BidGoods bidGoods);

    /**
     * 修改商品信息
     *
     * @param bidGoods 商品信息
     * @return 结果
     */
    public int updateBidGoods(BidGoods bidGoods);

    /**
     * 批量删除商品信息
     *
     * @param goodsIds 需要删除的商品信息ID
     * @return 结果
     */
    public int deleteBidGoodsByIds(Long[] goodsIds);

    /**
     * 删除商品信息信息
     *
     * @param goodsId 商品信息ID
     * @return 结果
     */
    public int deleteBidGoodsById(Long goodsId);
}
