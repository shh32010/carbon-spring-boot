package com.neu.carbon.bid.service;

import com.neu.carbon.bid.domain.BidOrderPaymentRecord;

import java.util.List;

/**
 * 订单支付记录Service接口
 *
 * @author neuedu
 * @date 2023-05-10
 */
public interface IBidOrderPaymentRecordService {
    /**
     * 查询订单支付记录
     *
     * @param id 订单支付记录ID
     * @return 订单支付记录
     */
    BidOrderPaymentRecord selectBidOrderPaymentRecordById(Long id);

    /**
     * 通过订单Id查询支持成功的支付记录
     *
     * @param id
     * @return
     */
    BidOrderPaymentRecord selectBidOrderPaymentRecordByOrderId(Long id);

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
     * 批量删除订单支付记录
     *
     * @param ids 需要删除的订单支付记录ID
     * @return 结果
     */
    int deleteBidOrderPaymentRecordByIds(Long[] ids);

    /**
     * 删除订单支付记录信息
     *
     * @param id 订单支付记录ID
     * @return 结果
     */
    int deleteBidOrderPaymentRecordById(Long id);
}
