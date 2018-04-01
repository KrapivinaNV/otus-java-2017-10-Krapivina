package otus.service.db.hibernate;

import otus.messageSystem.Address;
import otus.messageSystem.Addressee;
import otus.messageSystem.Message;
import otus.service.frontend.FrontendService;
import otus.service.msg.CacheParams;

import java.sql.SQLException;

public class GetCacheParamsResultMsg extends Message {

    private final CacheParams cacheParams;

    public GetCacheParamsResultMsg(Address address, Address frontAddress, int hitCount, int missCount, int gcMissCount) {
        super(address, frontAddress);

        this.cacheParams = new CacheParams(hitCount, missCount, gcMissCount);
    }

    @Override
    public void exec(Addressee addressee) throws SQLException {
        if (addressee instanceof FrontendService) {
            FrontendService frontendService = (FrontendService) addressee;

            frontendService.setCacheParams(cacheParams);
        }
    }
}
