package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {

    double floatValue;

    public RuntimeFloatValue(double v) {
        floatValue = v;
    }

    @Override
    String typeName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'typeName'");
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        return null; // Required by the compiler!
    }



    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue +
                    v.getFloatValue("+ operand", where));
        }

        else if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue +
                    v.getIntValue("+ operand", where));
        }
        runtimeError("Type error for +.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue -
                    v.getFloatValue("- operand", where));
        }

        else if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue -
                    v.getIntValue("- operand", where));
        }
        runtimeError("Type error for -.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue /
                    v.getFloatValue("/ operand", where));
        }

        else if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue /
                    v.getIntValue("/ operand", where));
        }
        runtimeError("Type error for /.", where);
        return null; // Required by the compiler.
    }

    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(floatValue / v.getFloatValue("// operand", where)));
        }

        else if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(Math.floor(floatValue / v.getIntValue("// operand", where)));
        }
        runtimeError("Type error for //.", where);
        return null; // Required by the compiler.
    }

    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue *
                    v.getFloatValue("* operand", where));
        }

        else if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue *
                    v.getIntValue("* operand", where));
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler.
    }

    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue - v.getFloatValue("% operand", where)
                    * Math.floor(floatValue / v.getFloatValue("% operand", where)));
        }

        else if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(floatValue - v.getFloatValue("% operand", where)
                    * Math.floor(floatValue / v.getIntValue("% operand", where)));
        }
        runtimeError("Type error for %.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(-floatValue);
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeFloatValue(floatValue); 
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (int) floatValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (floatValue != 0.0);
    }
}
