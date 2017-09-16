package water.htmlParser.entity;

import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

/**
 * 提前table内容
 * @author honghm
 *
 */
public class HtmlTable {
	
	private TableTag tableDom;
	private int colSize;
	private TextTree[][] contents;//不包含th
	private TextTree[] headers;
	
	public HtmlTable(TableTag tableDom) {
		super();
		this.tableDom = tableDom;
		TableRow[] rows = tableDom.getRows();
		if(rows != null && rows.length > 1){
			colSize = rows[1].getColumnCount();
			headers = new TextTree[colSize];
			int h = 0;
			for(TableHeader header:rows[0].getHeaders()){
				headers[h++] = new TextTree(header);
			}
			contents = new TextTree[getRowCount()][colSize];
			for(int i=1; i<rows.length; i++){
				TableRow row = rows[i];
				TableColumn[] col = row.getColumns();
				for(int j=0; j<col.length; j++){
					contents[i-1][j] = new TextTree(col[j]);
				}
			}
		}
	}
	
	public TextTree[] getHeader(){
		return headers;
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	public TextTree[] getRowByIndex(int index){
		if(index < getRowCount()){
			return contents[index];
		}
		return null;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public TextTree[] getColumnByIndex(int index){
		if(index < colSize){
			TextTree[] textTrees = new TextTree[getRowCount()];
			for(int i=0; i<contents.length; i++){
				textTrees[i] = contents[i][index];
			}
			return textTrees;
		}
		return null;
	}
	
	public int getColSize(){
		return colSize;
	}
	
	public int getRowCount(){
		return tableDom.getRowCount() - 1;
	}
	
	
}
