/**
 * 
 */
package spider;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import yuanbaowang_cms_utils.FileUtils;
/**
 * @author 袁保旺
 *
 * 2019年12月30日 下午8:00:20 
 */

public class SpiderTest {
	//Jsoup  是一个 Html 解析器
	
	@Test
	public void paChong() throws IOException {
		//模拟浏览器发送请求
		Connection connect = Jsoup.connect("http://news.sohu.com/");
		//发送get请求 获取所有源码
		Document document = connect.get();
		//获取所有的div
		Elements select = document.select(".list16");
		for (Element list16 : select) {
		//	System.out.println(list16);
			Elements select2 = list16.select("a[href]");
			for (Element a: select2) {
				//System.out.println(a);
				String url = a.attr("href");
				if(!url.startsWith("http")) {
					url="http:"+url;
				}
				//System.out.println(url);
				if(!url.contains("subject")&&!url.contains("sports")) {
					//模拟浏览器发送请求
					Connection connect2 = Jsoup.connect(url);
					//发送get请求 获取所有源码
					Document document2 = connect2.get();
					//System.out.println(document2);
					Elements select3 = document2.select(".article");
					String content = null;
					for (Element element : select) {
						content = element.text();
						//System.out.println(content);
					}
					String title = a.attr("title");
					//System.out.println(title);
					title = title.replace("|", "").replace("*", "").replace("\"", "").replace("?", "").replace("/", "")
							.replace("\\", "").replace(">", "").replace("<", "").replace(":", "");
					
					//写入本地磁盘
					FileUtils.writeFile("D:/爬取文章/"+title+".txt", content, "utf8");
					System.err.println("爬取成功，已写入文件！");
				}
			}
		}
			
	}
	

	
	
public static void main(String[] args) throws Exception  {
		
		//模拟浏览器发请求
		Connection connect = Jsoup.connect("http://news.sohu.com/");
		Document doc = connect.get();
//		System.out.println(doc);
		Elements select = doc.select(".list16");
		for (Element list16 : select) {
//			System.out.println(list16);
			Elements select2 = list16.select("a[href]");
				for (Element a : select2) {
//					System.out.println(a);
					String url = a.attr("href");
					if(!url.startsWith("http")) {
						url="http:"+url;
					}
					System.out.println(url);
					if(!url.contains("subject")&&!url.contains("sports")) {
						Connection connect2 = Jsoup.connect(url);
						Document document = connect2.get();
						Elements select3 = document.select(".article");
						String content=null;
						for (Element element : select3) {
//						System.out.println(element);
							content = element.text();
//						System.out.println(content);
						}
						String title = a.attr("title");
						title = title.replace("|", "").replace("*", "").replace("\"", "").replace("?", "").replace("/", "")
								.replace("\\", "").replace(">", "").replace("<", "").replace(":", "");
						//以标题作为文本的名称,已内容作为文本的内容存在本地磁盘
//						System.out.println(title);
						FileUtils.writeFile("D:/爬取文章/"+title+".txt", content, "utf8");
						System.err.println("爬取成功，已写入文件！");
					}
				}
			
		}
	}

//	@Test
//	public void Data() throws IOException {
//		Connection connect = Jsoup.connect("http://www.cbdio.com/node_2782.htm");
//		Document document = connect.get();
//		Elements select = document.select(".cb-media");
//		String title = null;
//		for (Element element : select) {
//			Elements select2 = element.select("a[href]");
//			for (Element element2 : select2) {
//				//获取所有的网址
//				String attr = element2.attr("href");
//				
//				if(!attr.startsWith("http:")) {
//					attr = "http://www.cbdio.com/"+attr;
//					Connection connect2 = Jsoup.connect(attr);
//					Document document2 = connect2.get();
//					
//					Elements select3 = document2.select("p");
//					for (Element element3 : select3) {
//						String text = element3.text();
//						if(text.length()>50)
//						System.out.println(text);
//					}
//					
//				}
//			}
//		}
//	}


}
