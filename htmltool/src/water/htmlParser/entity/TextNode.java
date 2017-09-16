package water.htmlParser.entity;

import java.beans.Transient;

import org.htmlparser.Node;

/**
 * 
 * @author honghm
 *
 */
public class TextNode {
	
	private String text;
	private Node root;
	private int deep;//the depth to root;
	private String tagName;
	private int preTb;//前置缩进
	
	public TextNode(){
		super();
	}
	
	public TextNode(String text) {
		super();
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Transient
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getPreTb() {
		return preTb;
	}

	public void setPreTb(int preTb) {
		this.preTb = preTb;
	}
	
	
}
