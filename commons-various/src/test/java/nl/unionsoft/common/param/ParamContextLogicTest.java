package nl.unionsoft.common.param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ParamContextLogicTest {
    private ParamContextLogicImpl paramContextLogicImpl;

    @Before
    public void before() {
        paramContextLogicImpl = new ParamContextLogicImpl();

    }

    @Test
    public void testParamContextResolver() {
        List<Context> params = paramContextLogicImpl.getContext(ParamDto.class);
        Assert.assertEquals(1, params.size());
        {
            Context context = params.get(0);
            Assert.assertEquals("simpleStringValue", context.getId());
            Assert.assertEquals("A simple String Value", context.getTitle());
        }
    }

    @Test
    public void testParamContextValuesResolver() {

        ParamDto paramDto = new ParamDto();
        paramDto.setSimpleStringValue("trol");
        List<ContextValue> params = paramContextLogicImpl.getContextValues(paramDto);
        Assert.assertEquals(1, params.size());
        {
            ContextValue context = params.get(0);
            Assert.assertEquals("simpleStringValue", context.getId());
            Assert.assertEquals("A simple String Value", context.getTitle());
            Assert.assertEquals("trol", context.getValue());
        }
    }

    @Test
    public void testSetValues() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        ParamDto paramDto = new ParamDto();
        valueMap.put("simpleStringValue", "helloWolrd");
        paramContextLogicImpl.setBeanValues(paramDto, valueMap);
        Assert.assertEquals("helloWolrd", paramDto.getSimpleStringValue());
    }

    @Test
    public void testSetContextValues() {

        ParamDto paramDto = new ParamDto();
        {
            paramDto.setSimpleStringValue("trol");
            List<ContextValue> params = paramContextLogicImpl.getContextValues(paramDto);
            params.get(0).setValue("test");
            paramContextLogicImpl.setContextValues(paramDto, params);
        }
        {
            List<ContextValue> params = paramContextLogicImpl.getContextValues(paramDto);
            Assert.assertEquals(1, params.size());
            {
                ContextValue context = params.get(0);
                Assert.assertEquals("simpleStringValue", context.getId());
                Assert.assertEquals("A simple String Value", context.getTitle());
                Assert.assertEquals("test", context.getValue());
            }
        }
    }

}
