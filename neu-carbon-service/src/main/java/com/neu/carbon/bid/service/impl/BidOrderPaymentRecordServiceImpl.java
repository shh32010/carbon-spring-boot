package com.neu.carbon.bid.service.impl;

import cn.hutool.core.util.IdUtil;
import com.neu.carbon.bid.domain.BidOrderPaymentRecord;
import com.neu.carbon.bid.mapper.BidOrderPaymentRecordMapper;
import com.neu.carbon.bid.service.IBidOrderPaymentRecordService;
import com.neu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单支付记录Service业务层处理
 *
 * @author neuedu
 * @date 2023-05-10
 */
@Service
public class BidOrderPaymentRecordServiceImpl implements IBidOrderPaymentRecordService {
    @Autowired
    private BidOrderPaymentRecordMapper bidOrderPaymentRecordMapper;

    /**
     * 查询订单支付记录
     *
     * @param id 订单支付记录ID
     * @return 订单支付记录
     */
    @Override
    public BidOrderPaymentRecord selectBidOrderPaymentRecordById(Long id) {
        return bidOrderPaymentRecordMapper.selectBidOrderPaymentRecordById(id);
    }

    /**
     * 通过订单号查询支付成功记录
     *
     * @param orderId
     * @return
     */
    @Override
    public BidOrderPaymentRecord selectBidOrderPaymentRecordByOrderId(Long orderId) {
        return bidOrderPaymentRecordMapper.selectBidOrderPaymentRecordByOrderId(orderId);
    }

    /**
     * 查询订单支付记录列表
     *
     * @param bidOrderPaymentRecord 订单支付记录
     * @return 订单支付记录
     */
    @Override
    public List<BidOrderPaymentRecord> selectBidOrderPaymentRecordList(BidOrderPaymentRecord bidOrderPaymentRecord) {
        return bidOrderPaymentRecordMapper.selectBidOrderPaymentRecordList(bidOrderPaymentRecord);
    }

    /**
     * 新增订单支付记录
     *
     * @param bidOrderPaymentRecord 订单支付记录
     * @return 结果
     */
    @Override
    public int insertBidOrderPaymentRecord(BidOrderPaymentRecord bidOrderPaymentRecord) {
        bidOrderPaymentRecord.setCreateTime(DateUtils.getNowDate());
        bidOrderPaymentRecord.setId(IdUtil.getSnowflake(1, 1).nextId());
        bidOrderPaymentRecord.setPaymentTime(DateUtils.getNowDate());
        return bidOrderPaymentRecordMapper.insertBidOrderPaymentRecord(bidOrderPaymentRecord);
    }

    /**
     * 修改订单支付记录
     *
     * @param bidOrderPaymentRecord 订单支付记录
     * @return 结果
     */
    @Override
    public int updateBidOrderPaymentRecord(BidOrderPaymentRecord bidOrderPaymentRecord) {
        bidOrderPaymentRecord.setUpdateTime(DateUtils.getNowDate());
        return bidOrderPaymentRecordMapper.updateBidOrderPaymentRecord(bidOrderPaymentRecord);
    }

    /**
     * 批量删除订单支付记录
     *
     * @param ids 需要删除的订单支付记录ID
     * @return 结果
     */
    @Override
    public int deleteBidOrderPaymentRecordByIds(Long[] ids) {
        return bidOrderPaymentRecordMapper.deleteBidOrderPaymentRecordByIds(ids);
    }

    /**
     * 删除订单支付记录信息
     *
     * @param id 订单支付记录ID
     * @return 结果
     */
    @Override
    public int deleteBidOrderPaymentRecordById(Long id) {
        return bidOrderPaymentRecordMapper.deleteBidOrderPaymentRecordById(id);
    }
}
