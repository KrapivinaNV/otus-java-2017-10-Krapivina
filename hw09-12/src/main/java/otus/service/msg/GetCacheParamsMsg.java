package otus.service.msg;

import otus.messageSystem.Address;
import otus.messageSystem.Addressee;
import otus.messageSystem.Message;
import otus.service.DBService;
import otus.service.frontend.FrontendService;

import java.sql.SQLException;

public class GetCacheParamsMsg extends Message {

    private CacheParams cacheParams;

    public GetCacheParamsMsg(Address from, Address to) {
        super(from, to);
    }

    public CacheParams getCacheParams() {
        return cacheParams;
    }

    public void setCacheParams(CacheParams cacheParams) {
        this.cacheParams = cacheParams;
    }

    @Override
    public void exec(Addressee addressee) throws SQLException {
        if (addressee instanceof DBService) {
            DBService dbService = (DBService) addressee;

            dbService.getCacheParams();
        }
    }
}
