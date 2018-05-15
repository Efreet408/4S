package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class MyTag extends SimpleTagSupport {

    private String address;

    public void setAddress(String address) {
        this.address = address;
    }

    StringWriter sw = new StringWriter();

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        getJspBody().invoke(sw);
        out.println("<br>" + sw.toString() + ": ");
        if (address != null) {
            out.println(address);
        } else {
            throw new JspTagException("Error!");
        }


    }
}