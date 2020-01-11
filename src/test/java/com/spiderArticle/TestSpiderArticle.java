/**
 * 
 */
package com.spiderArticle;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yuanbaowang.bean.Article;

import kafka.utils.Json;
import yuanbaowang_cms_utils.FileUtils;

/**
 * @author 袁保旺
 *
 * 2020年1月8日 下午4:37:44 
 */
//生产者
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:producer.xml")
public class TestSpiderArticle {
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Test
	public void testArticle() throws IOException {
		File file = new File("D:\\爬取文章");
		String[] list = file.list();
		String title;
		for (String string : list) {
			title = string.replace(".txt", "");
			
			List<String> content = FileUtils.readFile("D://爬取文章//"+string);
			//创建一个文章对象
			Article article = new Article();
			article.setTitle(title);
			article.setContent(content.toString());
			String jsonString = JSON.toJSONString(article);
			kafkaTemplate.send("Article", jsonString);
			
		}
		
		
	}

}
