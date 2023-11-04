package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspName;
import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.main.*;


public class RuntimeFunc extends RuntimeValue {
    AspFuncDef funcdef;
    RuntimeScope funcScope;
    String name;
    AspName an;
    

    public RuntimeFunc(AspFuncDef def, RuntimeScope scope, String v) {
        funcdef = def;
        funcScope = scope;
        name = v;
    }

    public RuntimeFunc(String v) {
        name = v;
    }

    @Override
    String typeName() {
        return "Func";
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
        checkNumParams(actualParams, funcdef.names.size(), "int", where);
        ArrayList<AspName> param = funcdef.names;
        RuntimeScope newScope = new RuntimeScope(funcScope); 
        
        if (actualParams.size() != funcdef.names.size()) {
            runtimeError("func error ", where);
        }
    
        for (int index = 0; index < actualParams.size(); index++) {
            newScope.assign(param.get(index).name, actualParams.get(index));
        }
    
        //hentet fra forelesningsfoiler
        try {
            funcdef.suite.eval(newScope);
        } 
        catch (RuntimeReturnValue rrv) {
            return rrv.value;
        }
        return new RuntimeNoneValue();
    }

    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
        if (actArgs.size() != nCorrect) {
            RuntimeValue.runtimeError("Wrong number of parameters to " + id + "!", where);
        }
    }
}
