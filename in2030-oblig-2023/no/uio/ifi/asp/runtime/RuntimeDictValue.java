package no.uio.ifi.asp.runtime;

import java.util.HashMap;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue{

    HashMap<Object, Object> dictValue = new HashMap<>();

    public RuntimeDictValue(HashMap<Object, Object> v) {
        dictValue = v;
    }

    @Override
    String typeName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'typeName'");
    }

    //Needs to be changed
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (dictValue.size() != 0); 
    }
    
}
