package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {

    ArrayList<RuntimeValue> listValue = new ArrayList<>();

    public RuntimeListValue(ArrayList<RuntimeValue> v) {
        listValue = v;
    }

    @Override
    String typeName() {
        return "List";
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {

        //list * int (int copies of list)
        if (v instanceof RuntimeIntValue) {
            int repetition = (int) v.getIntValue(" * operand", where);
            ArrayList<RuntimeValue> resultList = new ArrayList<>();

            for (int i = 0; i < repetition; i++) {
                resultList.addAll(listValue);
            }

            listValue = resultList;

            return new RuntimeListValue(listValue);
        }

        runtimeError("'*' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        
        //any == none
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!getBoolValue("not operand", where));// need to be fixed
    }

    // Needs to be changed
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (listValue.size() != 0);
    }

}
