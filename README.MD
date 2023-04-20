# Execució 

* El nostre codi inclou un arxiu .jar al directori principal. Per executar-lo, cal cridar java -jar av_cont4A.jar en una terminal oberta en el directori on es troba av_cont4A.jar. Cal aclarar que el codi esta compilat amb java 1.8.
* L'entrega contè el fitxer java amb el codi de la classe, dins d'una carpeta src. El fitxer csv amb els símbols i les probabilitats
i el .jar executable

# Ús OpenAI

* Per la nostra part, hem usat el ChatGPT per a comentar el codi basant-nos en els estàndards de javadoc, així com per buscar la forma de codificar certes parts del codi, com l'us de la funció str1.lastIndexOf(str2);.

# Preguntes Apartat 2

* Utilizando el programa anterior, investigad si es posible (ajustando los valores de Mdes y Ment) comprimir datos aleatorios mediante LZ77 (es decir, que la cadena de datos originales sea más larga que la cadena comprimida).¿Por qué?

Amb cadenes de 1000 bits hem arribat a cadenes compreses d'uns 520 bits amb finestres d'entrada de 8 y deslliçants de 16 bits.
Tambè obtenim el seguent resultat, #Ment = 512 / Mdes = 1024 Input lenth: 10000 Compressed: 5402. 
O fins i tot, #Ment = 4 / Mdes = 8 Input length: 29 Compressed: 41.

Podem observar que per a una cadena petita de bits (randoms), amb aquest algoritme no tenim la capacitat suficient de comprensió que els altres casos. Això és degut a que requereix que es guardi informació que ens permeti recuperar les dades originals, i en el cas de que existeixi baixa redundancia, en els casos en els que tinguem finestres petites no podrem ser capaços de detectar llargs patrons i llavors aquesta informació generada acabarà ocupant més.
Això té sentit jaq ue a la recerca de patrons amb tants pocs bits per a les finestres fa que podem trobar pocs hits per a comprimir. No obstant, es pot comprimir amb èxit vectors de 1 i 0s tot i que no seria una prova realista.
La comprensió de diferentes probes per a cadenes llargues es situa entorn al 40%.

