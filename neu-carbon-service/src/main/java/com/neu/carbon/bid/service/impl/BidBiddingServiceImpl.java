package com.neu.carbon.bid.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.neu.carbon.bid.domain.BidBiddingFiles;
import com.neu.carbon.bid.domain.BidEnterpriseBidding;
import com.neu.carbon.bid.service.IBidBiddingFilesService;
import com.neu.carbon.bid.service.IBidEnterpriseBiddingService;
import com.neu.common.core.domain.model.LoginUser;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.ServletUtils;
import com.neu.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.bid.mapper.BidBiddingMapper;
import com.neu.carbon.bid.domain.BidBidding;
import com.neu.carbon.bid.service.IBidBiddingService;

/**
 * 招投标Service业务层处理
 *
 * @author neuedu
 * @date 2023-04-21
 */
@Service
public class BidBiddingServiceImpl implements IBidBiddingService {
    private final TokenService tokenService;

    private final BidBiddingMapper bidBiddingMapper;
    private final IBidBiddingFilesService bidBiddingFilesService;

    private final IBidEnterpriseBiddingService enterpriseBiddingService;

    private static final int bidCategory = 2;

    @Autowired
    public BidBiddingServiceImpl(TokenService tokenService, BidBiddingMapper bidBiddingMapper
            , IBidBiddingFilesService bidBiddingFilesService
            , IBidEnterpriseBiddingService enterpriseBiddingService
    ) {
        this.tokenService = tokenService;
        this.bidBiddingMapper = bidBiddingMapper;
        this.bidBiddingFilesService = bidBiddingFilesService;
        this.enterpriseBiddingService = enterpriseBiddingService;
    }


    /**
     * 查询招投标
     *
     * @param id 招投标ID
     * @return 招投标
     */
    @Override
    public BidBidding selectBidBiddingById(Long id) {
        BidBidding bidBidding = bidBiddingMapper.selectBidBiddingById(id);
        BidBiddingFiles bidBiddingFiles = new BidBiddingFiles();
        if (bidBidding != null) {
            bidBiddingFiles.setBidId(bidBidding.getId());
            List<BidBiddingFiles> filesList = bidBiddingFilesService.selectBidBiddingFilesList(bidBiddingFiles);
            bidBidding.setFilesList(filesList);
        }

        return bidBidding;
    }

    @Override
    public BidBidding selectNewestBidBidding() {
        return bidBiddingMapper.selectNewestBidBidding();
    }

    /**
     * 查询招投标列表
     *
     * @param bidBidding 招投标
     * @return 招投标
     */
    @Override
    public List<BidBidding> selectBidBiddingList(BidBidding bidBidding) {
        return bidBiddingMapper.selectBidBiddingList(bidBidding);
    }

    @Override
    public PageInfo<BidBidding> selectBidBiddingListByNoSignUp() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Page<BidBidding> page = bidBiddingMapper.selectBidBiddingListByEnterpriseIdNege(loginUser.getUser().getUserId());
        return new PageInfo<>(page);
    }

    @Override
    public PageInfo<BidBidding> selectBidBiddingListBySignUp() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long enterpriseId = loginUser.getUser().getUserId();

        Page<BidEnterpriseBidding> page = (Page<BidEnterpriseBidding>)enterpriseBiddingService.selectBidEnterpriseBiddingList(new BidEnterpriseBidding() {{
            setEnterpriseId(enterpriseId);
        }});

        // 为了对列表数据进行正确的处理，必须套一层
        PageInfo info = new PageInfo<>(page);

        List<BidBidding> newList = page.stream()
                .map(it -> {
                    BidBidding bidBidding = it.getBidBidding();
                    it.setBidBidding(null);
                    if (bidBidding == null) {
                        bidBidding = new BidBidding();
                    }
                    bidBidding.setEnterpriseBidding(it);

                    return bidBidding;
                })
                .collect(Collectors.toList());

        info.setList(newList);

        return info;
    }

    /**
     * 新增招投标
     *
     * @param bidBidding 招投标
     * @return 结果
     */
    @Override
    public int insertBidBidding(BidBidding bidBidding) {
        bidBidding.setCreateTime(DateUtils.getNowDate());
        bidBidding.setId(IdUtil.getSnowflake(1, 1).nextId());
        int result = bidBiddingMapper.insertBidBidding(bidBidding);
        saveFiles(bidBidding);

        return result;
    }

    /**
     * 修改招投标
     *
     * @param bidBidding 招投标
     * @return 结果
     */
    @Override
    public int updateBidBidding(BidBidding bidBidding) {
        bidBidding.setUpdateTime(DateUtils.getNowDate());
        saveFiles(bidBidding);
        if (bidBidding.getCategory() == bidCategory) {
            if (bidBidding.getStatus() != null) {
                //待开标 0 资质审核 1 已过期 2 开标结果 3
                BidEnterpriseBidding bidEnterpriseBidding = new BidEnterpriseBidding();
                List<BidEnterpriseBidding> list = enterpriseBiddingService
                        .selectBidEnterpriseBiddingList(bidEnterpriseBidding);
                if (list.stream().noneMatch(x -> x.getStatus().equals(bidBidding.getStatus()))) {
                    list.forEach(x -> {
                        x.setStatus(bidBidding.getStatus());
                        enterpriseBiddingService.updateBidEnterpriseBidding(x);
                    });
                }
            }
        }
        return bidBiddingMapper.updateBidBidding(bidBidding);
    }

    @Override
    public int view(Long id) {
        return bidBiddingMapper.view(id);
    }

    /**
     * 保存文件
     *
     * @param bidBidding
     */
    private void saveFiles(BidBidding bidBidding) {
        bidBiddingFilesService.deleteBidBiddingFilesByPId(bidBidding.getId());

        if (CollectionUtil.isNotEmpty(bidBidding.getFilesList())) {
            bidBidding.getFilesList().forEach(item -> {
                item.setId(IdUtil.getSnowflake(1, 1).nextId());
                item.setCreateTime(DateUtils.getNowDate());
                item.setBidId(bidBidding.getId());
                bidBiddingFilesService.insertBidBiddingFiles(item);
            });
        }
    }

    /**
     * 批量删除招投标
     *
     * @param ids 需要删除的招投标ID
     * @return 结果
     */
    @Override
    public int deleteBidBiddingByIds(Long[] ids) {
        return bidBiddingMapper.deleteBidBiddingByIds(ids);
    }

    /**
     * 删除招投标信息
     *
     * @param id 招投标ID
     * @return 结果
     */
    @Override
    public int deleteBidBiddingById(Long id) {
        return bidBiddingMapper.deleteBidBiddingById(id);
    }
}
