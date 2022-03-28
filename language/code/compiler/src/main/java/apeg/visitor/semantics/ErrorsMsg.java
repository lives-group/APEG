package apeg.visitor.semantics;


import java.util.Hashtable;


/** ErrorMsg: Manages all errors messages.
              This class contains all error codes translations
              and the corresponding methods to convert erros
              in String. This class implements a singleton
              pattern project.
 */
public class ErrorsMsg {
    private Hashtable<Integer,String> msg;
    static ErrorsMsg instance = null;

    private ErrorsMsg(){
       msg = new Hashtable<Integer,String>();
       addErrorCodes();
    }

    public static ErrorsMsg getInstance(){
       if (instance == null) {
            instance = new ErrorsMsg();
       }
       return instance;
    }

    public void addErrorCodes(){
          msg.put(0,"Undefined rule name ");
          msg.put(2,"Unary operator type error");
          msg.put(3,"Base access is not a map");
          msg.put(4,"Undefined map type value");
          msg.put(5,"Map extension left side must be of type Map");
          msg.put(6,"Undefined attribute name");
          msg.put(7,"Empty map literal is not allowed");
          msg.put(8,"Map index type must be String");
          msg.put(9,"Map values types diverge");
          msg.put(10,"Map assocciation type error");

          msg.put(11,"Incompatible arithmetical type operands");
          msg.put(12,"Incompatible logical type operands");
          msg.put(13,"Incompatible relational type operands");
          msg.put(14,"Incompatible concatenation operands");

          msg.put(15,"Bind name  must have type String");
          msg.put(16,"Update type error");
          msg.put(18,"Ilegal language/grammar attribute composition");
          msg.put(19,"Constraint expression must have type Bool");

          msg.put(20,"Incompatible inherited parameter type");
          msg.put(21,"Synthetized paramter must be an attribute");
          msg.put(22,"Incompatible types parameter");
          msg.put(23,"Incompatible non terminal call");

          msg.put(24,"Undefined non terminal");

          msg.put(25,"Non constraint matching for operator");
          msg.put(26,"Unsolvable constraint ");
          msg.put(27,"Meta Operator application error");

          msg.put(28,"List index must be int");
          msg.put(29,"List base acces type error");
          msg.put(30,"List element type error");
          msg.put(31,"Meta rule name must be of type string");
          msg.put(32,"Meta rule types must be of type MetaType");
          msg.put(33,"Meta rule inherited must be of type String");
          msg.put(34,"Meta rule Synthetized must be of type MetaExpr");
          msg.put(35,"Meta rule body must be of type MetaPeg");
          msg.put(36,"Meta update construction error");
          msg.put(37,"Meta attribute error");
          msg.put(38,"Meta Maplit construction error");
          msg.put(39,"Meta construction expected.");
          msg.put(40,"Unsatisfied set restriction");

    }

    public String translate(ErrorEntry e){
         String err = e.info != null ? "Error at (" + e.info.getLine() + "," + e.info.getColumn() + ") " + msg.get(e.errCode) : "Error at";
         if(e.t != null){
             if((e.errCode >= 11) && (e.errCode <= 14) ){
                 err += ": " + e.t[0].getName() + " and " + e.t[1].getName();
             }else if( ((e.errCode >= 15) && (e.errCode <= 17)) || (e.errCode == 19)  ){
                 err += e.t.length == 2 ? " expected " + e.t[0] + " found " + e.t[1] : " found " + e.t[0] ;
             }else if (e.errCode == 40){
                 err += e.name + " must be one of {";
                 if(e.t.length > 0){
                     err += e.t[0];
                     for(int j = 1; j< e.t.length; j++){ err += ", " + e.t[j]; }
                 }
                 err += "} ";
             }
             else {
                if(e.t.length > 0 ){
                   err +=  " " + e.t[0].toString();
                   for(int i = 1; i < e.t.length;i++){
                      err += ", " + e.t[i].toString();
                   }
                }else{
                   err += " " + e.name;
                }
             }
         }else{ err += " " + e.name; }
         return err + " [ error code : "+ e.errCode + " ]";
    }

}
