package com.neu.carbon.bid.mapper;

import com.neu.carbon.bid.domain.BidOrderPaymentRecord;

import java.util.List;

/**
 * 订单支付记录Mapper接口
 *
 * @author neuedu
 * @date 2023-05-10
 */
public interface BidOrderPaymentRecordMapper {
    /**
     * 查询订单支付记录
     *
     * @param id 订单支付记录ID
     * @return 订单支付记录
     */
    BidOrderPaymentRecord selectBidOrderPaymentRecordById(Long id);

    /**
     * 通过订单号查询支付成功记录
     *
     * @param orderId
     * @return
     */
    BidOrderPaymentRecord selectBidOrderPaymentRecordByOrderId(Long orderId);

    /**
     * 查询订单支付记录列表
     *
     * @param bidOrderPaymentRecord 订单支付记录
     * @return 订单支付记录集合
     */
    List<BidOrderPaymentRecord> selectBidOrderPaymentRecordList(BidOrderPaymentRecord bidOrderPaymentRecord);

    /**
     * 新增订单支付记录
     *
     * @param bidOrderPaymentRecord 订单支付记录
     * @return 结果
     */
    int insertBidOrderPaymentRecord(BidOrderPaymentRecord bidOrderPaymentRecord);

    /**
     * 修改订单支付记录
     *
     * @param bidOrderPaymentRecord 订单支付记录
     * @return 结果
     */
    int updateBidOrderPaymentRecord(BidOrderPaymentRecord bidOrderPaymentRecord);

    /**
     * 删除订单支付记录
     *
     * @param id 订单支付记录ID
     * @return 结果
     */
    int deleteBidOrderPaymentRecordById(Long id);

    /**
     * 批量删除订单支付记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBidOrderPaymentRecordByIds(Long[] ids);


}
