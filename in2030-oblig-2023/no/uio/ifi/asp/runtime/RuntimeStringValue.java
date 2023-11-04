package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String stringValue;

    public RuntimeStringValue(String v) {
        stringValue = v;
    }

    @Override
    protected String typeName() {
        return "string";
    }

    public String showInfo() {
        return "\'" + stringValue + "\'";
    }

    @Override
    public String toString(){
        return stringValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (stringValue.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(stringValue.length());
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (stringValue.isEmpty()) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeStringValue(stringValue.concat(v.getStringValue(null, where)));
        } 
        runtimeError("Type error for '+'" + typeName() + "!", where);
        return null;
    }

    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeStringValue(stringValue.repeat((int)v.getIntValue("*", where)));
        }
        runtimeError("Type error for '*'", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.equals(v.getStringValue("== operand", where)));
        } 
        else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(stringValue.equals(v.getStringValue("== operand", where)));
        }
        runtimeError("Type error for '=='", where);
        return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(!stringValue.equals(v.getStringValue("!= operand", where)));
        } 
        else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(stringValue.equals(v.getStringValue("!= operand", where)));
        }
        runtimeError("Type error for '!='", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            int compre = stringValue.compareTo(v.getStringValue("< operand", where));

            if (compre < 0) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }
        runtimeError("Type error for '<'", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            int compre = stringValue.compareTo(v.getStringValue("<= operand", where));
            
            if (compre <= 0) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }
        runtimeError("Type error for '<='", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            int compre = stringValue.compareTo(v.getStringValue("> operand", where));

            if (compre > 0) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }
        runtimeError("Type error for '>'", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            int compre = stringValue.compareTo(v.getStringValue(">= operand", where));

            if (compre >= 0) {
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }
        runtimeError("Type error for '>='", where);
        return null;
    }

    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        RuntimeValue v1 = null;
        char x;
        if (v instanceof RuntimeIntValue) {
            int i = (int) (v.getIntValue("[]", where));
            x = stringValue.charAt(i);
            v1 = new RuntimeStringValue(String.valueOf(x));
        }
        return v1;
    }
}
