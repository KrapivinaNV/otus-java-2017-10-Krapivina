package otus.service.frontend;

import otus.messageSystem.Address;
import otus.messageSystem.Message;
import otus.messageSystem.MessageSystem;
import otus.messageSystem.MessageSystemContext;
import otus.service.msg.CacheParams;
import otus.service.msg.GetCacheParamsMsg;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FrontendServiceImpl implements FrontendService {

    private final Address address;
    private final MessageSystemContext context;

    private volatile CacheParams cacheParams;

    FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
        init();
    }

    @Override
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void setCacheParams(CacheParams cacheParams) {
        this.cacheParams = cacheParams;
        System.out.println("FrontendService:: cache params received:: " + cacheParams);
    }

    @Override
    public Future<CacheParams> getCacheParams() {
        return Executors.newSingleThreadExecutor().submit(
                () -> {
                    while (cacheParams == null) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return cacheParams;
                }
        );
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void sendCacheParamsRequest() {
        Message getCacheParamsMsg = new GetCacheParamsMsg(getAddress(), context.getDbAddress());
        context.getMessageSystem().sendMessage(getCacheParamsMsg);
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
