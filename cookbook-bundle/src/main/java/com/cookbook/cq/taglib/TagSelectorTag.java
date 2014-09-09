package com.cookbook.cq.taglib;

import com.day.cq.tagging.Tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class TagSelectorTag extends SimpleTagSupport {
	
	String name;
    String id;
    String style;
    String className;
    String value;
    Tag defaultTag;
    List<Tag> items;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Tag getDefaultTag() {
		return defaultTag;
	}

	public void setDefaultTag(Tag defaultTag) {
		this.defaultTag = defaultTag;
	}

	public List<Tag> getItems() {
		return items;
	}

	public void setItems(List<Tag> items) {
		this.items = items;
	}

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
        String selectProperties = "";

        if (this.getName() != null) {
            selectProperties = selectProperties + " name=\"" + this.getName() + "\"";
        }
        if (this.getId() != null) {
            selectProperties = selectProperties + " id=\"" + this.getId() + "\"";
        }
        if (this.getStyle() != null) {
            selectProperties = selectProperties + " style=\"" + this.getStyle() + "\"";
        }
        if (this.getClassName() != null) {
            selectProperties = selectProperties + " class=\"" + this.getClassName() + "\"";
        }

        out.println("<select"+selectProperties+">");
        
        out.println("<option value=\""+defaultTag.getTagID()+"\">"+value+"</option>");

        for (Tag tag: items) {
            String key = tag.getTagID();
            Object value = tag.getTitle();
            String selected = "";
            if (this.getValue() != null && this.getValue().equals(key)) {
                selected = " selected=\"selected\"";
            }
            out.println("<option value=\""+key+"\""+selected+">"+value+"</option>");
        }

        out.println("</select>");
	}
}
