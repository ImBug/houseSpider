package water.house.entity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;

import water.htmlParser.entity.HtmlPage;
import water.htmlParser.entity.PageParser;
import water.htmlParser.entity.Selector;
import water.htmlParser.util.HtmlUtil;
import water.tool.http.HttpRequest;
import water.tool.json.CustomObjectMapper;
import water.tool.util.bean.BeanUtil;

/**
 * 根据属性映射构建对象
 * @author honghm
 *
 */

@Entity(name="house")
public class HouseInfo extends PropertyInfo{
	
//		PropertyMap.put("", "estate");
//		PropertyMap.put("", "locate");
//		PropertyMap.put("", "buildyear");
	
//	<li><span class="label">户型结构</span>暂无数据</li>
//	<li><span class="label">建筑结构</span>钢混结构</li>

	private String id;
	private String bedroom;
	private String squre;
	private String insqure;
	private String style;
	private String estate;
	private String locate;
	private String buildyear;
	private String ownyear;
	private String floor;
	private String liftrate;
	private String decorate;
	private String headfor;
	private String detailUrl;
	private DealInfo dealInfo;
	
	private List<DealInfo> dealInfos;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParserException 
	 */
	public static void main(String[] args) throws ParserException, IOException {
//		HourseInfo house = new HourseInfo("E:\\RMS\\lianjia.html", "utf-8");
		String url = "https://hz.lianjia.com/ershoufang/c1811063915711/?sug=%E5%BA%AD%E9%99%A2%E6%B7%B1%E6%B7%B1";
		PageParser pageParser = new PageParser(new HttpRequest(url));
		HtmlPage page = pageParser.toPage();
		List<TagNode> houses = Selector.select(page.getBody(), "ul.sellListContent li a.img");
		List<HouseInfo>  list = houses.stream()
				.map(tag ->{
					String detail = ((LinkTag)tag).getLink();
					return new HouseInfo(new HttpRequest(detail));
				})
				.collect(Collectors.toList());
		System.out.println(list.get(0));
	}
	
	public HouseInfo(){super();} 
	
	public HouseInfo(HttpRequest req){
		try {
			dealInfo = new DealInfo();
			this.detailUrl = req.getFullUrl();
			PageParser parser = new PageParser(req);
			HtmlPage page = parser.toPage();
			resolveBodyTag(page.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HouseInfo(String file,String encode){
		try {
			dealInfo = new DealInfo();
			this.detailUrl = file;
			PageParser parser = new PageParser(new File(file), encode);
			HtmlPage page = parser.toPage();
			resolveBodyTag(page.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void resolveBodyTag(BodyTag bodyTag){
		//TODO可以优化查询
		TagNode over = Selector.selectTagNode(bodyTag, "div.overview");
		TagNode info = (TagNode)Selector.selectTagNode(bodyTag, ".title:eq(基本信息)").getParent().getParent();
		TagNode spec = (TagNode)Selector.selectTagNode(bodyTag, ".title:eq(房源特色)").getParent().getParent();
		resolveBase(info);
		resolveOver(over);
		resolveSpec(spec);
	}
	
	private void resolveOver(TagNode tag){
		dealInfo.setPrice(Selector.selectTagNode(tag, ".price span").toPlainTextString());
		dealInfo.setAvgPrice(Selector.selectTagNode(tag, ".unitPrice span.unitPriceValue").toPlainTextString());
		dealInfo.setDownPayment(HtmlUtil.getTextOfTag(Selector.selectTagNode(tag, "span.taxtext")));
		dealInfo.setTax(HtmlUtil.getTextOfTag(Selector.selectTagNode(tag, "span#PanelTax")));
		this.id = HtmlUtil.getTextOfTag(Selector.selectTagNode(tag, "div.houseRecord span.info"));
		this.estate = HtmlUtil.getTextOfTag(Selector.selectTagNode(tag, "div.communityName a.info"));
		this.locate = HtmlUtil.getTextOfTag(Selector.selectTagNode(tag, "div.areaName span.info a"));
		dealInfo.setHourseId(this.id);
	}
	
	private void resolveBase(TagNode tag){
		List<TagNode> list = Selector.select(tag, "div.base .content ul li");
		Map<String, Object> valueMap = new HashMap<>(PropertyMap.size());
		for(TagNode li:list){
			String key = PropertyMap.get(HtmlUtil.getTextOfTag(Selector.selectTagNode(li, "span")));
			if(key != null)valueMap.put(key,HtmlUtil.getTextOfTag(li));
		}
		try {
			BeanUtil.setValue(this, valueMap);
			BeanUtil.setValue(dealInfo, valueMap);
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	private void resolveSpec(TagNode tag){
		List<TagNode> list = Selector.select(tag, "div.introContent^showbasemore> div.baseattribute^clear");
		Map<String, Object> valueMap = new HashMap<>(PropertyMap.size());
		for(TagNode li:list){
			String key = getProperty(HtmlUtil.getTextOfTag(Selector.selectTagNode(li, "div.name")));
			if(key != null)valueMap.put(key,HtmlUtil.getTextOfTag(Selector.selectTagNode(li, "div.content")));
		}
		try {
			BeanUtil.setValue(this, valueMap);
			BeanUtil.setValue(dealInfo, valueMap);
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return CustomObjectMapper.encodeJson(this);
	}
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBedroom() {
		return bedroom;
	}
	public void setBedroom(String bedroom) {
		this.bedroom = bedroom;
	}
	public String getSqure() {
		return squre;
	}
	public void setSqure(String squre) {
		this.squre = squre;
	}
	public String getInsqure() {
		return insqure;
	}
	public void setInsqure(String insqure) {
		this.insqure = insqure;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getEstate() {
		return estate;
	}
	public void setEstate(String estate) {
		this.estate = estate;
	}
	public String getLocate() {
		return locate;
	}
	public void setLocate(String locate) {
		this.locate = locate;
	}
	public String getBuildyear() {
		return buildyear;
	}
	public void setBuildyear(String buildyear) {
		this.buildyear = buildyear;
	}
	public String getOwnyear() {
		return ownyear;
	}
	public void setOwnyear(String ownyear) {
		this.ownyear = ownyear;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getLiftrate() {
		return liftrate;
	}
	public void setLiftrate(String liftrate) {
		this.liftrate = liftrate;
	}
	public String getDecorate() {
		return decorate;
	}
	public void setDecorate(String decorate) {
		this.decorate = decorate;
	}
	public String getHeadfor() {
		return headfor;
	}
	public void setHeadfor(String headfor) {
		this.headfor = headfor;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	@Transient
	public DealInfo getDealInfo() {
		return dealInfo;
	}

	public void setDealInfo(DealInfo dealInfo) {
		this.dealInfo = dealInfo;
	}
	
	@Transient
	public List<DealInfo> getDealInfos() {
		return dealInfos;
	}

	public void setDealInfos(List<DealInfo> dealInfos) {
		this.dealInfos = dealInfos;
	}
	
}
