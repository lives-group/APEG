package apeg.rts;



public class Main {

    public static void main(String[] args) {
        
        try {
            SimpleTest t = new SimpleTest("apeg/rts/input");
            if(t.s().isSuccess()){
                System.out.println("OK");
                Symbol s = t.getDerivationTreeRoot();
                s.pprint();
            }else{
                System.out.println("NO");
            }
            /*PageStream page = new PageStream("/home/deise/Projeto/APEG/language/code/rts/src/teste1");
            page.next();
            page.next();
            page.next();
            page.next();
            if(page.match("rarrevo")){
              System.out.println("OK.");
              if (page.match("rarrevo")){System.out.println("Mais uma vez: OK."); }
            }else{
              if(page.match("rarrevo")){
                  System.out.println("na 2 tentativa: OK.");
              }
              System.out.println("No.");
            }*/
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}


