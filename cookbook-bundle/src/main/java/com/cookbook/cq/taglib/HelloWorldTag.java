package com.cookbook.cq.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;


public class HelloWorldTag extends SimpleTagSupport {

   @Override
   public void doTag() throws JspException, IOException {
	   JspWriter out = getJspContext().getOut();
        out.write("Hello World!");
   }
}
