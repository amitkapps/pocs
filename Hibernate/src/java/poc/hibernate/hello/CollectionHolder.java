package poc.hibernate.hello;

import java.util.Collection;

public class CollectionHolder {

	private String searchId;
	private Collection collection;

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
	
	
	
}
