package water.house.main;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import water.house.entity.HouseInfo;
import water.house.repository.HouseDao;
import water.htmlParser.entity.HtmlPage;
import water.htmlParser.entity.PageParser;
import water.htmlParser.entity.Selector;
import water.tool.http.HttpRequest;

@EntityScan(basePackages="water.house.entity")
@EnableJpaRepositories(basePackages = "water.house")
@SpringBootApplication
public class DerbyApp {
	public static void main(String[] args) {
		ConfigurableApplicationContext context  = SpringApplication.run(DerbyApp.class, args);
		HouseDao houseDao = (HouseDao)context.getBean(HouseDao.class);
		for(HouseInfo house:houseDao.findAll()){
			System.out.println(house);
		}
		String url = "https://hz.lianjia.com/ershoufang/c1811063915711/?sug=%E5%BA%AD%E9%99%A2%E6%B7%B1%E6%B7%B1";
//		PageParser pageParser;
//		try {
//			pageParser = new PageParser(new HttpRequest(url));
//			HtmlPage page = pageParser.toPage();
//			List<TagNode> houses = Selector.select(page.getBody(), "ul.sellListContent li a.img");
//			List<HouseInfo> list = houses.stream().map(tag ->{
//				String detail = ((LinkTag)tag).getLink();
//				return new HouseInfo(new HttpRequest(detail));
//			}).collect(Collectors.toList());
//			houseDao.save(list);
//		} catch (ParserException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
				
	}
}
