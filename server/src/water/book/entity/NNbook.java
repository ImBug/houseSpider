package water.book.entity;

public class NNbook extends NetBook {

	public NNbook(String name, String indexUrl) {
		super(name, indexUrl);
	}

	public static void main(String[] args) {
		NNbook nNbook = new NNbook("巨人的陨落","http://www.99lib.net/book/8441/index.htm");
		nNbook.startResolve();
		nNbook.getPages().forEach(page->{
			System.out.println(page.title);
		});
		nNbook.writeToFile(null);
	}
	@Override
	public String getDirectoryCondition() {
		return "#dir dd a";
	}

	@Override
	public String getPageCondition() {
		return "div#content";
	}

	@Override
	public String[] getFilterList() {
		return new String[]{"九九藏书","99lib","•",".","*","九_","九-","藏书"};
	}

}
