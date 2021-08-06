import closure.Sugar;

  public class Teste {
  
  private #int (char) p = #int(char c) {return c;};
     public static void Main (String args[]){

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
       </xsd:schema>;

     }

  }
