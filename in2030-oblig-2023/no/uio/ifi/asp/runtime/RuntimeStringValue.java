package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {

    String strValue;

    public RuntimeStringValue(String v) {
        strValue = v;
    }

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

    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeStringValue(strValue.repeat((int)v.getIntValue("* operand", where)));
        }
        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public String toString() {
        return strValue;
    }

    // Needs to be changed
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (strValue != "");
    }  
}
