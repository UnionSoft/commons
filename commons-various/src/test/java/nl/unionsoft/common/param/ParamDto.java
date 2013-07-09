package nl.unionsoft.common.param;

public class ParamDto {

    private String simpleStringValue;

    @Param(title="A simple String Value")
    public String getSimpleStringValue() {
        return simpleStringValue;
    }

    public void setSimpleStringValue(final String simpleStringValue) {
        this.simpleStringValue = simpleStringValue;
    }

}
