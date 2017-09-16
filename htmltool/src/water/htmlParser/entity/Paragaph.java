package water.htmlParser.entity;

import org.htmlparser.Tag;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.SimpleNodeIterator;
import org.htmlparser.visitors.NodeVisitor;

import water.tool.util.string.StringUtil;

/**
 * 段落
 * body的第一层孩子节点
 * @author honghm
 *
 */
public class Paragaph{
	
	CompositeTag tag;

	public Paragaph(CompositeTag tag) {
		super();
		this.tag = tag;
	}
	
	public void accept(NodeVisitor visitor){
		if(tag != null)tag.accept(visitor);
	}
	
	public String getTagName(){
		if(tag != null)
			return tag.getTagName();
		return null;
	}
	
	public String getDomClass(){
		if(tag != null)
			return tag.getAttribute("class");
		return null;
	}
	
	public String toHtml(){
		if(tag != null) return tag.toHtml();
		return null;
	}
	
	/**
	 * children 是个平面结构，拉直的
	 * @return
	 */
	public String toText(){
		if(tag != null){
			StringBuffer stringRepresentation = new StringBuffer();
      for(SimpleNodeIterator e= tag.children();e.hasMoreNodes();) {
      	String txt = e.nextNode().toPlainTextString();
      	if(StringUtil.isNotEmpty(txt)){
      		//stringRepresentation.append("***").append(txt);
      	}
      }
      stringRepresentation.delete(0, 3);
      return StringUtil.unEscapeHtml(stringRepresentation.toString());
			//return StringUtil.unEscapeHtml(tag.toPlainTextString());
		}
		return null;
	}
	
	public Tag getTag() {
		return tag;
	}

	public void setTag(CompositeTag tag) {
		this.tag = tag;
	} 
	
	
}
