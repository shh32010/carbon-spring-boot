package com.neu.smty.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.smty.domain.CmtArticle;
import com.neu.smty.service.ICmtArticleService;

/**
 * 鏂囩珷绠＄悊Controller
 * 
 * @author neusoft
 * @date 2021-07-11
 */
@RestController
@RequestMapping("/community/post")
public class CmtArticleController extends BaseController
{
    @Autowired
    private ICmtArticleService cmtArticleService;

    /**
     * 鏌ヨ鏂囩珷绠＄悊鍒楄〃
     */
    @PreAuthorize("@ss.hasPermi('cms:article:list')")
    @GetMapping("/list")
    public TableDataInfo list(CmtArticle cmtArticle)
    {
        startPage();
        List<CmtArticle> list = cmtArticleService.selectCmtArticleList(cmtArticle);
        return getDataTable(list);
    }

    /**
     * 瀵煎嚭鏂囩珷绠＄悊鍒楄〃
     */
    @PreAuthorize("@ss.hasPermi('cms:article:export')")
    @Log(title = "鏂囩珷绠＄悊", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CmtArticle cmtArticle)
    {
        List<CmtArticle> list = cmtArticleService.selectCmtArticleList(cmtArticle);
        ExcelUtil<CmtArticle> util = new ExcelUtil<CmtArticle>(CmtArticle.class);
        return util.exportExcel(list, "article");
    }

    /**
     * 鑾峰彇鏂囩珷绠＄悊璇︾粏淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('cms:article:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cmtArticleService.selectCmtArticleById(id));
    }

    /**
     * 鏂板鏂囩珷绠＄悊
     */
    @PreAuthorize("@ss.hasPermi('cms:article:add')")
    @Log(title = "鏂囩珷绠＄悊", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CmtArticle cmtArticle)
    {
        return toAjax(cmtArticleService.insertCmtArticle(cmtArticle));
    }

    /**
     * 淇敼鏂囩珷绠＄悊
     */
    @PreAuthorize("@ss.hasPermi('cms:article:edit')")
    @Log(title = "鏂囩珷绠＄悊", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CmtArticle cmtArticle)
    {
        return toAjax(cmtArticleService.updateCmtArticle(cmtArticle));
    }

    /**
     * 鍒犻櫎鏂囩珷绠＄悊
     */
    @PreAuthorize("@ss.hasPermi('cms:article:remove')")
    @Log(title = "鏂囩珷绠＄悊", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cmtArticleService.deleteCmtArticleByIds(ids));
    }
}

