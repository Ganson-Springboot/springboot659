package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.JianshenxiangmuEntity;
import com.entity.view.JianshenxiangmuView;

import com.service.JianshenxiangmuService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 健身项目
 * 后端接口
 * @author 
 * @email 
 * @date 2022-04-16 15:51:16
 */
@RestController
@RequestMapping("/jianshenxiangmu")
public class JianshenxiangmuController {
    @Autowired
    private JianshenxiangmuService jianshenxiangmuService;

    @Autowired
    private StoreupService storeupService;

    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JianshenxiangmuEntity jianshenxiangmu,
		HttpServletRequest request){
    	if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
    		jianshenxiangmu.setUserid((Long)request.getSession().getAttribute("userId"));
    	}
        EntityWrapper<JianshenxiangmuEntity> ew = new EntityWrapper<JianshenxiangmuEntity>();
		PageUtils page = jianshenxiangmuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jianshenxiangmu), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JianshenxiangmuEntity jianshenxiangmu, 
		HttpServletRequest request){
        EntityWrapper<JianshenxiangmuEntity> ew = new EntityWrapper<JianshenxiangmuEntity>();
		PageUtils page = jianshenxiangmuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jianshenxiangmu), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JianshenxiangmuEntity jianshenxiangmu){
       	EntityWrapper<JianshenxiangmuEntity> ew = new EntityWrapper<JianshenxiangmuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jianshenxiangmu, "jianshenxiangmu")); 
        return R.ok().put("data", jianshenxiangmuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JianshenxiangmuEntity jianshenxiangmu){
        EntityWrapper< JianshenxiangmuEntity> ew = new EntityWrapper< JianshenxiangmuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jianshenxiangmu, "jianshenxiangmu")); 
		JianshenxiangmuView jianshenxiangmuView =  jianshenxiangmuService.selectView(ew);
		return R.ok("查询健身项目成功").put("data", jianshenxiangmuView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JianshenxiangmuEntity jianshenxiangmu = jianshenxiangmuService.selectById(id);
		jianshenxiangmu.setClicknum(jianshenxiangmu.getClicknum()+1);
		jianshenxiangmu.setClicktime(new Date());
		jianshenxiangmuService.updateById(jianshenxiangmu);
        return R.ok().put("data", jianshenxiangmu);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JianshenxiangmuEntity jianshenxiangmu = jianshenxiangmuService.selectById(id);
		jianshenxiangmu.setClicknum(jianshenxiangmu.getClicknum()+1);
		jianshenxiangmu.setClicktime(new Date());
		jianshenxiangmuService.updateById(jianshenxiangmu);
        return R.ok().put("data", jianshenxiangmu);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JianshenxiangmuEntity jianshenxiangmu, HttpServletRequest request){
    	jianshenxiangmu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jianshenxiangmu);
    	jianshenxiangmu.setUserid((Long)request.getSession().getAttribute("userId"));
        jianshenxiangmuService.insert(jianshenxiangmu);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody JianshenxiangmuEntity jianshenxiangmu, HttpServletRequest request){
    	jianshenxiangmu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jianshenxiangmu);
        jianshenxiangmuService.insert(jianshenxiangmu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody JianshenxiangmuEntity jianshenxiangmu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jianshenxiangmu);
        jianshenxiangmuService.updateById(jianshenxiangmu);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        jianshenxiangmuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<JianshenxiangmuEntity> wrapper = new EntityWrapper<JianshenxiangmuEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}
		if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
    		wrapper.eq("userid", (Long)request.getSession().getAttribute("userId"));
    	}


		int count = jianshenxiangmuService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,JianshenxiangmuEntity jianshenxiangmu, HttpServletRequest request,String pre){
        EntityWrapper<JianshenxiangmuEntity> ew = new EntityWrapper<JianshenxiangmuEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicknum");
        params.put("order", "desc");
		PageUtils page = jianshenxiangmuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jianshenxiangmu), params), params));
        return R.ok().put("data", page);
    }







}
