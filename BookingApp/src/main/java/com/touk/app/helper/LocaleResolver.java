package com.touk.app.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class LocaleResolver extends AcceptHeaderLocaleResolver {

    private static final List<Locale> LOCALES;

    static {
        LOCALES = Arrays.asList(
                new Locale("en"),
                new Locale("pl")
        );
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language");
        if (language == null || language.isEmpty())
            return Locale.getDefault();
        return Locale.lookup(Locale.LanguageRange.parse(language), LOCALES);
    }
}
