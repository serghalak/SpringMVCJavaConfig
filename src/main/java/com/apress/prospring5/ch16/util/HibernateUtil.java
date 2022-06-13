package com.apress.prospring5.ch16.util;

import com.apress.prospring5.ch16.model.User;
import com.apress.prospring5.ch16.model.User1;
import com.apress.prospring5.ch16.model.converter.BirthdayConverter;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {

        final Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(User1.class);
        configuration.addAttributeConverter(new BirthdayConverter(), true);
        configuration.registerTypeOverride(new JsonStringType());
        configuration.configure("hibernate.cfg.xml");

        return configuration.buildSessionFactory();
    }
}
