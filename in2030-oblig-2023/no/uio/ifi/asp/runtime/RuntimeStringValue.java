package no.uio.ifi.asp.runtime;

public class RuntimeStringValue extends RuntimeValue {

    String strValue;

    @Override
    String typeName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'typeName'");
    }

    @Override
    public String showInfo() {
        if (strValue.indexOf('\'') >= 0)
            return '\"' + strValue + '\"';
        else
            return "\'" + strValue + "\'";
    }

    @Override
    public String toString() {
        return strValue;
    }

}
