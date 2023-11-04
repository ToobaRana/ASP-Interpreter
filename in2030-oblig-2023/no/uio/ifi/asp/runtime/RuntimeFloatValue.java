package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double floatValue;

    public RuntimeFloatValue(double v) {
        floatValue = v;
    }

    @Override
    public String toString() {
        return String.valueOf(floatValue);
    }

    @Override
    protected String typeName() {
        return "float";
    }
    
    public String showInfo() {
        return floatValue + "";
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return Double.toString(floatValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (floatValue > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double) floatValue;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (long) floatValue;
    }

    public RuntimeValue evalNegate(RuntimeValue v, AspSyntax where) {
        return new RuntimeFloatValue(-1 * floatValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (floatValue == 0) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }

    //baser på forelesningsfoiler
    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue + v.getIntValue("+", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue + v.getFloatValue("+", where));
        }
        runtimeError("Type error for '+'" + typeName() + "!", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue - v.getIntValue("- operand", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue - v.getFloatValue("- operand", where));
        }
        runtimeError("Type error for '-'", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue == v.getIntValue("== operand", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue == v.getFloatValue("== operand", where));
        } 
        else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(floatValue == v.getFloatValue("== operand", where));
        }
        runtimeError("Type error for '=='", where);
        return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue != v.getIntValue("!= operand", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue != v.getFloatValue("!= operand", where));
        } 
        else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(floatValue == v.getFloatValue("!= operand", where));
        }
        runtimeError("Type error for '!='", where);
        return null;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(Math.floor(floatValue / v.getIntValue("// operand", where)));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(floatValue / v.getFloatValue("// operand", where)));
        }
        runtimeError("Type error for '//'", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue / v.getIntValue("/ operand", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue / v.getFloatValue("/ operand", where));
        }
        runtimeError("Type error for '/'", where);
        return null;
    }

    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue * v.getIntValue("* operand", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue * v.getFloatValue("* operand", where));
        }
        runtimeError("Type error for '*'", where);
        return null;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(Math.floor(floatValue % v.getIntValue("% operand", where)));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(floatValue % v.getFloatValue("% operand", where)));
        }
        runtimeError("Type error for '%'", where);
        return null;
    }

    // Return positiv verdi (dersom får negativ)
    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        if (floatValue < 0) {
            return new RuntimeFloatValue(-1 * floatValue);
        }
        return new RuntimeFloatValue(floatValue);
    }

    // Return negativ verdi
    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(-1 * floatValue);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue < v.getIntValue("< operand", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue < v.getFloatValue("< operand", where));
        }
        runtimeError("Type error for '<'", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue <= v.getIntValue("<= operand", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue <= v.getFloatValue("<= operand", where));
        }
        runtimeError("Type error for '<='", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue > v.getIntValue("> operand", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue > v.getFloatValue("> operand", where));
        }
        runtimeError("Type error for '>'", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(floatValue >= v.getIntValue(">= operand", where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue >= v.getFloatValue(">= operand", where));
        }
        runtimeError("Type error for '>='", where);
        return null;
    }
}
