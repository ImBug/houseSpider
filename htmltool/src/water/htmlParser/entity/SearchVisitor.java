package water.htmlParser.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.visitors.NodeVisitor;

import water.htmlParser.entity.Selector.TagCond;
import water.tool.util.string.StringUtil;

/**
 * accept 用的是深度遍历
 * @author honghm
 *
 */
public class SearchVisitor extends NodeVisitor{
	
	protected List<TagNode> targets = new ArrayList<TagNode>();

	private String word;
	private Node root;
	private List<TagCond> conds;
	private boolean visitNext = true;
	private boolean recurseSelf = true;
	private Node preNode;
	
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PageParser parser = new PageParser(new File("E:\\RMS\\lianjia.html"), "utf-8");
			HtmlPage page = parser.toPage();
			TagNode tagNode = page.getBody();
			TagNode total= Selector.selectTagNode(tagNode, "span.total");
			System.out.println(total); 
//			TagNode tag = Selector.selectTagNode(tagNode, ".info .list #infoList .row:客厅");
//			System.out.println(tag);
			List<TagNode> list = Selector.select(tagNode, ".info .list #infoList .row");
//			for(TagNode tag:list){
//			}
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void visitTag(Tag tag) {
		System.out.println(tag.getAttribute("value"));
		System.out.println(tag.getAttribute("value")+"_pa=" + ((Tag)tag.getParent()).getAttribute("id"));
		//super.visitTag(tag);
	}

	
	@Override
	public void visitStringNode(Text string) {
		if(StringUtil.isNotEmpty(string.getText())){
			System.out.println("txt parent=" + string.getParent().getClass());
		}
	}

	@Override
	public boolean shouldRecurseChildren() {
		return visitNext;
	}

	@Override
	public boolean shouldRecurseSelf() {
		return recurseSelf;
	}

	public void setConds(List<TagCond> conds) {
		if(conds  != null){
			this.conds = new ArrayList<>(conds.size());
			for(int i=0; i<conds.size();i++){
				this.conds.add(conds.get(conds.size()-1-i));
			}
		}
	}
	
	
}
