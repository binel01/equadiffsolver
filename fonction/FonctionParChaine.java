/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonction;

import fonction.AFonction;
import java.util.Objects;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.sourceforge.jeval.Evaluator;

/**
 *
 * @author KevinJio
 */
public class FonctionParChaine extends AFonction{

    private String expression;
    private String variable;
    private String formatedExpression;
    //private static ScriptEngine engine;
    
    private Evaluator evaluator;

    public FonctionParChaine() {
        super();
        this.variable = "X";
        this.expression = "X";
        formatExpression();
        //if(engine == null)
        //    engine = new ScriptEngineManager().getEngineByName("js");
        if(evaluator == null){
            evaluator = new Evaluator();
        }
    }
    
    public FonctionParChaine(String expression) {
        this();
        this.expression = expression;
    }  
    
    public FonctionParChaine(String expression, String variable) {
        this(expression);
        this.variable = variable;
    }   

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
        formatExpression();
    }
    
    
    
    
    @Override
    public double evaluer(double x) throws Exception {
        String chaine = this.expression.replaceAll(this.variable, ""+x);
        return Double.valueOf(evaluator.evaluate(chaine));
    }
    
    protected void formatExpression(){
        this.formatedExpression = this.expression.replaceAll(this.variable, "#{" + this.variable + "}");
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FonctionParChaine other = (FonctionParChaine) obj;
        if (!Objects.equals(this.expression, other.expression)) {
            return false;
        }
        return true;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
}
