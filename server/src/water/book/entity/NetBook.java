package water.book.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;

import water.htmlParser.entity.HtmlPage;
import water.htmlParser.entity.PageParser;
import water.htmlParser.entity.Selector;
import water.htmlParser.entity.TextVisitor;
import water.tool.http.HttpRequest;
import water.tool.json.CustomObjectMapper;

/**
 * 
 * @author honghm
 *
 */
public abstract class NetBook {
	
	private String encode;
	private String name;
	//private String mainUrl;
	private String indexUrl;
	private TextVisitor visitor = new TextVisitor();
	private List<BookPage> pages;
	private boolean resolve;
	
	public NetBook(String name, String indexUrl) {
		super();
		this.name = name;
		this.indexUrl = indexUrl;
	}
	
	public String toJson(){
		return CustomObjectMapper.encodeJson(this);
	}
	
	public abstract String getDirectoryCondition();
	public abstract String getPageCondition();
	public abstract String[] getFilterList();
	
	public void startResolve(){
		long start = System.currentTimeMillis();
		resolve = false;
		HttpRequest request = new HttpRequest(indexUrl);
		if(encode != null){
			request.setEncoding(encode);
		}
		
		String[] words = getFilterList();
		if(words != null){
			for(String word:words){
				visitor.addFilterWord(word);
			}
		}
		
		try {
			PageParser parser= new PageParser(request);
			HtmlPage page = parser.toPage();
			if(page != null){
				List<TagNode> list = Selector.select(page.getBody(), getDirectoryCondition());
				pages = list.stream().parallel().map(tag->{
					LinkTag a = (LinkTag)tag;
					BookPage pageBook = new BookPage(a.getLinkText(), a.getLink());
					pageBook.resolve();
					return pageBook;
				}).collect(Collectors.toList());
			}
		} catch (ParserException | IOException e) {
			e.printStackTrace();
		}
		resolve = true;
		System.out.println(String.format("解析《%s》完成，耗时: %s 秒", name,(System.currentTimeMillis()-start)/1000));
	}
	
	public void writeToFile(File file){
		if(resolve){
			if(file == null || !file.exists()){
				file = new File(name + ".txt");
			}
			try {
				FileWriter fileWriter=new FileWriter(file);
				for(BookPage page:pages){
					fileWriter.write(page.getTitle() + "\n");
					fileWriter.write(page.getContent());
				}
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<BookPage> getPages() {
		return pages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	class BookPage{
		
		String title;
		String url;
		String content;
		
		public BookPage(String title, String url) {
			super();
			this.title = title;
			this.url = url;
		}

		void resolve(){
			try {
				HttpRequest request = new HttpRequest(url);
				if(encode != null){
					request.setEncoding(encode);
				}
				PageParser parser= new PageParser(request);
				HtmlPage page = parser.toPage();
				if(page != null){
					TagNode contentTag=Selector.selectTagNode(page.getBody(), getPageCondition());
					contentTag.accept(visitor);
					content = visitor.getContent();
				}
			} catch (ParserException | IOException e) {
				e.printStackTrace();
			}
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
		
	}
}
