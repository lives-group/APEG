import pair.Sugar;

  public class Test {
      private (String, Integer) p = ("12", 34 + 10);

      public (int, int) divMod (int n , int q) {
        return(n / q, n % q);
      }
      
       public static void main(String[] args) {
        (int, int) divmod = divMod(100, 9);  

        <xsd:schema
         targetNamespace="http://www.w3.org/2001/XMLSchema"
         blockDefault="#all"
         elementFormDefault="qualified"
         version="1.0">
         <{http://www.w3.org/2001/XMLSchema}simpleType name="derivationSet">
           <annotation>
             <documentation>
               A utility type, not for public use
             </documentation> 
             <documentation>
               #all or (possibly empty) subset of {extension, restriction}
             </documentation>
          </annotation>
          <union>
             <simpleType>
               <restriction base="token">
                 <enumeration value="#all"/>
               </restriction>
             </simpleType>
             <simpleType>
               <list itemType="reducedDerivationControl"/>
            </simpleType>
          </union>
         </{http://www.w3.org/2001/XMLSchema}simpleType>  
       </xsd:schema>     
      }


  }