package poc.hibernate.caching.oscache;

import com.opensymphony.oscache.base.CacheEntry;
import com.opensymphony.oscache.base.EntryRefreshPolicy;

public class TimedRefreshPolicy implements EntryRefreshPolicy{

	private static final long refreshPeriodMillis = 3 * 1000;  
	public boolean needsRefresh(CacheEntry entry) {
		return ((System.currentTimeMillis() - entry.getCreated()) >= refreshPeriodMillis);
	}
}
