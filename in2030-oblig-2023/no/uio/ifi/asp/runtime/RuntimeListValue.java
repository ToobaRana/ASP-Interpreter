package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import no.uio.ifi.asp.parser.AspSyntax;

//Need an arraylist
public class RuntimeListValue extends RuntimeValue {

    ArrayList<Object> listValue = new ArrayList<>();
    ArrayList<ArrayList<Object>> listOfLists;

    public RuntimeListValue(ArrayList<Object> v) {
        listValue = v;
    }

    @Override
    String typeName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'typeName'");
    }

    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {


        if (v instanceof RuntimeIntValue) {
            int repetition = (int) v.getIntValue(" * operand", where);
            ArrayList<Object> resultList = new ArrayList<>();

            for (int i = 0; i < repetition; i++) {
                resultList.addAll(listValue);
            }

            listValue = resultList;

            return new RuntimeListValue(listValue);
        }

        runtimeError("'*' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    // Needs to be changed
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (listValue.size() != 0);
    }

}
