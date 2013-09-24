package nl.unionsoft.common.list.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import nl.unionsoft.common.list.dto.SomeDto;
import nl.unionsoft.common.list.model.ListResponse;
import nl.unionsoft.common.list.model.Sort;

import org.apache.commons.io.output.StringBuilderWriter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockJspWriter;
import org.springframework.mock.web.MockPageContext;

public class PagingTagTest {

    @Test
    public void testDoStartTagOffset0MaxResults10TotalRecords100() throws JspException {
        final PagingTag pagingTag = new PagingTag();
        final ListResponse<SomeDto> listResponse = new ListResponse<SomeDto>();
        listResponse.setOffset(0);
        listResponse.setMaxResults(10);
        listResponse.setTotalRecords(100);
        pagingTag.setListResponse(listResponse);
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        final MockJspWriter mockJspWriter = new MockJspWriter(stringBuilderWriter);
        final MockPageContext mockPageContext = new MockPageContext() {
            @Override
            public JspWriter getOut() {
                return mockJspWriter;
            }
        };
        pagingTag.setPageContext(mockPageContext);
        pagingTag.doStartTag();
        //@formatter:off
        final String expectedResults = 
                "<a href=\"?maxResults=10&firstResult=0\">First</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">Previous</a>||" +
                "<a href=\"?maxResults=10&firstResult=10\">Next</a>||" +
                "<a href=\"?maxResults=10&firstResult=90\">Last</a>||" +
                "<a href=\"?maxResults=0&firstResult=0\">All</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">10</a>||" +
                "<a href=\"?maxResults=20&firstResult=0\">20</a>||" +
                "<a href=\"?maxResults=50&firstResult=0\">50</a>||" +
                "<a href=\"?maxResults=100&firstResult=0\">100</a>";
        //@formatter:on
        Assert.assertEquals(expectedResults, stringBuilderWriter.toString());
    }

    @Test
    public void testDoStartTagOffset0MaxResults10TotalRecords10() throws JspException {
        final PagingTag pagingTag = new PagingTag();
        final ListResponse<SomeDto> listResponse = new ListResponse<SomeDto>();
        listResponse.setOffset(0);
        listResponse.setMaxResults(10);
        listResponse.setTotalRecords(10);
        pagingTag.setListResponse(listResponse);
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        final MockJspWriter mockJspWriter = new MockJspWriter(stringBuilderWriter);
        final MockPageContext mockPageContext = new MockPageContext() {
            @Override
            public JspWriter getOut() {
                return mockJspWriter;
            }
        };
        pagingTag.setPageContext(mockPageContext);
        pagingTag.doStartTag();
        //@formatter:off
        final String expectedResults = 
                "<a href=\"?maxResults=10&firstResult=0\">First</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">Previous</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">Next</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">Last</a>||" +
                "<a href=\"?maxResults=0&firstResult=0\">All</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">10</a>||" +
                "<a href=\"?maxResults=20&firstResult=0\">20</a>||" +
                "<a href=\"?maxResults=50&firstResult=0\">50</a>||" +
                "<a href=\"?maxResults=100&firstResult=0\">100</a>";
        //@formatter:on
        Assert.assertEquals(expectedResults, stringBuilderWriter.toString());
    }

    @Test
    public void testDoStartTagOffset0MaxResults10TotalRecords5() throws JspException {
        final PagingTag pagingTag = new PagingTag();
        final ListResponse<SomeDto> listResponse = new ListResponse<SomeDto>();
        listResponse.setOffset(0);
        listResponse.setMaxResults(10);
        listResponse.setTotalRecords(5);
        pagingTag.setListResponse(listResponse);
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        final MockJspWriter mockJspWriter = new MockJspWriter(stringBuilderWriter);
        final MockPageContext mockPageContext = new MockPageContext() {
            @Override
            public JspWriter getOut() {
                return mockJspWriter;
            }
        };
        pagingTag.setPageContext(mockPageContext);
        pagingTag.doStartTag();
        //@formatter:off
        final String expectedResults = 
                "<a href=\"?maxResults=10&firstResult=0\">First</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">Previous</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">Next</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">Last</a>||" +
                "<a href=\"?maxResults=0&firstResult=0\">All</a>||" +
                "<a href=\"?maxResults=10&firstResult=0\">10</a>||" +
                "<a href=\"?maxResults=20&firstResult=0\">20</a>||" +
                "<a href=\"?maxResults=50&firstResult=0\">50</a>||" +
                "<a href=\"?maxResults=100&firstResult=0\">100</a>";
        //@formatter:on
        Assert.assertEquals(expectedResults, stringBuilderWriter.toString());
    }

    @Test
    public void testDoStartTagOffset25MaxResults100TotalRecords250() throws JspException {
        final PagingTag pagingTag = new PagingTag();
        final ListResponse<SomeDto> listResponse = new ListResponse<SomeDto>();
        listResponse.setOffset(40);
        listResponse.setMaxResults(100);
        listResponse.setTotalRecords(250);
        pagingTag.setListResponse(listResponse);
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        final MockJspWriter mockJspWriter = new MockJspWriter(stringBuilderWriter);
        final MockPageContext mockPageContext = new MockPageContext() {
            @Override
            public JspWriter getOut() {
                return mockJspWriter;
            }
        };
        pagingTag.setPageContext(mockPageContext);
        pagingTag.doStartTag();
        //@formatter:off
        final String expectedResults = 
                "<a href=\"?maxResults=100&firstResult=0\">First</a>||" +
                "<a href=\"?maxResults=100&firstResult=0\">Previous</a>||" +
                "<a href=\"?maxResults=100&firstResult=140\">Next</a>||" +
                "<a href=\"?maxResults=100&firstResult=150\">Last</a>||" +
                "<a href=\"?maxResults=0&firstResult=0\">All</a>||" +
                "<a href=\"?maxResults=10&firstResult=40\">10</a>||" +
                "<a href=\"?maxResults=20&firstResult=40\">20</a>||" +
                "<a href=\"?maxResults=50&firstResult=40\">50</a>||" +
                "<a href=\"?maxResults=100&firstResult=40\">100</a>";
        //@formatter:on
        Assert.assertEquals(expectedResults, stringBuilderWriter.toString());
    }

    @Test
    public void testDoStartTagOffset170MaxResults100TotalRecords250() throws JspException {
        final PagingTag pagingTag = new PagingTag();
        final ListResponse<SomeDto> listResponse = new ListResponse<SomeDto>();
        listResponse.setOffset(170);
        listResponse.setMaxResults(100);
        listResponse.setTotalRecords(250);
        pagingTag.setListResponse(listResponse);
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        final MockJspWriter mockJspWriter = new MockJspWriter(stringBuilderWriter);
        final MockPageContext mockPageContext = new MockPageContext() {
            @Override
            public JspWriter getOut() {
                return mockJspWriter;
            }
        };
        pagingTag.setPageContext(mockPageContext);
        pagingTag.doStartTag();
        //@formatter:off
        final String expectedResults = 
                "<a href=\"?maxResults=100&firstResult=0\">First</a>||" +
                "<a href=\"?maxResults=100&firstResult=70\">Previous</a>||" +
                "<a href=\"?maxResults=100&firstResult=150\">Next</a>||" +
                "<a href=\"?maxResults=100&firstResult=150\">Last</a>||" +
                "<a href=\"?maxResults=0&firstResult=0\">All</a>||" +
                "<a href=\"?maxResults=10&firstResult=170\">10</a>||" +
                "<a href=\"?maxResults=20&firstResult=170\">20</a>||" +
                "<a href=\"?maxResults=50&firstResult=170\">50</a>||" +
                "<a href=\"?maxResults=100&firstResult=150\">100</a>";
        //@formatter:on
        Assert.assertEquals(expectedResults, stringBuilderWriter.toString());
    }

    @Test
    public void testDoStartTagOffset95MaxResults10TotalRecords100() throws JspException {
        final PagingTag pagingTag = new PagingTag();
        final ListResponse<SomeDto> listResponse = new ListResponse<SomeDto>();
        listResponse.setOffset(95);
        listResponse.setMaxResults(10);
        listResponse.setTotalRecords(100);
        pagingTag.setListResponse(listResponse);
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        final MockJspWriter mockJspWriter = new MockJspWriter(stringBuilderWriter);
        final MockPageContext mockPageContext = new MockPageContext() {
            @Override
            public JspWriter getOut() {
                return mockJspWriter;
            }
        };
        pagingTag.setPageContext(mockPageContext);
        pagingTag.doStartTag();
        //@formatter:off
        final String expectedResults = 
                "<a href=\"?maxResults=10&firstResult=0\">First</a>||" +
                "<a href=\"?maxResults=10&firstResult=85\">Previous</a>||" +
                "<a href=\"?maxResults=10&firstResult=90\">Next</a>||" +
                "<a href=\"?maxResults=10&firstResult=90\">Last</a>||" +
                "<a href=\"?maxResults=0&firstResult=0\">All</a>||" +
                "<a href=\"?maxResults=10&firstResult=90\">10</a>||" +
                "<a href=\"?maxResults=20&firstResult=80\">20</a>||" +
                "<a href=\"?maxResults=50&firstResult=50\">50</a>||" +
                "<a href=\"?maxResults=100&firstResult=0\">100</a>";
        //@formatter:on
        Assert.assertEquals(expectedResults, stringBuilderWriter.toString());
    }

    @Test
    public void testDoStartTagOffset100MaxResults10TotalRecords100() throws JspException {
        final PagingTag pagingTag = new PagingTag();
        final ListResponse<SomeDto> listResponse = new ListResponse<SomeDto>();
        listResponse.setOffset(100);
        listResponse.setMaxResults(10);
        listResponse.setTotalRecords(100);
        pagingTag.setListResponse(listResponse);
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        final MockJspWriter mockJspWriter = new MockJspWriter(stringBuilderWriter);
        final MockPageContext mockPageContext = new MockPageContext() {
            @Override
            public JspWriter getOut() {
                return mockJspWriter;
            }
        };
        pagingTag.setPageContext(mockPageContext);
        pagingTag.doStartTag();
        //@formatter:off
        final String expectedResults = 
                "<a href=\"?maxResults=10&firstResult=0\">First</a>||" +
                "<a href=\"?maxResults=10&firstResult=90\">Previous</a>||" +
                "<a href=\"?maxResults=10&firstResult=90\">Next</a>||" +
                "<a href=\"?maxResults=10&firstResult=90\">Last</a>||" +
                "<a href=\"?maxResults=0&firstResult=0\">All</a>||" +
                "<a href=\"?maxResults=10&firstResult=90\">10</a>||" +
                "<a href=\"?maxResults=20&firstResult=80\">20</a>||" +
                "<a href=\"?maxResults=50&firstResult=50\">50</a>||" +
                "<a href=\"?maxResults=100&firstResult=0\">100</a>";
        //@formatter:on
        Assert.assertEquals(expectedResults, stringBuilderWriter.toString());
    }

    @Test
    public void testDoStartTagOffset0MaxResults10TotalRecords100WithSorts() throws JspException {
        final PagingTag pagingTag = new PagingTag();
        final ListResponse<SomeDto> listResponse = new ListResponse<SomeDto>();

        final List<Sort> sorts = new ArrayList<Sort>();
        sorts.add(new Sort("harry|ASC"));
        sorts.add(new Sort("potter|DESC"));
        listResponse.setSorts(sorts);
        listResponse.setOffset(0);
        listResponse.setMaxResults(10);
        listResponse.setTotalRecords(100);
        pagingTag.setListResponse(listResponse);
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        final MockJspWriter mockJspWriter = new MockJspWriter(stringBuilderWriter);
        final MockPageContext mockPageContext = new MockPageContext() {
            @Override
            public JspWriter getOut() {
                return mockJspWriter;
            }
        };
        pagingTag.setPageContext(mockPageContext);
        pagingTag.doStartTag();
        //@formatter:off
        final String expectedResults = 
                "<a href=\"?maxResults=10&firstResult=0&sort=harry|ASC&sort=potter|DESC\">First</a>||" +
                "<a href=\"?maxResults=10&firstResult=0&sort=harry|ASC&sort=potter|DESC\">Previous</a>||" +
                "<a href=\"?maxResults=10&firstResult=10&sort=harry|ASC&sort=potter|DESC\">Next</a>||" +
                "<a href=\"?maxResults=10&firstResult=90&sort=harry|ASC&sort=potter|DESC\">Last</a>||" +
                "<a href=\"?maxResults=0&firstResult=0&sort=harry|ASC&sort=potter|DESC\">All</a>||" +
                "<a href=\"?maxResults=10&firstResult=0&sort=harry|ASC&sort=potter|DESC\">10</a>||" +
                "<a href=\"?maxResults=20&firstResult=0&sort=harry|ASC&sort=potter|DESC\">20</a>||" +
                "<a href=\"?maxResults=50&firstResult=0&sort=harry|ASC&sort=potter|DESC\">50</a>||" +
                "<a href=\"?maxResults=100&firstResult=0&sort=harry|ASC&sort=potter|DESC\">100</a>";
        //@formatter:on
        Assert.assertEquals(expectedResults, stringBuilderWriter.toString());
    }
}
