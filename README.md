Esta pesadilla es la demostración de que MAVEN puede gestionar un JasperReport con una pantalla inicial en JavaFX y es capaz de guardar el informe como PDF

Entre las muchas consideraciones a tener en cuenta estan las siguientes:
1. hay que excluir el paquete com.lowaie del bloque de jasper reports
2. hay que añadir manualmente el com lowaie
3. Aunque no usemos sql pide estar en module-info.java
4. la versión 6.20.6 de jasperIDE tiene un error, ha que bajar a 6.20.5
5. Los informes se pueden hacer con el IDE de JasperReports 6.20.5 y luego guardarlo en el directorio del proyecto

Un dolor innecesario y unos errores nada descriptivos.
