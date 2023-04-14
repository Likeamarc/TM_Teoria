# Execució Client

* El nostre codi inclou un arxiu .jar al directori principal. Per executar-lo, cal cridar java -jar av_cont2B.jar en una terminal oberta en el directori on es troba 
av_cont2B.jar. Cal aclarar que el codi esta compilat amb java 1.8.

# Ús OpenAI

* Per la nostra part, hem usat el ChatGPT per a comentar el codi basant-nos en els estàndards de javadoc, així com per buscar la forma de codificar certes parts del codi,
com els hashmaps.

# Preguntes Apartat D

* ¿Qué factor de compresión alcanzáis? ¿Qué relación tiene éste con la entropía calculada teóricamente para el conjunto de símbolos del apartado a)?
* El codi ens diu que l'entropia es de 2.507 bits, i que el número de bits mitjans per a codificar uns 1000 caràcters es de 2.722 bits. Sabent que tenint 8 símbols
sense codificar ens farien falta 3 bits. Obtenim un factor de compresió de 3:2.722 o el que es el mateix, un 9.7% de compresió. Vist que el l'entropia es 2.507, aquest 
factor encara es podria millorar.
