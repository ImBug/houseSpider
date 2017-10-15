package water.htmlParser.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

import water.htmlParser.util.HtmlUtil;
import water.tool.http.HttpClient;
import water.tool.http.HttpRequest;

/**
 * 
 * @author honghm
 *
 */
public class PageParser extends Parser {

	private static final long serialVersionUID = 4636928040690863516L;
	private String encode;
	private File htmlFile;
	
	/**
	 * 是否包含某文本
	 * @param txt
	 * @return
	 */
	public boolean isMatch(String txt) {
		Page page = mLexer.getPage();
		if(page instanceof MyPage){
			return ((MyPage)page).getText().indexOf(txt) > - 1;
		}
		//TODO else
		return true;
	}

	public PageParser(HttpRequest request) throws ParserException, IOException{
		super(HttpClient.getDefault().openConnection(request));
	}
	
	public PageParser(File htmlFile,String encode) throws IOException {
		super(new Lexer (new MyPage (getHtmlContent(htmlFile,encode), encode)));
		this.encode = encode;
		this.htmlFile = htmlFile;
	}

	public HtmlPage toPage() throws ParserException{
		for (NodeIterator i = this.elements(); i.hasMoreNodes();) {
			Node node = i.nextNode();
			if(node instanceof TagNode){
				TagNode tagNode = HtmlUtil.findNodeByTag((TagNode)node,"body");
				if(tagNode instanceof BodyTag){
					HtmlPage page = new HtmlPage((BodyTag)tagNode);
					if(this.htmlFile != null)
						page.setFileName(this.htmlFile.getName());
					return page;
				}else{
				}
			}
		}
		return null;
	}
	
	private static byte[] joinBytes(byte[] pr,byte[] ap,int len){
		byte[] bytes = new byte[pr.length + len];
		System.arraycopy(pr, 0, bytes, 0, pr.length);
		System.arraycopy(ap, 0, bytes, pr.length, len);
		return bytes;
	}
	
	public static String getHtmlContent(File file,String encode) throws IOException{
		StringBuffer html = new StringBuffer();
		byte[] buffer = new byte[1024];
		InputStream iStream = new FileInputStream(file);
		try {
			while (true) {
				int len = iStream.read(buffer);
				if(len < 1)break;
				else{
					byte last = buffer[len-1];
					if(last > 32 && last < 127){
						html.append(new String(buffer,0,len,encode));
					}else{
						byte[] ap = new byte[100];
						for(;;){
							 len = iStream.read(ap);
							 if(len < 1){
								 return html.toString();
							 }else{
								 last = ap[len-1];
								 if(last > 32 && last < 127){
									 html.append(new String(joinBytes(buffer,ap,len),encode));
									 break;
								 }else{
									 buffer = joinBytes(buffer,ap,len);
								 }
							 }
						}
					}
				}
			
			}
		} finally {
			iStream.close();
		}
		return html.toString();
	}

	public String getEncode() {
		return encode;
	}
	
	static class MyPage extends Page{

		private static final long serialVersionUID = 1L;
		
		String html;
		
		public MyPage(String text, String charset) {
			super(text, charset);
			this.html = text;
		}

		public String getText(){
			return html;
		}
	}
}
