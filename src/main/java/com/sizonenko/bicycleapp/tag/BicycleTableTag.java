package com.sizonenko.bicycleapp.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class BicycleTableTag extends TagSupport{
    private String head;
    private Integer rows;

    public void setHead(String head){
        this.head = head;
    }

    public void setRows(String rows){
        if(rows.isEmpty()){
            this.rows = 0;
        }
        else{
            this.rows = Integer.parseInt(rows);
        }
    }

    @Override
    public int doStartTag() throws JspTagException{
        try{
            JspWriter out = pageContext.getOut();
            out.write("<table border='1'><colgroup span='2' title='title' />");
            out.write("<thead><tr><th scope='col'>"+head+"</th></tr></thead>");
            out.write("<tbody><tr><td>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspTagException{
        if(rows-- > 1){
            try{
                pageContext.getOut().write("</td></tr><tr><td>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return EVAL_BODY_AGAIN;
        }else{
            return SKIP_BODY;
        }
    }

    @Override
    public  int doEndTag() throws JspTagException{
        try{
            pageContext.getOut().write("</td></tr></tbody></table>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
