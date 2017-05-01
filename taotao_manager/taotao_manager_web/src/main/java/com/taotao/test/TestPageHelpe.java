package com.taotao.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestPageHelpe {

	@Test
	public void testPageHelper() {

		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//从spring容器中获得Mapper的代理对象
		TbItemMapper bean = ac.getBean(TbItemMapper.class);

		TbItemExample example = new TbItemExample();

		/*添加查询条件*/
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andTitleLike("%双卡双待%");
		criteria.andSellPointLike("%清仓%");

		//分页处理
		PageHelper.startPage(2,10);
		List<TbItem> list = bean.selectByExample(example);
		//取商品列表
//		for (TbItem tbItem : list) {
//			System.out.println(tbItem.getTitle());
//		}

		for (TbItem tbItem : list) {
			System.out.println(tbItem.getTitle());
		}
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		System.out.println("共有商品:" + total);
	}
}
