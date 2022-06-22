package com.apress.prospring5.ch16;

import com.apress.prospring5.ch16.model.Company;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.hibernate.proxy.ProxyConfiguration;
import org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor;

public class CompanyProxy extends Company implements HibernateProxy, ProxyConfiguration {

    private ByteBuddyInterceptor byteBuddyInterceptor;


    @Override
    public Object writeReplace() {
        return null;
    }

    @Override
    public LazyInitializer getHibernateLazyInitializer() {
        return null;
    }

    @Override
    public void $$_hibernate_set_interceptor(Interceptor interceptor) {

    }
}