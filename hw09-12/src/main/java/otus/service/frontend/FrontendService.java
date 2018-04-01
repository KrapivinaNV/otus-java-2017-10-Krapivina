package otus.service.frontend;

import otus.messageSystem.Addressee;
import otus.service.msg.CacheParams;

import java.util.concurrent.Future;

public interface FrontendService extends Addressee {

    void init();

    void setCacheParams(CacheParams cacheParams);

    Future<CacheParams> getCacheParams();

    void sendCacheParamsRequest();
}
