package water.htmlParser.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.visitors.NodeVisitor;

/**
 * 
 * @author honghm
 *
 */
public class HtmlPage extends PageEntity implements Iterable<Paragaph>{
	
	List<Paragaph> paragaphs = new ArrayList<>();
	private SearchVisitor searcher = new SearchVisitor();
	private BodyTag body;
	
	public HtmlPage() {
		super();
	}
	
	public HtmlPage(BodyTag body){
		if(body != null){
			Visitor visitor = new Visitor(CompositeTag.class);
			body.accept(visitor);
			paragaphs.addAll(visitor.list);
			this.body = body;
		}
	}
	
	public BodyTag getBody() {
		return body;
	}

	public String toHtml(){
		return body.toHtml();
	}
	
	@Override
	public boolean isNotEmpty() {
		return paragaphs.size() > 0;
	}
	
	public void addParagaph(Paragaph para){
		paragaphs.add(para);
	}
	
	public List<Paragaph> getParagaphs() {
		return paragaphs;
	}

	public List<TableTag> getTableDoms(){
		List<TableTag> list = new ArrayList<>();
		for(Paragaph paragaph:paragaphs){
			Visitor visitor = new Visitor(TableTag.class);
			paragaph.accept(visitor);
			if(visitor.tags.size() > 0){
				for(Tag p:visitor.tags){
					list.add((TableTag)p);
				}
			}
		}
		return list;
	}
	
	
	public static class Visitor extends NodeVisitor{
		
		private Class<? extends CompositeTag> cls;
		List<Paragaph> list = new ArrayList<>(5);
		List<Tag> tags = new ArrayList<>(5);
		
		public Visitor(Class<? extends CompositeTag> T){
			super();
			this.cls = T;
		}
		
		@Override
		public void visitTag(Tag tag) {
			if(cls.isAssignableFrom(tag.getClass())){
				tags.add(tag);
				Node parent = tag.getParent();
				if(parent instanceof TagNode){
					TagNode node = (TagNode)parent;
					if(node.getTagName().equals("BODY")){
						list.add(new Paragaph((CompositeTag)tag));
					}
				}
			}
		}
		
		public List<Paragaph> getParas(){
			return list;
		}
	}

	@Override
	public Iterator<Paragaph> iterator() {
		return paragaphs.iterator();
	}
	
	
	public TableTag searchFirstTable(String thead){
		TagNode node = searchFirstTagNode("table", thead);
		if(node != null){
			return (TableTag)node;
		}
		return null;
	}
	
	/**
	 * 寻找包含txt的最小Dom
	 * @param tagName 
	 * @param txt
	 * @return
	 */
	public TagNode searchFirstTagNode(String tagName,String txt){
		searcher.reset();
		searcher.setWord(txt);
		searcher.setTagName(tagName);
		for(Paragaph paragaph:paragaphs){
			searcher.root = paragaph.getTag();
			paragaph.getTag().accept(searcher);
			if(searcher.target != null){
				return searcher.target;
			}
		}
		return null;
	}
	
	public TagNode searchFirstTagNodeByAttMap(String tagName,Map<String, String> attrMap){
		searcher.reset();
		searcher.attrMap = attrMap;
		searcher.setTagName(tagName);
		for(Paragaph paragaph:paragaphs){
			searcher.root = paragaph.getTag();
			paragaph.getTag().accept(searcher);
			if(searcher.target != null){
				return searcher.target;
			}
		}
		return null;
	}
	
	public static class SearchVisitor extends NodeVisitor{
		
		private String tagName;
		private String word;
		private boolean visitNext = true;
		TagNode target;
		private Node root;
		private Map<String, String> attrMap;
		
		public void reset(){
			visitNext = true;
			root = null;
			target = null;
		}
		
		public void setTagName(String tagName){
			if(tagName != null){
				this.tagName = tagName.toUpperCase();
			}
		}
		
		public void setWord(String txt){
			this.word = txt;
		}
		
		
		@Override
		public void visitTag(Tag tag) {
			if(attrMap != null){
				if(tag.getTagName().equals(tagName)){
					long count = attrMap.entrySet().stream()
							.filter(entry ->{return entry.getValue().equals(tag.getAttribute(entry.getKey()));})
							.count();
					if(attrMap.size() == count){
						target = (TagNode)tag;
						visitNext = false;
					}
				}
			}
		}

		@Override
		public void visitStringNode(Text string) {
			if(word != null){
				if(string.getText().indexOf(word) > -1){
					TagNode tnNode = (TagNode)string.getParent();
					while(true){
						if(tnNode.getTagName().equals(tagName)){
							target = tnNode;
							visitNext = false;
							break;
						}else{
							if(tnNode == root){break;}
							tnNode = (TagNode)tnNode.getParent();
						}
					}
				}
			}
		}
		
		@Override
		public boolean shouldRecurseChildren() {
			return visitNext;
		}

		@Override
		public boolean shouldRecurseSelf() {
			return true;
		}
		
	}
}
