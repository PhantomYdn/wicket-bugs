package com.weaxme.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Vitaliy Gonchar
 */
public class WebJarsClassLoader extends URLClassLoader {

    private static final Logger LOG = LoggerFactory.getLogger(WebJarsClassLoader.class);

    public WebJarsClassLoader(URL urlToResource, ClassLoader classLoader) {
        super(new URL[0], classLoader);
        if (urlToResource != null) addURL(urlToResource);
        LOG.info("Parent classloader: " + classLoader);
    }

    @Override
    public String toString() {
        return "WebJarsClassLoader";
    }
}
