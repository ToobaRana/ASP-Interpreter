package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {

    String strValue;

    public RuntimeStringValue(String v) {
        strValue = v;
    }

    @Override
    String typeName() {
        return "String";
    }

    @Override
    public String showInfo() {
        if (strValue.indexOf('\'') >= 0)
            return '\"' + strValue + '\"';
        else
            return "\'" + strValue + "\'";
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {

        //string * int
        if (v instanceof RuntimeIntValue) {
            return new RuntimeStringValue(strValue.repeat((int) v.getIntValue("* operand", where)));
        }
        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {

        // string == string
        if (v instanceof RuntimeStringValue) {
            if (strValue == v.getStringValue("== operand", where)) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }

        //any == none
        else if (v instanceof RuntimeNoneValue) {

        }

        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {

        //string != string
        if (v instanceof RuntimeStringValue) {
            if (strValue != v.getStringValue("!= operand", where)) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }

        //any != none
        else if (v instanceof RuntimeNoneValue) {

        }

        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {

        //string < string
        if (v instanceof RuntimeStringValue) {
            if (strValue.length() < v.getStringValue("< operand", where).length()) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }

        runtimeError("Type error for <.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {

        //string <= string
        if (v instanceof RuntimeStringValue) {
            if (strValue.length() <= v.getStringValue("<= operand", where).length()) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }

        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {

        //string > string
        if (v instanceof RuntimeStringValue) {
            if (strValue.length() > v.getStringValue("> operand", where).length()) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }

        runtimeError("Type error for >.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {

        //string >= string
        if (v instanceof RuntimeStringValue) {
            if (strValue.length() >= v.getStringValue(">= operand", where).length()) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }

        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!getBoolValue("not operand", where));// need to be fixed
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return strValue;
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
