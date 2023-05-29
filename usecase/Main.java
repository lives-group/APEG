import apeg.VirtualParser;

public class Main {
    public static void main(String[] args){
        VirtualParser vp = new VirtualParser("foo.apeg", "input.txt");
        
        if(vp.TypeCheckGrammar(true)){
            vp.interpretsFile(true);

            if(vp.checkInterpretation())
                vp.generateDOT("bar");
        }
    }
}
