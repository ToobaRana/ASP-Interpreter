package no.uio.ifi.asp.runtime;

import java.util.HashMap;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue{

    HashMap<RuntimeStringValue, RuntimeValue> dictValue = new HashMap<>();

    public RuntimeDictValue(HashMap<RuntimeStringValue, RuntimeValue> v) {
        dictValue = v;
    }

    @Override
    String typeName() {
        return "Dictionary";
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

    //Needs to be changed
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (dictValue.size() != 0); 
    }
    
}
