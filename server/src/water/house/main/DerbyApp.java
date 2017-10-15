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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import water.house.entity.HouseInfo;
import water.house.logic.HouseLogic;
import water.htmlParser.entity.HtmlPage;
import water.htmlParser.entity.PageParser;
import water.htmlParser.entity.Selector;
import water.tool.http.HttpRequest;

@EntityScan(basePackages="water.house.entity")
@EnableJpaRepositories(basePackages = "water.house")
@ComponentScan(basePackages="water.house")
@SpringBootApplication
@RestController
@EnableSwagger2
public class DerbyApp {
	
	@RequestMapping("/hello")
	public String hello(){
		return "hello,rest!";
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context  = SpringApplication.run(DerbyApp.class, args);
		HouseLogic houseDao = (HouseLogic)context.getBean(HouseLogic.class);
//		System.out.println(houseDao.findAllDeal());
//		for(DealInfo deal:houseDao.findAllDeal()){
//			System.out.println(CustomObjectMapper.encodeJson(deal));
//		}
		String url = "https://hz.lianjia.com/ershoufang/c1811063915711/?sug=%E5%BA%AD%E9%99%A2%E6%B7%B1%E6%B7%B1";
		PageParser pageParser;
		try {
			pageParser = new PageParser(new HttpRequest(url));
			HtmlPage page = pageParser.toPage();
			List<TagNode> houses = Selector.select(page.getBody(), "ul.sellListContent li a.img");
			List<HouseInfo> list = houses.stream().map(tag ->{
				String detail = ((LinkTag)tag).getLink();
				HouseInfo houseInfo = new HouseInfo(new HttpRequest(detail));
				houseDao.saveHouse(houseInfo);
				System.out.println("save:" + houseInfo);
				return houseInfo;
			}).collect(Collectors.toList());
		} catch (ParserException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}
