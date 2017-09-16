package water.htmlParser.fangy;

public class Lianjia {

	public static void main(String[] args) {
//		String url = "https://hz.lianjia.com/ershoufang/c1811063915711/?sug=%E5%BA%AD%E9%99%A2%E6%B7%B1%E6%B7%B1";
//		try {
//			PageParser pageParser = new PageParser(new HttpRequest(url));
//			HtmlPage page = pageParser.toPage();
//			Map<String, String> map = new HashMap<>();
//			map.put("class", "sellListContent");
//			map.put("log-mod", "list");
//			TagNode pode=page.searchFirstTagNodeByAttMap("ul", map);
//			List<TagNode> list = HtmlTagSearcher.findTagNodesByTag(pode, "a");
//			Set<String> set = list.stream().map(tag -> ((LinkTag)tag).getLink()).collect(Collectors.toSet());
//			for(String link:set)System.out.println(link);
//			for(TagNode node:list){
//				//System.out.println(((LinkTag)node).getLink());
//			}
//			//System.out.println(((LinkTag)HtmlUtil.findNodeByTag(pode, "a")).getLink());
//		} catch (ParserException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		showDetail("https://hz.lianjia.com/ershoufang/103101322773.html");
	}
	
	public static void showDetail(String url){
	}
}
