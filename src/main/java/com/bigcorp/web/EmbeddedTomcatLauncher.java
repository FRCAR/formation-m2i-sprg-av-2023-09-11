package com.bigcorp.web;

import java.io.File;

import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcatLauncher {

    public static void main(String[] args) throws Exception {
		String contextPath = "/formation-spring-av";
		String webappDir = new File("src/main/webapp").getAbsolutePath();

		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir("temp");
		tomcat.setPort(8080);
		tomcat.getConnector();

		tomcat.addWebapp(contextPath, webappDir);

		tomcat.start();
		tomcat.getServer().await();
    }
}
