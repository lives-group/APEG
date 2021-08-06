gen: ./parsers/rats/java/javarats.rats ./parsers/rats/closure/closure.rats ./parsers/rats/closure_xml/closure_xml.rats ./parsers/rats/java_xml/java_xml.rats ./parsers/rats/pair/pair.rats ./parsers/rats/pair_closure/pair_closure.rats ./parsers/rats/pair_closure_xml/pair_closure_xml.rats ./parsers/rats/pair_xml/pair_xml.rats ./parsers/rats/xml/xml.rats
	java -jar ./libs/rats.jar -out ./parsers/rats/java/ ./parsers/rats/java/javarats.rats 
	java -jar ./libs/rats.jar -out ./parsers/rats/closure/ ./parsers/rats/closure/closure.rats
	java -jar ./libs/rats.jar -out ./parsers/rats/closure_xml/ ./parsers/rats/closure_xml/closure_xml.rats
	java -jar ./libs/rats.jar -out ./parsers/rats/java_xml/ ./parsers/rats/java_xml/java_xml.rats
	java -jar ./libs/rats.jar -out ./parsers/rats/pair/ ./parsers/rats/pair/pair.rats
	java -jar ./libs/rats.jar -out ./parsers/rats/pair_closure/ ./parsers/rats/pair_closure/pair_closure.rats
	java -jar ./libs/rats.jar -out ./parsers/rats/pair_closure_xml/ ./parsers/rats/pair_closure_xml/pair_closure_xml.rats
	java -jar ./libs/rats.jar -out ./parsers/rats/pair_xml/ ./parsers/rats/pair_xml/pair_xml.rats
	java -jar ./libs/rats.jar -out ./parsers/rats/xml/ ./parsers/rats/xml/xml.rats

compile: gen
	javac -cp .;./libs/rats.jar ./tester/Main.java

DIR=""

run: compile
	java -cp .;./libs/rats.jar tester.Main $(DIR) 
	python ./tester/grafico.py


clean:
	del /S *.class
	del ./parsers/rats/java/javarats.java