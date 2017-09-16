package water.htmlParser.entity;

import org.htmlparser.visitors.NodeVisitor;

/**
 * 单页内容构造一个对象
 * @author honghm
 *
 */
public abstract class PageEntityVisitor extends NodeVisitor{
		
	protected String fileName;
	
	public abstract PageEntity getPageEntity();

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
