package com.example.vaadindemo;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import com.vaadin.server.VaadinServlet;

@WebServlet(
    asyncSupported=false,
    urlPatterns={"/*","/VAADIN/*"},
    initParams={
        @WebInitParam(name="ui", value="com.example.vaadindemo.VaadinDemoUI")
    })
public class VaadinDemoServlet extends VaadinServlet { }
